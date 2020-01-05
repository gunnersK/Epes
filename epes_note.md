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
                       root  /usr/local/upload/test;               //这里表示代理前端项目的路径
                       index tabbar.html;
                    }
                    location /api {                                //这里是配置后台接口的路径，表示/api会映射到后台http://192.168.80.1:8080
                       rewrite      ^/api/(.*)$ /$1 break;
                       proxy_pass   http://192.168.80.1:8080;      //这里注意ip(或域名)前面要加http
                    }
            #       access_log  logs/bhz.com.access.log  main;
                }

            ```
      
      教程：https://www.jianshu.com/p/849f58eb0614
