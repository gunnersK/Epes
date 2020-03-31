/* 隐藏完成按钮 */
$("#finish").css("display", "none");

(function($){
	
	/* 加载任务详情 */
	mui.ajax(urlPattern.value+'/taskEva/getEva', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				showFinishBtn(data.data.status);
				document.getElementById('task_name').innerHTML = data.data.taskName;
				document.getElementById('task_desc').innerHTML = data.data.taskDesc;
				document.getElementById('weight').innerHTML = data.data.weight;
				document.getElementById('empName').innerHTML = data.data.empName;
				if(data.data.status == 0){
					document.getElementById('status').innerHTML = "进行中";
				} else if(data.data.status == 1){
					document.getElementById('status').innerHTML = "已完成";
				}
				document.getElementById('score').value = data.data.score;
				document.getElementById('performance').innerHTML = data.data.performance;
				var createTime = data.data.createTime;
				var lastUpdTime = data.data.lastUpdTime;
				var finishTime = data.data.finishTime;
				document.getElementById('create_time').innerHTML = EPES.formatDateTime(createTime * 1000);
				if(lastUpdTime != null){
					document.getElementById('last_upd_time').innerHTML = EPES.formatDateTime(lastUpdTime * 1000);
				}if(finishTime != null){
					document.getElementById('finish_time').innerHTML = EPES.formatDateTime(finishTime * 1000);
				}
			}
		}
	});

	/* 完成任务按钮 */
	var finishBtn = document.getElementById('finish');
	finishBtn.addEventListener('tap', function(){
		mui.confirm('确认完成该任务？', 'Hello MUI', btnArray, function(e){
			if (e.index == 1) {
				mui.ajax(urlPattern.value+'/taskEva/finish', {
					data: {"score": document.getElementById('score').value},
					dataType:'json',//服务器返回json格式数据
					type:'post',//HTTP请求类型
					success: function(data){
						if(data.status == "200"){
							mui.toast('任务已完成');
							mui.back();
						}
					}
				});
			}
		}, 'div'); 
		var btnArray = ['确认', '取消'];
	});
})(mui);

/* 重新显示完成按钮 */
function showFinishBtn(status){
	if(status == 0){
		$("#finish").text("完成");
	} else if(status == 1){
		$("#finish").addClass("finish");
		$("#finish").text("已完成");
		$("#finish").attr("disabled", true);
	}
	$("#finish").css("display", "block");
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