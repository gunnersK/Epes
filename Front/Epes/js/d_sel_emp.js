/* 显示员工列表 */
mui.ajax(urlPattern.value+'/employee/getTaskEmp', {
	dataType:'json',//服务器返回json格式数据
	type:'get',//HTTP请求类型
	success: function(data){
		if(data.status == "200"){
			for(i = 0; i < data.data.length; i++){
				var item = "<div class='mui-input-row mui-checkbox'><label>"+data.data[i].empName+"</label>"+
					"<input name=checkbox1' value="+data.data[i].empId+" type='checkbox'></div>"
				$("#emp_list").append(item);
			}
		}
	} 
});

/* 确认按钮 */
var conBtn = document.getElementById('confirm');
conBtn.addEventListener('tap', function(){
	var obj=document.getElementsByTagName("input");
	var ids = [];
	for(var i=0; i<obj.length; i++){
		if(obj[i].checked){
			ids.push(obj[i].value);
		}
	}
	var idList = ids.join(",");
	mui.ajax(urlPattern.value+'/taskEva/save', {
		data: {"empIdList": idList},
		dataType:'json',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				mui.toast("新增成功");
				mui.back();
			}
		}
	}); 
});