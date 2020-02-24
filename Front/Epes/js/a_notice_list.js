(function($){
	
	/* 公告item */
	mui(".ntc_list").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'notice_detail.html'
		});
	});
	
	/* 公告item右滑删除 */
	$('.ntc_list').on('tap', '.mui-btn', function(event) {
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
	
	// var filt_btn = document.getElementById('filt_btn');
	// filt_btn.addEventListener('tap', function(){
	// 	mui.openWindow({
	// 		url: 'notice_date_filt.html'
	// 	});
	// });
	
	/* 发布公告 */
	var pubNtc = document.getElementById('pub_ntc');
	pubNtc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'new_notice.html'
		});
	});
	
})(mui);
