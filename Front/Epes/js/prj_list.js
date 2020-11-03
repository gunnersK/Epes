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
	var search = document.getElementById('search');
	search.blur();
	
	loadFirstPage();
	
	/* 右上角过滤 */
	var filBtn = document.getElementById('filt_btn');
	filBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'prj_filt.html'
		});
	});
	
	/* 项目item */
	mui("#prj_list").on('tap', '.mui-table-view-cell', function(){
		mui.ajax(urlPattern.value+'/project/transId', {
			data: {
				"prjId": this.id
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.openWindow({
						url: 'prj_detail.html'
					});
				}
			},
			error: function(){
				mui.toast('失败', { duration:'long', type:'div' });
			}
		});
	});
	
	/* 项目item右滑删除 */
	$('#prj_list').on('tap', '.mui-btn', function(event) {
		var elem = this;
		var li = elem.parentNode.parentNode;
		mui.confirm('确认删除该条记录？', 'Hello MUI', btnArray, function(e) {
			if (e.index == 0) {
				mui.ajax(urlPattern.value+'/project/delete', {
					data: {
						"prjId": li.id
					},
					dataType:'json',//服务器返回json格式数据
					type:'post',//HTTP请求类型
					success: function(data){
						if(data.status == "200"){
							li.parentNode.removeChild(li);
							location.reload();
							mui('#pullrefresh').pullRefresh().refresh(true); //重置上拉加载
							mui.toast('删除成功', { duration:'long', type:'div' });
						}
					}
				});
			} else {
				setTimeout(function() {
					$.swipeoutClose(li);
				}, 0);
			}
		}, 'div');
	});
	var btnArray = ['确认', '取消'];
	
	/* 新建项目 */
	var crePrj = document.getElementById('cre_prj');
	crePrj.addEventListener('tap', function(){
		mui.openWindow({
			url: 'new_prj.html'
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
	mui.ajax(urlPattern.value+'/project/list', {
		data: {
			"page": 1,
			"size":10,
			"prjName": document.getElementById('search').value
		},
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".prj-item").remove();
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
		mui.ajax(urlPattern.value+'/project/list', {
			data: {
				"page": current,
				"size": 10,
				"prjName": document.getElementById('search').value
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
		var item = "<li class='mui-table-view-cell prj-item' id="+data.data[i].prjId+">"
			+"<div class='mui-slider-right mui-disabled'><a class='mui-btn mui-btn-red'>删除</a></div>"
			+"<div class='mui-table mui-slider-handle'>"+data.data[i].prjName+"</div></li>"
		$("#prj_list").append(item);
	} 
}

/**
 * 下拉刷新具体业务实现
 */
	 

