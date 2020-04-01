window.addEventListener('refresh', function(e){//执行刷新
	  location.reload();
});

(function($){
	
	loadDpartName();

	loadList();
	
	/* 右上角添加员工 */
	document.getElementById("add_btn").addEventListener('tap', function(e) {
		mui.openWindow({
			url: 'new_emp.html'
		});
	});
	
	/* 员工item */
	mui("#emp_list").on('tap', '.mui-table-view-cell', function(){
		mui.ajax(urlPattern.value+'/employee/transId', {
			data: {
				"empId": this.id
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.openWindow({
						url: 'a_emp_info.html'
					});
				}
			}, 
			error: function(){
				mui.toast('失败', { type:'div' });
			}
		});
	});
	
	/* 员工item左滑删除 */
	$('#emp_list').on('tap', '.mui-btn', function(event) {
		var elem = this;
		var li = elem.parentNode.parentNode;
		mui.confirm('确认删除该条记录？', 'Hello MUI', btnArray, function(e) {
			if (e.index == 0) {
				mui.ajax(urlPattern.value+'/employee/delete', {
					data: {
						"empId": li.id
					},
					dataType:'json',//服务器返回json格式数据
					type:'post',//HTTP请求类型
					success: function(data){
						if(data.status == "200"){
							li.parentNode.removeChild(li);
							mui.toast('删除成功', { type:'div' });
						}
					}
				});
			} else {
				setTimeout(function() {
					$.swipeoutClose(li);
				}, 0);
			}
		}, 'div');
	});
	var btnArray = ['确认', '取消'];
	
})(mui);

/* 加载部门名称 */
function loadDpartName(){
	mui.ajax(urlPattern.value+'/department/getDpart', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$("#title").text(data.data.dpartName);
			}
		}
	});
}

/* 加载员工list */
function loadList(){
	mui.ajax(urlPattern.value+'/employee/list', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".emp-item").remove();
				for(i = 0; i < data.data.length; i++){
					var item = "<li class='mui-table-view-cell emp-item' id="+data.data[i].empId+"><div class='mui-slider-right mui-disabled'>"
						+"<a class='mui-btn mui-btn-red'>删除</a></div><div class='mui-slider-handle'>"+data.data[i].empName+"</div></li>";
					$("#emp_list").append(item);
				} 
			}
		}
	});
}


