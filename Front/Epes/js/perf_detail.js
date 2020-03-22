(function($){
	mui.ajax(urlPattern.value+'/taskEva/getEva', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				document.getElementById('task_name').innerHTML = data.data.taskName;
				document.getElementById('task_desc').innerHTML = data.data.taskDesc;
				document.getElementById('weight').innerHTML = data.data.weight;
				document.getElementById('empName').innerHTML = data.data.empName;
				if(data.data.status == 0){
					document.getElementById('status').innerHTML = "进行中";
				} else if(data.data.status == 1){
					document.getElementById('status').innerHTML = "已完成";
				}
				document.getElementById('score').innerHTML = data.data.score;
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
})(mui);

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