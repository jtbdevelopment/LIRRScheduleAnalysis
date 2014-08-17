/**
 * Created by buscej on 3/25/14.
 */
var zones = [];
var lines = [];

var westTable;
var eastTable;
var overallTable;
var miles;
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

function clientFilterTable(oSettings, aData, iDataIndex) {
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
    chartTable(overallTable, overallChart);
    eastTable.draw();
    chartTable(eastTable, eastChart);
    westTable.draw();
    chartTable(westTable, westChart);
}

function computeTickedZones() {
    zones = [];
    $.each($("input[id='zones[]']:checked"), function () {
        zones.push($(this).val());
    });
}

function chartTable(table, chart) {
    chart.children().remove();
    var dataByHeader = initializeDataByHeader();
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
    drawChart(chart, stations, dataByHeader);
}

function computeTickedLines() {
    lines = [];
    $.each($("input[id='lines[]']:checked"), function () {
        lines.push($(this).val());
    });
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
            westChart = $("#WestChart");
            eastChart = $("#EastChart");
            overallChart = $("#OverallChart");
            overallTable = $("#Overall").DataTable(dataTableOptions);
            westTable = $("#West").DataTable(dataTableOptions);
            eastTable = $("#East").DataTable(dataTableOptions);
            $.fn.dataTableExt.afnFiltering.push(clientFilterTable);
            showGroup('Overall', 'group-0');
            showGroup('West', 'group-0');
            showGroup('East', 'group-0');
            $("#showMe").button('reset');
            $("input[id='zones[]']").change(function () {
                computeTickedZones();
                filterTablesAndCharts();
            });
            $("input[id='lines[]']").change(function () {
                computeTickedLines();
                filterTablesAndCharts();
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


