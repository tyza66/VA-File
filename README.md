# VA-File
### VA-文件管理

##### 概述
- 主体使用Java实现的一个内部文件管理系统
- 支持一些基本的文档操作、一些高级智能功能
- 因为文件夹是使用的路径的形式进行访问的，使用这套系统的时候（并且没有进行文件夹网络映射的情况下），两个后端程序需要与树根文件夹在同一个设备上

##### 预计实现功能
- [X] 用户登录
- [X] 文件夹管理
- [X] 文件管理
- [X] 文件上传
- [X] 文件下载
- [ ] 文件内容检索
- [ ] 更换根文件的时候进行预检索
- [ ] 内部通信（类似于网页微信）
- [ ] 文档分享
- [ ] 文档在线查看
- [ ] 格式转换(例如word转PDF)
- [ ] 基于NLP的文档检索系统
- [ ] 基于OCR的文档存储系统
- [ ] 基于ES的全文检索系统
- [ ] 基于大模型的文档知识问答系统

##### 实现方案
- 文件夹管理：在计算机中，文件夹的结构是树结构，我们可以通过递归的方式检索根文件夹下的所有文件夹，并且如果想在前端展示的话，我们可以直接定义一个文件和文件夹公用的类进行同List返回文件夹内容，之后到前端用很容易区分的特征进行不同形式的展示
- 文件管理：文件发现和文件夹管理实现起来一样（原生Java中提供了相关功能），文件的添加删除直接使用IO流操作即可
- 文件上传、下载：使用Java的IO流操作，加上API处理，然后将文件被操作的相关信息存储到数据库中
- 文件内容检索：正常直接检索没有保存主题的文件的时候，只对大小小于一定阈值并且直接可读的文件进行内容检索。每当新增文件的时候，根据文件大小来判断（为了提高检索速率）是否需要进行操作，如果文件过大，将直接可读的文件（txt或经转换过的纯文字文件等）的内容片段交给*语言模型*进行主题提取，将相关主题存储到数据库中，然后根据用户的检索内容，与检索内容相关的文件名返回。（这样虽然提高了效率，但是文件夹挂载前文件夹中存在的过大的文件无法被检索，为了解决这个问题，可以在更换根文件夹目录的操作中启动一个遍历生成能够完全检索原有文件的信息的线程进行并发操作）
- 更换根文件的时候进行预检索：在更换根文件夹目录的操作中启动一个遍历生成能够完全检索原有文件的信息的线程进行并发操作
- 内部通信：使用Socket长连接实现内部聊天
- 文档分享：这个原本可以使用Socket长连接进行实现，但是考虑到这个功能的使用场景，使用Socket会有一些不必要的麻烦，我这里面向完全使用HTTP短连接进行实现，所以进行的操作就是将分享文件操作放入Redis中（其实可以用消息队列），然后在前端进行轮询，如果有新的分享文件，就进行提示，然后进行下载操作
- 文档在线查看：

##### 项目结构

##### 后端程序
- 环境：Java8、MySQL、Redis、Docker、Redis
- Java后端使用技术：Spring、Spring Boot、MyBatis、MyBatis Plus、Knife4j、Hutool、Sa-Token、Lettuce
- Python后端使用技术：FastAPI、Uvicorn、HuggingFace、PyTorch、Transformers
- Java后端(VAF-CORE)端口：9090
- 响应代码：199失败、200成功、201权限不足
- Python后端
    - VAF-MIND0端口：9091

##### 前端程序
- 环境：Node、Vue3.0
- Web前端使用技术：Element-plus、Axios、Vue-router、Vuex

##### V计划
- https://github.com/tyza66/ProjectV

By：tyza66
