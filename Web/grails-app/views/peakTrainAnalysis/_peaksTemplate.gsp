<%@ page import="org.joda.time.LocalTime" %>
<g:set var="groupsForDirection" value="${groupsPerDirection[direction]}"/>
<g:if test="${'Overall' != direction}">
    <ul class="pagination">
    <g:each in="${groupsForDirection}" status="i" var="groupForDirection">
        <li><a href="#" onclick="showGroup('${direction}', 'group-' +${i});
        return false">${groupForDirection}</a></li>
    </g:each>
</ul>
</g:if>

<!-- Div for chart -->
<div id="${direction}Chart" style="height:500px;width:100%;"></div>

<!-- Tab panes -->
<table class="table table-striped table-bordered table-condensed table-hover" id="${direction}">
    <thead>
    <tr>
        <th rowspan="2">Station</th>
        <th rowspan="2">Miles To Penn</th>
        <th rowspan="2">Line</th>
        <th rowspan="2">Zone</th>
        <g:set var="detailsPerGroupSize" value="${detailsPerGroup.size()}"/>
        <g:each in="${groupsForDirection}" status="i" var="groupForDirection">
            <th colspan="${detailsPerGroupSize}" class="text-center ${i}">${groupForDirection}</th>
        </g:each>
    </tr>
    <tr>
        <g:each in="${groupsForDirection}" status="i" var="groupForDirection">
            <g:each in="${detailsPerGroup}" status="j" var="groupDetail">
                <th class="group-${i} group dataLabel">${groupDetail}</th>
            </g:each>
        </g:each>
    </tr>
    </thead>
    <tbody>
    <g:set var="analysisForDirection" value="${analysisInstance.details[direction]}"/>
    <g:each in="${analysisForDirection}" status="k" var="stationAnalysis">
        <tr class="dataRow">
            <td class="text-left station">${stationAnalysis.key.name}</td>
            <td class="text-center zone">${stationAnalysis.key.milesToPenn}</td>
            <td class="text-center zone">${stationAnalysis.key.line.name}</td>
            <td class="text-center zone">${stationAnalysis.key.zone.numeric}</td>
            <g:each in="${groupsForDirection}" status="l" var="groupForDirection">
                <g:set var="stationGroupDetails"
                       value="${stationAnalysis.value[groupForDirection]}"/>
                <g:each in="${detailsPerGroup}" status="m" var="detail">
                    <td class="text-center">
                        <g:if test="${stationGroupDetails[detail] in LocalTime}">
                            ${stationGroupDetails[detail].toString("HH:mm")}
                        </g:if>
                        <g:else>
                            ${stationGroupDetails[detail]}
                        </g:else>
                    </td>
                </g:each>
            </g:each>
        </tr>
    </g:each>
    </tbody>
    <tfoot>
    </tfoot>
</table>
