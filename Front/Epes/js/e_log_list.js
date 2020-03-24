mui.init({
	pullRefresh: {
		container: '#log_list',
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
/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh() {
	setTimeout(function() {
		mui('#log_list').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 1000);
}
(function($){
	var filt_btn = document.getElementById('filt_btn');
	
	filt_btn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_log_filt.html'
		});
	});
	
	mui(".log-list").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'e_log_detail.html'
		});
	});
	
})(mui);

