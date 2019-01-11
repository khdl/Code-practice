### springboot快速整合mybatis（去xml化+注解进阶）

### MyBatis 基础注解

MyBatis 主要提供了以下CRUD注解：</br>

- @Select
- @Insert
- @Update
- @Delete

增删改查占据了绝大部分的业务操作，掌握这些基础注解的使用还是很有必要的，例如下面这段代码无需XML即可完成数据查询：

	@Mapper
	public interface UserMapper {
	   @Select("select * from t_user")
	   List<User> list();
	}

映射注解

Mybatis主要提供这些映射注解：

- @Results  用于填写结果集的多个字段的映射关系.
- @Result  用于填写结果集的单个字段的映射关系.
- @ResultMap 根据ID关联XML里面<resultMap>.


		@Results({
		           @Result(property = "userId", column = "USER_ID"),
		           @Result(property = "username", column = "USERNAME"),
		           @Result(property = "password", column = "PASSWORD"),
		           @Result(property = "mobileNum", column = "PHONE_NUM")
		   })
		   @Select("select * from t_user")
		   List<User> list();


为了免除手工编写映射关系的烦恼，这里提供了一个快速生成映射结果集的方法，具体内容如下：


	   /**
	    * 1.用于获取结果集的映射关系
	    */
	   public static String getResultsStr(Class origin) {
	       StringBuilder stringBuilder = new StringBuilder();
	       stringBuilder.append("@Results({\n");
	       for (Field field : origin.getDeclaredFields()) {
	           String property = field.getName();
	           //映射关系：对象属性(驼峰)->数据库字段(下划线)
	           String column = new PropertyNamingStrategy.SnakeCaseStrategy().translate(field.getName()).toUpperCase();
	           stringBuilder.append(String.format("@Result(property = \"%s\", column = \"%s\"),\n", property, column));
	       }
	       stringBuilder.append("})");
	       return stringBuilder.toString();
	   }


### 高级注解

MyBatis-3 主要提供了以下CRUD的高级注解：

- @SelectProvider
- @InsertProvider
- @UpdateProvider
- @DeleteProvider

这些高级注解主要用于动态SQL，这里以@SelectProvider 为例，主要包含两个注解属性，其中type表示工具类，method 表示工具类的某个方法，用于返回具体的SQL。


	@Mapper
	public interface UserMapper {
	   @SelectProvider(type = UserSqlProvider.class, method = "list222")
	   List<User> list2();
	}
</br>
	
	public class UserSqlProvider {
	   public String list222() {
	       return "select * from t_user ;
	   }


### 操作过程

1. 创建工程，引入依赖
	
	     <dependencies>
	       <dependency> <!--添加Web依赖 -->
	           <groupId>org.springframework.boot</groupId>
	           <artifactId>spring-boot-starter-web</artifactId>
	       </dependency>
	       <dependency> <!--添加Mybatis依赖 -->
	           <groupId>org.mybatis.spring.boot</groupId>
	           <artifactId>mybatis-spring-boot-starter</artifactId>
	           <version>1.3.1</version>
	       </dependency>
	       <dependency><!--添加MySQL驱动依赖 -->
	           <groupId>mysql</groupId>
	           <artifactId>mysql-connector-java</artifactId>
	           <scope>runtime</scope>
	       </dependency>
	       <dependency><!--添加Test依赖 -->
	           <groupId>org.springframework.boot</groupId>
	           <artifactId>spring-boot-starter-test</artifactId>
	           <scope>test</scope>
	       </dependency>
	   </dependencies>

2. 添加配置：这里主要是添加数据源，配置驼峰映射和开启SQL日志的控制台打印。在项目的资源目录中，添加 application.yml 配置如下
		
		spring:
		  datasource:
		    url: jdbc:mysql://localhost:3306/test?useSSL=false&&serverTimezone=GMT%2B8
		    username: root
		    password: 123
		    driver-class-name: com.mysql.jdbc.Driver
		
		mybatis:
		  configuration:
		    map-underscore-to-camel-case: true
		
		
		logging:
		  level:
		    com.yu.demo: debug

3. 编写数据成代码：  UserMapper 、 UserSqlProvider、实体类User
4. 添加数据库记录

		  CREATE TABLE `t_user` (
		 `USER_ID` varchar(50) ,
		 `USERNAME` varchar(50) ,
		 `PASSWORD` varchar(50) ,
		   `PHONE_NUM` varchar(15) 
		) ;
		
		INSERT INTO `t_user` VALUES ('1', 'admin', 'admin','15011791234');
		INSERT INTO `t_user` VALUES ('2', 'roots', 'roots','18812342017');

5. 编写控制层代码:UserController 
6. 启动和测试
