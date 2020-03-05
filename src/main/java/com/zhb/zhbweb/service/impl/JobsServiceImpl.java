package com.zhb.zhbweb.service.impl;

import com.zhb.zhbweb.entity.Jobs;
import com.zhb.zhbweb.mapper.JobsMapper;
import com.zhb.zhbweb.service.IJobsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhb
 * @since 2020-02-26
 */
@Service
public class JobsServiceImpl extends ServiceImpl<JobsMapper, Jobs> implements IJobsService {

}
