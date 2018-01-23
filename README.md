# AndroidRipperdemo
MT Ripper 的一个实例
1） ripper的仓库代码：
        http://git.sankuai.com/projects/HOTEL/repos/android_ripper/browse
2）文档参考
      Ripper初识
     给酒旅Android开发者的Ripper详解（上）
3）一个小实例
        https://github.com/hualuojingyuchen/AndroidRipperdemo
        这是由3个Block组成的。
        酒店旅游事业群-产品技术 > Ripper的一个小实例——初识Ripper > Jietu20180123-155242.jpg

4）画了一个流程图，如下：
1、Main也就是Activity负责对BlockManager、LinearlayoutManager、WhiteBoard的初始化，将几个Block添加进去。
2、LinearLayoutManager也就是负责对Block的管理，添加删除等，可以看做是一个可复用可插拔的列表。
3、Block就是一个最小的不可拆分的单元，我们可以称之为块，每一个块都有自己的view与presenter，用来操作自己的U更新与业务逻辑等等，块之间可以相互组合，由具体的需求而定，随意组合成不同样式的页面或者列表。
4、presenter与view之间的数据传输通过WhiteBoard来实现，WhiteBoard可以用来发送一个消息再或者通过观察者接收想要的消息等等，不同的块之间用WhiteBlock也可以很方便的实现通讯。
酒店旅游事业群-产品技术 > Ripper的一个小实例——初识Ripper > 基于rapper的一个计算实例.png

5）总的来说
WhiteBoard事件总线，极其方便的在任意能够得到WhiteBoard的地点发送接收数据。
Block将每个页面更细的拆分，可以将某一部分单独的view作为更新和请求数据的最小单元，方便多处复用、维护、拓展等。
写起来比较麻烦，不过考虑到以后复用或者添加新模块的花，改起来方便还是挺爽的。
