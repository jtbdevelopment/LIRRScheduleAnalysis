<%@ page import="com.jtbdevelopment.LIRR.dataobjects.core.Zone" %>
<html>
<head>
    <meta name="layout" content="mainBS"/>
    <g:javascript>
        $(document).ready(function () {
            markTabActive("#overview")
        })
    </g:javascript>
</head>

<body>
<h2>Quick Summary</h2>

<p>Analysis Shows the Details - Number of Trains, Average/Std Dev/Median Time Between Trains, etc</p>

<p>Score Breaks the Analysis into Quartiles - 4 points for each top quartile rank for a station, 1 point for bottom quartile</p>
</body>
</html>