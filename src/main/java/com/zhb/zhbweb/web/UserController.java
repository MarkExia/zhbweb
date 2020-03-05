package com.zhb.zhbweb.web;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhb.zhbweb.entity.Jobs;
import com.zhb.zhbweb.entity.User;
import com.zhb.zhbweb.mapper.UserMapper;
import com.zhb.zhbweb.utils.JWTToken;
import com.zhb.zhbweb.utils.JWTVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhb
 * @since 2020-02-04
 */
@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {
    private Map<String, Object> map;

    @Autowired
    UserMapper userMapper;

    /**
     * 注册
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(@RequestBody HashMap<String,String> requestMap) {
        String nickName = requestMap.get("nickname");
        String pwd = requestMap.get("pwd");
        map = new HashMap<String, Object>();
        User u = new User();
        u.setUserName(nickName);
        u.setUserPwd(pwd);
        u.setType(2);
        int insert = userMapper.insert(u);
        if(insert != 0){
            map.put("msg","注册成功");
            map.put("code", 200);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String doLogin(@RequestBody HashMap<String,String> requestMap) {
        String nickName = requestMap.get("nickname");
        String pwd = requestMap.get("pwd");
        map = new HashMap<String, Object>();
        Map<String, Object> selectMap = new HashMap<String, Object>();
        selectMap.put("user_name", nickName);
        List<User> users = userMapper.selectByMap(selectMap);
        if (users.size() != 0) {
            User user = users.get(0);
            if (pwd.equals(user.getUserPwd())) {
                try {
                    map.put("token", JWTToken.createToken(user));
                    map.put("user_name", user.getUserName());
                    map.put("type",user.getType());
                    map.put("code", 200);
                    map.put("msg", "success");
                    return JSON.toJSONString(map);
                } catch (Exception e) {
                    // TODO Auto-generated catch block

                    e.printStackTrace();
                }
            } else {
                map.put("code", 201);
                map.put("msg", "密码错误");
                return JSON.toJSONString(map);
            }
        } else {
            map.put("code", 500);
            map.put("msg", "用户名不存在");
            return JSON.toJSONString(map);
        }
        return null;
    }

    /**
     * 查询所有用户信息
     * @param authorization
     * @return
     */
    @RequestMapping(value = "get-users", method = RequestMethod.POST)
    public String getAllJobs(@RequestHeader("Authorization") String authorization,@RequestBody HashMap<String,String> requestMap) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            //查找所有用户
            //List<User> userList=userMapper.selectList(new EntityWrapper<User>());

            //查找所有用户数count
            int count = userMapper.selectCount(new EntityWrapper<User>());
            map = new HashMap<String, Object>();
            //分页查找用户
            EntityWrapper<User> queryWrapper =  new EntityWrapper<>();
            queryWrapper.orderBy("id",true);
            int currentPage =  Integer.parseInt(requestMap.get("currentPage"));
            int pageSize =  Integer.parseInt(requestMap.get("pageSize"));
            // 查询第currentPage页，每页返回pageSize条
            Page<User> page = new Page<>(currentPage,pageSize);
            List<User> usersByPage= userMapper.selectPage(page, queryWrapper);


                map.put("code", 200);
                map.put("msg", "success");
                map.put("user_list", usersByPage);
                map.put("total", count);
                return JSON.toJSONString(map);
            }


    }

    /**
     * 修改职位信息
     */
    @RequestMapping(value = "update-user", method = RequestMethod.POST)
    public String updateJob(@RequestBody User user,@RequestHeader("Authorization") String authorization) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            map = new HashMap<String, Object>();

            int update = userMapper.updateById(user);
            if(update != 0){
                map.put("msg","修改成功");
                map.put("code", 200);
            }
            return JSON.toJSONString(map);
        }
    }
    @RequestMapping(value = "delete-user", method = RequestMethod.GET)
    public String deleteJob(@RequestHeader("Authorization") String authorization,@RequestParam("id") String id) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            int isDelete = userMapper.deleteById((Integer.parseInt(id)));
            map = new HashMap<String, Object>();
            if (isDelete != 0) {
                map.put("code", 200);
                map.put("msg", "删除成功");
                return JSON.toJSONString(map);
            } else {
                map.put("code", 500);
                map.put("msg", "删除失败");
                return JSON.toJSONString(map);
            }
        }
    }


}
