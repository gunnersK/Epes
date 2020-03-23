// $(function(){
	
	/* 获取登录用户权限 */
	mui.ajax(urlPattern.value+'/employee/getProfile', {
		async: true,
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				// var role = 1;
				var role = data.data.role
				var name = data.data.empName
				if(role == 0){
					initAdmin("管理员", name);
				} else if(role == 1){
					initDirector("主管", name);
				} else if(role == 2){
					initEmployee("员工", name);
				}
			}
		}
	});
	
	/* 清除所有按钮 */
	var btns = document.getElementsByClassName("ind-btn-div");
	for(i = 0; i < btns.length; i++){
		btns[i].style.display = "none";
	} 
	// Array.prototype.map.call(btns,functions(e){
	// 	console.log(e);
	// 	e.style.display = "none";
	// })	
	
	/* 初始化管理员按钮 */
	function initAdmin(role, name){
		var btns = document.getElementsByClassName("admin");
		for(i = 0; i < btns.length; i++){
			btns[i].style.display = "inline";
		}
		welcome(role, name);
	}
	
	/* 初始化部门主管按钮 */
	function initDirector(role, name){
		var btns = document.getElementsByClassName("director");
		for(i = 0; i < btns.length; i++){
			btns[i].style.display = "inline";
		}
		welcome(role, name);
	}
	
	/* 初始化普通员工按钮 */
	function initEmployee(role, name){
		var btns = document.getElementsByClassName("employee");
		for(i = 0; i < btns.length; i++){
			btns[i].style.display = "inline";
		}
		welcome(role, name);
	}
	
	/* 设置欢迎栏 */
	function welcome(role, name){
		document.getElementById('emp_role').innerHTML = role;
		document.getElementById('emp_name').innerHTML = name;
	}
	
	/* 获取最新公告 */
	getLastNtc();
	
	/* 按钮 */
	var logBtn = document.getElementById('log_btn');
	var perfBtn = document.getElementById('perf_btn');
	var ctcBtn = document.getElementById('ctc_btn');
	var logManaBtn = document.getElementById('log_mana_btn');
	var perfEvaBtn = document.getElementById('perf_eva_btn');
	var taskDisBtn = document.getElementById('task_dis_btn');
	var dpManaBtn = document.getElementById('dp_mana_btn');
	var ntcManaBtn = document.getElementById('ntc_mana_btn');
	var prjManaBtn = document.getElementById('prj_mana_btn');
	var taskManaBtn = document.getElementById('task_mana_btn');
	
	/* 左上退出 */
	var logoutBtn = document.getElementById('log_out');
	
	/* 公告 */
	var allNtc = document.getElementById('all_ntc');
	var ntcContent = document.getElementById('ntc_content');
	
	/* 右上菜单 */
	var profile = document.getElementById('profile');
	
	/* 按钮监听 */	
	/* 工作日志 */
	logBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_log.html'
		});
	});
	
	/* 我的绩效 */
	perfBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'perf_list.html'
		});
	});
	
	/* 通讯录 */
	ctcBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'ctc_list.html'
		});
	});
	
	/* 日志管理 */
	logManaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'd_log_list.html'
		});
	});
	
	/* 绩效评分 */
	perfEvaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'd_perf_list.html'
		});
	});
	
	/* 任务分配 */
	taskDisBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'task_dis.html'
		});
	});
	
	/* 部门管理 */
	dpManaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'dpart_list.html'
		});
	});
	
	/* 公告管理 */
	ntcManaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'a_notice_list.html'
		});
	});
	
	/* 项目管理 */
	prjManaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'prj_list.html'
		});
	});
	
	/* 任务管理*/
	taskManaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'a_task_list.html'
		});
	});
	
	/* 左上退出 */
	logoutBtn.addEventListener('tap', function(){
		mui.confirm(' ', '确定退出登录?', btnArray, function(e) {
			if (e.index == 0) {
				mui.ajax(urlPattern.value+'/login/logout', {
					dataType:'json',//服务器返回json格式数据
					type:'post',//HTTP请求类型
					success: function(data){
						if(data.status == "200"){
							mui.back();
						}
					}
				});
			}
		}, 'div');
	});
	var btnArray = ['确认', '取消'];
	
	/* 个人信息 */
	profile.addEventListener('tap', function(){
		mui.openWindow({
			url: 'emp_info.html'
		});
	});
	
	/* 查看全部(公告) */
	allNtc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_list.html'
		});
	});
	
	/* 定时获取最新公告 */
	window.setInterval(getLastNtc, 2000);
	function getLastNtc(){
		var lastNtc = document.getElementById("last_ntc");
		mui.ajax(urlPattern.value+'/notice/lastNtc', {
			dataType:'json',//服务器返回json格式数据
			type:'get',//HTTP请求类型
			success: function(data){	
				if(data.status == "200"){
					lastNtc.data = data.data.ntId;
					lastNtc.innerHTML = data.data.title
				}
			}
		});
	}
	
	/* 公告详情 */
	ntcContent.addEventListener('tap', function(){
		var lastNtc = document.getElementById("last_ntc");
		mui.ajax(urlPattern.value+'/notice/transId', {
			data: {
				"ntId": lastNtc.data
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.openWindow({
						url: 'notice_detail.html'
					});
				}
			},
			error: function(){
				mui.toast('失败', { duration:'long', type:'div' });
			}
		});
	});

// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('chart'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                // text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data:['销量']
            },
            xAxis: {
                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            },
            yAxis: {},
			grid: {
				x: 35,
				x2: 10,
				y: 30,
				y2: 25,
			},
            series: [{
                name: '销量',
                type: 'line',
                data: [5, 20, 36, 10, 10, 20]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

