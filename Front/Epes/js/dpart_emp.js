(function($){
// 	var list = document.getElementById('list');
	
	// log_btn.addEventListener('tap', function(){
	// 	mui.openWindow({
	// 		url: 'e_daily_log.html'
	// 	});
	// });
	
	/* 右上角添加员工 */
	document.getElementById("add_btn").addEventListener('tap', function(e) {
		mui.openWindow({
			url: 'new_emp.html'
		});
	});
	
	/* 员工item */
	mui(".emp-item").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'a_emp_info.html'
		});
	});
	
	/* 员工item左滑删除 */
	$('.dpart-item').on('tap', '.mui-btn', function(event) {
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
	
})(mui);

