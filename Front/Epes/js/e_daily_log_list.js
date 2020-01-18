mui.init({
	pullRefresh: {
		container: '#log_list',
		down: {
			style:'circle',//必选，下拉刷新样式，目前支持原生5+ ‘circle’ 样式
		    color:'#2BD009', //可选，默认“#2BD009” 下拉刷新控件颜色
		    height:'50px',//可选,默认50px.下拉刷新控件的高度,
		    range:'100px', //可选 默认100px,控件可下拉拖拽的范围
		    offset:'10px', //可选 默认0px,下拉刷新控件的起始位置
			callback: pulldownRefresh
		}
	}
});
/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh() {
	setTimeout(function() {
		mui('#log_list').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 1000);
}

(function($) {
	var result = $('#result')[0];
	var res = document.getElementById('result')
	var btns = $('.btn');
	btns.each(function(i, btn) {
		btn.addEventListener('tap', function() {
			var _self = this;
			var optionsJson = this.getAttribute('data-options') || '{}';
			var options = JSON.parse(optionsJson);
			var id = this.getAttribute('id');
			/*
			 * 首次显示时实例化组件
			 * 示例为了简洁，将 options 放在了按钮的 dom 上
			 * 也可以直接通过代码声明 optinos 用于实例化 DtPicker
			 */
			_self.picker = new $.DtPicker(options);
			_self.picker.show(function(rs) {
				/*
				 * rs.value 拼合后的 value
				 * rs.text 拼合后的 text
				 * rs.y 年，可以通过 rs.y.vaue 和 rs.y.text 获取值和文本
				 * rs.m 月，用法同年
				 * rs.d 日，用法同年
				 * rs.h 时，用法同年
				 * rs.i 分（minutes 的第二个字母），用法同年
				 */
				_self.value = rs.y.text + '-' + rs.m.text + '-' + rs.d.text;
				/* 
				 * 返回 false 可以阻止选择框的关闭
				 * return false;
				 */
				/*
				 * 释放组件资源，释放后将将不能再操作组件
				 * 通常情况下，不需要示放组件，new DtPicker(options) 后，可以一直使用。
				 * 当前示例，因为内容较多，如不进行资原释放，在某些设备上会较慢。
				 * 所以每次用完便立即调用 dispose 进行释放，下次用时再创建新实例。
				 */
				_self.picker.dispose();
				_self.picker = null;
			});
			
		}, false);
	});
})(mui);
