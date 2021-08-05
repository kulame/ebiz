package net.feelapp.ebiz.account;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.smallrye.mutiny.Uni;
import javax.inject.Inject;
import io.vertx.mutiny.sqlclient.Tuple;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Path("/api/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {
    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    private static String login_sql = """
                select id,password_crypt from ebiz_users where email = $1
            """;

    @POST
    @Path("account")
    public Uni<Resp> doLogin(LoginReq req){
        var encoder = new BCryptPasswordEncoder();
        var email = req.getEmail();
        System.out.println(email);
        System.out.println(req.getPassword());
        return client.preparedQuery(login_sql)
                .execute(Tuple.of(req.getEmail()))
                .map(rs -> {
                    int count = rs.rowCount();
                    Resp resp = new Resp();
                    if (count > 0) {
                        var row = rs.iterator().next();
                        String password_crypt = row.getString("password_crypt");
                        boolean matched = encoder.matches(req.getPassword(), password_crypt);
                        if(matched){
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
                    return resp;
                });

    }
}