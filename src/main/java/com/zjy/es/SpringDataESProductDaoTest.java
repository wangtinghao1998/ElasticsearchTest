package com.zjy.es;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESProductDaoTest {

    @Autowired
    private ProductDao productDao;

    /**
     * 新增
     */
    @Test
    public void save() throws IOException {
        Product product = new Product();
        product.setId(5L);
        product.setNum("第5句");
        //目标文件路径
        File file = new File("D:\\Filesave\\1.docx");
        //创建文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //创建word文档对象
        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
        //创建执行器
        XWPFWordExtractor wordExtractor = new XWPFWordExtractor(xwpfDocument);
        //读取
        String text = wordExtractor.getText();
        System.out.println("文档内容为:"+text);
        //关闭输入流
        fileInputStream.close();
        product.setText(text);
        productDao.save(product);
    }
    //POSTMAN, GET http://localhost:9200/product/_doc/2

    //修改
//    @Test
//    public void update(){
//        Product product = new Product();
//        product.setId(2L);
//        product.setTitle("小米 2 手机");
//        product.setCategory("手机");
//        product.setPrice(9999.0);
//        product.setImages("http://www.atguigu/xm.jpg");
//        productDao.save(product);
//    }
    //POSTMAN, GET http://localhost:9200/product/_doc/2

    //根据 id 查询
    @Test
    public void findById(){
        Product product = productDao.findById(2L).get();
        System.out.println(product);
    }

    @Test
    public void findAll(){
        Iterable<Product> products = productDao.findAll();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    //删除
    @Test
    public void delete(){
        Product product = new Product();
        product.setId(2L);
        productDao.delete(product);
    }
    //POSTMAN, GET http://localhost:9200/product/_doc/2

    //批量新增
    @Test
//    public void saveAll(){
//        List<Product> productList = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Product product = new Product();
//            product.setId(Long.valueOf(i));
//            product.setTitle("["+i+"]小米手机");
//            product.setCategory("手机");
//            product.setPrice(1999.0 + i);
//            product.setImages("http://www.atguigu/xm.jpg");
//            productList.add(product);
//        }
//        productDao.saveAll(productList);
//    }
    public void saveAll(){



        Product product = new Product();
        product.setId(6L);
        product.setNum("第6句");
        product.setText("需要某某证书");
        productDao.save(product);

        product.setId(5L);
        product.setNum("第7句");
        product.setText("需要某某0证书");
        productDao.save(product);

        product.setId(1L);
        product.setNum("第7句");
        product.setText("张三丰");
        productDao.save(product);

        product.setId(2L);
        product.setNum("张三封");
        product.setText("张三封");
        productDao.save(product);

        product.setId(3L);
        product.setNum("张叁丰");
        product.setText("张叁丰");
        productDao.save(product);

        product.setId(7L);
        product.setNum("第7句");
        product.setText("需要某某文件证书,");
        productDao.save(product);

        product.setId(20L);
        product.setNum("第7句");
        product.setText("阿拉山口除了可能<s>需要某某水水水水水水水水水jjafjald<S>需要gslkfg小姐们然后点击水水水文件证书<B>搜索,");
        productDao.save(product);

        product.setId(8L);
        product.setNum("第8句");
        product.setText("需要某某某某证书.");
        productDao.save(product);

        product.setId(10L);
        product.setNum("第9句");
        product.setText("surprise");
        productDao.save(product);

        product.setId(11L);
        product.setNum("第7句");
        product.setText("surprised");
        productDao.save(product);

        product.setId(12L);
        product.setNum("第8句");
        product.setText("surprising");
        productDao.save(product);

        product.setId(13L);
        product.setNum("第9句");
        product.setText("surprise1");
        productDao.save(product);

        product.setId(14L);
        product.setNum("第9句");
        product.setText("surprize");
        productDao.save(product);

        product.setId(15L);
        product.setNum("第9句");
        product.setText("surprizes");
        productDao.save(product);

        product.setId(16L);
        product.setNum("第9句");
        product.setText("surprisess");
        productDao.save(product);



    }

    //分页查询
    @Test
    public void findByPageable(){
        //设置排序(排序方式，正序还是倒序，排序的 id)
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        int from=0;//当前页，第一页从 0 开始， 1 表示第二页
        int size = 5;//每页显示多少条
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(from, size,sort);
        //分页查询
        Page<Product> productPage = productDao.findAll(pageRequest);
        for (Product Product : productPage.getContent()) {
            System.out.println(Product);
        }
    }
}


