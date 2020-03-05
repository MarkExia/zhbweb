package com.zhb.zhbweb.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.zhb.zhbweb.VO.UserCommentVO;
import com.zhb.zhbweb.entity.Comments;
import com.zhb.zhbweb.mapper.CommentsMapper;
import com.zhb.zhbweb.service.ICommentsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhb
 * @since 2020-02-28
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements ICommentsService {

//    @Override
//    public Page<UserCommentVO> getUserComment(Page<UserCommentVO> page) {
//        return page.setRecords(this.baseMapper.getUserComment(page));
//    }
}
