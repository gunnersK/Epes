mui.init({
	pullRefresh: {
		container: '#pullrefresh',
		up : {
		  height:50,//可选.默认50.触发上拉加载拖动距离
		  // auto:true,//可选,默认false.自动上拉加载一次
		  contentrefresh : "正在加载...",//可选，正在加载状态时，上拉加载控件上显示的标题内容
		  contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
		  callback :pullupRefresh//必选，刷新函数，根据具体业务来编写，比如通过ajax从服务器获取新数据；
		}
		// down: {
		// 	style:'circle',//必选，下拉刷新样式，目前支持原生5+ ‘circle’ 样式
		//     color:'#2BD009', //可选，默认“#2BD009” 下拉刷新控件颜色
		//     height:'50px',//可选,默认50px.下拉刷新控件的高度,
		//     range:'100px', //可选 默认100px,控件可下拉拖拽的范围
		//     offset:'10px', //可选 默认0px,下拉刷新控件的起始位置
		// 	callback: pulldownRefresh
		// }
	}
});

window.addEventListener('refresh', function(e){//执行刷新
	  location.reload();
});

(function($){
	
	loadFirstPage();
	
	/* 右上角过滤 */
	var filBtn = document.getElementById('filt_btn');
	filBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_log_filt.html'
		});
	});
	
	/* 日志item */
	mui("#log_list").on('tap', '.mui-table-view-cell', function(){
		mui.ajax(urlPattern.value+'/dailyLog/transId', {
			data: {
				"id": this.id
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.openWindow({
						url: 'e_log_detail.html'
					});
				}
			},
			error: function(){
				mui.toast('失败', { duration:'long', type:'div' });
			}
		});
	});
	
})(mui);

/* 加载第一页数据 */
function loadFirstPage(){
	mui.ajax(urlPattern.value+'/dailyLog/listByEmpId', {
		data: {
			"page": 1,
			"size":10,
		},
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".log-item").remove();
				addItem(data);
			}
		}
	});
	// pullupRefresh();
}

var current = 1;
/* 下拉刷新加载下一页 */
function pullupRefresh() {
	setTimeout(function() {
		current++;
		mui.ajax(urlPattern.value+'/dailyLog/listByEmpId', {
			data: {
				"page": current,
				"size": 10,
			},
			dataType:'json',//服务器返回json格式数据
			type:'get',//HTTP请求类型
			success: function(data){	
				if(data.status == "200" && data.data.length > 0){
					addItem(data);
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //参数为true代表没有更多数据了。
				}	
				if(data.data.length == 0){
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(true); //参数为true代表没有更多数据了。
					current = 1;
				}
			}
		});
	}, 500);
}

/* 刷新时添加项目item */
function addItem(data){
	var span;
	var time;
	for(i = 0; i < data.data.length; i++){
		time = data.data[i].createTime;
		if(data.data[i].status == 0){
			span = "<span class='mui-h5' style='color: red;'>未审阅</span>";
		} else{
			span = "<span class='mui-h5'>已审阅</span>";
		}
		var item = "<li class='mui-table-view-cell log-item' id="+data.data[i].id+"><div class='mui-table'>"
			+"<div class='mui-table-cell mui-col-xs-10'><h4 class='mui-ellipsis'>"+data.data[i].content+"</h4>"
			+"<h5>"+data.data[i].empName+"</h5><h5>"+EPES.formatDateTime(time * 1000)+"</h5></div>"
			+"<div class='mui-table-cell mui-col-xs-2 mui-text-right'>"+span+"</div></div></li>";
		$("#log_list").append(item);
	} 
}

/**
 * 下拉刷新具体业务实现
 */
// function pulldownRefresh() {
// 	setTimeout(function() {
// 		mui('#log_list').pullRefresh().endPulldownToRefresh(); //refresh completed
// 	}, 1000);
// }

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