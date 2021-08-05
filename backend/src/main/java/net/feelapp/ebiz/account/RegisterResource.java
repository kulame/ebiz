package net.feelapp.ebiz.account;
import io.smallrye.mutiny.Uni;

import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("api/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    @POST
    public Uni<RegisterResp> doRegister(){
        RegisterResp resp = new RegisterResp();
        resp.setStatus("ok");
        return  Uni.createFrom().item(resp);
    }

}
