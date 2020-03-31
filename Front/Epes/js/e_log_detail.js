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