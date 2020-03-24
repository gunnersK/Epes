(function($) {
	var allNtc = document.getElementById('all_ntc');
	
	allNtc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_log_list.html'
		});
	});
})(mui);