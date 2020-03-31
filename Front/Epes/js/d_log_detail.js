/* 隐藏审阅按钮 */
$("#read").css("display", "none");

/* 加载日志详情 */
mui.ajax(urlPattern.value+'/dailyLog/getDailyLog', {
		dataType:'json',//服务器返回json格式数据
	type:'get',//HTTP请求类型
	success: function(data){
		if(data.status == "200"){
			showReadBtn(data.data.status);
			var createTime = data.data.createTime;
			document.getElementById('create_time').innerHTML = EPES.formatDateTime(createTime * 1000);
			document.getElementById('log_emp').innerHTML = data.data.empName;
			$('#log_content').append(data.data.content);
		}
	}
});

/* 审阅按钮监听 */
var readBtn = document.getElementById('read');
readBtn.addEventListener('tap', function(){
	mui.confirm('确认审阅该日志？', 'Hello MUI', btnArray, function(e){
		if (e.index == 1) {
			mui.ajax(urlPattern.value+'/dailyLog/read', {
				dataType:'json',//服务器返回json格式数据
				type:'post',//HTTP请求类型
				success: function(data){
					if(data.status == "200"){
						mui.toast('已审阅');
						mui.back();
					}
				}
			});
		}
	}, 'div'); 
	var btnArray = ['确认', '取消'];
});

/* 重新显示显示审阅按钮 */
function showReadBtn(status){
	if(status == 0){
		$("#read").text("审阅");
	} else if(status == 1){
		$("#read").addClass("read");
		$("#read").text("已审阅");
		$("#read").attr("disabled", true);
	}
	$("#read").css("display", "block");
}

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

