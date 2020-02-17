// $(function(){
	var logBtn = document.getElementById('log_btn');
	var perfBtn = document.getElementById('perf_btn');
	var ctcBtn = document.getElementById('ctc_btn');
	var logManaBtn = document.getElementById('log_mana_btn');
	var perfEvaBtn = document.getElementById('perf_eva_btn');
	var allNtc = document.getElementById('all_ntc');
	var ntcContent = document.getElementById('ntc_content');
	var empInfo = document.getElementById('emp_info');
	
	logBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_log.html'
		});
	});
	
	perfBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'perf_list.html'
		});
	});
	
	ctcBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'ctc_list.html'
		});
	});
	
	logManaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'd_log_list.html'
		});
	});
	
	perfEvaBtn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'd_perf_list.html'
		});
	});
	
	allNtc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_list.html'
		});
	})
	
	ntcContent.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_detail.html'
		});
	})
	
	empInfo.addEventListener('tap', function(){
		mui.openWindow({
			url: 'emp_info.html'
		});
		mui('#topPopover').popover('toggle');//show hide toggle
	})
// });

// 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('chart'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                // text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data:['销量']
            },
            xAxis: {
                data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
            },
            yAxis: {},
			grid: {
				x: 35,
				x2: 10,
				y: 30,
				y2: 25,
			},
            series: [{
                name: '销量',
                type: 'line',
                data: [5, 20, 36, 10, 10, 20]
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

