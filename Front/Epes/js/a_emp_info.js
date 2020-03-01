(function($) {
	
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
		value: 1,
		text: '部门主管'
	}, {
		value: 2,
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
	
	/* 加载员工信息 */
	mui.ajax(urlPattern.value+'/employee/getEmpInfo', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				genderPicker.pickers[0].setSelectedValue(data.data.gender);
				rolePicker.pickers[0].setSelectedValue(data.data.role);
				document.getElementById('emp_id').value = data.data.empId;
				document.getElementById('emp_name').value = data.data.empName;
				document.getElementById('gender').value = genderPicker.getSelectedItems()[0].text;
				document.getElementById('gender').data = genderPicker.getSelectedItems()[0].value;
				document.getElementById('dpart').value = data.data.dpartName;
				document.getElementById('dpart').data = data.data.dpartId;
				document.getElementById('role').value = rolePicker.getSelectedItems()[0].text;
				document.getElementById('role').data = rolePicker.getSelectedItems()[0].value;
				document.getElementById('contact').value = data.data.contact;
			}
		}
	});
	
	/* 更新员工 */
	var updBtn = document.getElementById('update');   
	updBtn.addEventListener('tap', function(){
		mui.ajax(urlPattern.value+'/employee/update', {
			data: {
				"empId": document.getElementById('emp_id').value,
				"empName": document.getElementById('emp_name').value,
				"gender": document.getElementById('gender').data,
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
						mui.toast('更新成功', { duration:'long', type:'div' });
					});
				}
			}
		});
	});
	
})(mui); 

