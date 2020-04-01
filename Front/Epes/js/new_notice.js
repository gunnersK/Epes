(function($) {
	 
	/* 确定添加按钮 */
	var publish = document.getElementById('publish');
	publish.addEventListener('tap', function(){
		if(validInput()){
			mui.ajax(urlPattern.value+'/notice/save', {
				data: {
					"title": document.getElementById('title').value,
					"content": document.getElementById('textarea').value,
				},
				dataType:'json',//服务器返回json格式数据
				type:'post',//HTTP请求类型
				success: function(data){
					if(data.status == "200"){
						mui.toast('发布成功');
						mui.back();
`					}
				}
			});
		}
	});
	
})(mui);

/* 验证表单输入 */
function validInput(){
	if(document.getElementById('title').value == ""){
		mui.toast("请输入标题", {type: 'div'});
		return false;
	} else if(document.getElementById('textarea').value == ""){
		mui.toast("请输入正文", {type: 'div'});
		return false;
	}
	return true;
}