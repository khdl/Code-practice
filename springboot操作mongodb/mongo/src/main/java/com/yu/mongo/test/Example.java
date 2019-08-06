package com.yu.mongo.test;

import com.yu.mongo.entity.Article;
import com.yu.mongo.tool.SpringDataPageable;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @className: Example
 * @author: yu.liu
 * @date: 2019/8/6 10:52
 * @description:
 */
@Service
public class Example {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 数据量大时批量添加性能更好
     */
    public  void initArticle() {
        //循环添加
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setTitle("MongoTemplate的基本使用");
            article.setAuthor("cehsi");
            article.setUrl("ceshi" + i);
            article.setTags(Arrays.asList("java", "mongodb", "spring"));
            article.setVisitCount(0L);
            article.setAddTime(new Date());
            mongoTemplate.save(article);
        }

        //批量添加
        List<Article> articles = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setTitle("MongoTemplate的基本使用");
            article.setAuthor("ceshi");
            article.setUrl("ceshi" + i);
            article.setTags(Arrays.asList("java", "mongodb", "spring"));
            article.setVisitCount(0L);
            article.setAddTime(new Date());
            articles.add(article);
        }
        mongoTemplate.insert(articles, Article.class);
    }

    public void delete(){
        //符合条件的全部删除
        Query query = Query.query(Criteria.where("author").is("cehsi"));
        //mongoTemplate.remove(query,Article.class);
        mongoTemplate.remove(query,"article_info");

        //删除集合，可以传实体或者名称
        mongoTemplate.remove(Article.class);
        mongoTemplate.remove("article_info");

        //删除数据库
        mongoTemplate.getDb().drop();

        //删除一条记录
        query = Query.query(Criteria.where("author").is("cehsi"));
        Article article = mongoTemplate.findAndRemove(query, Article.class);

        //删除符合条件的所有记录
        List<Article> articles = mongoTemplate.findAllAndRemove(query, Article.class);
    }

    public void update(){
        //修改符合条件的额第一条数据
        Query query = Query.query(Criteria.where("author").is("ceshi"));
        Update update = Update.update("title","aaaa").set("money",1200);
        mongoTemplate.updateFirst(query, update, Article.class);

        //修改全部符合条件的
       mongoTemplate.updateMulti(query, update, Article.class);

        //更新符合条件的一条数据，如果没有则创建
       mongoTemplate.upsert(query, update, Article.class);

        //更新一个集合中不存在的字段，用set方法如果更新的key不存在则创建一个新的key(符合条件的全部)
        mongoTemplate.updateMulti(query, update, Article.class);

        //update的rename方法用于修改key的名称
        update = Update.update("title", "MongoTemplate").rename("visitCount", "vc");
        mongoTemplate.updateMulti(query, update, Article.class);

        //update的unset方法用于删除key
        update = Update.update("title", "MongoTemplate").unset("vc");
        mongoTemplate.updateMulti(query, update, Article.class);

        //update的pull方法用于删除tags数组中的java
        update = Update.update("title", "MongoTemplate").pull("tags", "java");
        mongoTemplate.updateMulti(query, update, Article.class);
    }

    public void select(){
        //查询符合条件的全部数据
        Query query = Query.query(Criteria.where("author").is("ceshi"));
        List<Article> articles = mongoTemplate.find(query, Article.class);

        //查询符合条件的第一条数据
        Article article = mongoTemplate.findOne(query, Article.class);

        //查询集合中所有数据
        articles = mongoTemplate.findAll(Article.class);

        //查询符合条件的数量
        long count = mongoTemplate.count(query, Article.class);

        //根据主键Id查询
        article = mongoTemplate.findById(new ObjectId("313131"), Article.class);

        //in查询
        List<String> authors = Arrays.asList("ceshi", "aaa");
        query = Query.query(Criteria.where("author").in(authors));
        articles = mongoTemplate.find(query, Article.class);

        //ne（!=）查询
        query = Query.query(Criteria.where("author").ne("ceshi"));
        articles = mongoTemplate.find(query, Article.class);

        //范围查询，大于 5 小于 10
        query = Query.query(Criteria.where("visitCount").gt(5).lt(10));
        articles = mongoTemplate.find(query, Article.class);

        //模糊查询，author 中包含 a 的数据
        query = Query.query(Criteria.where("author").regex("a"));
        articles = mongoTemplate.find(query, Article.class);

        //数组查询，查询 tags 里数量为 3 的数据
        query = Query.query(Criteria.where("tags").size(3));
        articles = mongoTemplate.find(query, Article.class);

        //or 查询，查询 author=jason 的或者 visitCount=0 的数据
        query = Query.query(Criteria.where("").orOperator(
                Criteria.where("author").is("jason"),
                Criteria.where("visitCount").is(0)));
        articles = mongoTemplate.find(query, Article.class);

        // mongodb 提供的分页方法skip()、limit()，但这样实现会扫描全部的文档
    }


    public  Page<Article> selectForPages(){
        SpringDataPageable pageable = new SpringDataPageable();
        Query query = new Query();
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "visitCount"));
        Sort sort = new Sort(orders);

        pageable.setNumber(2);
        pageable.setSize(5);
        pageable.setSort(sort);

        Long count = mongoTemplate.count(query,Article.class);
        List<Article> articles = mongoTemplate.find(query.with(pageable), Article.class);

        //封装数据
        Page<Article> page = new PageImpl<Article>(articles, pageable, count);

        return page;
    }
}
