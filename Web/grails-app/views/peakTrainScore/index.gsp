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
        $("#report-content").children().remove();
        var data = miles.getValue();
        var minMiles = data[0];
        var maxMiles = data[1];
        $.post(
                "filterAnalysis",
                {"lines[]": lines, "zones[]": zones, "minDistance": minMiles, "maxDistance": maxMiles},
                function (data) {
                    $("#report-content").append(data);
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
                    overallTable = $("#Overall").DataTable(dataTableOptions);
                    westTable = $("#West").DataTable(dataTableOptions);
                    eastTable = $("#East").DataTable(dataTableOptions);
                    showGroup('Overall', 'group-0');
                    showGroup('West', 'group-0');
                    showGroup('East', 'group-0');
                });
    }

    function chartTable(table, chart) {
        chart.children().remove();
        var usedHeaders = [
//            'Score',
            '# of Peak Trains',
            'Avg Ride Time',
            'MPH',
            'Avg Wait Between Peaks',
            'Longest Wait Between Peaks',
            'Std Dev Wait Between Peaks',
            'Median Wait Between Peaks'
        ];
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
        $("#report").children().remove();
        $("#showMe").button('loading');
        $.post(
                "show",
                {"id": id},
                function (data) {
                    $("#report").append(data);
                    var dataTableOptions = {
                        sDom: 'rt',
                        paging: false,
                        bAutoWidth: false,
                        width: "100%",
                        order: [
                            [1, "asc"]
                        ]
                    };
                    computeTickedLines();
                    computeTickedZones();
                    westChart = $("#WestChart");
                    eastChart = $("#EastChart");
                    overallChart = $("#OverallChart");
                    overallTable = $("#Overall").DataTable(dataTableOptions);
                    westTable = $("#West").DataTable(dataTableOptions);
                    eastTable = $("#East").DataTable(dataTableOptions);
                    showGroup('Overall', 'group-0');
                    showGroup('West', 'group-0');
                    showGroup('East', 'group-0');
                    $("#showMe").button('reset');
                    miles = $("#miles").slider().on('slideStop', refilterTablesAndCharts).data('slider');
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

    $(document).ready(function () {
        $("nav nav-pills li").removeClass("active");
        $("#peakTrainScore").addClass("active");
    })
</g:javascript>
</head>

<body>
<g:render template="/shared/analysisDropDown"/>

<div id="report" class="container-fluid"/>

</div>
</body>
</html>