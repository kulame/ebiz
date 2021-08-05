package net.feelapp.ebiz.account;
import lombok.Data;

@Data
public class RegisterReq {
    private String email;
    private String password;
    private String confirm;
}
