package net.feelapp.ebiz.account;

import io.quarkus.redis.client.reactive.ReactiveRedisClient;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.vertx.core.json.JsonObject;
import lombok.Data;

import io.smallrye.mutiny.Uni;
import net.feelapp.ebiz.RedisTools;

@Data
class CurrentUserReq{
    private String access;
}

@Path("/api/currentUser")
@Produces(MediaType.APPLICATION_JSON)
public class CurrentUserResource {
    @Inject
    ReactiveRedisClient redisClient;

    @Data
    class Context{
        CurrentUserReq req;
    }
    @POST
    public Uni<Resp> get(CurrentUserReq req){
        Context ctx = new Context();
        return redisClient.get(RedisTools.getUserInfoKey(req.getAccess()))
                .map(response -> {
                    System.out.println(response);
                    Resp resp = new Resp();
                    if(response == null) {
                        resp.setStatus("fail");
                        resp.setMsg("no session");
                    }
                    else{
                        resp.setStatus("ok");
                        resp.setMsg("success");
                        JsonObject jsonObject = new JsonObject(response.toString());
                        resp.setData(jsonObject.getMap());
                    }
                    return resp;
                })
                ;
    }
}
