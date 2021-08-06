package net.feelapp.ebiz.account;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import io.vertx.core.json.JsonObject;

@Data
public class Resp {
    private String status;
    private String msg;
    private Map<String,Object> data;
}
