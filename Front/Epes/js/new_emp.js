(function($) {
	console.log('');
	/* 性别picker */
	var genderPicker = new $.PopPicker();
	genderPicker.setData([{
		value: 'ywj',
		text: '男'
	}, {
		value: 'lj',
		text: '女'
	}]);
	var gender = document.getElementById('gender');
	gender.addEventListener('tap', function(event) {
		genderPicker.show(function(items) {
			gender.value = JSON.stringify(items[0].text);
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	
	/* 部门picker */
	var dpartPicker = new $.PopPicker();
	dpartPicker.setData([{
		value: 'ywj',
		text: '研发部'
	}, {
		value: 'lj',
		text: '运营部'
	}]);
	var dpart = document.getElementById('dpart');
	// var scoStaRes = document.getElementById('gender');
	dpart.addEventListener('tap', function(event) {
		dpartPicker.show(function(items) {
			dpart.setAttribute("value", JSON.stringify(items[0].text));
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	
	/* 角色picker */
	var rolePicker = new $.PopPicker();
	rolePicker.setData([{
		value: 'ywj',
		text: '部门主管'
	}, {
		value: 'lj',
		text: '普通员工'
	}]);
	var role = document.getElementById('role');
	// var scoStaRes = document.getElementById('gender');
	role.addEventListener('tap', function(event) {
		rolePicker.show(function(items) {
			role.setAttribute("value", JSON.stringify(items[0].text));
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	
	var addBtn = document.getElementById('add_btn');
	console.log('');
	addBtn.addEventListener('tap', function(){
		mui.ajax('api/employee/delete', {
			// data:{
			// 	"empId":"10000103",
			// 	"empName":"张无",
			// 	"password":"1111",
			// 	"role":2
			// },
			data:{
				"empId":"10000102"
			},
			
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			// headers:{'Content-Type':'application/json'},
			success: function(data){
				console.log(data.msg);
			}
		});
	});
	
})(mui);