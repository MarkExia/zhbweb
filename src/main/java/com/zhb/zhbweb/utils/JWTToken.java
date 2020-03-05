package com.zhb.zhbweb.utils;

import com.zhb.zhbweb.entity.User;
import io.jsonwebtoken.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JWTToken {
    public static String SECRET ="WangMengNan";
    private static Claims body=null;

    /**
     * 生成token
     */
    public static String createToken(User user) throws Exception{

        //签发时间
        Date iaDate = new Date();
        //过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE, 120);
        Date expiresDate = nowTime.getTime();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("alg", "HS256");
        map.put("typ","JWT");
        map.put("nickName", user.getUserName());
        map.put("id", user.getId());

        String token  = Jwts.builder()
                .addClaims(map)
                .setExpiration(expiresDate)
                .setIssuedAt(iaDate).signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();

                //.sign(Algorithm.HMAC256(SECRET));

        System.out.println("签发时间："+iaDate);
        System.out.println("过期时间："+ expiresDate);

        return token;

    }



    /**
     * 解密token
     * @param token
     * @return
     * @throws Exception
     */


    public static void verifyToken(String token) {
        try {

            Jws<Claims> parseClaimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);//compactJws为jwt字符串
             body = parseClaimsJws.getBody();//得到body后我们可以从body中获取我们需要的信息
            //比如 获取主题,当然，这是我们在生成jwt字符串的时候就已经存进来的
            //String subject = body.getSubject();
            //OK, we can trust this JWT
            Object nickName = body.get("nickName");
            System.out.println("有效");

        } catch (SignatureException | MalformedJwtException e) {
            // TODO: handle exception
            // don't trust the JWT!
            // jwt 解析错误
            throw new RuntimeException("登陆凭证解析错误");
        } catch (ExpiredJwtException e) {
            // TODO: handle exception
            // jwt 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
            throw new RuntimeException("登陆凭证已过期，请重新登录");
        }

    }


    /**
     * 取token中的数据
     * @param
     */
    public static  String verifyTokenToGetData(String key) {

        //Map<String,Object> map = new HashMap<String,Object>();
        if(body==null){
            return null;
        }
        Object val = body.get(key);
        return val.toString();


    }




}
