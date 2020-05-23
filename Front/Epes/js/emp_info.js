mui.ajax(urlPattern.value+'/employee/getProfile', {
	dataType:'json',//服务器返回json格式数据
	type:'get',//HTTP请求类型
	success: function(data){
		if(data.status == "200"){
			document.getElementById('emp_id').innerText = data.data.empId;
			document.getElementById('emp_name').innerText = data.data.empName;
			document.getElementById('gender').innerText = '男';
			if(data.data.gender == 1){
				document.getElementById('gender').innerText = '女';
			}
			document.getElementById('dpart_name').innerText = data.data.dpartName;
			document.getElementById('contact').innerText = data.data.contact
		}
	}
});
		
var confBtn = document.getElementById("confirm");
confBtn.addEventListener('tap', function(){
	if(validInput()){
		var oldPasswd = document.getElementById("old_passwd").value;
		var newPasswd = document.getElementById("new_passwd").value;
		var confPasswd = document.getElementById("conf_passwd").value;
		if(newPasswd == confPasswd){
			mui.ajax(urlPattern.value+'/user/modifyPwd', {
				data: {
					"old_passwd": oldPasswd,
					"new_passwd": newPasswd
				},
				dataType:'json',//服务器返回json格式数据
				type:'post',//HTTP请求类型
				success: function(data){
					if(data.msg == "success"){
						mui.toast('修改成功', { duration:'long', type:'div'});
						mui('#mod_popover').popover('toggle');//show hide toggle
						clearInput();
					} else{
						mui.toast('旧密码不正确', { duration:'long', type:'div'});
					}
				}
			});
		} else{
			mui.toast('两次输入密码不一致', { duration:'long', type:'div'});
		}
	}
});

var cancel = document.getElementById("cancel");
cancel.addEventListener('tap', function(){
	mui('#mod_popover').popover('toggle');//show hide toggle
})
	
function validInput(){
	var oldPasswd = document.getElementById("old_passwd").value;
	var newPasswd = document.getElementById("new_passwd").value;
	var confPasswd = document.getElementById("conf_passwd").value;
	if(oldPasswd == ''){
		mui.toast('请输入旧密码', { duration:'long', type:'div'});
		return false;
	} else if(newPasswd == ''){
		mui.toast('请输入新密码', { duration:'long', type:'div'});
		return false;
	} else if(confPasswd == ''){
		mui.toast('请输入确认密码', { duration:'long', type:'div'});
		return false;
	}
	return true;
}
	
function clearInput(){
	document.getElementById("old_passwd").value = '';
	document.getElementById("new_passwd").value = '';
	document.getElementById("conf_passwd").value = '';
}