<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<html>
<head>
    <meta name="layout" content="mainBS"/>
    <g:javascript>
        var west;
        var east;
        function showGroup(direction, group) {
            var table;
            var chart;
            if (direction == "West") {
                table = west
                chart = $("#WestChart");
            } else {
                table = east
                chart = $("#EastChart");
            }
            table.columns('.group').visible(false);
            table.columns('.' + group).visible(true);
            table.draw();
            chartTable(table, chart)
        }

        function chartTable(table, chart) {
            chart.children().remove();
            var stations = [];
            table.column(0).nodes().to$().each(function (cell) {
                stations[cell] = $(this).html();
            });
            var usedHeaders = [
                '# Of Peak Trains',
//                'Avg Ride Time',
                'Avg Wait Between Peaks',
                'Longest Wait Between Peaks',
                'Std Dev Between Peaks',
                'Median Wait Between Peaks'
            ];
            var dataMatrix = [];
            var matrixCount = -1;
            var headers = table.columns().header().to$();
            var visible = table.columns().visible();
            var rows = table.rows().data();
            rows.each(function (row) {
                ++matrixCount;
                var station = row[0];
                var dataValues = [];
                var valueCounter = -1;
                for (var i = 2; i < row.length; ++i) {
                    var name = headers[i].innerHTML;
                    if (visible[i] &&
                            name != "First Peak" &&
                            name != "Last Peak" &&
                            name != "Last Pre Peak" &&
                            name != "Wait for First Peak" &&
                            name != "Avg Ride Time"
                            ) {
                        ++valueCounter;
                        var number = parseInt(row[i]);
                        if (number == 9999) {
                            number = null;
                        }
                        dataValues[valueCounter] = number;
                    }
                }
                dataMatrix[matrixCount] = {
                    name: station,
                    data: dataValues
                }
            });
            chart.highcharts({
                chart: {
                    zoomType: 'xy',
                    type: 'column'
                },
                title: {
                    text: 'Peak Analysis'
                },
                xAxis: [
                    {
                        categories: usedHeaders
                    }
                ],
                yAxis: [
                    {
                        title: {
                            text: 'Minutes'
                        },
                        opposite: true
                    },
                    {
                        gridLineWidth: 0,
                        title: {
                            text: '# Of Peak Trains'
                        }
                    },


                ],
                series: dataMatrix
            });
        }

        function showMe() {
            var id = $("#analysis").val();
            var zone = $("#zone").val();
            $("#reportContent").children().remove();
            $("#showMe").button('loading');
            $.post(
                    "show",
                    {"id": id, "zone": zone},
                    function (data) {
                        $("#reportContent").append(data);
                        west = $("#West").DataTable({
                            sDom: 'rt',
                            paging: false,
                            bAutoWidth: false,
                            width: "100%",
                            order: [
                                [0, "asc"]
                            ]
                        });
                        east = $("#East").DataTable({
                            sDom: 'rt',
                            paging: false,
                            bAutoWidth: false,
                            width: "100%",
                            order: [
                                [0, "asc"]
                            ]
                        });
                        showGroup('West', 'group-0');
                        showGroup('East', 'group-0');
                        $("#showMe").button('reset');
//                        chartTable(west, $("#WestChart"));
//                        chartTable(east, $("#EastChart"));
                    });

        }

        $(document).ready(function () {
                    $("#zone").append('<option value=ALL>All</option>');
                }
        )
    </g:javascript>
</head>

<body>
<form class="form-inline top-input" action="javascript:showMe()">
    <div class="form-group">
        <label for="analysis">When:</label>
        <g:select class="form-control" name="analysis" from="${stringInstanceMap.entrySet()}" optionKey="key"
                  optionValue="value"/>
        <label for="zone">Zone:</label>
        <g:select class="form-control text-right" name="zone" from="${Zone.values()}" value="numeric"
                  optionKey="numeric" optionValue="numeric"/>
        <button id="showMe" data-loading-text="Loading.." class="btn btn-default">Show Me</button>
    </div>
</form>

<div id="reportContent">
</div>
</body>
</html>