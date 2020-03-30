/* 用户角色全局变量 */
// var role;
(function($) {
	
	/* 获取登录用户权限 */
	mui.ajax(urlPattern.value+'/employee/getProfile', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				// var role = 1;
				var role = data.data.role;
				var name = data.data.empName;
				var empId = data.data.empId;
				var dpartId = data.data.dpartId;
				var dpartName = data.data.dpartName;
				if(role == 0){
					initAdmin(dpartName, name);
				} else if(role == 1){
					initDirector(dpartId, dpartName+"主管", name);
				} else if(role == 2){
					initEmployee(empId, dpartName+"员工", name);
				}
				/* 刷新图表 */
				/* 因为是异步请求,所以要等初始化完各个角色的按钮和过滤下拉框后才加载图表,
					否则会拿不到dpartId或empId导致数据出错 */
				refreshChart();
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

	/* 初始化管理员按钮和过滤下拉框 */
	function initAdmin(role, name){
		welcome(role, name);
		var btns = document.getElementsByClassName("admin");
		for(i = 0; i < btns.length; i++){
			btns[i].style.display = "block";
		}
		initDpartPicker();
		document.getElementsByClassName("filter-right")[0].style.display = "inline";
	}

	/* 初始化部门主管按钮和过滤下拉框 */
	function initDirector(dpartId, role, name){
		welcome(role, name);
		var btns = document.getElementsByClassName("director");
		for(i = 0; i < btns.length; i++){
			btns[i].style.display = "block";
		}
		//当部门主管请求图表数据时带上他，dpartId是空的,所以当选择all员工,就会导致查到全公司的员工数据
		//所以要先把dpartId存起来,请求图表数据时带上他,就能只查到本部门的员工数据
		document.getElementById("dpart_id").value = dpartId;
		initEmpPicker();
		document.getElementsByClassName("filter-right")[0].style.display = "inline";
	}

	/* 初始化普通员工按钮 */
	function initEmployee(empId, role, name){
		welcome(role, name);
		var btns = document.getElementsByClassName("employee");
		for(i = 0; i < btns.length; i++){
			btns[i].style.display = "inline";
		}
		//当普通员工请求图表数据时带上他，否则会查到全公司的员工数据
		//所以要先把empId存起来,请求图表数据时带上他,就能只查到自己数据
		document.getElementById("emp_id").value = empId;
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


	/* 初始化日期picker */
	var btns = $('.date');
	btns.each(function(i, btn) {
		btn.addEventListener('tap', function() {
			var _self = this;
			var res = _self.getElementsByClassName('result')
			var optionsJson = this.getAttribute('data-options') || '{}';
			var options = JSON.parse(optionsJson);
			var id = this.getAttribute('id');
			_self.picker = new $.DtPicker(options);
			_self.picker.show(function(rs) {
				res[0].value = rs.y.text + '年';
				document.getElementById("year_value").value = rs.y.text;
				_self.picker.dispose();
				_self.picker = null;
				refreshChart();
			});
		}, false);
	});
	
	/* 初始化部门picker */
	function initDpartPicker(){
		var dpartPicker = new $.PopPicker();
		var dpartList = [];
		mui.ajax(urlPattern.value+'/department/list', {
			dataType:'json',//服务器返回json格式数据
			type:'get',//HTTP请求类型
			success: function(data){
				dpartList.push({"value": "", "text": "all"});
				if(data.status == "200"){
					for(var i = 0; i < data.data.length; i++){
						if(data.data[i].dpartId == 1){
							continue;
						}
						dpartList.push({
							"value": data.data[i].dpartId,
							"text": data.data[i].dpartName
						});
					}
					dpartPicker.setData(dpartList);
				}
			}
		});
		var dpart = document.getElementById('dpart');
		var dpartRes = document.getElementById('dpart_res');
		var dpartId = document.getElementById('dpart_id');
		dpart.addEventListener('tap', function(event) {
			dpartPicker.show(function(items) {
				dpartRes.value = items[0].text;
				dpartId.setAttribute("value", items[0].value);
				refreshChart();
				//返回 false 可以阻止选择框的关闭
				//return false;
			});
		}, false);
	}
	 
	/* 初始化员工picker */
	function initEmpPicker(){
		var empPicker = new $.PopPicker();
		var empList = [];
		mui.ajax(urlPattern.value+'/employee/listByDpart', {
			dataType:'json',//服务器返回json格式数据
			type:'get',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					empList.push({"value": "", "text": "all"});
					for(var i = 0; i < data.data.length; i++){
						empList.push({
							"value": data.data[i].empId,
							"text": data.data[i].empName
						});
					}
					empPicker.setData(empList);
				}
			}
		});
		var emp = document.getElementById('emp');
		var empRes = document.getElementById('emp_res');
		var empId = document.getElementById('emp_id');
		emp.addEventListener('tap', function(event) {
			empPicker.show(function(items) {
				empRes.value = items[0].text;
				empId.setAttribute("value", items[0].value);
				refreshChart();
				//返回 false 可以阻止选择框的关闭
				//return false;
			});
		}, false);
	}
})(mui);

/* 加载chart图表数据 */
function refreshChart(){
	var year = document.getElementById('year_value').value;
	var dpartId = document.getElementById('dpart_id').value;
	var empId = document.getElementById('emp_id').value;
	mui.ajax(urlPattern.value+'/taskEva/getChartData', {
		// async: false,
		data: {
			"year": year,
			"dpartId": dpartId,
			"empId": empId
		},
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				var dataList = [];
				// console.log(data.data);
				for(var i = 0; i < data.data.length; i++){
					dataList.push(data.data[i]);
				}
				// 基于准备好的dom，初始化echarts实例
				var myChart = echarts.init(document.getElementById('chart'));
				
				// 指定图表的配置项和数据
				var option = {
					title: {
						// text: 'ECharts 入门示例'
					},
					tooltip: {},
					legend: {
						data:['月度绩效']
					},
					xAxis: {
						data: ["1","2","3","4","5","6","7","8","9","10","11","12"]
					},
					yAxis: {},
					grid: {
						x: 35,
						x2: 10,
						y: 30,
						y2: 25,
					},
					series: [{
						name: '月度绩效',
						type: 'line',
						data: dataList
					}]
				};
				
				// 使用刚指定的配置项和数据显示图表。
				myChart.setOption(option);
			}
		}
	});
}


