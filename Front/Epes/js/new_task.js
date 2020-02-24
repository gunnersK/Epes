(function($) {

	/* 项目picker */
	var prjPicker = new $.PopPicker();
	prjPicker.setData([{
		value: 'ywj',
		text: 'OL'
	}, {
		value: 'aaa',
		text: 'Maversal'
	}]);
	var prj = document.getElementById('prj');
	prj.addEventListener('tap', function(event) {
		prjPicker.show(function(items) {
			prj.setAttribute("value", JSON.stringify(items[0].text));
			//返回 false 可以阻止选择框的关闭
			//return false;
		});
	}, false);
	
})(mui);