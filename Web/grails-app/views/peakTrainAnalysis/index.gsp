<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<html>
<head>
    <meta name="layout" content="mainBS"/>
    <g:javascript>
        var west;
        var east;
        function showGroup(group) {
            west.columns('.group').visible(false);
            west.columns('.' + group).visible(true);
            east.columns('.group').visible(false);
            east.columns('.' + group).visible(true);
        }
        function showMe() {
            var id = $("#analysis").val();
            var zone = $("#zone").val();
            $.post(
                    "show",
                    {"id": id, "zone": zone},
                    function (data) {
                        $("#reportContent").children().remove();
                        $("#reportContent").append(data);
                        west = $("#West").DataTable({
                            paging: false,
                            bAutoWidth: false,
                            width: "100%",
                            order: [
                                [0, "asc"]
                            ]
                        });
                        east = $("#East").DataTable({
                            paging: false,
                            bAutoWidth: false,
                            width: "100%",
                            order: [
                                [0, "asc"]
                            ]
                        });
                        showGroup('group-0');
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
        <label for="zone">Zone:</label>
        <g:select class="form-control text-right" name="zone" from="${Zone.values()}" value="numeric"
                  optionKey="numeric" optionValue="numeric"/>
        <g:submitButton name="Show Me" class="btn btn-default"/>
    </div>
</form>

<div id="reportContent">
</div>
</body>
</html>