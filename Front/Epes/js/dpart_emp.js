(function($){
// 	var list = document.getElementById('list');
	
	// log_btn.addEventListener('tap', function(){
	// 	mui.openWindow({
	// 		url: 'e_daily_log.html'
	// 	});
	// });
	mui(".emp-item").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'a_emp_info.html'
		});
	});
	
})(mui);

