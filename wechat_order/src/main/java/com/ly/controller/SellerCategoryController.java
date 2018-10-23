package com.ly.controller;

import com.ly.dataproject.ProductCategory;
import com.ly.dataproject.ProductInfo;
import com.ly.exception.SellException;
import com.ly.form.CategoryForm;
import com.ly.form.CategoryForm;
import com.ly.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.ws.soap.Addressing;
import java.util.List;
import java.util.Map;

/**
 * @author ly
 * @ 2018-10-16
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 类目列表
     *
     *
     * @param map
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView findCategoryList( Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 展示
     * @param categoryId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView categoryDetail(@RequestParam(value = "categoryId", required = false) Integer categoryId, Map<String, Object> map) {
        if (categoryId != null) {
            ProductCategory productCategory = categoryService.findOne(categoryId);
            map.put("productCategory", productCategory);
            return new ModelAndView("category/index", map);
        }
        return new ModelAndView("category/index");
    }

    /**
     * 保存和更新
     * @param categoryForm
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
    public ModelAndView saveCategory(CategoryForm categoryForm, BindingResult bindingResult,Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("/common/error",map);
        }
        ProductCategory productCategory= new ProductCategory();
        try{
            if(categoryForm.getCategoryId() !=null){
                productCategory =  categoryService.findOne(categoryForm.getCategoryId());
            }
            BeanUtils.copyProperties(categoryForm,productCategory);
            //此处有bug修改完类目的类型，应该修改相对应类目下商品的类目类型，，以后完善
            categoryService.save(productCategory);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("/common/error",map);
        }
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }


}
