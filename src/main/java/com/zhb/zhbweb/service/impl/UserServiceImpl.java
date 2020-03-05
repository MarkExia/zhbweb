package com.zhb.zhbweb.service.impl;

import com.zhb.zhbweb.entity.User;
import com.zhb.zhbweb.mapper.UserMapper;
import com.zhb.zhbweb.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhb
 * @since 2020-02-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
