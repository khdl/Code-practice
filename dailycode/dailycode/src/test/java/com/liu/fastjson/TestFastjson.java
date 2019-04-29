package com.liu.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liu.fastjson.entity.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @className: TestFastjson
 * @author: yu.liu
 * @date: 2019/4/28 14:38
 * @description:
 */
public class TestFastjson {
    private List<Person> list = new ArrayList<>();

    @Before
    public void setup(){
        list.add(new Person(11, "Alan", new Date()));
        list.add(new Person(89, "Eric", new Date()));
    }

    @Test
    public void toJosnString(){
        String jsonString = JSON.toJSONString(list);
        String jsonString1 = JSON.toJSONString(list, SerializerFeature.BeanToArray);
        System.out.println(jsonString);
        System.out.println(jsonString1);
    }

    @Test
    public void toJson(){
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 2; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AGE", 10);
            jsonObject.put("Full Name", "Alan" + i);
            jsonObject.put("Date of Birth", "2018/1/11");
            jsonArray.add(jsonObject);
        }
        String jsonString = jsonArray.toJSONString();
        System.out.println(jsonString);
    }

    @Test
    public void  parseJson(){
        Person person = new Person(65,"Alnm", new Date());
        String jsonObject = JSON.toJSONString(person);
        //Person newPerson = (Person) JSON.parse(jsonObject);
        Person newPerson =  JSON.parseObject(jsonObject, Person.class);
        System.out.println("1");
    }

    @Test
    public void  contextValueFlt(){
        ContextValueFilter valueFilter = new ContextValueFilter() {
            @Override
            public Object process(BeanContext beanContext, Object object, String name, Object value) {
               if(name.equals("Date of Birth")){
                   return "Not to Disclose";
               }
               if(value.equals("Alan")){
                   return  ((String)value).toUpperCase();
               }
               return null;
            }
        };
        String out = JSON.toJSONString(list,valueFilter);
        System.out.println(out);
    }
}
