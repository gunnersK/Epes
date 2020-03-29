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
	}
});

window.addEventListener('refresh', function(e){//执行刷新
	  location.reload();
});

(function($){
	
	loadFirstPage();
	
	/* 右上角过滤 */
	var filt_btn = document.getElementById('filt_btn');
	filt_btn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_date_filt.html'
		});
	});
	
	/* 公告item */
	mui("#ntc_list").on('tap', '.mui-table-view-cell', function(){
		mui.ajax(urlPattern.value+'/notice/transId', {
			data: {
				"ntId": this.id
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.openWindow({
						url: 'notice_detail.html'
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
	mui.ajax(urlPattern.value+'/notice/list', {
		data: {
			"page": 1,
			"size":10
		},
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".ntc-item").remove();
				addItem(data);
			}
		}
	});
}

var current = 1;
/* 下拉刷新加载下一页 */
function pullupRefresh() {
	setTimeout(function() {
		current++;
		mui.ajax(urlPattern.value+'/notice/list', {
			data: {
				"page": current,
				"size": 10
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
	for(i = 0; i < data.data.length; i++){
		var time = data.data[i].createTime;
		var item = "<li class='mui-table-view-cell ntc-item' id="+data.data[i].ntId+">"
			+"<div class='mui-table'><h4 class='mui-ellipsis'>"+data.data[i].title+"</h4>"
			+"<p class='mui-h6 mui-ellipsis'>"+EPES.formatDateTime(time * 1000)+"</p></div>"
		$("#ntc_list").append(item);
	} 
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
		return now.format("yyyy-MM-dd hh:mm:ss");
	}

}
