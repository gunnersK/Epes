(function($){
// 	var list = document.getElementById('list');
	
	// log_btn.addEventListener('tap', function(){
	// 	mui.openWindow({
	// 		url: 'e_daily_log.html'
	// 	});
	// });
	mui(".emp-item").on('tap', '.mui-table-view-cell', function(){
		var item = this.children[0];
		mui('#mod_popover').popover('toggle', item);//show hide toggle
	});
	
})(mui);

