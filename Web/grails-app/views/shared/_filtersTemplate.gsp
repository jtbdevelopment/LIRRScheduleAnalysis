<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Line; com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<div id="report-filters">
    <!-- Filters  -->
    <div class="row">
        <div class="col-md-1 col-sm-2">
            <label class="small">Lines:</label>
        </div>

        <div class="col-md-11 col-sm-10">
            <button onclick="javascript:allLines()" id=alllines" name="allines"
                    class="btn btn-default btn-xs">All</button>
            <g:each in="${Line.values()}" status="i" var="line">
                <g:if test="${!(line.name =~ 'City')}">
                    <label class="checkbox-inline small">
                        <input value="${line.name}" id="lines[]" type="checkbox" checked>${line.name}
                    </label>
                </g:if>
            </g:each>
            <button onclick="javascript:noLines()" id=nolines" name="nolines"
                    class="btn btn-default btn-xs">None</button>
        </div>
    </div>

    <div class="row">
        <div class="col-md-1 col-sm-2">
            <label class="small">Zones:</label>
        </div>

        <div class="col-md-3 col-sm-3">
            <button onclick="javascript:allZones()" id=allzones" name="allzones"
                    class="btn btn-default btn-xs">All</button>
            <g:each in="${Zone.values()}" status="i" var="zone">
                <g:if test="${zone.numeric >= 3}">
                    <label class="checkbox-inline small">
                        <input value="${zone.numeric}" id="zones[]" type="checkbox" checked>${zone.numeric}
                    </label>
                </g:if>
            </g:each>
            <button onclick="noZones()" id=nozones" name="nozones"
                    class="btn btn-default btn-xs">None</button>
        </div>

        <div class="col-md-1 col-sm-2">
            <label class="small" for="miles">Distance:</label>
        </div>

        <div class="col-md-5 col-sm-4 small">
            0<input id="miles" type="text" data-slider-min="0" data-slider-max="117" data-slider-step="1"
                    class="span2"
                    data-slider-value="[0,117]"/>117
        </div>

        <div class="col-md-2 col-sm-1 small">
            <label for="asscore">As Score:</label>
            <input type="checkbox" id="asscore">
        </div>
    </div>
</div>
