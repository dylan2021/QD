function HighchartsPluginMark (H) {
	var flagUrl = util.handleImageUrl("flag.png");

	function clearFlags(chart) {
		var flags = chart._flags, labels = chart._labels;
		if(flags && flags.length) {
			flags.forEach(function(item){
				item.destroy();
			});
			chart._flags.length = 0;
		}
		if(labels && labels.length) {
			labels.forEach(function(item){
				item.destroy();
			});
			chart._labels.length = 0;
		}
	}
	function hitFlag(chart) {
		var flags = [], labels = [], flagList = [];
		var chartWidth = chart.chartWidth,
			chartHeight = chart.chartHeight,
			chartPlotLeft = chart.plotLeft,
			chartPlotTop = chart.plotTop;
		chart.series.forEach(function(e, index){
			e.data.forEach(function(item) {
				if(item.marked) {	
					if(!item.plotX) return;
					var x = item.plotX + chartPlotLeft - 3, 
						y = chart.series[index].yAxis.top + item.plotY  - 15;
					if(y < 0) y = 10;
					var text = (item.mark.match(/.{0,15}/g) || []).join("<br/>");
					var _label = chart.renderer.label(text, x - 20, y + 20,  'callout', x + 3,  y, true).css({
						color: '#FFFFFF',
						whiteSpace: 'normal',
					}).attr({
						fill: 'rgba(0, 0, 0, 0.75)',
						padding: 8,
						r: 5,
						zIndex: 6
					}).add();
					_label.hide();
					_label.text.hide();
					var _x = _label.x, _y = _label.y, _w = _label.width, _h = _label.height;
					if(_x + _w > chartWidth) {
						_label.attr({x: chartWidth - _w, anchorX: _x + 20})
					}
					if(_y + _h > chartHeight) {
						_label.attr({y: _y - _h - 25, anchorY: _y + 10})
					}
	
	
					var _flag = chart.renderer.image(flagUrl,  x, y, 14, 14);
	
					_flag.on('click', function(){
						item.firePointEvent('click');
						if(_label.visibility === "hidden"){
							_label.text.show();
							_label.show().toFront();
						}else{
							_label.text.hide();
							_label.hide();
						}
					});
					_flag.add().toFront();
	
					if(!chart.series[index].visible){
						_flag.hide();
					}

					flags.push(_flag);
					labels.push(_label);
					flagList.push(_flag);
				}
	
				flags.length > 0 && (chart._flags = flags);
				labels.length > 0 && (chart._labels = labels);
			})
			chart.series[index]._flagList = flagList;
			flagList = [];
		})
		
	}
	H.Chart.prototype.callbacks.push(function(chart){
		hitFlag(chart);
		H.addEvent(chart, 'redraw', function(){
			clearFlags(chart);
			hitFlag(chart);
		})

	})
}

window.HighchartsPluginYAxis = HighchartsPluginMark;