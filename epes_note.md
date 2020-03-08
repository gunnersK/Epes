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

- redis放一些以id为key的实体类对象，方便查询

	- redis放热数据（经常访问的数据），但需要条件查询或者分页的数据还是要去sql数据库查，这些数据不能放redis

- 优化缓存service为单例模式

- 部门管理那些员工信息还没有放redis
  
- 给每个绩效的实体类添加一个评分状态的属性，这是数据库没有的字段，然后和任务记录一起存到redis，然后主管筛选任务列表的页面就多加个评分状态的属性（有待验证可行性）

- mybatis-plus使用IPage分页需要在config类中配置分页的bean

- 碰到了spring无法为静态变量自动注入的问题

- 公告和日志记录也存在redis

    - 个人资料还要用图片服务器

    - 任务管理的新建任务还得改改

