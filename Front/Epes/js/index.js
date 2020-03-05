var failureMsg = document.getElementById('failure_msg');
failureMsg.style.display = "none"; 

var login = document.getElementById('login');
login.addEventListener('tap', function(){
	mui.ajax(urlPattern.value+'/login', {
		dataType:'json',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		success: function(data){
			if(data.msg == "success"){
				mui.openWindow({
					url: 'home.html'
				});
			} else{
				failureMsg.style.display = "block";
			}
		}
	});
	
});
 
