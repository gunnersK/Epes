(function($){
	
	/* 任务item */
	mui(".task_list").on('tap', '.mui-table-view-cell', function(){
		mui.openWindow({
			url: 'task_detail.html'
		});
	});
	
	/* 任务item右滑删除 */
	$('.task_list').on('tap', '.mui-btn', function(event) {
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
	
	var filt_btn = document.getElementById('filt_btn');
	filt_btn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'a_task_filt.html'
		});
	});
	
	/* 新建任务 */
	var creTask = document.getElementById('cre_task');
  	creTask.addEventListener('tap', function(){
		mui.openWindow({
			url: 'new_task.html'
		});
	});
	
})(mui);
