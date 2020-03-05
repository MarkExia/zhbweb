package com.zhb.zhbweb.service.impl;

import com.zhb.zhbweb.entity.Products;
import com.zhb.zhbweb.mapper.ProductsMapper;
import com.zhb.zhbweb.service.IProductsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhb
 * @since 2020-03-01
 */
@Service
public class ProductsServiceImpl extends ServiceImpl<ProductsMapper, Products> implements IProductsService {

}
