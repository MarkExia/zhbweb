package com.zhb.zhbweb.mapper;

import com.zhb.zhbweb.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhb
 * @since 2020-02-04
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
