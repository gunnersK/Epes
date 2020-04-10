<a href="#front">前端</a>
<a href="#back">后端</a>

# <a name="front">前端</a> 

- mui批量元素事件绑定

	- 可用于监听列表item的事件，例如一个<div class='aa'\></div\>里面有几个<span\>类型的item，就可以这么写

    ```
        mui(".aa").on('tap', 'span', function(){
            mui.openWindow({
            	url: '....html'
            });
        });
    ```

	- mui(选择器).on('事件名称', '选择器', 回调函数)

- 前端服务器页面间传递数据可用localStorage 和 sessionStorage

- back()返回上一页，需要在本页html的init加点东西（prj_filt.html）

# <a name="back">后端</a> 

- 跨域问题

    - 跨域的定义：浏览器从一个域名的网页去请求另一个域名的资源时，域名、端口、协议任一不同，都是跨域

    - 由于是前后分离的项目，前端用HBX开发，后端用idea开发，前端js用到后端提供的api接口，在浏览器访问前端项目，当访问api接口的时候会出现跨域问题，
    因为浏览器默认是同源请求，不能跨源请求，所以需要用nginx给前后端项目做一下代理
    
    - nginx配置方式：
    
        - 添加server
        
            ```
                server {
                    listen  1234;
                    server_name localhost;

                    location / {
            #        location ~ test {
                       root  /usr/local/upload/test;               #这里表示代理前端项目的路径
                       index tabbar.html;
                    }
                    location /api {                                #这里是配置后台接口的路径，表示/api会映射到后台http://192.168.80.1:8080
                       rewrite      ^/api/(.*)$ /$1 break;
                       proxy_pass   http://192.168.80.1:8080;      #这里注意ip(或域名)前面要加http
                    }
            #       access_log  logs/bhz.com.access.log  main;
                }
              }

            ```
      
      教程：https://www.jianshu.com/p/849f58eb0614
    
- 中文存mysql乱码：在properties文件中定义定义数据源时加上characterEncoding=UTF-8     

- 把页面间传递的id以及过滤条件放redis中该用户的session中

- 设置session超时清除，登录时设置expire超时为30分钟 ，然后用aop，在发起任何请求之后（或之前）重置expire的超时时间

- redis放一些以id为key的实体类对象，方便查询

	- redis放热数据（经常访问的数据），但需要条件查询或者分页的数据还是要去sql数据库查，这些数据不能放redis

- 查询list的时候不查询全部字段，只查询需要的字段，提高效率，减轻数据库压力（部门管理还没这么处理）

- mybatis-plus使用IPage分页需要在config类中配置分页的bean

- 碰到了spring无法为静态变量自动注入的问题

- key种类

	- session的key
	
	- 各种实体对象key

	- 公告已读未读set的key

	- 最新公告map的key



# 待处理问题

- 必做

	- //重要

		- //公告管理

			- //公告要设置已读未读，管理员公告管理要看到已读未读的人数。公告已读未读可用两个set分别存储read和unread的用户id

			- //在Redis建一个最新公告的key，从redis读最新公告，别直接读MySQL。
	
		- //session过期处理，session失效后要在redis中移除，上面有记

			- 主要解决当用户非正常退出程序时，session仍然存在服务器内存的问题，设置了失效时间之后，当非正常退出，或者有效时间内无任何操作，session都会被清理，用户需要重新登录

	- 不重要

		- //x-mind画Redis脑图（用OneNote画了）

		- 登录

			- 把loginController的逻辑放到service

			- //login表还没插记录

		- //处理主管重复分配任务问题

		- 有些功能要加日志

		- service的增删改要加事务

- 非必做

	- 管理员可在部门管理直接login进任何员工的界面，但是要考虑限制一个用户多端同时登录的问题

	- 图片服务器

		- http://www.ityouknow.com/springboot/2018/01/16/spring-boot-fastdfs.html

	- 有一个想法（有时间再做）：每个用户都开辟一个域保存消息队列，公告、任务、绩效、留言各用一个队列保存。当有管理员新增了项目或主管新分配了任务或留言，就放到各自队列里，用一个定时任务去监听队列，对应的相关用户要被提醒有新任务或留言

- //部门管理

	- //前端把管理员屏蔽显示，因为管理员存不进redis，获取信息会报错，还有管理员的个人资料也显示不了，要处理一下

	- //新增后返回要刷新

	- //新增员工返回toast提示

- //限制空输入

- //主管绩效评分模块对完成的任务要按钮要置灰，显示已完成，可参考日志管理的审阅状态按钮

- //首页

	- //显示欢迎哪个用户，角色

- //首页个人资料

	- //修改密码的样式改一下

- //项目管理

	- //新增项目成功的toast要改

	- //完成项目修改为关闭项目

	- //已完成的项目要把下面的按钮隐藏，任务的也要这么做

- //任务管理

	- //任务管理的新建任务还得改改

- //savecache那三个service看能不能把相同代码提出来

- //部门管理那些员工信息还没有放redis，现在是直接在数据库读，所以数据读取慢

- //公告和日志记录也存在redis

# 论文

- 系统开发环境、需求分析、功能设计（数据库设计）、模块实现

- 摘要第二段：论文阐述的结构等论文写完再完善，对应修改下面英文

- 1.3论文结构也是等论文写完再完善



