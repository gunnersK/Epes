mui.ajax(urlPattern.value+'/dailyLog/getDailyLog', {
		dataType:'json',//服务器返回json格式数据
	type:'get',//HTTP请求类型
	success: function(data){
		if(data.status == "200"){
			var createTime = data.data.createTime;
			document.getElementById('create_time').innerHTML = EPES.formatDateTime(createTime * 1000);
			document.getElementById('log_emp').innerHTML = data.data.empName;
			$('#log_content').append(data.data.content);
		}
	}
});
/* 完成任务按钮 */
// var finishBtn = document.getElementById('finish');

// /* 限制只有管理员可见 */
// mui.ajax(urlPattern.value+'/login/getUser', {
// 	dataType:'json',//服务器返回json格式数据
// 	type:'get',//HTTP请求类型
// 	success: function(data){
// 		if(data.status == "200"){
// 			if(data.data.role == 0){
// 				finishBtn.style.display = "inline";
// 			}
// 		}
// 	}
// });
// finishBtn.addEventListener('tap', function(){
// 	mui.confirm('确认完成该项目？', 'Hello MUI', btnArray, function(e){
// 		if (e.index == 1) {
// 			mui.ajax(urlPattern.value+'/prjTask/finish', {
// 				dataType:'json',//服务器返回json格式数据
// 				type:'post',//HTTP请求类型
// 				success: function(data){
// 					if(data.status == "200"){
// 						mui.toast('任务已完成', { duration:'long', type:'div' });
// 						mui.back();
// 					}
// 				}
// 			});
// 		}
// 	}, 'div'); 
// 	var btnArray = ['确认', '取消'];
// });

Date.prototype.format = function(format){ 
	var o =  { 
	"M+" : this.getMonth()+1, //month 
	"d+" : this.getDate(), //day 
	"h+" : this.getHours(), //hour 
	"m+" : this.getMinutes(), //minute 
	"s+" : this.getSeconds(), //second 
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
	"S" : this.getMilliseconds() //millisecond 
	};
	if(/(y+)/.test(format)){ 
		format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	}
	for(var k in o)  { 
		if(new RegExp("("+ k +")").test(format)){ 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		} 
	} 
	return format; 
};

var EPES = {	
	// 格式化时间
	formatDateTime : function(val,row){
		var now = new Date(val);
		return now.format("yyyy-MM-dd");
	}

}