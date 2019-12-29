<a href="demand">需求分析</a>

<a href="database">数据库设计</a>

# <a name="demand">需求分析</a>

- 系统主要完成的工作包括：针对员工提交的日常工作，做出公平公正的全方位考核；根据具体考核分值，按照设定的绩效考核奖惩办法，给予工作相应奖励或惩罚。

- 主要功能模块如下（这里提供思路，可自行扩展）：

    - 普通员工操作模块需要以下功能：
    
        - 工作日志的管理；个人信息维护；企业通讯录；企业公告。

    - 主管操作模块需要以下功能：
    
        - 员工工作日志管理；个人信息维护；
        
        - 绩效评分管理：
        
            - 对直属部门下每个员工所负责的任务进行绩效评定（打分）
        
        - 员工绩效管理
        
            - 管理员工的绩效分数

    - 系统管理员操作模块需要以下功能：
    
        - 部门管理；企业公告管理；
        
        - 考核项目维护：
        
            - crud项目和项目的任务，设定权重和评分说明等 
            
         - 评分标准维护：
         
            - 分为优秀、良好、一般、不合格四个等级，管理每个等级对应的评分范围
         
         - 绩效评分管理：
         
            - 对全公司员工所负责的任务进行绩效评定（打分）
         
         - 个人信息维护；系统管理，包括角色管理、用户管理、权限管理、控制面板等功能。

    - 能够配置不同评分标准来进行绩效考评。

    - 能够根据条件统计，绘制统计图：员工考评评分结果统计，可分别按绩效考评项、员工、时间段进行统计。

# <a name="database">数据库设计</a>

- user（用户表）

    - 外键关联role表，employee表；
    
    - 主要字段：自增id，user_id, role

- department（部门表）

    - 主要字段：自增dpart_id

- employee（员工表）

    - 主要字段：自增id，emp_id，dpart_id（外键关联department）, isActive

- daily_log（工作日志表）

    - 主要字段：自增id, emp_id（外键关联employee）, content, time

- project（项目表）

    - 主要字段：自增id, prj_id, create_time, finish_time, prj_desc（项目描述）, status（0未开始，1进行中，2已完成，3已作废）

- prj_task（项目任务表）

    - 主要字段：自增id, task_id, prj_id（外键关联project）, weight, create_time, finish_time, task_desc（任务描述）, score_desc（评分说明）,  status（0未开始，1进行中，2已完成，3已作废）

- task_eva（任务绩效表）
    
    - 主要字段：自增id, eva_id, emp_id（外键关联employee）, create_time, finish_time, last_upd_time, score, status（0未开始，1进行中，2已完成，3已作废）
    
- score_strategy（评分标准表）    

    - 主要字段：自增stra_id

- notice

- login
