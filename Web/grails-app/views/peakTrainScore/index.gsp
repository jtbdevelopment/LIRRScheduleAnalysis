<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<html>
<head>
    <meta name="layout" content="mainBS"/>
    <g:javascript>
        var usedHeaders = [
            '# of Peak Trains',
            'Avg Ride Time',
            'MPH',
            'Avg Wait Between Peaks',
            'Longest Wait Between Peaks',
            'Std Dev Wait Between Peaks',
            'Median Wait Between Peaks'
        ];

        function initializeDataByHeader() {
            var dataByHeader = [];
            for (var i = 0; i < usedHeaders.length; ++i) {
                var axis = 0;
                var type = 'area'
                dataByHeader[i] = {
                    data: [],
                    name: usedHeaders[i],
                    yAxis: axis,
                    type: type
                }
            }
            return dataByHeader;
        }

        function drawChart(chart, stations, dataByHeader) {
            chart.highcharts({
                chart: {
                    zoomType: 'x',
                    type: 'area'
                },
                plotOptions: {
                    area: {
                        stacking: 'normal',
                        trackByArea: true
                    }
                },
                title: {
                    text: '',
                    enabled: false
                },
                xAxis: [
                    {
                        categories: stations,
                        labels: {
                            enabled: stations.length < 26
                        }
                    }
                ],
                yAxis: [
                    {
                        title: {
                            text: 'Total Score'
                        }
                    }
                ],
                series: dataByHeader
            });
        }

        $(document).ready(function () {
            markTabActive("#peakTrainScore")
        })
    </g:javascript>
</head>

<body>
<g:render template="/shared/analysisDropDown"/>
</body>
</html>