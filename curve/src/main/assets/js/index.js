var DRAW_LINE = "drawLine",
    ADD_REMARK = "addTagging";

// 曲线颜色
var COLOR = ['#2d73c5', '#285c5c', '#f5967d', '#41e741', '#b8860b', '#12b4ab', '#fae919', '#00bfff'];
var chartIns = null;

// 红旗标注
HighchartsPluginYAxis(Highcharts);

// 全局配置
Highcharts.setOptions({
    global: {
        useUTC: false
    },
    lang: {
        contextButtonTitle: '导出图片',
        downloadJPEG: "保存JPEG图片",
        downloadPDF: "保存PDF文档",
        downloadPNG: "保存PNG图片",
        downloadSVG: "保存SVG矢量图",
        loading: "载入中...",
        printChart: "打印曲线",
        resetZoom: "取消放大",
        resetZoomTitle: "取消放大",
        shortMonths: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
        noData: '暂无数据'
    },
    noData: {
        position: {
            verticalAlign: 'middle'
        },
        attr: {
            stroke: '#cccccc'
        },
        style: {
            color: '#202030',
            fontSize: '15px'
        }
    }
});

// 转数值
function washYValue(v) {
    if(v === '' || v === null || Number.isNaN(Number(v))) return null;
    return Number(v);
}

// point 点击事件
var pointClick = function(event){
    if(dsBridge.hasNativeMethod(ADD_REMARK)){
        dsBridge.call(ADD_REMARK, {
            x: event.point.x,
            y: event.point.y,
            mpointId: event.point.mpointId,
            mark: event.point.mark
        });
    }
}

// 页面高度初始化
var heightInit = function (data) {
    if (chartIns) {
        chartIns.destroy();
    }
    var clientHeight = document.querySelector('body').clientHeight;
    var height = (clientHeight - 50) / 3 * data.length;

    if (data.length > 3) {
        height = height + (data.length > 4 ? 70 : 50);
    } else {
        height = clientHeight;
    }
    document.querySelector('.wrap').style.height = height + 'px';
}

// 数据处理
var dataHandel = function (data) {
    return data.map(function (value, index) {
        var xAxis = [], _data = [], values = [];
        if(value.data==null){
           value.data=[];
        }
        value.data.forEach(function (item, ind) {
            xAxis.push(item.dataDate);
            values.push(item.dataValue);
            _data.push({
                x: new Date(item.dataDate).getTime(), 
                y: Number(item.dataValue),
                mark: item.remark,
                marked: !!(item.remark),
                mkid: item.id,
                mpointId: value.mpointId,
                timestamp : new Date(item.dataDate)
            })
        });
        if(value.siteName){
            value.mpointName = value.mpointName  + "--" +  value.siteName;
        }
        return {
            name: value.mpointName,
            minData: washYValue(value.curveYaxisLowerRange),
            maxData: washYValue(value.curveYaxisUpperRange),
            unit: value.unit,
            xAxis: xAxis,
            data: _data
        }
    })
}

// 图表配置初始化
var optionInit = function (data, dateType) {
    var target = document.querySelector('.wrap');
    var clientHeight = target.clientHeight;
    var height = (clientHeight - (data.length * 20 + 50)) / data.length;
    if (data.length > 4) {
        height = (clientHeight - (data.length * 20 + 70)) / data.length;
    }

    var option = {
        chart: {
            zoomType: 'x',
            resetZoomButton: {
                position: {
                    x: -60,
                    y: 10
                },
                relativeTo: 'chart'
            },
            alignTicks: false,
            panning: true,
            panKey: 'ctrl'
        },
        title: {
            text: "",
            style: {
                fontSize: '18px',
                color: '#545454'
            }
        },
        xAxis: {
            type: 'datetime',
            maxPadding: 0,
            minPadding: 0,
            min: new Date(util.formatePolyfill(dateType.start)).getTime(),
            max: new Date(util.formatePolyfill(dateType.end)).getTime() - 1000,
            dateTimeLabelFormats:
            {
                millisecond: '%H:%M:%S',
                second: '%H:%M:%S',
                minute: '%H:%M',
                hour: '%H:%M',
                day: '%b-%e',
                week: '%b-%e',
                month: '%Y-%b',
                year: '%Y'
            }
        },
        tooltip: { // 数据提示框
            xDateFormat: '%Y-%m-%d %H:%M:%S',
            useHTML: true,
            hideDelay: 0,
            crosshairs: true,
            shared: true,
            nearShared: true,
            followTouchMove: true,
            valueSuffix: '第一个单位'
        },
        legend: { // 图例
            enabled: true,
            align: 'center',
//                itemWidth: target.clientWidth / 4,
            margin: 4,
            padding: 6,
            maxHeight: 70,
            y: 0,
            itemStyle: {
                color: '#989898',
                fontSize: "8px",
                fontWeight: 'normal'
            }
        },
        plotOptions: {
            series: {
                lineWidth: 1,
                marker: {
                    radius: 2
                },
                turboThreshold: 100000,
                events:{
                    click: pointClick,
                }
            }
        },
        credits: {
            enabled: false
        }
    };

    var yAxis = [],
        series = [];

    data.forEach(function (value, index) {
        yAxis.push({
            name: value.unit,
            color: COLOR[index],
            shadow: false,
            lineWidth: 1,
            tooltip: { valueSuffix: value.unit },
            offset: 0,
            top: (height + 20) * index + 20,
            height: height,
            min: value.minData,
            max: value.maxData,
            title: {
                text: value.unit
            },
        });
        series.push({
            type: 'spline',
            name: value.name,
            data: value.data,
            color: COLOR[index],
            shadow: false,
            lineWidth: 1,
            yAxis: index,
            tooltip: { valueSuffix: value.unit },
        });
    });
    option.yAxis = yAxis;
    option.series = series;
    chartIns = Highcharts.chart(target, option);
    chartIns.legend.update({
        y: chartIns.xAxis[0].bottom - (data.length > 4 ? 70 : 50)
    })
}

window.onload = function () {
    dsBridge.register(DRAW_LINE, function (res, dateType) {
        var data = dataHandel(res || []);
        heightInit(data);
        optionInit(data, dateType);
    });

    // 假数据测试
    // setTimeout(function () {
    //     var data = dataHandel(mock.items || []);
    //     heightInit(data);
    //     optionInit(data, {
    //         start: 1569772800000,
    //         end: 1569823740000
    //     });
    // }, 1000);
}