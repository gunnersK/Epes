(function($) {
	var btns = $('.date');
	btns.each(function(i, btn) {
		btn.addEventListener('tap', function() {
			var _self = this;
			var res = _self.getElementsByClassName('result')
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
				res[0].innerHTML = rs.y.text + '-' + rs.m.text + '-' + rs.d.text;
				res[0].data = rs.y.text + '-' + rs.m.text + '-' + rs.d.text;
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
	
	/* 确认按钮*/
	var confBtn = document.getElementById("confirm");
	confBtn.addEventListener('tap', function(){
		var startTime = document.getElementById("start_res").data;
		var endTime = document.getElementById("end_res").data;
		if(startTime == undefined){
			startTime = "";
		} else{
			var startTime = Date.parse(new Date(startTime)) / 1000;
		}
		if(endTime == undefined){
			endTime = "";
		} else{
			var endTime = Date.parse(new Date(endTime)) / 1000;
		}
		mui.ajax(urlPattern.value+'/notice/transFilter', {
			data: {
				"startTime": startTime,
				"endTime": endTime,
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.back();
				}
			}
		});
	});
})(mui);