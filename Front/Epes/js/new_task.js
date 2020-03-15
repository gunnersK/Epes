(function($) {
	
	/* 项目picker */
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
				var prjPicker = new $.PopPicker();
				prjPicker.setData(undoPrj);
				var prj = document.getElementById('prj');
				prj.addEventListener('tap', function(event) {
					prjPicker.show(function(items) {
						prj.setAttribute("value", items[0].text);
						prj.setAttribute("data", items[0].value);
						//返回 false 可以阻止选择框的关闭
						//return false;
					});
				}, false);
			}
		}
	});
	
	var confBtn = document.getElementById("confirm");
	
	confBtn.addEventListener('tap', function(){
		mui.ajax(urlPattern.value+'/prjTask/save', {
			data: {
				"taskName": document.getElementById('task_name').value,
				"prjId": document.getElementById('prj').value,
				"prjName": document.getElementById('prj').data,
				"weight": document.getElementById('weight').value,
				"taskDesc": document.getElementById('task_desc').value, 
				"scoreDesc": document.getElementById('score_desc').value
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.toast('新增成功', { duration:'long', type:'div' });
					mui.back();
				}
			}  
		}); 
	});

	
})(mui);