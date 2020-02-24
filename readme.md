- <a href="#demand">需求分析</a>

- <a href="#tech">技术选型</a>

- <a href="#database">数据库设计</a>

# <a name="demand">需求分析</a> 

- 系统主要完成的工作包括：针对员工提交的日常工作，做出公平公正的全方位考核；根据具体考核分值，按照设定的绩效考核奖惩办法，给予工作相应奖励或惩罚。

- 主要功能模块如下（这里提供思路，可自行扩展）：

    - 普通员工操作模块需要以下功能：
    
        - 工作日志的管理；个人信息维护；企业通讯录；企业公告。

    - 主管操作模块需要以下功能：
    
        - 员工工作日志管理；个人信息维护；
    
        - 绩效评分管理：
    
            - 对直属部门下每个员工所负责的任务进行绩效评定（打分）
    
        - 员工绩效管理（任务分配）
    
            - //管理员工的绩效分数
            
            - 领取任务发布给指定员工
    
    - 系统管理员操作模块需要以下功能：
    
        - 部门管理；企业公告管理；
        
        - 考核项目维护、评分标准维护：
        
            - crud项目和项目的任务，设定任务权重和评分标准等 
        
         - 绩效评分管理：
        
            - 对全公司员工所负责的任务进行绩效评定（打分）
        
        - 个人信息维护；系统管理，包括角色管理、用户管理、权限管理(这三个都一样)、控制面板等功能。
    
    - 能够配置不同评分标准来进行绩效考评。
    
    - 能够根据条件统计，绘制统计图：员工考评评分结果统计，可分别按绩效考评项、员工、时间段进行统计。
    
# <a name="tech">技术选型</a>

- 本项目是java语言的前后端分离的webapp：

    - 前端：html+mui+jquery，HBuilderX开发，nginx部署
    
    - 后端：jdk8+springboot+mybatisPlus，idea开发，tomcat部署
    
    - 由于前后端分开开发，会产生跨域问题，故开发中用nginx做代理服务器

# <a name="database">数据库设计</a>

- user（用户表）
  
    - 字段：自增user_id, emp_id（外键关联employee表）, password, role（0管理员，1部门主管，2普通员工）, last_login_time, isActive（0失效，1生效）

- department（部门表）

    - 字段：自增dpart_id, dpart_name

- employee（员工表）

    - 字段：自增id，emp_id，emp_name, dpart_id（外键关联department）, status（0离职，1在职）

- daily_log（工作日志表）      

    - 字段：自增id, emp_id（外键关联employee）, content, create_time, last_upd_time, status（0未审阅，1已审阅）

- project（项目表）

    - 字段：自增prj_id, prj_name, create_time, finish_time, prj_desc（项目描述）, status（0未开始，1进行中，2已完成，3已作废）

- prj_task（项目任务表）

    - 字段：自增task_id, task_name, prj_id（外键关联project）, weight（任务权重）, create_time, finish_time, task_desc（任务描述）, score_desc（评分标准说明）,  status（0未开始，1进行中，2已完成，3已作废）

- task_eva（任务绩效表）
  
    - 字段：自增eva_id, emp_id（外键关联employee）, task_id, create_time, finish_time, last_upd_time, score（任务得分）, performance（绩效=任务得分*任务权重）, status（0未开始，1进行中，2已完成，3已作废）

- notice（公告表）

    - 字段：自增nt_id, title, content, create_time

- login

    - 字段：自增id, user_id（外键关联user表）, login_time
