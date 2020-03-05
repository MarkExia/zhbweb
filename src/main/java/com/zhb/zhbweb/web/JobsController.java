package com.zhb.zhbweb.web;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhb.zhbweb.entity.Jobs;
import com.zhb.zhbweb.entity.User;
import com.zhb.zhbweb.mapper.JobsMapper;
import com.zhb.zhbweb.mapper.UserMapper;
import com.zhb.zhbweb.utils.JWTVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhb
 * @since 2020-02-26
 */
@Controller
@RequestMapping("/jobs")
@ResponseBody
public class JobsController {
    private Map<String, Object> map;

    @Autowired
    JobsMapper jobsMapper;

    /**
     * 新增职位
     */
    @RequestMapping(value = "add-job", method = RequestMethod.POST)
    public String addJob(@RequestBody Jobs job,@RequestHeader("Authorization") String authorization) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
        map = new HashMap<String, Object>();
        //处理富文本数据
        job.setDesc(HtmlUtils.htmlEscape(job.getDesc()));

        int insert = jobsMapper.insert(job);
        if(insert != 0){
            map.put("msg","新增职位成功");
            map.put("code", 200);
        }
            return JSON.toJSONString(map);
        }
    }

    /**
     * 查询所有职位信息
     * @param authorization
     * @return
     */
    @RequestMapping(value = "get-jobs", method = RequestMethod.POST)
    public String getAllJobs(@RequestHeader("Authorization") String authorization,@RequestBody HashMap<String,String> requestMap) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            //List<Jobs> jobList=jobsMapper.selectList(new EntityWrapper<Jobs>());
            //查找所有用户数count
            int count = jobsMapper.selectCount(new EntityWrapper<Jobs>());
            map = new HashMap<String, Object>();
            //分页查找用户
            EntityWrapper<Jobs> queryWrapper =  new EntityWrapper<>();
            queryWrapper.orderBy("id",true);
            int currentPage =  Integer.parseInt(requestMap.get("currentPage"));
            int pageSize =  Integer.parseInt(requestMap.get("pageSize"));
            // 查询第currentPage页，每页返回pageSize条
            Page<Jobs> page = new Page<>(currentPage,pageSize);
            List<Jobs> jobsByPage= jobsMapper.selectPage(page, queryWrapper);

            map = new HashMap<String, Object>();
            if(jobsByPage.size() != 0){
                for (Jobs jobs : jobsByPage) {
                    jobs.setDesc(HtmlUtils.htmlUnescape(jobs.getDesc()));
                }
                map.put("code", 200);
                map.put("msg", "success");
                map.put("job_list", jobsByPage);
                map.put("total", count);
                return JSON.toJSONString(map);
            }
            return JSON.toJSONString(erroToken);
        }
    }

    /**
     * 修改职位信息
     */
    @RequestMapping(value = "update-job", method = RequestMethod.POST)
    public String updateJob(@RequestBody Jobs job,@RequestHeader("Authorization") String authorization) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            map = new HashMap<String, Object>();
            //处理富文本数据
            job.setDesc(HtmlUtils.htmlEscape(job.getDesc()));

            int update = jobsMapper.updateById(job);
            if(update != 0){
                map.put("msg","修改成功");
                map.put("code", 200);
            }
            return JSON.toJSONString(map);
        }
    }
    @RequestMapping(value = "delete-job", method = RequestMethod.GET)
    public String deleteJob(@RequestHeader("Authorization") String authorization,@RequestParam("id") String id) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            int isDelete = jobsMapper.deleteById((Integer.parseInt(id)));
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

    /**
     * 展示所有职位信息
     *
     * @return
     */
    @RequestMapping(value = "show-jobs", method = RequestMethod.GET)
    public String showAllJobs() {

            List<Jobs> jobList=jobsMapper.selectList(new EntityWrapper<Jobs>());
            map = new HashMap<String, Object>();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            if(jobList.size() != 0) {
                for (Jobs jobs : jobList) {

                    String html = "<div><div>工作地点：&nbsp;<span>"+
                            jobs.getBase()+"</span></div><div>招聘人数：&nbsp;"+"" +
                            jobs.getNeeds()+"&nbsp;<span classtype=\"static\" keyvalue=\"person\">人</span></div></div><div><div>工作年限：&nbsp;<span>"+
                            jobs.getWorking()+"</span></div><div>最低学历：&nbsp;"+
                            jobs.getBackground()+"</span></div><div>发布时间：&nbsp;"+
                            ft.format(jobs.getPublicdate())+"</div></div>";
                            jobs.setDesc( html+"</br>"+ HtmlUtils.htmlUnescape(jobs.getDesc()));

                }
                String html = "<div><div>工作地点：&nbsp;<span>"+
                        "浙江省杭州市"+"</span></div><div>招聘人数：&nbsp;"+"" +
                        "5"+"&nbsp;<span classtype=\"static\" keyvalue=\"person\">人</span></div></div><div><div>工作年限：&nbsp;<span>"+
                        "2"+"</span></div><div>最低学历：&nbsp;"+
                        "本科"+"</span></div><div>发布时间：&nbsp;"+
                        "2020-02-27"+"</div></div>";

                map.put("code", 200);
                map.put("msg", "success");
                map.put("job_list", jobList);
                map.put("total", jobList.size());
                return JSON.toJSONString(map);
            }
           return null;

    }

}
