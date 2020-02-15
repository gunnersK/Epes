(function($){
	mui(".ntc_list").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'notice_detail.html'
		});
	});
	
	var filt_btn = document.getElementById('filt_btn');
	
	filt_btn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_date_filt.html'
		});
	});
	
})(mui);
