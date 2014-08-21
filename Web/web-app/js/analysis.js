/**
 * Created by buscej on 3/25/14.
 */
var zones = [];
var lines = [];
var showScore = false;

var usedHeaders = [
    '# of Peak Trains',
    'Avg Ride Time',
    'MPH',
    'Avg Wait Between Peaks',
    'Longest Wait Between Peaks',
    'Std Dev Wait Between Peaks',
    'Median Wait Between Peaks'
];
var lessIsBadHeaders = [
    '# of Peak Trains',
    'MPH'
];

var overallTable;
var miles;
var overallChart;

function showGroup(group) {
    overallTable.columns('.group').visible(false);
    overallTable.columns('.group-' + group).visible(true);
    overallTable.draw();
    markSubTabActive(group);
    chartTable();
}

function filterTable(oSettings, aData, iDataIndex) {
    var milesData = miles.getValue();
    var minMiles = milesData[0];
    var maxMiles = milesData[1];

    if (aData[1] < minMiles) {
        return false;
    }
    if (aData[1] > maxMiles) {
        return false;
    }
    if ($.inArray(aData[2], lines) == -1) {
        return false;
    }
    if ($.inArray(aData[3], zones) == -1) {
        return false;
    }
    return true;
}

function filterTablesAndCharts() {
    overallTable.draw();
    chartTable();
}

function computeTickedZones() {
    zones = [];
    $.each($("input[id='zones[]']:checked"), function () {
        zones.push($(this).val());
    });
}

function initializeDataByHeader() {
    var dataByHeader = [];
    for (var i = 0; i < usedHeaders.length; ++i) {
        var axis;
        var type;
        if (showScore) {
            axis = 0;
            type = 'area'
        } else {
            axis = i < 2 ? i : 2;
            type = i < 1 ? 'column' : 'line';
        }
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
    if (showScore) {
        drawChartScore(chart, stations, dataByHeader);
    } else {
        drawChartRegular(chart, stations, dataByHeader);
    }
}

function drawChartScore(chart, stations, dataByHeader) {
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

function drawChartRegular(chart, stations, dataByHeader) {
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
                categories: stations,
                labels: {
                    enabled: stations.length < 26
                }
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

function scoreData(dataByHeader) {
    for (var i = 0; i < usedHeaders.length; ++i) {
        var data = dataByHeader[i].data;
        var sortedData = data.filter(function (element) {
            return element != null;
        });
        sortedData.sort();
        var quartile = sortedData[Math.round(sortedData.length / 4) - 1];
        var half = sortedData[Math.round(sortedData.length / 2) - 1];
        var threequartile = sortedData[Math.round(sortedData.length / 4 * 3) - 1];

        var lessIsBad = (jQuery.inArray(usedHeaders[i], lessIsBadHeaders) > -1);
        for (var j = 0; j < data.length; ++j) {
            if (data[j] <= quartile) {
                if (lessIsBad) {
                    data[j] = 1;
                } else {
                    data[j] = 4;
                }
            } else if (data[j] <= half) {
                if (lessIsBad) {
                    data[j] = 2;
                } else {
                    data[j] = 3;
                }
            } else if (data[j] <= threequartile) {
                if (lessIsBad) {
                    data[j] = 3;
                } else {
                    data[j] = 2;
                }
            } else {
                if (lessIsBad) {
                    data[j] = 4;
                } else {
                    data[j] = 1;
                }
            }
        }
    }
}

function chartTable() {
    overallChart.children().remove();
    var dataByHeader = initializeDataByHeader();
    var matrixCount = -1;
    var headers = overallTable.columns().header().to$();
    var rows = overallTable.$('tr', {'filter': 'applied'});
    var stations = [];
    rows.each(function (rowCounter) {
        var row = rows[rowCounter].cells;
        ++matrixCount;
        stations[matrixCount] = row[0].innerHTML;
        for (var i = 0; i < row.length; ++i) {
            var name = headers[i].innerHTML;
            var headerPosition = jQuery.inArray(name, usedHeaders);
            if (headerPosition > -1) {
                var number = parseInt(row[i].innerHTML);
                if (number == 9999) {
                    number = null;
                }
                dataByHeader[headerPosition].data[matrixCount] = number;
            }
        }
    });
    if (showScore) {
        scoreData(dataByHeader);
    }
    drawChart(overallChart, stations, dataByHeader);
}

function computeTickedLines() {
    lines = [];
    $.each($("input[id='lines[]']:checked"), function () {
        lines.push($(this).val());
    });
}

function markSubTabActive(name) {
    $(".subtab").removeClass("active");
    $(".subtab-" + name).addClass("active");
}

function markTabActive(name) {
    $("nav nav-pills li").removeClass("active");
    $(name).addClass("active");
}

function allOrNoZones(value) {
    $.each($("input[id='zones[]']"), function () {
        $(this).prop('checked', value);
    });
    computeTickedZones();
    filterTablesAndCharts();
}

function allOrNoLines(value) {
    $.each($("input[id='lines[]']"), function () {
        $(this).prop('checked', value);
    });
    computeTickedLines();
    filterTablesAndCharts();
}

function allZones() {
    allOrNoZones(true);
}

function allLines() {
    allOrNoLines(true);
}

function noZones() {
    allOrNoZones(false);
}

function noLines() {
    allOrNoLines(false);
}

function showAnalysis() {
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
            miles = $("#miles").slider().on('slideStop', filterTablesAndCharts).data('slider');
            overallChart = $("#chartArea");
            overallTable = $("#tableData").DataTable(dataTableOptions);
            $.fn.dataTableExt.afnFiltering.push(filterTable);
            $('#asscore').click(function () {
                if ($(this).is(':checked')) {
                    showScore = true;
                } else {
                    showScore = false;
                }
                chartTable();
            });
            showGroup('0');
            $("#showMe").button('reset');
            $("input[id='zones[]']").change(function () {
                computeTickedZones();
                filterTablesAndCharts();
            });
            $("input[id='lines[]']").change(function () {
                computeTickedLines();
                filterTablesAndCharts();
            });
        });
}


