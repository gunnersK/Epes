(function($){
	var list = document.getElementById('list');
	
	mui(".emp-item").on('tap', '.mui-table-view-cell', function(){
		var item = this.children[0];
		mui('#mod_popover').popover('toggle', item);//show hide toggle
	});
	
})(mui);

