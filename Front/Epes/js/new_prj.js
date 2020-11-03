var publishBtn = document.getElementById("publish");

publishBtn.addEventListener('tap', function(){
	if(validInput()){
		mui.ajax(urlPattern.value+'/project/save', {
			data: {
				"prjName": document.getElementById('prj_name').value,
				"prjDesc": document.getElementById('textarea').value
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

/* 验证表单输入 */
function validInput(){
	if(document.getElementById('prj_name').value == ""){
		mui.toast("请输入项目名", {type: 'div'});
		return false;
	} else if(document.getElementById('textarea').value == ""){
		mui.toast("请输入项目描述", {type: 'div'});
		return false;
	}
	return true;
}