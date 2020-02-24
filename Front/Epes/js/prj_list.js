mui.init({
	pullRefresh: {
		container: '#prj_list',
		down: {
			style:'circle',//必选，下拉刷新样式，目前支持原生5+ ‘circle’ 样式
		    color:'#2BD009', //可选，默认“#2BD009” 下拉刷新控件颜色
		    height:'50px',//可选,默认50px.下拉刷新控件的高度,
		    range:'100px', //可选 默认100px,控件可下拉拖拽的范围
		    offset:'10px', //可选 默认0px,下拉刷新控件的起始位置
			callback: pulldownRefresh
		}
	}
});

(function($){
	
	/* 右上角过滤 */
	var filt_btn = document.getElementById('filt_btn');
	filt_btn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'prj_filt.html'
		});
	});
	
	mui(".prj-list").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'prj_detail.html'
		});
	});
	
	/* 项目item右滑删除 */
	$('.prj-list').on('tap', '.mui-btn', function(event) {
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
	
	/* 新建项目 */
	var crePrj = document.getElementById('cre_prj');
	crePrj.addEventListener('tap', function(){
		mui.openWindow({
			url: 'prj_filt.html'
		});
	});
	 
})(mui);
/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh() {
	setTimeout(function() {
		mui('#prj_list').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 1000);
}

