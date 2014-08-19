/**
 * Created by buscej on 3/25/14.
 */
var zones = [];
var lines = [];

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
    chartTable();
}

function computeTickedZones() {
    zones = [];
    $.each($("input[id='zones[]']:checked"), function () {
        zones.push($(this).val());
    });
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
            $.fn.dataTableExt.afnFiltering.push(clientFilterTable);
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


