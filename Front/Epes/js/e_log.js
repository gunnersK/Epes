(function($) {
	
	/* 提交按钮 */
	var subBtn = document.getElementById('submit');
	subBtn.addEventListener('tap', function(){
		mui.ajax(urlPattern.value+'/dailyLog/save', {
			data: {
				"content": document.getElementById('textarea').value
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.msg == "success"){
					mui.toast('提交成功');
					mui.back();
				}
			}
		});
	});
	
	/* 所有公告 */
	var allNtc = document.getElementById('all_ntc');
	allNtc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_log_list.html'
		});
	});
})(mui);