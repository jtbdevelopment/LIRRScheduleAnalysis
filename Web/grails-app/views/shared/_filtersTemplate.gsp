<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Line; com.jtbdevelopment.lirr.dataobjects.core.Zone" %>
<div id="report-filters">
    <!-- Filters  -->
    <div class="row">
        <div class="col-md-1 col-sm-2">
            <label>Lines:</label>
        </div>

        <div class="col-md-11 col-sm-10">
            <g:each in="${Line.values()}" status="i" var="line">
                <g:if test="${!(line.name =~ 'City')}">
                    <label class="checkbox-inline">
                        <input value="${line.name}" id="lines[]" type="checkbox" checked>${line.name}
                    </label>
                </g:if>
            </g:each>
        </div>
    </div>

    <div class="row">
        <div class="col-md-1 col-sm-2">
            <label>Zones</label>
        </div>

        <div class="col-md-4 col-sm-3">
            <g:each in="${Zone.values()}" status="i" var="zone">
                <g:if test="${zone.numeric >= 3}">
                    <label class="checkbox-inline">
                        <input value="${zone.numeric}" id="zones[]" type="checkbox" checked>${zone.numeric}
                    </label>
                </g:if>
            </g:each>
        </div>

        <div class="col-md-1 col-sm-2">
            <label for="miles">Distance:</label>
        </div>

        <div class="col-md-6 col-sm-5">
            0<input id="miles" type="text" data-slider-min="0" data-slider-max="117" data-slider-step="1"
                    class="span2"
                    data-slider-value="[0,117]"/>117
        </div>
    </div>

    <!-- Nav tabs -->
    <ul class="nav nav-pills">
        <li class="active"><a href="#overall" data-toggle="tab">Overall</a></li>
        <li><a href="#amPeak" data-toggle="tab">AM Peak</a></li>
        <li><a href="#pmPeak" data-toggle="tab">PM Peak</a></li>
    </ul>
</div>
