package com.zjy.es;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.zjy.es.Product;
import org.junit.Test;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESIndexTest {

    //注入 ElasticsearchRestTemplate
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void createIndex(){
        //创建索引，系统初始化会自动创建索引
        System.out.println("创建索引");
    }

    @Test
    public void deleteIndex(){
        //创建索引，系统初始化会自动创建索引

        boolean flg = elasticsearchRestTemplate.indexOps(Product.class).delete();
//        boolean flg = elasticsearchRestTemplate.deleteIndex(Product.class);    //已弃用
        System.out.println("删除索引 = " + flg);
    }

}
