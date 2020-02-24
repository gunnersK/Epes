(function($){
// 	var list = document.getElementById('list');
	
	// log_btn.addEventListener('tap', function(){
	// 	mui.openWindow({
	// 		url: 'e_daily_log.html'
	// 	});
	// });
	mui(".dpart-item").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'dpart_emp.html'
		});
	});
	
})(mui);

