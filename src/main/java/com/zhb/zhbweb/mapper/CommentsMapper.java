package com.zhb.zhbweb.mapper;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zhb.zhbweb.VO.UserCommentVO;
import com.zhb.zhbweb.entity.Comments;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhb
 * @since 2020-02-28
 */
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

    @Select("SELECT comments.* ,`user`.user_name FROM comments,`user` WHERE comments.user_id=`user`.id")
    List<UserCommentVO> getUserComment(Pagination page);
}
