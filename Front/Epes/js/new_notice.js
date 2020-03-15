(function($) {
	 
	/* 确定添加按钮 */
	var publish = document.getElementById('publish');
	publish.addEventListener('tap', function(){
		mui.ajax(urlPattern.value+'/notice/save', {
			data: {
				"title": document.getElementById('title').value,
				"content": document.getElementById('textarea').value,
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.back();
					// history.back();
				}
			}
		});
	});
	
})(mui);