<%@ page import="com.jtbdevelopment.lirr.dataobjects.core.Zone; com.jtbdevelopment.lirr.dataobjects.core.Line; com.jtbdevelopment.lirr.dataobjects.core.Direction" %>
<div class="panel-group container-fluid">
    <cache:render template="/shared/filtersTemplate" key="${analysisInstance.id}"/>
    <cache:render template="/shared/reportTemplate" key="${analysisInstance.id}"/>
</div>
