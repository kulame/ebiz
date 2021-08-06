package net.feelapp.ebiz.account;
import lombok.Data;
import io.vertx.core.json.JsonObject;
import java.util.HashMap;

@Data
public class User{
    private int id;
    private String email;
    private boolean is_staff;
    private boolean is_active;
    private String nickname;

    public JsonObject toJson(){
       JsonObject json = new JsonObject();
       json.put("id",this.getId())
               .put("email",this.getEmail())
               .put("is_staff",this.is_staff())
               .put("is_active",this.is_active());
       return json;
    }

}