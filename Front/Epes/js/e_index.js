$(function(){
	var log_btn = document.getElementById('log_btn');
	var all_ntc = document.getElementById('all_ntc');
	
	log_btn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_daily_log.html'
		});
	});
	
	all_ntc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_list.html'
		});
	})
});

