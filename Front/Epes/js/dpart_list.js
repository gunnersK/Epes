(function($){
	
	loadList();
	
	/* 右上角添加部门 */
	document.getElementById("add_btn").addEventListener('tap', function(e) {
		e.detail.gesture.preventDefault(); //修复iOS 8.x平台存在的bug，使用plus.nativeUI.prompt会造成输入法闪一下又没了
		var btnArray = ['确定', '取消']; //e.index:[0,1]
		mui.prompt(' ', ' ', '新部门名称', btnArray, function(e) {
			if (e.index == 0) {
				if(e.value != ""){
					mui.ajax(urlPattern.value+'/department/save', {
						data: {
							"dpartName": e.value,
						},
						dataType:'json',//服务器返回json格式数据
						type:'post',//HTTP请求类型
						success: function(data){
							if(data.status == "200"){
								loadList();
								mui.toast('新增成功', { duration:'long', type:'div' });
							}
						}, 
						error: function(){
							mui.toast('新增失败', { duration:'long', type:'div' });
						}
					});
				} else{
					mui.toast('请输入部门名称', { duration:'long', type:'div' });
				}
			}
		}, 'div')
	});
	
	/* 部门item左滑删除 */
	$('#dpart_list').on('tap', '.mui-btn', function(event) {
		var elem = this;
		var li = elem.parentNode.parentNode;
		mui.confirm('确认删除该条记录？', 'Hello MUI', btnArray, function(e) {
			if (e.index == 0) {
				mui.ajax(urlPattern.value+'/department/delete', {
					data: {
						"dpartId": li.id
					},
					dataType:'json',//服务器返回json格式数据
					type:'post',//HTTP请求类型
					success: function(data){
						if(data.status == "200"){
							li.parentNode.removeChild(li);
							mui.toast('删除成功', { duration:'long', type:'div' });
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

	/* 部门item */
	mui("#dpart_list").on('tap', '.mui-table-view-cell', function(){
		mui.ajax(urlPattern.value+'/department/transId', {
			data: {
				"dpartId": this.id
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.openWindow({
						url: 'dpart_emp.html'
					});
				}
			}, 
			error: function(){
				mui.toast('失败', { duration:'long', type:'div' });
			}
		});
		
	});
	
})(mui);

/* 加载部门list */
function loadList(){
	mui.ajax(urlPattern.value+'/department/list', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				$(".dpart-item").remove();
				for(i = 0; i < data.data.length; i++){
					var item = "<li class='mui-table-view-cell dpart-item' id="+data.data[i].dpartId+"><div class='mui-slider-right mui-disabled'>"
						+"<a class='mui-btn mui-btn-red'>删除</a></div><div class='mui-slider-handle'>"+data.data[i].dpartName+"</div></li>";
					$("#dpart_list").append(item);
				} 
			}
		}
	});
}

