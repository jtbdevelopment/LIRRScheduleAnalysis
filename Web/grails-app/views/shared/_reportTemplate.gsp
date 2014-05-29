<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Direction" %>
<div class="tab-content" id="report-content">
    <div class="tab-pane active" id="overall">
        <g:render template="/shared/subReportTemplate"
                  model="[direction           : 'Overall',
                          detailsPerGroup     : detailsPerGroup,
                          groupsPerDirection  : groupsPerDirection,
                          analysisInstanceList: analysisInstanceList
                  ]"/>
    </div>

    <div class="tab-pane" id="amPeak">
        <g:render template="/shared/subReportTemplate"
                  model="[direction           : Direction.West.toString(),
                          detailsPerGroup     : detailsPerGroup,
                          groupsPerDirection  : groupsPerDirection,
                          analysisInstanceList: analysisInstanceList
                  ]"/>
    </div>

    <div class="tab-pane" id="pmPeak">
        <g:render template="/shared/subReportTemplate"
                  model="[direction           : Direction.East.toString(),
                          detailsPerGroup     : detailsPerGroup,
                          groupsPerDirection  : groupsPerDirection,
                          analysisInstanceList: analysisInstanceList
                  ]"/>
    </div>
</div>