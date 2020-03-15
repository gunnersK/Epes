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
	var search = document.getElementById('search');
	search.blur();
	
	loadFirstPage();
	
	/* 右上角过滤 */
	var filBtn = document.getElementById('filt_btn');
	filBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'a_task_filt.html'
		});
	});
	
	/* 任务item */
	mui(".task_list").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'task_detail.html'
		});
	});
	
	/* 任务item右滑删除 */
	$('.task_list').on('tap', '.mui-btn', function(event) {
		var elem = this;
		var li = elem.parentNode.parentNode;
		mui.confirm('确认删除该条记录？', 'Hello MUI', btnArray, function(e) {
			if (e.index == 0) {
				li.parentNode.removeChild(li);
			} else {
				setTimeout(function() {
					$.swipeoutClose(li);
				}, 0);
			}
		});
	});
	var btnArray = ['确认', '取消'];
	
	/* 新建任务 */
	var creTask = document.getElementById('cre_task');
  	creTask.addEventListener('tap', function(){
		mui.openWindow({
			url: 'new_task.html'
		});
	});
	
})(mui);

/* 搜索框监听 */
$("#search").keyup(function(){
	loadFirstPage();
	
	mui('#pullrefresh').pullRefresh().refresh(true); //重置上拉加载
}) 

/* 加载第一页数据 */
function loadFirstPage(){
	mui.ajax(urlPattern.value+'/prjTask/list', {
		data: {
			"page": 1,
			"size":10,
			"taskName": document.getElementById('search').value
		},
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".task-item").remove();
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
		mui.ajax(urlPattern.value+'/prjTask/list', {
			data: {
				"page": current,
				"size": 10,
				"taskName": document.getElementById('search').value
			},
			dataType:'json',//服务器返回json格式数据
			type:'get',//HTTP请求类型
			success: function(data){	
				if(data.status == "200"){
					addItem(data);
				}	
				if(data.data.length == 0){
					mui('#pullrefresh').pullRefresh().endPullupToRefresh(true); //参数为true代表没有更多数据了。
					current = 1;
				}
			}
		});
		mui('#pullrefresh').pullRefresh().endPullupToRefresh(false); //参数为true代表没有更多数据了。
	}, 500);
}

/* 刷新时添加项目item */
function addItem(data){
	for(i = 0; i < data.data.length; i++){
		var item = "<li class='mui-table-view-cell task-item' id="+data.data[i].taskId+">"
			+"<div class='mui-slider-right mui-disabled'><a class='mui-btn mui-btn-red'>删除</a></div>"
			+"<div class='mui-table mui-slider-handle'><div class='mui-table-cell mui-col-xs-10'>"
			+"<h4 class='mui-ellipsis'>"+data.data[i].taskName+"</h4><h5>"+data.data[i].prjName+"</h5></div></div></li>"
		$("#task_list").append(item);
	} 
}
