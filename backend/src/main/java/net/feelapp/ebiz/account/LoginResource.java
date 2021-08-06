package net.feelapp.ebiz.account;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import javax.inject.Inject;

import io.vertx.core.json.JsonObject;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Data;
import net.feelapp.ebiz.RedisTools;
import org.jboss.resteasy.spring.web.ResponseEntityContainerResponseFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.apache.commons.lang3.RandomStringUtils;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;

import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Data
class LoginReq {
    private String email;
    private String password;
    private boolean autoLogin;
    private String type;

}


@Path("/api/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    @Inject
    ReactiveRedisClient redisClient;

    private Integer session_ttl = 3600*24*365;

    @Data
    class Context{
        User user;
        Resp resp;
    }

    private static String login_sql = """
                select * from ebiz_users where email = $1
            """;

    @POST
    @Path("account")
    public Uni<Resp> doLogin(LoginReq req){
        var encoder = new BCryptPasswordEncoder();
        var email = req.getEmail();
        return client.preparedQuery(login_sql)
                .execute(Tuple.of(req.getEmail()))
                .map(rs -> {
                    int count = rs.rowCount();
                    Resp resp = new Resp();
                    Context ctx = new Context();
                    if (count > 0) {
                        var row = rs.iterator().next();
                        String password_crypt = row.getString("password_crypt");
                        boolean matched = encoder.matches(req.getPassword(), password_crypt);
                        if(matched){
                            User user = new User();
                            user.setId(row.getInteger("id"));
                            user.setEmail(row.getString("email"));
                            user.set_active(row.getBoolean("is_active"));
                            user.set_staff(row.getBoolean("is_staff"));
                            user.setNickname("nickname");
                            System.out.println(user);
                            ctx.user = user;
                            resp.setStatus("ok");
                            resp.setMsg("success");
                        }else{
                            resp.setStatus("fail");
                            resp.setMsg("password not match");
                        }
                    }else{
                        resp.setStatus("fail");
                        resp.setMsg("用户名密码错误");
                    }
                    ctx.resp = resp;
                    return ctx;
                }).flatMap(ctx ->{
                    if (ctx.resp.getStatus() == "ok"){
                        String token = RandomStringUtils.randomAlphabetic(32);
                        var redis_key = RedisTools.getUserInfoKey(token);
                        System.out.println(token);
                        return redisClient
                                .setex(redis_key, session_ttl.toString(), ctx.user.toJson().toString())
                                .map(response -> {
                                    Map<String,Object> map = new HashMap<>();
                                    map.put("access",token);
                                    ctx.resp.setData(map);
                                    return ctx;
                                });
                    }else{
                        return Uni.createFrom().item(ctx);
                    }
                }).map(ctx -> ctx.resp);


    }
}