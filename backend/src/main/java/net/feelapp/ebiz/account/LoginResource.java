package net.feelapp.ebiz.account;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import io.smallrye.mutiny.Uni;
import javax.inject.Inject;

@Path("/api/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    @GET
    @Path("account")
    public Uni<User> getLoginAccount(){
        return User.getUser();
    }
}