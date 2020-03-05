package com.zhb.zhbweb.web;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.zhb.zhbweb.entity.Products;
import com.zhb.zhbweb.mapper.ProductsMapper;
import com.zhb.zhbweb.utils.JWTVerify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import javax.print.Doc;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhb
 * @since 2020-03-01
 */
@Controller
@RequestMapping("/products")
@ResponseBody
public class ProductsController {

    private Map<String, Object> map;

        @Autowired
        ProductsMapper productsMapper;

    String filepath = "/data/zhbweb/image/upload/";
    //上传头像
    @PostMapping("/proImg")
    public String uploadImg(@RequestHeader("Authorization") String authorization,@RequestParam("file") MultipartFile file) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            if (!file.isEmpty()) { //文件不是空文件
                try {

                    String name="test";
                    BufferedOutputStream out = new BufferedOutputStream(
                            //C:\IDEA_mode_project\agriculture\src\main

                            new FileOutputStream(new File(filepath + name + ".jpg")));//保存图片到目录下,建立保存文件的输入流
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                    String filename = filepath + name + ".jpg";

                    map = new HashMap<String, Object>();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "上传失败"+e.getMessage());
                       return JSON.toJSONString(map);
                    //return "上传失败," + e.getMessage();  //文件路径错误
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "上传失败"+e.getMessage());
                    return JSON.toJSONString(map);

                    //return "上传失败," + e.getMessage();  //文件IO错误
                }
                map.put("code", 200);
                map.put("msg", "上传图片成功");
                return JSON.toJSONString(map);
                //return new Reponse(true, "上传头像成功", user);//返回用户信息
            } else {
                map.put("code", 500);
                map.put("msg", "上传失败，因为文件是空的");
                return JSON.toJSONString(map);
                //return new Reponse(false, "上传失败，因为文件是空的");
            }
        }
    }


    @PostMapping("/docUpload")
    public String docUpload(@RequestHeader("Authorization") String authorization,@RequestParam("title") String title,
                            @RequestParam("describe") String description,
                            @RequestParam("file") MultipartFile file ) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {

            String fileName = file.getOriginalFilename().toString();//获取文件名
//        //System.out.println( fileName );
//        if(fileName.indexOf('?')!=fileName.length()-1)
//            fileName=title+fileName.substring(fileName.lastIndexOf("."));

            final SimpleDateFormat sDateFormate = new SimpleDateFormat("yyyyMMddHHmmss");  //设置时间格式
            String nowTimeStr = sDateFormate.format(new Date()); // 当前时间
            fileName = fileName.substring(0, fileName.indexOf(".")) + nowTimeStr + fileName.substring(fileName.lastIndexOf("."));

            Products products = new Products();
            map = new HashMap<String, Object>();
            if (!file.isEmpty()) {
                try {
                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(new File(filepath + fileName)));//保存图片到目录下,建立保存文件的输入流
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                    String filename = filepath + fileName;
                    Long fileSize = file.getSize();
                    System.out.println(file.getSize());

                    products.setTitle(title);
                    products.setDescribe(description);
                    products.setProImg(filename);

                    productsMapper.insert(products);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "上传失败" + e.getMessage());
                    return JSON.toJSONString(map);
                    //return "上传失败," + e.getMessage();  //文件路径错误
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "上传失败" + e.getMessage());
                    return JSON.toJSONString(map);

                    //return "上传失败," + e.getMessage();  //文件IO错误
                }
                map.put("code", 200);
                map.put("msg", "上传图片成功");
                return JSON.toJSONString(map);
                //return new Reponse(true, "上传头像成功", user);//返回用户信息
            } else {
                map.put("code", 500);
                map.put("msg", "上传失败，因为文件是空的");
                return JSON.toJSONString(map);
                //return new Reponse(false, "上传失败，因为文件是空的");
            }
        }
    }


    /**
     * 查询所有商品
     * @param authorization
     * @return
     */
    @RequestMapping(value = "get-pros", method = RequestMethod.POST)
    public String getAllPros(@RequestHeader("Authorization") String authorization,@RequestBody HashMap<String,String> requestMap) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {
            //List<Jobs> jobList=jobsMapper.selectList(new EntityWrapper<Jobs>());
            //查找所有用户数count
            int count = productsMapper.selectCount(new EntityWrapper<Products>());
            map = new HashMap<String, Object>();
            //分页查找用户
            EntityWrapper<Products> queryWrapper =  new EntityWrapper<>();
            queryWrapper.orderBy("id",true);
            int currentPage =  Integer.parseInt(requestMap.get("currentPage"));
            int pageSize =  Integer.parseInt(requestMap.get("pageSize"));
            // 查询第currentPage页，每页返回pageSize条

            List<Products> products = productsMapper.selectPage(new Page<>(currentPage, pageSize), queryWrapper);
            map = new HashMap<String, Object>();
            if(products.size() != 0){
                for (Products p : products) {

                    p.setDescribe(HtmlUtils.htmlUnescape(p.getDescribe()));

                }
                map.put("code", 200);
                map.put("msg", "success");
                map.put("products_list", products);
                map.put("total", count);
                map.put("currentPage", currentPage);
                map.put("pageSize", pageSize);
                return JSON.toJSONString(map);
            }
            return JSON.toJSONString(erroToken);
        }
    }

    /**
     * 前台查询所有商品
     * @param
     *
     * @return
     */
    @RequestMapping(value = "get-proslist", method = RequestMethod.POST)
    public String getAllPros(@RequestBody HashMap<String,String> requestMap) {

            //List<Jobs> jobList=jobsMapper.selectList(new EntityWrapper<Jobs>());
            //查找所有用户数count
            int count = productsMapper.selectCount(new EntityWrapper<Products>());
            map = new HashMap<String, Object>();
            //分页查找用户
            EntityWrapper<Products> queryWrapper =  new EntityWrapper<>();
            queryWrapper.orderBy("id",true);
            int currentPage =  Integer.parseInt(requestMap.get("currentPage"));
            int pageSize =  Integer.parseInt(requestMap.get("pageSize"));
            // 查询第currentPage页，每页返回pageSize条


            List<Products> products = productsMapper.selectPage(new Page<>(currentPage, pageSize), queryWrapper);
            map = new HashMap<String, Object>();
            if(products.size() != 0){
                for (Products p : products) {

                    p.setDescribe(HtmlUtils.htmlUnescape(p.getDescribe()));

                }

                map.put("code", 200);
                map.put("msg", "success");
                map.put("products_list", products);
                map.put("total", count);
                map.put("currentPage", currentPage);
                map.put("pageSize", pageSize);
                return JSON.toJSONString(map);
            }

        return null;
    }

    @PostMapping("/docUpdate")
    public String docUpdate(@RequestHeader("Authorization") String authorization,@RequestParam("title") String title,
                            @RequestParam("describe") String description,@RequestParam("id") String id,
                            @RequestParam("file") MultipartFile file ) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {

            String fileName = file.getOriginalFilename().toString();//获取文件名
//        //System.out.println( fileName );
//        if(fileName.indexOf('?')!=fileName.length()-1)
//            fileName=title+fileName.substring(fileName.lastIndexOf("."));

            final SimpleDateFormat sDateFormate = new SimpleDateFormat("yyyyMMddHHmmss");  //设置时间格式
            String nowTimeStr = sDateFormate.format(new Date()); // 当前时间
            fileName = fileName.substring(0, fileName.indexOf(".")) + nowTimeStr + fileName.substring(fileName.lastIndexOf("."));

            Products products = new Products();
            map = new HashMap<String, Object>();
            if (!file.isEmpty()) {
                try {
                    //删除旧照片，不管更新没有更新照片
                    deleteServerFile(productsMapper.selectById(Integer.parseInt(id)).getProImg());

                    BufferedOutputStream out = new BufferedOutputStream(
                            new FileOutputStream(new File(filepath + fileName)));//保存图片到目录下,建立保存文件的输入流
                    out.write(file.getBytes());
                    out.flush();
                    out.close();
                    String filename = filepath + fileName;
                    Long fileSize = file.getSize();
                    System.out.println(file.getSize());

                    products.setId(Integer.parseInt(id));
                    products.setTitle(title);
                    products.setDescribe(description);
                    products.setProImg(filename);


                    productsMapper.updateById(products);


                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "上传失败" + e.getMessage());
                    return JSON.toJSONString(map);
                    //return "上传失败," + e.getMessage();  //文件路径错误
                } catch (IOException e) {
                    e.printStackTrace();
                    map.put("code", 500);
                    map.put("msg", "上传失败" + e.getMessage());
                    return JSON.toJSONString(map);

                    //return "上传失败," + e.getMessage();  //文件IO错误
                }
                map.put("code", 200);
                map.put("msg", "修改成功");
                return JSON.toJSONString(map);
                //return new Reponse(true, "上传头像成功", user);//返回用户信息
            } else {
                map.put("code", 500);
                map.put("msg", "上传失败，因为文件是空的");
                return JSON.toJSONString(map);
                //return new Reponse(false, "上传失败，因为文件是空的");
            }
        }
    }
    @RequestMapping(value = "delete-pro", method = RequestMethod.GET)
    public String deletePro(@RequestHeader("Authorization") String authorization,@RequestParam("id") String id) {
        Map<String, Object> erroToken = JWTVerify.verifyToken(authorization);
        if (erroToken != null) {
            return JSON.toJSONString(erroToken);
        } else {

            boolean b = deleteServerFile(productsMapper.selectById(Integer.parseInt(id)).getProImg());

            int isDelete = productsMapper.deleteById((Integer.parseInt(id)));

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
     * 查询所有商品
     * @param
     * @return
     */
    @RequestMapping(value = "get-pro/{id}", method = RequestMethod.GET)
    public String getOnePro(@PathVariable("id") String id) {
        Products product = productsMapper.selectById(Integer.parseInt(id));


        product.setDescribe(HtmlUtils.htmlUnescape(product.getDescribe()));
        map= new HashMap<String, Object>();

                map.put("code", 200);
                map.put("msg", "success");
                map.put("product_info", product);
                return JSON.toJSONString(map);


    }



    /**
     * 删除服务上的文件
     * @param filePath 路径
     * @param
     * @return
     */
    public static boolean deleteServerFile(String filePath){
        boolean delete_flag = false;
        File file = new File(filePath);
        if (file.exists() && file.isFile() && file.delete())
            delete_flag = true;
        else
            delete_flag = false;
        return delete_flag;
    }


}
