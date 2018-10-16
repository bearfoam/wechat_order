package com.ly.controller;

import com.ly.dataproject.ProductCategory;
import com.ly.dataproject.ProductInfo;
import com.ly.dto.OrderDTO;
import com.ly.enums.ResultEnum;
import com.ly.exception.SellException;
import com.ly.form.ProductForm;
import com.ly.service.CategoryService;
import com.ly.service.ProductService;
import com.ly.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author ly
 * @ 2018-10-16
 */

@Controller
@Slf4j
@RequestMapping("seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView productList(@RequestParam(value="page",defaultValue = "1") Integer page, @RequestParam(value="size",defaultValue = "2") Integer size, Map<String,Object> map){
        PageRequest pageRequest = new PageRequest(page-1,size);
        Page<ProductInfo> productInfoPage =  productService.findALL(pageRequest);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }

    /**
     * 商品上架
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/onSale")
    public ModelAndView productOnSale(@RequestParam("productId") String productId,Map<String,Object> map){
        try{
            productService.onSale(productId);

        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.PRODUCT_ONSALE_SUCCESS.getMessage());
        map.put("url","list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/offSale")
    public ModelAndView productOffSale(@RequestParam("productId") String productId,Map<String,Object> map){
        try{
            productService.offSale(productId);

        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.PRODUCT_OFFSALE_SUCCESS.getMessage());
        map.put("url","list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 保存和更新
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView productDetail(@RequestParam(value="productId",required = false) String productId,Map<String,Object> map) {
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList =  categoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }

    @PostMapping("/save")
    public ModelAndView productSave(@Valid ProductForm productForm, BindingResult bindingResult, Map<String, Object>map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","list");
            return new ModelAndView("common/error",map);
        }

        try {
            ProductInfo productInfo = new ProductInfo();
            //如果id不为空才去数据库查
            if(!StringUtils.isEmpty(productForm.getProductId())){
                productInfo= productService.findOne(productForm.getProductId());
            }else{
               productForm.setProductId(KeyUtil.getUniqueKey()); ;
            }
            BeanUtils.copyProperties(productForm,productInfo);
            productService.save(productInfo);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","list");
            return new ModelAndView("common/error",map);
        }

        map.put("msg",ResultEnum.SUCCESS.getMessage());
        map.put("url","list");
        return new ModelAndView("common/success",map);
    }


}
