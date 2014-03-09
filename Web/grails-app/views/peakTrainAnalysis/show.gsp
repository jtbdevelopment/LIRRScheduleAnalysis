<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Direction" %>
<body>
<div class="panel-group" id="accordion">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs">
        <li class="active"><a href="#amPeak" data-toggle="tab">AM Peak</a></li>
        <li><a href="#pmPeak" data-toggle="tab">PM Peak</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div class="tab-pane active" id="amPeak">
            <g:render template="peaksTemplate"
                      model="[direction: Direction.West,
                              detailsPerGroup: detailsPerGroup,
                              groupsPerDirection: groupsPerDirection,
                              analysisInstanceList: analysisInstanceList
                      ]"/>
        </div>

        <div class="tab-pane" id="pmPeak">
            <g:render template="peaksTemplate"
                      model="[direction: Direction.East,
                              detailsPerGroup: detailsPerGroup,
                              groupsPerDirection: groupsPerDirection,
                              analysisInstanceList: analysisInstanceList
                      ]"/>
        </div>
    </div>
</div>
