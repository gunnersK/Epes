(function($){
// 	var list = document.getElementById('list');
	
	// log_btn.addEventListener('tap', function(){
	// 	mui.openWindow({
	// 		url: 'e_daily_log.html'
	// 	});
	// });
	
	/* 右上角添加部门 */
	document.getElementById("add_btn").addEventListener('tap', function(e) {
		e.detail.gesture.preventDefault(); //修复iOS 8.x平台存在的bug，使用plus.nativeUI.prompt会造成输入法闪一下又没了
		var btnArray = ['确定', '取消']; //e.index:[0,1]
		mui.prompt(' ', ' ', '新部门名称', btnArray, function(e) {
			if (e.index == 1) {
				alert(e.index);
			} else { 
				alert(e.index);
			}
		})
	});
	
	/* 部门item */
	mui(".dpart-item").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'dpart_emp.html'
		});
	});
	
	/* 部门item左滑删除 */
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

