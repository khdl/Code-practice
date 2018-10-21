# 自定义注解和AOP

### 到底是什么是AOP

所谓AOP也就是面向切面编程，能够让我们在不影响原有业务功能的前提下，横切扩展新的功能。这里面有一个比较显眼的词我们需要注意一下，横切，它是基于横切面对程序进行扩展的。


### AOP相关术语

- **连接点（Joinpoint）**：在程序执行过程中某个特定的点，比如类初始化前、类初始化后，方法调用前，方法调用后；
- **切点（Pointcut）**：所谓切点就是你所切取的类中的方法，比如你横切的这个类中有两个方法，那么这两个方法都是连接点，对这两个方法的定位就称之为切点；
- **增强（Advice）**：增强是织入到连接点上的一段程序，另外它还拥有连接点的相关信息；
- **目标对象（Target）**：增强逻辑的织入目标类，就是我的增强逻辑植入到什么位置；
- **引介（Introduction）**：一种特殊的增强，它可以为类添加一些属性和方法；
- **织入（Weaving）**：织入就是讲增强逻辑添加到目标对象的过程；
- **代理（Proxy）**：一个类被AOP织入增强后，就会产生一个结果类，他是融合了原类和增强逻辑的代理类；
- **切面（Aspect）**：切面由切点和增强组成，他是横切逻辑定义和连接点定义的组成；


### AOP功能实践

引入依赖

首先创建一个Controller类：


	@RestController
	public class LoginController {
	
	    @GetMapping(value = "/userName")
	    public  String getLoginName(String userName,Integer age){
	        return  userName + "---" + age;
	    }
	}


创建切面LogAspect：

	@Aspect
	@Component
	public class LogAspect {
	    /**
	     * 拦截对这个包下的所有访问
	     */
	    @Pointcut("execution(* com.liu.practise.springaop.controller.*.*(..))")
	    public  void loginLog(){
	
	    }
	
	    /**
	     * 前置通知
	     * @param joinPoint
	     */
	    @Before("loginLog()")
	    public void  loginBefore(JoinPoint joinPoint){
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	
	        System.out.println("请求路劲:"+ request.getRequestURL());
	        System.out.println("请求方式:"+ request.getMethod());
	        System.out.println("请求包含工程名的当前路劲:"+ request.getRequestURI());
	        System.out.println("方法名:"+ joinPoint.getSignature().getName());
	        System.out.println("类路劲:"+ joinPoint.getSignature().getDeclaringTypeName());
	        System.out.println("参数:"+ Arrays.toString(joinPoint.getArgs()));
	    }
	
	    /**
	     *   方法退出时执行
	     */
	    @AfterReturning(returning = "object" ,pointcut = "loginLog()")
	    public  void doAfterReturning(Object object){
	        System.out.println("方法的返回值:"+object);
	    }
	
	    /**
	     * 方法发生异常执行
	     * @param e
	     * @param joinPoint
	     */
	    @AfterThrowing(throwing = "e",pointcut = "loginLog()")
	    public void throwExecute(Exception e,JoinPoint joinPoint){
	        System.out.println("方法执行异常:"+ e.getMessage());
	    }
	
	    /**
	     * 后置通知
	     */
	    @After("loginLog()")
	    public  void afterInform(){
	        System.out.println("后置通知结束");
	    }
	
	    /**
	     * 环绕通知
	     * @param proceedingJoinPoint
	     * @return
	     */
	    @Around("loginLog()")
	    public Object surroundingInform(ProceedingJoinPoint proceedingJoinPoint){
	        System.out.println("环绕通知开始：");
	
	        try {
	            Object object = proceedingJoinPoint.proceed();
	            System.out.println("方法环绕proceed,结果是:" + object);
	            return  object;
	        } catch (Throwable throwable) {
	            throwable.printStackTrace();
	            return null;
	        }
	    }
	}



注解概述：

 - @Apsect：将当前类标识为一个切面；
 - @Pointcut：定义切点，这里使用的是条件表达式；
 - @Before：前置增强，就是在目标方法执行之前执行；
 - @AfterReturning：后置增强，方法退出时执行；
 - @AfterThrowing：有异常时该方法执行；
 - @After：最终增强，无论什么情况都会执行；
 - @Around：环绕增强



		环绕通知开始：
		请求路劲:http://localhost:8080/userName
		请求方式:GET
		方法名:getLoginName
		类路劲:com.liu.practise.springaop.controller.LoginController
		参数:[张三, 12]
		方法环绕proceed,结果是:张三---12
		后置通知结束
		方法的返回值:张三---12


### 用注解验证是否登录


创建一个注解：

	@Target({ElementType.METHOD,ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface Auth {
	    boolean login() default  false;
	}


创建一个AOP切面：

	@Aspect
	@Component
	public class loginAspect {
	
	    @Before("@annotation(auth)")
	    public void loginBefore( Auth auth){
	        System.out.println("验证登录开始");
	    }
	
	    @Around("@annotation(auth)")
	    public Object validate(ProceedingJoinPoint joinPoint, Auth auth) throws Throwable {
	        //获取class对象
	        Class<? extends Object> targetClass = joinPoint.getTarget().getClass();
	        //获得方法名
	        String methodName = joinPoint.getSignature().getName();
	
	        Method trgetMethod = null;
	        Method[] methods = targetClass.getDeclaredMethods();
	        for (Method method : methods) {
	            if (method.getName().equals(methodName)) {
	                trgetMethod = method;
	                break;
	            }
	        }
	        //获得方法上的注解
	        auth = trgetMethod.getAnnotation(Auth.class);
	        if (auth.login() ){
	            return  joinPoint.proceed();
	        }else{
	            return "用户没有登录";
	        }
	
	    }
	}


controller 里的方法：

    @Auth(login = true)
    @GetMapping(value = "/login")
    public String getUser() {

        return "用户已经登录";
    }


</br>

	环绕通知开始：
	请求路劲:http://localhost:8080/login
	请求方式:GET
	方法名:getUser
	类路劲:com.liu.practise.springaop.controller.LoginController
	参数:[]
	验证登录开始
	方法环绕proceed,结果是:用户已经登录
	后置通知结束
	方法的返回值:用户已经登录