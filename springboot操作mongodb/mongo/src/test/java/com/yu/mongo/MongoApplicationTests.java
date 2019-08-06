package com.yu.mongo;

import com.yu.mongo.entity.Article;
import com.yu.mongo.test.Example;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoApplicationTests {

    @Autowired
    private Example exm;
    @Test
    public void contextLoads() {
        exm.initArticle();
        exm.delete();
        exm.update();
        Page<Article> articles= exm.selectForPages();
    }

}
