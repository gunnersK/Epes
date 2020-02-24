(function($) {
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
	
})(mui);