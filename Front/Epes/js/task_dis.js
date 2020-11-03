mui.init({
	pullRefresh: {
		container: '#task_list',
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

/* 加载未完成任务 */
$(".task_item").remove();
mui.ajax(urlPattern.value+'/prjTask/listUnfinish', {
	dataType:'json',//服务器返回json格式数据
	type:'get',//HTTP请求类型
	success: function(data){
		if(data.status == "200"){
			for(i = 0; i < data.data.length; i++){
				var item = "<div class='mui-input-row mui-radio' ><span class='task_item' id="+data.data[i].taskId+">"+
					data.data[i].taskName+"</span><input type='radio' name='task_item' id="+data.data[i].taskId+"></div>";
				$("#task_list").append(item);
			}
		}
	}
});

(function($){

	/* 任务item详情显示 */
	mui("#task_list").on('tap', '.task_item', function(){
		mui.ajax(urlPattern.value+'/prjTask/transId', {
			data: {
				"taskId": this.id
			},
			dataType:'json',//服务器返回json格式数据
			type:'post',//HTTP请求类型
			success: function(data){
				if(data.status == "200"){
					mui.openWindow({
						url: 'task_detail.html'
					});
				}
			},
			error: function(){
				mui.toast('失败', { duration:'long', type:'div' });
			}
		}); 
	}); 
	
	/* 选择员工 */
	var selEmpBtn = document.getElementById('sel_emp_btn');
	selEmpBtn.addEventListener('tap', function(){
		
		//获取选中的任务id
		var taskId = undefined;
		var obj=document.getElementsByTagName("input");
		for(var i=0; i<obj.length; i++){
			if(obj[i].checked){
				taskId = obj[i].id;
			}
		}
		if(taskId != undefined){
			mui.ajax(urlPattern.value+'/prjTask/transId', {
				data: {
					"taskId": taskId
				}, 
				dataType:'json',//服务器返回json格式数据
				type:'post',//HTTP请求类型
				success: function(data){
					if(data.status == "200"){
						mui.openWindow({
							url: 'd_sel_emp.html'
						});
					}
				}
			});
		} else{
			mui.toast('请选择任务', { duration:'long', type:'div' });
		}
	});
	
})(mui);

/**
 * 下拉刷新具体业务实现
 */
function pulldownRefresh() {
	setTimeout(function() {
		mui('#task_list').pullRefresh().endPulldownToRefresh(); //refresh completed
	}, 1000);
}
