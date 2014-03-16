<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<html>
<head>
<meta name="layout" content="mainBS"/>
<g:javascript>
    var westTable;
    var eastTable;
    var overallTable;
    var miles;
    var zones = [];
    var lines = [];
    var westChart;
    var eastChart;
    var overallChart;

    function showGroup(direction, group) {
        var table;
        var chart;
        if (direction == "West") {
            table = westTable;
            chart = westChart;
        } else if (direction == "East") {
            table = eastTable;
            chart = eastChart;
        } else {
            table = overallTable;
            chart = overallChart;
        }

        table.columns('.group').visible(false);
        table.columns('.' + group).visible(true);
        table.draw();
        chartTable(table, chart)
    }

    function refilterTablesAndCharts() {
        overallTable.draw();
        eastTable.draw();
        westTable.draw();
        chartTable(overallTable, overallChart);
        chartTable(eastTable, eastChart);
        chartTable(westTable, westChart);
    }

    function chartTable(table, chart) {
        chart.children().remove();
        var usedHeaders = [
            '# of Peak Trains',
            'Avg Ride Time',
            'Avg Wait Between Peaks',
            'Longest Wait Between Peaks',
            'Std Dev Wait Between Peaks',
            'Median Wait Between Peaks'
        ];
        var dataByHeader = [];
        for (var i = 0; i < usedHeaders.length; ++i) {
            var axis = i < 2 ? i : 2;
            var type = i < 1 ? 'column' : 'spline';
            dataByHeader[i] = {
                data: [],
                name: usedHeaders[i],
                yAxis: axis,
                type: type
            }
        }
        var matrixCount = -1;
        var headers = table.columns().header().to$();
        var rows = table.$('tr', {'filter': 'applied'});
        var stations = [];
        rows.each(function (rowCounter) {
            var row = rows[rowCounter].cells;
            ++matrixCount;
            var dataValues = [];
            var valueCounter = -1;
            stations[matrixCount] = row[0].innerHTML;
            for (var i = 0; i < row.length; ++i) {
                var name = headers[i].innerHTML;
                var headerPosition = jQuery.inArray(name, usedHeaders);
                if (headerPosition > -1) {
                    ++valueCounter;
                    var number = parseInt(row[i].innerHTML);
                    if (number == 9999) {
                        number = null;
                    }
                    dataValues[valueCounter] = number;
                    dataByHeader[headerPosition].data[matrixCount] = number;
                }
            }
        });
        chart.highcharts({
            chart: {
                zoomType: 'x',
                type: 'column'
            },
            title: {
                text: '',
                enabled: false
            },
            xAxis: [
                {
                    categories: stations
                }
            ],
            yAxis: [
                {
                    title: {
                        text: '# Of Peak Trains'
                    }
                },
                {
                    title: {
                        text: 'Minutes (Rides)'
                    },
                    opposite: false
                },
                {
                    title: {
                        text: 'Minutes (Waits)'
                    },
                    opposite: true
                }
            ],
            series: dataByHeader
        });
    }

    function computeTickedZones() {
        zones = [];
        $.each($("input[id='zones[]']:checked"), function () {
            zones.push($(this).val());
        });
    }

    function computeTickedLines() {
        lines = [];
        $.each($("input[id='lines[]']:checked"), function () {
            lines.push($(this).val());
        });
    }

    function showMe() {
        var id = $("#analysis").val();
        $("#reportContent").children().remove();
        $("#showMe").button('loading');
        $.post(
                "show",
                {"id": id},
                function (data) {
                    $("#reportContent").append(data);
                    var dataTableOptions = {
                        sDom: 'rt',
                        paging: false,
                        bAutoWidth: false,
                        width: "100%",
                        order: [
                            [1, "asc"]
                        ]
                    };
                    westChart = $("#WestChart");
                    eastChart = $("#EastChart");
                    overallChart = $("#OverallChart");
                    computeTickedLines();
                    computeTickedZones();
                    overallTable = $("#Overall").DataTable(dataTableOptions);
                    westTable = $("#West").DataTable(dataTableOptions);
                    eastTable = $("#East").DataTable(dataTableOptions);
                    showGroup('Overall', 'group-0');
                    showGroup('West', 'group-0');
                    showGroup('East', 'group-0');
                    $("#showMe").button('reset');
                    miles = $("#miles").slider().on('slideStop', refilterTablesAndCharts).data('slider');
                    $.fn.dataTableExt.afnFiltering.length = 0;
                    $.fn.dataTableExt.afnFiltering.push(
                            function (oSettings, aData, iDataIndex) {
                                var data = miles.getValue();
                                var minMiles = data[0];
                                var maxMiles = data[1];
                                if (minMiles <= aData[1]
                                        && aData[1] <= maxMiles
                                        && jQuery.inArray(aData[2], lines) >= 0
                                        && jQuery.inArray(aData[3], zones) >= 0) {
                                    return true;
                                }
                                return false;
                            }
                    );
                    $("input[id='zones[]']").change(function () {
                        computeTickedZones();
                        refilterTablesAndCharts();
                    });
                    $("input[id='lines[]']").change(function () {
                        computeTickedLines();
                        refilterTablesAndCharts();
                    });
                    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                        var tab = $('.nav-pills .active').text();
                        if (tab == "Overall") {
                            chartTable(overallTable, overallChart);
                        } else if (tab == "AM Peak") {
                            chartTable(westTable, westChart);
                        } else if (tab == "PM Peak") {
                            chartTable(eastTable, eastChart);
                        }
                    })
                });

    }

</g:javascript>
</head>

<body>
<form class="form-inline top-input" action="javascript:showMe()">
    <div class="form-group">
        <label for="analysis">When:</label>
        <g:select class="form-control" name="analysis" from="${stringInstanceMap.entrySet()}" optionKey="key"
                  optionValue="value"/>
        <button id="showMe" data-loading-text="Loading.." class="btn btn-default">Show Me</button>
    </div>
</form>

<div id="reportContent" class="container-fluid">
</div>
</body>
</html>