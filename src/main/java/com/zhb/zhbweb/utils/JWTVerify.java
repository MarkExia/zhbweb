package com.zhb.zhbweb.utils;

import java.util.HashMap;
import java.util.Map;

public class JWTVerify {
    public static   Map<String,Object>  verifyToken(String authorization){
        Map<String,Object> map  = new HashMap<String,Object>();
        if(authorization != null){
            try {
                JWTToken.verifyToken(authorization);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                map.put("code", 201);
                map.put("msg", e.getMessage());
                return map;
            }
        }else{
            map.put("code", 202);
            map.put("msg","非法访问");

            return map;
        }
        return  null;
    }
}
