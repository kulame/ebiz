package net.feelapp.ebiz.account;
import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
    private boolean autoLogin;
    private String type;

}
