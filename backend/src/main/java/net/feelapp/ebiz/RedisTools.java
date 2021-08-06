package net.feelapp.ebiz;

public class RedisTools {
    public static String getUserInfoKey(String token){
        return  "access:"+token+":token";
    }
}
