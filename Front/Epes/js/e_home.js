$(function(){
	var log_btn = document.getElementById('log_btn');
	var all_ntc = document.getElementById('all_ntc');
	var ntc_content = document.getElementById('ntc_content');
	
	log_btn.addEventListener('tap', function(){
		mui.openWindow({
			url: 'e_daily_log.html'
		});
	});
	
	all_ntc.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_list.html'
		});
	})
	
	ntc_content.addEventListener('tap', function(){
		mui.openWindow({
			url: 'notice_detail.html'
		});
	})
});

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

