package com.ly.controller;

import com.ly.dataproject.ProductCategory;
import com.ly.dataproject.ProductInfo;
import com.ly.service.CategoryService;
import com.ly.service.ProductService;
import com.ly.utils.ResultVOUtil;
import com.ly.vo.ProductInfoVO;
import com.ly.vo.ProductVO;
import com.ly.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 */

@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {
   @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResultVO productList(){
        //1.查询所有的上架商品
       List<ProductInfo> productInfoList =  productService.findUpALL();

       //2.查询需要的类目而不是所有，一次查询，不能分开查
//        List <Integer> categoryTypeList = new ArrayList<Integer>();
//        传统方法
//        for(ProductInfo productInfo:productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法 （java8  lambda）        //3.数据拼装，转化为前端展示的Json格式
        List <Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        List<ProductVO> productVOList = new ArrayList<ProductVO>();
        for(ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<ProductInfoVO>();
            for(ProductInfo productInfo : productInfoList){
                System.out.println(productInfo.toString());
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //spring 提供的方法，将相同属性赋给另外一个对象
                    BeanUtils.copyProperties(productInfo,productInfoVO);

                    productInfoVOList.add(productInfoVO);
                    System.out.println("productInfoVO:"+productInfoVO.toString());
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);

        }


                return ResultVOUtil.success(productVOList);
    }
}
