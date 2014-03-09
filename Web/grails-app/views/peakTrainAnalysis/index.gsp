<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<html>
<head>
    <meta name="layout" content="mainBS"/>
    <g:javascript>
        var west;
        var east;
        function showGroup(direction, group) {
            var table;
            if (direction == "West") {
                table = west
            } else {
                table = east
            }
            table.columns('.group').visible(false);
            table.columns('.' + group).visible(true);
        }

        function showMe() {
            var id = $("#analysis").val();
            var zone = $("#zone").val();
            $("")
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
                    });

        }

        $(document).ready(function () {
            $("#zone").append('<option value=ALL>All</option>');
        })
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