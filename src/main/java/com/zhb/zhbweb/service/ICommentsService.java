package com.zhb.zhbweb.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhb.zhbweb.VO.UserCommentVO;
import com.zhb.zhbweb.entity.Comments;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhb
 * @since 2020-02-28
 */
public interface ICommentsService extends IService<Comments> {
    //Page<UserCommentVO> getUserComment(Page<UserCommentVO> page);
}
