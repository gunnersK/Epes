(function($) {
	
	loadPicker($);
	
	generateEmpId();
	
	loadDpart();
	
	/* 确定添加按钮 */
	var addBtn = document.getElementById('add_btn');
	addBtn.addEventListener('tap', function(){
		mui.ajax(urlPattern.value+'/employee/save', {
			data: {
				"empId": document.getElementById('emp_id').value,
				"empName": document.getElementById('emp_name').value,
				"password": "12345678",
				"gender": document.getElementById('gender').data,
				"dpartId": document.getElementById('dpart').data,
				"role": document.getElementById('role').data,
				"contact": document.getElementById('contact').value
			},
			// processData: false,
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			// headers:{'Content-Type':'application/json'},
			success: function(data){
				if(data.status == "200"){
					
					mui.back(function(){
						mui.toast('新增成功', { duration:'long', type:'div' });
					});
				}
			}
		});
	});
	
})(mui);

/* 加载picker选择器 */
function loadPicker($){
	/* 性别picker */
	var genderPicker = new $.PopPicker();
	genderPicker.setData([{
		value: 0,
		text: '男'
	}, {
		value: 1,
		text: '女'
	}]);
	var gender = document.getElementById('gender');
	gender.addEventListener('tap', function(event) {
		genderPicker.show(function(items) {
			gender.value = items[0].text;
			gender.data = items[0].value;
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	
	/* 角色picker */
	var rolePicker = new $.PopPicker();
	rolePicker.setData([{
		value: '1',
		text: '部门主管'
	}, {
		value: '2',
		text: '普通员工'
	}]);
	var role = document.getElementById('role');
	// var scoStaRes = document.getElementById('gender');
	role.addEventListener('tap', function(event) {
		rolePicker.show(function(items) {
			role.value = items[0].text;
			role.data = items[0].value;
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
}

/* 生成新员工id */
function generateEmpId(){
	mui.ajax(urlPattern.value+'/employee/newId', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		// async:false,
		success: function(data){
			if(data.status == "200"){
				document.getElementById('emp_id').value = data.data;
			}
		}
	});
}

/* 加载部门 */
function loadDpart(){
	mui.ajax(urlPattern.value+'/department/getDpart', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				document.getElementById('dpart').value = data.data.dpartName;
				document.getElementById('dpart').data = data.data.dpartId;
			}
		}
	});
}

