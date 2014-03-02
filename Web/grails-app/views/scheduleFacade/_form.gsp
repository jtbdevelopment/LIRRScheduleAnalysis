<%@ page import="com.jtbdevelopment.lirr.web.ScheduleFacade" %>



<div class="fieldcontain ${hasErrors(bean: scheduleFacadeInstance, field: 'end', 'error')} required">
    <label for="end">
        <g:message code="scheduleFacade.end.label" default="End"/>
        <span class="required-indicator">*</span>
    </label>
    <joda:datePicker name="end" value="${scheduleFacadeInstance?.end}"></joda:datePicker>
</div>

<div class="fieldcontain ${hasErrors(bean: scheduleFacadeInstance, field: 'inputFeeds', 'error')} ">
    <label for="inputFeeds">
        <g:message code="scheduleFacade.inputFeeds.label" default="Input Feeds"/>

    </label>

</div>

<div class="fieldcontain ${hasErrors(bean: scheduleFacadeInstance, field: 'processed', 'error')} required">
    <label for="processed">
        <g:message code="scheduleFacade.processed.label" default="Processed"/>
        <span class="required-indicator">*</span>
    </label>
    <joda:dateTimePicker name="processed" value="${scheduleFacadeInstance?.processed}"></joda:dateTimePicker>
</div>

<div class="fieldcontain ${hasErrors(bean: scheduleFacadeInstance, field: 'schedules', 'error')} ">
    <label for="schedules">
        <g:message code="scheduleFacade.schedules.label" default="Schedules"/>

    </label>

</div>

<div class="fieldcontain ${hasErrors(bean: scheduleFacadeInstance, field: 'start', 'error')} required">
    <label for="start">
        <g:message code="scheduleFacade.start.label" default="Start"/>
        <span class="required-indicator">*</span>
    </label>
    <joda:datePicker name="start" value="${scheduleFacadeInstance?.start}"></joda:datePicker>
</div>

