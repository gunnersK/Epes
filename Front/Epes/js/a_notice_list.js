(function($){
	var search = document.getElementById('search');
	search.blur();
	
	loadAllNtc();
	
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
	
	/* 公告item右滑删除 */
	$('#ntc_list').on('tap', '.mui-btn', function(event) {
		var elem = this;
		var li = elem.parentNode.parentNode;
		mui.confirm('确认删除该条记录？', 'Hello MUI', btnArray, function(e) {
			if (e.index == 0) {
				mui.ajax(urlPattern.value+'/notice/delete', {
					data: {
						"ntId": li.id
					},
					dataType:'json',//服务器返回json格式数据
					type:'post',//HTTP请求类型
					success: function(data){
						if(data.status == "200"){
							li.parentNode.removeChild(li);
							mui.toast('删除成功', { duration:'long', type:'div' });
						}
					}
				});
			} else {
				setTimeout(function() {
					$.swipeoutClose(li);
				}, 0);
			}
		});
	});
	var btnArray = ['确认', '取消'];
	
	/* 发布公告 */
	var pubNtc = document.getElementById('pub_ntc');
	pubNtc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'new_notice.html'
		});
	});
	
})(mui);

/* 搜索框监听 */
$("#search").keyup(function(){
	mui.ajax(urlPattern.value+'/notice/search', {
		data: {
			"keyWord": document.getElementById('search').value
		},
		// async: false,
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".ntc-item").remove();
				for(i = 0; i < data.data.length; i++){
					var time = data.data[i].createTime;
					var item = "<li class='mui-table-view-cell ntc-item' id="+data.data[i].ntId+">"
						+"<div class='mui-slider-right mui-disabled'><a class='mui-btn mui-btn-red'>删除</a></div>"
						+"<div class='mui-table mui-slider-handle'><div class='mui-table-cell mui-col-xs-10'>"
						+"<h4 class='mui-ellipsis'>"+data.data[i].title+"</h4><h5>"+EPES.formatDateTime(time * 1000)+"</h5></div></div></li>"
					$("#ntc_list").append(item);
				} 
			}
		}
	});
})

/* 加载所有公告 */
function loadAllNtc(){
	mui.ajax(urlPattern.value+'/notice/list', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".ntc-item").remove();
				for(i = 0; i < data.data.length; i++){
					var time = data.data[i].createTime;
					var item = "<li class='mui-table-view-cell ntc-item' id="+data.data[i].ntId+">"
						+"<div class='mui-slider-right mui-disabled'><a class='mui-btn mui-btn-red'>删除</a></div>"
						+"<div class='mui-table mui-slider-handle'><div class='mui-table-cell mui-col-xs-10'>"
						+"<h4 class='mui-ellipsis'>"+data.data[i].title+"</h4><h5>"+EPES.formatDateTime(time * 1000)+"</h5></div></div></li>"
					$("#ntc_list").append(item);
				} 
			}
		}
	});
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