package com.zjy.es;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESSearchTest {

    @Autowired
    private ProductDao productDao;

    @Autowired          //@Autowired是根据类型进行自动装配的，如果需要按名称进行装配，则需要配合@Qualifier使用；
    private RestHighLevelClient restHighLevelClient;

    /**
     * term 查询
     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
     */
    @Test
    public void termQuery(){

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("text", "小米");

        Iterable<Product> products = productDao.search(termQueryBuilder);
        for (Product product : products) {
            System.out.println(product);
        }

    }

    @Test
    //高亮
    public void testSearch() throws IOException {

        List<Product> productList = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest("t1");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("text","需要证书"))
                .highlighter(new HighlightBuilder().field("*")
                        .fragmentSize(100000000)
                        .preTags("<span style ='color : red'>")
                        .postTags("</span>"));


        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        SearchHit[] hits = searchResponse.getHits().getHits();

        for (SearchHit hit : hits) {
            //输出原始文档
            System.out.println(hit.getSourceAsString());
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            Product product = new Product();
            product.setNum(sourceAsMap.get("num").toString());

            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            product.setText(highlightFields.get("text").toString());

//            //建立文档,将高亮输出
//            XWPFDocument xwpfDocument = new XWPFDocument();
//
//            //创建段落
//            XWPFParagraph paragraph = xwpfDocument.createParagraph();
//            XWPFRun paragraphRun = paragraph.createRun();
//            paragraphRun.setText(highlightFields.get("text").toString());
//
//            //创建对象
//            File file = new File("D:\\Filesave\\2.docx");
//            //创建文件输出流
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            //写入磁盘
//            xwpfDocument.write(fileOutputStream);
//
//            fileOutputStream.close();


//            for (int i = 0; i < highlightFields.get("text").fragments().length ; i++) {
//                System.out.print(highlightFields.get("text").fragments()[i].toString());
//            }
            productList.add(product);
        }

        productList.forEach(product -> System.out.print(product));

    }

    /**
     * term 查询加分页
     */
    @Test
    public void termQueryByPage(){
        int currentPage= 0 ;
        int pageSize = 5;
        //设置查询分页
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");

        Iterable<Product> products =
                productDao.search(termQueryBuilder,pageRequest);
        for (Product product : products) {
            System.out.println(product);
        }
    }


}
