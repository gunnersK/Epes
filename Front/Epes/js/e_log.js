(function($) {
	var check_all = document.getElementById('check_all');
	
	check_all.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_log_list.html'
		});
		mui('#topPopover').popover('toggle');//show hide toggle
	});
})(mui);