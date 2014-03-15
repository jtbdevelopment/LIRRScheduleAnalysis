<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Direction" %>
<body>
<div class="panel-group">
    <!-- Filters  -->
    <label for="miles">Miles To Penn:</label>
    0<input id="miles" type="text" data-slider-min="0" data-slider-max="117" data-slider-step="1" class="span2"
            data-slider-value="[0,117]"/>117
    <label for="zones">Zones</label>
    1<input id="zones" type="text" data-slider-min="1" data-slider-max="14" data-slider-step="1" class="span2"
            data-slider-value="[1,14]"/>14
<!-- Nav tabs -->
    <ul class="nav nav-pills">
        <li class="active"><a href="#overall" data-toggle="tab">Overall</a></li>
        <li><a href="#amPeak" data-toggle="tab">AM Peak</a></li>
        <li><a href="#pmPeak" data-toggle="tab">PM Peak</a></li>
    </ul>
    <div class="tab-content">
        <div class="tab-pane active" id="overall">
            <g:render template="peaksTemplate"
                      model="[direction: 'Overall',
                              detailsPerGroup: detailsPerGroup,
                              groupsPerDirection: groupsPerDirection,
                              analysisInstanceList: analysisInstanceList
                      ]"/>
        </div>

        <div class="tab-pane" id="amPeak">
            <g:render template="peaksTemplate"
                      model="[direction: Direction.West.toString(),
                              detailsPerGroup: detailsPerGroup,
                              groupsPerDirection: groupsPerDirection,
                              analysisInstanceList: analysisInstanceList
                      ]"/>
        </div>

        <div class="tab-pane" id="pmPeak">
            <g:render template="peaksTemplate"
                      model="[direction: Direction.East.toString(),
                              detailsPerGroup: detailsPerGroup,
                              groupsPerDirection: groupsPerDirection,
                              analysisInstanceList: analysisInstanceList
                      ]"/>
        </div>
    </div>
</div>
