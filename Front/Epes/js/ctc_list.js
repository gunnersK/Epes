// (function($){
	
	/* 部门list */
	mui.ajax(urlPattern.value+'/department/list', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				for(i = 0; i < data.data.length; i++){
					var item = "<li class='mui-table-view-cell mui-collapse prj-item' id="+data.data[i].dpartId+">"
						+"<a class='mui-navigate-right' href='#'>"+data.data[i].dpartName+"</a></li>";
					$("#dpart_list").append(item);
				} 
			}
		},
		error: function(){
			mui.toast('失败', { duration:'long', type:'div' });
		}
	});

	mui(".emp-item").on('tap', '.mui-table-view-cell', function(){
		var item = this.children[0];
		mui('#mod_popover').popover('toggle', item);//show hide toggle
	});
	
// })(mui);

