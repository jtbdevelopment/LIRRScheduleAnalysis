<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<html>
<head>
    <meta name="layout" content="mainBS"/>
    <g:javascript>
        $(document).ready(function () {
            markTabActive("#peakTrainAnalysis")
        })
    </g:javascript>
</head>

<body>
<g:render template="/shared/analysisDropDown"/>

</body>
</html>