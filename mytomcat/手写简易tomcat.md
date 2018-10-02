# 手写简易 tomcat

Tomcat是非常流行的Web Server，它还是一个满足Servlet规范的容器。

从感性上来说，我们一般需要把Web应用打成WAR包部署到Tomcat中，在我们的Web应用中，我们要指明URL被哪个类的哪个方法所处理（不论是原始的Servlet开发，还是现在流行的Spring MVC都必须指明）。

于我们的Web应用是运行在Tomcat中，那么显然，请求必定是先到达Tomcat的。Tomcat对于请求实际上会进行下面的处理：

1. 提供Socket服务</br>
   Tomcat的启动，必然是Socket服务，只不过它支持HTTP协议而已！
2. 进行请求的分发</br>
   要知道一个Tomcat可以为多个Web应用提供服务，那么很显然，Tomcat可以把URL下发到不同的Web应用。
3. 需要把请求和响应封装成request/response</br>
   我们在Web应用这一层，可从来没有封装过request/response的，我们都是直接使用的，这就是因为Tomcat已经为你做好了！

### 具体实现

工程结构：![](https://i.imgur.com/J0DDtdU.png)




### 具体实现（关键代码）

MyRequest：通过输入流，对HTTP协议进行解析，拿到了HTTP请求头的方法以及URL。

	public class MyRequest {
	
	    private  String url;
	    private String method;
	
	    public MyRequest(InputStream inputStream) throws IOException {
	        String httpRequest="";
	        byte[] httpRequestBytes = new byte[1024];
	        int length =0;
	        if((length = inputStream.read(httpRequestBytes)) > 0){
	            httpRequest = new String(httpRequestBytes,0,length);
	        }
	        String httpHead = httpRequest.split("\n")[0];
	        url = httpHead.split("\\s")[1];
	        method = httpHead.split("\\s")[0];
	        System.out.println(this);
	    }



MyResponse：基于HTTP协议的格式进行输出写入。


	public class MyResponse {
	    private OutputStream outputStream;
	    public MyResponse(OutputStream outputStream) {
	        this.outputStream = outputStream;
	    }
	
	    public  void  write(String content) throws IOException {
	        StringBuffer httpResponse  = new StringBuffer();
	        httpResponse.append("HTTP/1.1 200 OK\n")
	                .append("Content-Type: text/html\n")
	                .append("\r\n")
	                .append("<html><body>")
	                .append(content)
	                .append("</body></html");
	        outputStream.write(httpResponse.toString().getBytes());
	        outputStream.close();
	    }
	
	}

MyServlet：Tomcat是满足Servlet规范的容器，那么自然Tomcat需要提供API。这里你看到了Servlet常见的doGet/doPost/service方法。
	
	public abstract  class MyServlet {
	    public  abstract  void doGet(MyRequest myRequest,MyResponse myResponse);
	    public  abstract  void  doPost(MyRequest myRequest,MyResponse myResponse);
	    public   void  service(MyRequest myRequest,MyResponse myResponse){
	        if(myRequest.getMethod().equalsIgnoreCase("GET")){
	            doGet(myRequest,myResponse);
	        }else if(myRequest.getMethod().equalsIgnoreCase("POST")){
	            doPost(myRequest,myResponse);
	        }
	    }
	}


FindGirlServlet和HelloWorldServlet：servlet具体实现，为了后面测试

 
	public class FindGirlServlet extends  MyServlet {
	    @Override
	    public void doGet(MyRequest myRequest, MyResponse myResponse) {
	        try {
	            myResponse.write("get girl...");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	    @Override
	    public void doPost(MyRequest myRequest, MyResponse myResponse) {
	        try {
	            myResponse.write("get girl...");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}

</br>

	public class HelloWordServlet extends MyServlet {
	    @Override
	    public void doGet(MyRequest myRequest, MyResponse myResponse) {
	        try {
	            myResponse.write("get word...");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	    @Override
	    public void doPost(MyRequest myRequest, MyResponse myResponse) {
	        try {
	            myResponse.write("get word...");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}


ServletMapping和ServletMappingConfig:会在web.xml中通过<servlet></servlet>和<servlet-mapping></servlet-mapping>来进行指定哪个URL交给哪个servlet进行处理
	
	public class ServletMapping {
	    private  String servletName;
	    private  String  url;
	    private  String clazz;
	
	    public ServletMapping(String servletName, String url, String clazz) {
	        this.servletName = servletName;
	        this.url = url;
	        this.clazz = clazz;
	    }
	
	    public String getServletName() {
	        return servletName;
	    }
	
	    public void setServletName(String servletName) {
	        this.servletName = servletName;
	    }
	
	    public String getUrl() {
	        return url;
	    }
	
	    public void setUrl(String url) {
	        this.url = url;
	    }
	
	    public String getClazz() {
	        return clazz;
	    }
	
	    public void setClazz(String clazz) {
	        this.clazz = clazz;
	    }
	}


</br>
	
	public class ServletMappingConfig {
	    public  static List<ServletMapping> servletMappingList = new ArrayList<>();
	
	    static {
	        servletMappingList.add(new ServletMapping("findGirl","/girl","com.yu.mytomcat.FindGirlServlet"));
	        servletMappingList.add(new ServletMapping("helloWord","/word","com.yu.mytomcat.HelloWordServlet"));
	    }
	}



MyTomcat: 主要有 start()，dispach()方法，omcat的处理流程：把URL对应处理的Servlet关系形成，解析HTTP协议，封装请求/响应对象，利用反射实例化具体的Servlet进行处理即可。
	
     //主函数
	public class Mytomcat {
	    private int port = 8080;
	    private Map<String,String>  urlServletMap  = new HashMap<>();
	
	    public Mytomcat(int port) {
	        this.port = port;
	    }
	
	     public static void main(String[] args){
	        new Mytomcat(8080).start();
	     }
	   

</br>

		        public  void  start(){
		        initServletMapping();
		        ServerSocket serverSocket  =  null;
		        try {
		            serverSocket = new ServerSocket(port);
		            System.out.println("MyYTomcat is start...");
		            while (true){
		                Socket socket = serverSocket.accept();
		                InputStream inputStream = socket.getInputStream();
		                OutputStream outputStream = socket.getOutputStream();
		
		                MyRequest myRequest = new MyRequest(inputStream);
		                MyResponse myResponse = new MyResponse(outputStream);
		
		                dispatch(myRequest,myResponse);
		                socket.close();
		            }
		        }catch (IOException e){
		            e.printStackTrace();
		        }finally {
		            if(serverSocket != null){
		                try{
		                    serverSocket.close();
		                }catch (IOException e){
		                    e.printStackTrace();;
		                }
		            }
		        }
		    }  


</br>


	    private void dispatch(MyRequest myRequest, MyResponse myResponse) {
	        String clazz = urlServletMap.get(myRequest.getUrl());
	        try{
	            Class<MyServlet> myServletClass  = (Class<MyServlet>) Class.forName(clazz);
	            MyServlet myServlet = myServletClass.newInstance();
	            myServlet.service(myRequest,myResponse);
	        }catch (Exception e){
	            e.printStackTrace();
	        }
	    }
	
	    private void initServletMapping() {
	        for (ServletMapping servletMapping:ServletMappingConfig.servletMappingList) {
	            urlServletMap.put(servletMapping.getUrl(),servletMapping.getClazz());
	        }
	    }