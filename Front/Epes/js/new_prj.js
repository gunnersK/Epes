var publishBtn = document.getElementById("publish");

publishBtn.addEventListener('tap', function(){
	mui.ajax(urlPattern.value+'/project/save', {
		data: {
			"prjName": document.getElementById('prj_name').value,
			"prjDesc": document.getElementById('textarea').value
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