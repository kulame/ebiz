package net.feelapp.ebiz.account;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.inject.Inject;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import java.time.LocalDateTime;
import java.util.function.Function;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Path("api/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    @Inject
    io.vertx.mutiny.pgclient.PgPool client;

    private static String create_user_sql = """
                insert into ebiz_users 
                    (email,password_crypt,date_joined) 
                    values ($1,$2,$3) on conflict (email) do nothing returning id 
            """;

    @POST
    public Uni<Resp> add(RegisterReq req){
        System.out.println(req.getEmail());
        var encoder = new BCryptPasswordEncoder();
        var password_crypt = encoder.encode(req.getPassword());
        return client.preparedQuery(create_user_sql)
                .execute(Tuple.of(req.getEmail(),password_crypt, LocalDateTime.now()))
                .map(rs -> {
                    int count = rs.rowCount();
                    Resp resp = new Resp();
                    if(count == 1) {
                        resp.setStatus("ok");
                    }else{
                        resp.setStatus("fail");
                        resp.setMsg("email exists");
                    }
                    return resp;
                });
    }

}
