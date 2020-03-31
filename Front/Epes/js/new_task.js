(function($) {
	
	/* 项目picker */
	var prjPicker = new $.PopPicker();
	var undoPrj = [];
	mui.ajax(urlPattern.value+'/project/listAll', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				for(var i = 0; i < data.data.length; i++){
					undoPrj.push({
						"value": data.data[i].prjId,
						"text": data.data[i].prjName
					});
				}
				prjPicker.setData(undoPrj);
			}
		}
	});
	 
	var prjName = document.getElementById('prj_name');
	var prjId = document.getElementById('prj_id');
	prjName.addEventListener('tap', function(event) {
		prjPicker.show(function(items) {
			prjName.setAttribute("value", items[0].text);
			prjId.setAttribute("value", items[0].value);
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	
	/* 确认按钮 */
	var confBtn = document.getElementById("confirm");
	confBtn.addEventListener('tap', function(){
		if(validInput()){
			mui.ajax(urlPattern.value+'/prjTask/save', {
				data: {
					"taskName": document.getElementById('task_name').value,
					"prjId": document.getElementById('prj_id').value,
					"prjName": document.getElementById('prj_name').value,
					"weight": document.getElementById('weight').value,
					"taskDesc": document.getElementById('task_desc').value, 
					"scoreDesc": document.getElementById('score_desc').value
				},
				dataType:'json',//服务器返回json格式数据
				type:'post',//HTTP请求类型
				success: function(data){
					if(data.status == "200"){
						mui.toast('新增成功');
						mui.back();
					}
				}  
			}); 
		}
	});

})(mui);

/* 验证表单输入 */
function validInput(){
	if(document.getElementById('task_name').value == ""){
		mui.toast("请输入任务名", {type: 'div'});
		return false;
	} else if(document.getElementById('prj_id').value == ""){
		mui.toast("请输入项目", {type: 'div'});
		return false;
	} else if(document.getElementById('task_desc').value == ""){
		mui.toast("请输入项任务描述", {type: 'div'});
		return false;
	} else if(document.getElementById('score_desc').value == ""){
		mui.toast("请输入评分标准", {type: 'div'});
		return false;
	}
	return true;
}