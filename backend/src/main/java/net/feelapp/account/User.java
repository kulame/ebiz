package net.feelapp.ebiz.account;
import lombok.Data;
import io.smallrye.mutiny.Uni;

@Data
public class User{
    private String email;

    public static Uni<User> getUser(){
        User user = new User();
        user.setEmail("kula@live.com");
        return Uni.createFrom().item(user);
    }

}