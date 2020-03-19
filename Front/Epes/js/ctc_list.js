// (function($){
	
	/* 部门list */
	mui.ajax(urlPattern.value+'/department/list', {
		dataType:'json',//服务器返回json格式数据
		type:'get',//HTTP请求类型
		success: function(data){
			if(data.status == "200"){
				for(i = 0; i < data.data.length; i++){
					var item = "<li class='mui-table-view-cell mui-collapse dpart-item' id="+data.data[i].dpartId+">"
						+"<a class='mui-navigate-right' href='#'>"+data.data[i].dpartName+"</a>"
						+"<ul class='mui-table-view mui-table-view-chevron emp-item'>s</ul></li>";
					$("#dpart_list").append(item);
				} 
			}
		}
	});
	
	/* 部门item */
	mui("#dpart_list").on('tap', '.mui-table-view-cell', function(){
		var empList = this;
		console.log(this.children[1]);
		mui.ajax(urlPattern.value+'/employee/listByDpartId', {
			data: {
				"dpartId": this.getAttribute("id")
			},
			dataType:'json',//服务器返回json格式数据
			type:'get',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					for(i = 0; i < data.data.length; i++){
						var item = "<li class='mui-table-view-cell emp-item' id="+data.data[i].empId+">"
							+"<a class='' data-title-type='native' href='#'>"+data.data[i].empName+"</a></li>";
						empList.children[1].append(item);
					} 
				}
			}
		});
	});

	mui(".emp-item").on('tap', '.mui-table-view-cell', function(){
		var item = this.children[0];
		mui('#mod_popover').popover('toggle', item);//show hide toggle
	});
	
// })(mui);

