<%@ page import="com.jtbdevelopment.lirr.web.ScheduleFacade" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'scheduleFacade.label', default: 'ScheduleFacade')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-scheduleFacade" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                     default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-scheduleFacade" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list scheduleFacade">

        <g:if test="${scheduleFacadeInstance?.end}">
            <li class="fieldcontain">
                <span id="end-label" class="property-label"><g:message code="scheduleFacade.end.label"
                                                                       default="End"/></span>

                <span class="property-value" aria-labelledby="end-label"><g:fieldValue bean="${scheduleFacadeInstance}"
                                                                                       field="end"/></span>

            </li>
        </g:if>

        <g:if test="${scheduleFacadeInstance?.inputFeeds}">
            <li class="fieldcontain">
                <span id="inputFeeds-label" class="property-label"><g:message code="scheduleFacade.inputFeeds.label"
                                                                              default="Input Feeds"/></span>

                <span class="property-value" aria-labelledby="inputFeeds-label"><g:fieldValue
                        bean="${scheduleFacadeInstance}" field="inputFeeds"/></span>

            </li>
        </g:if>

        <g:if test="${scheduleFacadeInstance?.processed}">
            <li class="fieldcontain">
                <span id="processed-label" class="property-label"><g:message code="scheduleFacade.processed.label"
                                                                             default="Processed"/></span>

                <span class="property-value" aria-labelledby="processed-label"><g:fieldValue
                        bean="${scheduleFacadeInstance}" field="processed"/></span>

            </li>
        </g:if>

        <g:if test="${scheduleFacadeInstance?.schedules}">
            <li class="fieldcontain">
                <span id="schedules-label" class="property-label"><g:message code="scheduleFacade.schedules.label"
                                                                             default="Schedules"/></span>

                <span class="property-value" aria-labelledby="schedules-label"><g:fieldValue
                        bean="${scheduleFacadeInstance}" field="schedules"/></span>

            </li>
        </g:if>

        <g:if test="${scheduleFacadeInstance?.start}">
            <li class="fieldcontain">
                <span id="start-label" class="property-label"><g:message code="scheduleFacade.start.label"
                                                                         default="Start"/></span>

                <span class="property-value" aria-labelledby="start-label"><g:fieldValue
                        bean="${scheduleFacadeInstance}" field="start"/></span>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource: scheduleFacadeInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${scheduleFacadeInstance}"><g:message
                    code="default.button.edit.label" default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
