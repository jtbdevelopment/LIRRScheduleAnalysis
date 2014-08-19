<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Station; org.joda.time.LocalTime; com.jtbdevelopment.lirr.dataobjects.core.Direction" %>
<div class="tab-content" id="report-content">
    <g:set var="directionList" value="[
            'Overall'                  : 'Overall',
            (Direction.West.toString()): 'AM Peak',
            (Direction.East.toString()): 'PM Peak'
    ]"/>

    <g:set var="groupCounter" value="${-1}"/>
    <ul class="pagination">
        <g:each in="${directionList}" status="directionCount" var="direction">
            <g:set var="groupsForDirection" value="${groupsPerDirection[direction.key]}"/>
            <g:set var="groupName" value=""/>
            <g:if test="${groupsForDirection.size() > 1}">
                <g:each in="${groupsForDirection}" status="i" var="groupForDirection">
                    <g:set var="groupCounter" value="${groupCounter + 1}"/>
                    <g:if test="${groupForDirection == 'Overall'}">
                        <g:set var="groupName" value="${direction.value + '-' + groupForDirection}"/>
                    </g:if>
                    <g:else>
                        <g:set var="groupName" value="${groupForDirection}"/>
                    </g:else>
                    <li class="subtab subtab-${groupCounter}"><a href="#"
                                                                 onclick="showGroup(${groupCounter})">${groupName}</a>
                    </li>
                </g:each>
            </g:if>
            <g:else>
                <g:set var="groupCounter" value="${groupCounter + 1}"/>
                <g:set var="groupName" value="${direction.value}"/>
                <li class="subtab subtab-${groupCounter}"><a href="#"
                                                             onclick="showGroup(${groupCounter})">${groupName}</a></li>
            </g:else>
        </g:each>
    </ul>

    <!-- Div for chart -->
    <div id="chartArea" style="height:550px;width:100%;"></div>

    <!-- Tab panes -->
    <table class="table table-striped table-bordered table-condensed table-hover" id="tableData">
        <thead>
        <tr>
            <th rowspan="2">Station</th>
            <th rowspan="2">Miles To Penn</th>
            <th rowspan="2">Line</th>
            <th rowspan="2">Zone</th>
            <g:set var="groupCounter" value="${-1}"/>
            <g:each in="${directionList}" status="directionCount" var="direction">
                <g:set var="groupsForDirection" value="${groupsPerDirection[direction.key]}"/>
                <g:set var="detailsPerGroupSize" value="${detailsPerGroup.size()}"/>
                <g:each in="${groupsForDirection}" status="i" var="groupForDirection">
                    <g:set var="groupCounter" value="${groupCounter + 1}"/>
                    <th colspan="${detailsPerGroupSize}" class="text-center ${groupCounter}">${groupForDirection}</th>
                </g:each>
            </g:each>
        </tr>
        <tr>
            <g:set var="groupCounter" value="${-1}"/>
            <g:each in="${directionList}" status="directionCount" var="direction">
                <g:set var="groupsForDirection" value="${groupsPerDirection[direction.key]}"/>
                <g:each in="${groupsForDirection}" status="i" var="groupForDirection">
                    <g:set var="groupCounter" value="${groupCounter + 1}"/>
                    <g:each in="${detailsPerGroup}" status="j" var="groupDetail">
                        <th class="group-${groupCounter} group dataLabel">${groupDetail}</th>
                    </g:each>
                </g:each>
            </g:each>
        </tr>
        </thead>
        <tbody>
        <g:each in="${Station.STATIONS}" var="station">
            <g:if test="${!station.ignoreForAnalysis}">
                <tr class="dataRow">
                    <td class="text-left station">${station.name}</td>
                    <td class="text-center zone">${station.milesToPenn}</td>
                    <td class="text-center zone">${station.line.name}</td>
                    <td class="text-center zone">${station.zone.numeric}</td>
                    <g:each in="${directionList}" status="directionCount" var="direction">
                        <g:set var="groupsForDirection" value="${groupsPerDirection[direction.key]}"/>
                        <g:set var="analysisForDirection" value="${analysisInstance.details[direction.key]}"/>
                        <g:each in="${analysisForDirection}" status="k" var="stationAnalysis">
                            <g:if test="${station.name == stationAnalysis.key.name}">
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
                            </g:if>
                        </g:each>
                    </g:each>
                </tr>
            </g:if>
        </g:each>
        </tbody>
        <tfoot>
        </tfoot>
    </table>
</div>