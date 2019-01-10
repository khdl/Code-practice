# mina 框架

### mina框架介绍

Apache Mina Server 是一个网络通信应用框架，也就是说，它主要是对基于TCP/IP、UDP/IP协议栈的通信框架。Mina 可以帮助我们快速开发高性能、高扩展性的网络通信应用，Mina 提供了事件驱动、异步（Mina 的异步IO 默认使用的是JAVA NIO 作为底层支持）操作的编程模型。Mina 同时提供了网络通信的Server 端、Client 端的封装。

执行流程如下：

![](img/1.png)


(1.) **IoService**：最底层的是IOService，负责具体的IO相关工作。</br>
这一层的典型代表有IOSocketAcceptor和IOSocketChannel，分别对应TCP协议下的服务端和客户端的IOService。IOService的意义在于隐藏底层IO的细节，对上提供统一的基于事件的异步IO接口。</br>
每当有数据到达时，**IOService会先调用底层IO接口读取数据，封装成IoBuffer**，之后以事件的形式通知上层代码，从而将Java NIO的同步IO接口转化成了异步IO。

(2.) **IoProcessor**：这个接口在另一个线程上，负责检查是否有数据在通道上读写，也就是说它也拥有自己的Selector，这是与我们使用JAVA NIO 编码时的一个不同之处，通常在JAVA NIO 编码中，我们都是使用一个Selector，也就是不区分IoService与IoProcessor 两个功能接口。</br>
另外，IoProcessor 负责调用注册在IoService 上的过滤器，并在过滤器链之后调用IoHandler。

(3.) **IoFilter**：这个接口定义一组拦截器，这些拦截器可以包括日志输出、黑名单过滤、数据的编码（write 方向）与解码（read 方向）等功能，其中**数据的encode 与decode是最为重要的**、也是你在使用Mina 时最主要关注的地方。

(4.) **IoHandler**：这个接口负责编写业务逻辑，也就是接收、发送数据的地方。需要有开发者自己来实现这个接口。**IoHandler可以看成是Mina处理流程的终点，每个IoService都需要指定一个IoHandler**。

(5.)**IoSession**:是对底层连接（服务器与客户端的特定连接，该连接由服务器地址、端口以及客户端地址、端口来决定）的封装，一个IoSession对应于一个底层的IO连接（在Mina中UDP也被抽象成了连接）。</br>
通过IoSession，可以获取当前连接相关的上下文信息，以及向远程peer发送数据。发送数据其实也是个异步的过程。发送的操作首先会逆向穿过IoFilterChain，到达IoService。但IoService上并不会直接调用底层IO接口来将数据发送出去，而是会将该次调用封装成一个WriteRequest，放入session的writeRequestQueue中，最后由IoProcessor线程统一调度flush出去。

Mina 核心类图:

![](img/2.png)

### IoService

这个接口是服务端IoAcceptor、客户端IoConnector 的抽象，提供IO 服务和管理IoSession的功能，它有如下几个常用的方法：

1. TransportMetadata getTransportMetadata()：这个方法获取传输方式的元数据描述信息，也就是底层到底基于什么的实现，如：nio、apr 等
2. void addListener(IoServiceListener listener)：这个方法可以为IoService 增加一个监听器，用于监听IoService 的创建、活动、失效、空闲、销毁，，这为你参与IoService 的生命周期提供了入口。
3. void removeListener(IoServiceListener listener)：这个方法用于移除上面的方法添加的监听器。
4.  void setHandler(IoHandler handler)：这个方法用于向IoService 注册IoHandler，同时有getHandler()方法获取Handler。
5.  Map<Long,IoSession> getManagedSessions()：这个方法获取IoService 上管理的所有IoSession，Map 的key 是IoSession 的id
6.  IoSessionConfig getSessionConfig()：
这个方法用于获取IoSession 的配置对象，通过IoSessionConfig 对象可以设置Socket连接的一些选项。

### IoAcceptor

这个接口是TCPServer 的接口，主要增加了void bind()监听端口、void unbind()解除对套接字的监听等方法。</br>
这里与传统的JAVA 中的ServerSocket 不同的是IoAcceptor 可以多次调用bind()方法（或者在一个方法中传入多个SocketAddress 参数）同时监听多个端口。 

### IoConnector

这个接口是TCPClient 的接口，主要增加了ConnectFuture connect(SocketAddress remoteAddress,
SocketAddress localAddress)方法，用于与Server 端建立连接，第二个参数如果不传递则使用本地的一个随机端口访问Server 端。</br>
这个方法是异步执行的，同样的，也可以同时连接多个服务端。

### IoSession

这个接口用于表示Server 端与Client 端的连接，IoAcceptor.accept()的时候返回实例。

1. WriteFuture write(Object message)：这个方法用于写数据，该操作是**异步**的。
2. CloseFuture close(boolean immediately)：这个方法用于关闭IoSession，该操作也是异步的，参数指定true 表示立即关闭，否则就在所有的写操作都flush 之后再关闭。
3. Object setAttribute(Object key,Object value)：这个方法用于给我们向会话中添加一些属性，这样可以在会话过程中都可以使用，类似于HttpSession 的setAttrbute()方法。IoSession 内部使用同步的HashMap 存储你添加的自定义属性。
4. SocketAddress getRemoteAddress()：这个方法获取远端连接的套接字地址。
5. IoService getService()：这个方法返回与当前会话对象关联的IoService 实例。

关于TCP连接的关闭：
无论在客户端还是服务端，IoSession 都用于表示底层的一个TCP 连接，那么你会发现无论是Server 端还是Client 端的IoSession 调用close()方法之后，TCP 连接虽然显示关闭， 但主线程仍然在运行，也就是JVM 并未退出，这是因为IoSession 的close()仅仅是关闭了TCP的连接通道，并没有关闭Server 端、Client 端的程序。你需要调用IoService 的dispose()方法停止Server 端、Client 端。

### IoSessionConfig

用于指定此次会话的配置

1. void setReadBufferSize(int size)：这个方法设置读取缓冲的字节数，但一般不需要调用这个方法，因为IoProcessor 会自动调整缓冲的大小。
2. void setIdleTime(IdleStatus status,int idleTime)：这个方法设置关联在通道上的读、写或者是读写事件在指定时间内未发生，该通道就进入空闲状态。
3. void setWriteTimeout(int time)：这个方法设置写操作的超时时间
4. void setUseReadOperation(boolean useReadOperation)：这个方法设置IoSession 的read()方法是否可用，默认是false。

### IoHandler

**这个接口是你编写业务逻辑的地方，从上面的示例代码可以看出，读取数据、发送数据基本都在这个接口总完成，这个实例是绑定到IoService 上的，有且只有一个实例**（没有给一个IoService 注入一个IoHandler 实例会抛出异常）

1. void sessionCreated(IoSession session)：这个方法当一个Session 对象被创建的时候被调用。
2. void sessionOpened(IoSession session)：这个方法在连接被打开时调用，它总是在sessionCreated()方法之后被调用。
3. void sessionClosed(IoSession session)：对于TCP 来说，连接被关闭时，调用这个方法。
4. void sessionIdle(IoSession session, IdleStatus status)：这个方法在IoSession 的通道进入空闲状态时调用
5. void exceptionCaught(IoSession session, Throwable cause)：这个方法在你的程序、Mina 自身出现异常时回调，一般这里是关闭IoSession。
6. **void messageReceived(IoSession session, Object message)**：接收到消息时调用的方法，也就是用于接收消息的方法，一般情况下，message 是一个IoBuffer 类，如果你使用了协议编解码器，那么可以强制转换为你需要的类型。通常我们都是会使用协议编解码器的。
7. void messageSent(IoSession session, Object message)：当发送消息成功时调用这个方法。

###  IoBuffer

这个接口是对JAVA NIO 的ByteBuffer 的封装，这主要是因为ByteBuffer 只提供了对基本数据类型的读写操作，没有提供对字符串等对象类型的读写方法，使用起来更为方便，

1. static IoBuffer allocate(int capacity,boolean useDirectBuffer)：这个方法内部通过SimpleBufferAllocator 创建一个实例，第一个参数指定初始化容量，第二个参数指定使用直接缓冲区还是JAVA 内存堆的缓存区，默认为false。
2. void free()：释放缓冲区，以便被一些IoBufferAllocator 的实现重用，一般没有必要调用这个方法，除非你想提升性能（但可能未必效果明显）
3. IoBuffer setAutoExpand(boolean autoExpand)：这个方法设置IoBuffer 为自动扩展容量，也就是前面所说的长度可变，那么可以看出长度可变这个特性默认是不开启的。
4. IoBuffer putString(String value,CharsetEncoder encoder):以指定的编码方式存储字符串

### IoFuture

在Mina 的很多操作中，你会看到返回值是XXXFuture，实际上他们都是IoFuture 的子类，看到这样的返回值，这个方法就说明是异步执行的，主要的子类有ConnectFuture、CloseFuture 、ReadFuture 、WriteFuture 

1. IoFuture addListener(IoFutureListener<?> listener)：这个方法用于添加一个监听器
2. IoFuture removeListener(IoFutureListener<?> listener)：这个方法用于移除指定的监听器。
3.  IoSession getSession()：这个方法返回当前的IoSession

 - 举个例子，我们在客户端调用connect()方法访问Server 端的时候，实际上这就是一个异步执行的方法，也就是调用connect()方法之后立即返回，执行下面的代码，而不管是否连接成功。那么如果我想在连接成功之后执行一些事情，可以用awaitUninterruptibly()方法来实现。
 



	        ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9090));
	        // 等待是否连接成功，相当于是转异步执行为同步执行。
	        future.awaitUninterruptibly();
	        // 连接成功后获取会话对象。如果没有上面的等待，由于connect()方法是异步的，session可能会无法获取。
	        System .out.println(session);
	
	
	        ConnectFuture future = connector.connect(new InetSocketAddress("127.0.0.1", 9090));
	        future.addListener(new IoFutureListener<ConnectFuture>() {
	            public void operationComplete(ConnectFuture future) {
	                try {
	                    Thread.sleep(3000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                IoSession session = future.getSession();
	                System.out.println("++++++++++++++++++++++++++++");
	            }
	        });
	        System.out.println("*************");



### 日志配置

这是因为Mina 内部使用的就是SLF4J，你也使用SLF4J 可以与之保持一致性。Mina 如果想启用日志跟踪Mina 的运行细节，你可以配置LoggingFilter 过滤器，这样你可以看到Session 建立、打开、空闲等一系列细节在日志中输出，默认SJF4J 是按照DEBUG级别输出跟踪信息的，如果你想给某一类别的Mina 运行信息输出指定日志输出级别，可以调用LoggingFilter 的setXXXLogLevel(LogLevel.XXX)。

### 过滤器

前面我们看到了LoggingFilter、ProtocolCodecFilter 两个过滤器，一个负责日志输出，一个负责数据的编解码。通过最前面的Mina 执行流程图，在IoProcessor 与IoHandler 之间可以有很多的过滤器，这种设计方式为你提供可插拔似的扩展功能提供了非常便利的方式。ina 中的IoFilter 是单例的。

IoService 实例上会绑定一个DefaultIoFilterChainBuilder 实例，DefaultIoFilterChainBuilder 会把使用内部的EntryImpl 类把所有的过滤器按照顺序连在一起，组成一个过滤器链。

编写一个过滤器很简单，你需要实现IoFilter 接口，如果你只关注某几个方法，可以继承IoFilterAdapter 适配器类。IoFilter 接口中主要包含两类方法，一类是与IoHandler 中的方法名一致的方法，相当于拦截IoHandler 中的方法，另一类是IoFilter 的生命周期回调方法。

要注意必须在实现时调用参数nextFilter 的同名方法，否则，过滤器链的执行将被中断，IoHandler 中的同名方法一样也不会被执行，这就相当于Servlet 中的Filter 必须调用filterChain.doFilter(request,response)才能继续前进是一样的道理。

mina中自带的过滤器：

- BlacklistFilter 设置一些IP 地址为黑名单，不允许访问。
- BufferedWriteFilter 设置输出时像BufferedOutputStream 一样进行缓冲。
- CompressionFilter 设置在输入、输出流时启用JZlib 压缩。
- 等等

### 协议编解码器

前面说过，协议编解码器是在使用Mina 的时候你最需要关注的对象，因为在网络传输的数据都是二进制数据（byte），而你在程序中面向的是JAVA 对象，这就需要你实现在发送数据时将JAVA 对象编码二进制数据，而接收数据时将二进制数据解码为JAVA 对象（这个可不是JAVA 对象的序列化、反序列化那么简单的事情）。Mina 中的协议编解码器通过过滤器ProtocolCodecFilter 构造，这个过滤器的构造方法需要一个ProtocolCodecFactory，这从前面注册TextLineCodecFactory 的代码就可以看出来。

ProtocolCodecFactory 中有如下两个方法：
   
	  public interface ProtocolCodecFactory {
	　　ProtocolEncoder getEncoder(IoSession session) throws Exception;
	　　ProtocolDecoder getDecoder(IoSession session) throws Exception;
	  }



因此，构建一个ProtocolCodecFactory 需要ProtocolEncoder、ProtocolDecoder 两个实例。

### 线程模型配置

Mina 中的很多执行环节都使用了多线程机制，用于提高性能。Mina 中默认在三个地方使用了线程：

1.  IoAcceptor：这个地方用于接受客户端的连接建立，每监听一个端口（每调用一次bind()方法），都启用一个线程，这个数字我们不能改变。这个线程监听某个端口是否有请求到来，一旦发现，则创建一个IoSession 对象。因为这个动作很快，所以有一个线程就够了
2.  IoConnector：这个地方用于与服务端建立连接，每连接一个服务端（每调用一次connect()方法），就启用一个线程，我们不能改变。同样的，这个线程监听是否有连接被建立，一旦发现，则创建一个IoSession 对象。因为这个动作很快，所以有一个线程就够了。
3.  IoProcessor：这个地方用于执行真正的IO 操作，默认启用的线程个数是CPU 的核数+1，如：单CPU 双核的电脑，默认的IoProcessor 线程会创建3 个。这也就是说一个IoAcceptor 或者IoConnector 默认会关联一个IoProcessor 池，这个池中有3 个IoProcessor。因为IO 操作耗费资源，所以这里使用IoProcessor 池来完成数据的读写操作，有助于提高性能。这也就是前面说的IoAccetor、IoConnector 使用一个Selector，而IoProcessor 使用自己单独的Selector 的原因。那么为什么IoProcessor 池中的IoProcessor 数量只比CPU 的核数大1 呢？因为IO 读写操作是耗费CPU 的操作，而每一核CPU 同时只能运行一个线程，因此IoProcessor 池中的IoProcessor 的数量并不是越多越好。

这个IoProcessor 的数量可以调整，如下所示：

		IoAcceptor acceptor=new NioSocketAcceptor(5);
		IoConnector connector=new NioSocketConnector(5);

这样就会将IoProcessor 池中的数量变为5 个，也就是说可以同时处理5 个读写操作

还记得前面说过Mina 的解码器要使用IoSession 保存状态变量，而不是Decoder 本身，这是因为Mina 不保证每次执行doDecode()方法的都是同一个IoProcessor 这句话吗？

- 其实这个问题的根本原因是IoProcessor 是一个池，每次IoSession 进入空闲状态时（无读些数据发生），IoProcessor 都会被回收到池中，以便其他的IoSession 使用，所以当IoSession从空闲状态再次进入繁忙状态时，IoProcessor 会再次分配给其一个IoProcessor 实例，而此时已经不能保证还是上一次繁忙状态时的那个IoProcessor了。

你还会发现IoAcceptor 、IoConnector 还有一个构造方法， 你可以指定一个java.util.concurrent.Executor 类作为线程池对象，那么这个线程池对象是做什么用的呢？

- 其实就是用于创建你还会发现IoAcceptor、IoConnector中的用于监听是否有TCP 连接建立的那个线程，默认情况下，使用Executors.newCachedThreadPool()方法创建Executor 实例，也就是一个无界的线程池（具体内容请参看JAVA 的并发库）。大家不要试图改变这个Executor 的实例，也就是使用内置的即可，否则可能会造成一些莫名其妙的问题。

#### 综述mina工作流程

下面我们完整的综述一下Mina 的工作流程：

 1. 当 IoService 实例创建的时候，同时一个关联在IoService上的IoProcessor 池、线程池也被创建；
 2. 当 IoService 建立套接字（IoAcceptor 的bind()或者是IoConnector 的connect()方法被调用）时，IoService 从线程池中取出一个线程，监听套接字端口；
 3. 当 IoService 监听到套接字上有连接请求时，建立IoSession 对象，从IoProcessor池中取出一个IoProcessor 实例执行这个会话通道上的过滤器、IoHandler；
 4. 当这条IoSession 通道进入空闲状态或者关闭时，IoProcessor 被回收。上面说的是Mina 默认的线程工作方式，那么我们这里要讲的是如何配置IoProcessor 的多线程工作方式。因为一个IoProcessor 负责执行一个会话上的所有过滤器、IoHandler，也就是对于IO 读写操作来说，是单线程工作方式（就是按照顺序逐个执行）。</br>
 假如你想让某个事件方法（如：sessionIdle()、sessionOpened()等）在单独的线程中运行（也就是非IoProcessor 所在的线程），那么这里就需要用到一个ExecutorFilter 的过滤器。你可以看到IoProcessor 的构造方法中有一个参数是java.util.concurrent.Executor，也就是可以让IoProcessor 调用的过滤器、IoHandler 中的某些事件方法在线程池中分配的线程上独立运行，而不是运行在IoProcessor 所在的线程。</BR>

其实ExecutorFilter 的工作机制很简单，就是在调用下一个过滤器的事件方法时，把其交给Executor 的execute(Runnable runnable)方法来执行，其实你自己在IoHandler 或者某个过滤器的事件方法中开启一个线程，也可以完成同样的功能，只不过这样做，你就失去了程序的可配置性，线程调用的代码也会完全耦合在代码中。</br>
想清楚它该放在过滤器链的哪个位置，针对哪些事件做异步处理机制。一般ExecutorFilter 都是要放在ProtocolCodecFilter 过滤器的后面，也就是不要让编解码运行在独立的线程上，而是要运行在IoProcessor 所在的线程，因为编解码处理的数据都是由IoProcessor 读取和发送的，没必要开启新的线程，否则性能反而会下降。一般使用ExecutorFilter 的典型场景是将业务逻辑（譬如：耗时的数据库操作）放在单独的线程中运行，也就是说与IO 处理无关的操作可以考虑使用ExecutorFilter 来异步执行
	
		例：
		acceptor.getFilterChain().addLast("exceutor", new ExecutorFilter());



