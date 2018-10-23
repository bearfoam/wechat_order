package com.ly.repository;

import com.ly.dataproject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.plaf.PanelUI;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void findOneTest(){
       ProductCategory productCategory = repository.findOne(1);
        System.out.println(productCategory.toString());
    }
    @Test
    public void addNewOneTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(2);
        productCategory.setCategoryName("热销榜");
        productCategory.setCategoryType(3);
        repository.save(productCategory);
    }

    @Test
    @Transactional
    //回滚了事务把测试数据删掉
    public void updateOneById(){
        ProductCategory productCategory = repository.findOne(2);
        productCategory.setCategoryType(7);
        repository.save(productCategory);

    }
    @Test
    public void saveTest(){
        ProductCategory productCategory=new ProductCategory("男生最爱",3);
        ProductCategory result=repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list= Arrays.asList(2,3,4);
//在这种查询中实体必须有一个无餐构造方法
        List<ProductCategory>result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void find(){
        ProductCategory productCategory =repository.findProductCategoryByCategoryName("女生最爱");
        System.out.println(productCategory.toString());
    }

}