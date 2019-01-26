# Apache Commons IO

(代码在工程dailycode包 com.liu.io下)

Apache Commons IO是Apache基金会创建并维护的Java函数库。它提供了许多类使得开发者的常见任务变得简单，同时减少重复（boiler-plate）代码。

###  Apache Commons IO 示例

分别会用几段代码来演示下面的功能，每部分功能都代表Apache Commons IO所覆盖的一个单独领域，具体如下：

- 工具类
- 输入
- 输出
- 过滤器
- 比较器
- 文件监控器

### 工具类

- FilenameUtils： 这个工具类是用来处理文件名的，他可以轻松解决不同操作系统文件名称规范不同的问题（比如windows和Unix） 
- FileUtils：提供文件操作（移动文件，读取文件，检查文件是否存在等等）的方法。
- IOCase：提供字符串操作以及比较的方法。
- FileSystemUtils：提供查看指定目录剩余空间的方法。

代码 UtilityExample类

### 文件监控器

org.apache.commons.io.monitor包下的类包含的方法可以获取文件的指定信息，不过更重要的是，它可以创建处理器（handler）来跟踪指定文件或目录的变化并且可以在文件或目录发生变化的时候进行一些操作。让我们来看看下面的代码

代码 FileMonitorExample，内容如下：

1. 创建一个File对象，这个对象指向我们需要监听变化的目录。
2. 创建一个FileAlterationObserver对象，这个对象会观察这些变化。
3. 通过调用addListener()方法，为observer对象添加一个 FileAlterationListenerAdaptor对象。你可以通过很多种方式来创建一个适配器，在我们的例子中我们使用内部类的方式进行创建并且只实现其中的一部分方法（只需要实现我们例子中需要用的方法即可）。
4. 创建一个FileAlterationMonitor 对象，将已经创建好的observer对象添加其中并且传入时间间隔参数（单位是毫秒）。
5. 调用start()方法即可开启监视器，如果你想停止监视器，调用stop()方法即可

### 过滤器

过滤器可以以组合的方式使用并且它的用途非常多样。它可以轻松的区分不同的文件并且找到满足我们条件的文件。我们可以组合不同的过滤器来执行文件的逻辑比较并且精确的获取我们所需要文件，而无需使用冗余的字符串比较来寻找我们的文件

代码 FiltersExample类

### 比较器

使用org.apache.commons.io.comparator 包下的类可以让你轻松的对文件或目录进行比较或者排序。你只需提供一个文件列表，选择不同的类就可以实现不同方式的文件比较。

代码 ComparatorExample类

- NameFileComparator：通过文件名来比较文件。
- SizeFileComparator：通过文件大小来比较文件。
- LastModifiedFileComparator：通过文件的最新修改时间来比较文件。

### 输入

在org.apache.commons.io.input包下有许多InputStrem类的实现，我们来测试一个最实用的类，TeeInputStream，将InputStream以及OutputStream作为参数传入其中，自动实现将输入流的数据读取到输出流中。而且，通过传入第三个参数，一个boolean类型参数，可以在数据读取完毕之后自动关闭输入流和输出流

代码 InputExample类

### 输出

与org.apache.commons.io.input包中的类相似， org.apache.commons.io.output包中同样有OutputStream类的实现，他们可以在多种情况下使用，一个非常有意思的类就是 TeeOutputStream，它可以将输出流进行分流，换句话说我们可以用一个输入流将数据分别读入到两个不同的输出流


代码 OutputExample类