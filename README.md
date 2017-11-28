#概述
师道教学管理系统的目的是解决教学过程中的一些列耗时的工作。比如安排
作业，批改作业，平时测评，学生练习等问题。

##一、系统架构

整个架构采用AJAX+SpringMVC+Spring+JDBC+Mysql。
核心目标是前后端分离，各层分离，便于扩展。

##二、数据库设计
数据库选择mysql，便于安装部署。
###1.数据库表名表字段规范

表名需添加前缀，便于理解查找。如：
- 系统基础表前缀为sys
- 题库中心相关表前缀为qst
- 所有的关联关系表前缀为rel
- 日志表前缀为log

表名多个单词用下划线隔开,列名中不要出现表名或其缩写。
主键列一般都命名为id
日期时间类型的列一般为xxx_time
总数量类型的列一般为total_xxx
外键列命名规范为：xxx_关联表名_id
所有备注含义的列都命名为remark
所有表示状态的列都命名为status

###2、关于数据库主键
主键都采用12位uuid，可使用工具类PlatformUtil来进行生成



##三、前后端交互借口设计
整个系统的模板是前后端分离。前端采用http协议进行通信，
后端返回json数据结构，格式统一为:
如果成功：
{
ok：true
data：数据...
}
如果失败：
{
errors:[异常代号1，异常代号2，...],
messages:[异常消息1，异常消息2,...]
}
其中异常消息和异常代号一一对应。
异常代号格式如下：
异常类型.发生位置(异常参数) 如must-between.age(18,99) 表示age字段必须在18和99之间

请求URL设计

格式设计:
/模块/数据实体/操作？参数列表
如：
/platform/user/add
/platform/user/mod?id=xxx
/platform/user/get?id=xxx
/platform/user/del?id=xxx
/platform/user/list?key=xxx&pageSize=10&pageNo=1

/questionhub/question/add
/questionhub/question/mod?id=xxx
/questionhub/question/del?id=xxx
/questionhub/question/list?kind=xxx&key=xxx

所有请求可使用如下js方法发出

$.load(url,data,done)
$.ajaxGet(url,data,done)
$.ajaxPost(url,data,done)
$.ajaxPut(url,data,done)
$.ajaxDelete(url,data,done)

以上方法的使用方式和原生jquery方法一致。
其成功（done）和失败（fail）回调已封装为对应请求的成功和失败，
如done方法，入参为data，fail方法入参为整个应答。



##四、前端设计
###1.整体结构
前端所有页面都采用一个view.html和controller.js。
页面名称通过目录体现。
如用户管理页面结构如下：
-userManage
--add
    view.html
    controller.js
--check
    view.html
    controller.js

###2.页面间数据传递有如下方法可选
1. url参数 
    window.location.href=../check/view.html?xxxx
2. window.sessionStorage
    
3. window.postMessage机制
        此机制已封装为window.broadcast()/window.onbroadcast()
        A页面发出广播
            window.broadcast("xxxx",data);
        B页面接收广播
            window.onbroadcast("xxxx",function(data){
                 //get data   
            });    
###3.页面布局
已提供如下布局css
layout-vbox
    所有子元素垂直居中放置，高度自适应。
    子元素可添加grow，让其充满父元素剩余空间
layout-hbox
    所有子元素水平垂直居中放置，宽度自适应。
    子元素可添加grow，让其充满父元素剩余空间
    
layout-justify
    让子元素两端对齐放置，且高度充满父容器。

layout-middle
    让第一个子元素高宽子适应水平垂直居中
layout-full
    让所有子元素高宽都和父元素一致。重叠放置。
layout-tab
     选项卡布局
     必选包含两个元素
        .tabs
            其下面的a标签作为选项卡
        .tabs-cards 
            其下面的tab-card作为选项卡的内容




##五、后端设计
###1、Dao层设计
    目前仅提供了单表的SingleTableDaoSupport
    后续还需要提供公用主键的多表DaoSupport
    所有子类进行实现两个方法
    row2obj
    obj2row
    
    
    
系统提供了一个JDBCUtil工具类
    用于执行增删改查sql
    
###2、业务层设计
    所有参数验证都放在业务层完成。
    所有业务方法默认都进行了事务管理。
    如果需要特殊事务，请在方法上使用@Transcational注解。
    
    业务逻辑判断请使用AssertThrowUtil类
    
    
       
    

###3、表现层设计

    已对springMVC进行了封装，控制器类中公开方法名称对应请求路径中的操作。
    所有方法需要返回一个JsonResponse对象。所有请求都不需要对异常进行处理，默认已统一处理。
    如：

~~~java
@RequestMaping("/xxx")
class XXXXHandler{
    public JsonResponse get(String id){
        Object data=getData(id);
        return JsonResponse.ok(data);
    }
}
~~~

则此请求的url为：/xxx/get?id=xxx
应答为：{
    ok:true,
    data:...
}







