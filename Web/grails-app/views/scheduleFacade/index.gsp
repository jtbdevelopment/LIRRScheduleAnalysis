<%@ page import="com.jtbdevelopment.lirr.web.ScheduleFacade" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'scheduleFacade.label', default: 'ScheduleFacade')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-scheduleFacade" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                     default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-scheduleFacade" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>

            <g:sortableColumn property="end" title="${message(code: 'scheduleFacade.end.label', default: 'End')}"/>

            <g:sortableColumn property="inputFeeds"
                              title="${message(code: 'scheduleFacade.inputFeeds.label', default: 'Input Feeds')}"/>

            <g:sortableColumn property="processed"
                              title="${message(code: 'scheduleFacade.processed.label', default: 'Processed')}"/>

            <g:sortableColumn property="schedules"
                              title="${message(code: 'scheduleFacade.schedules.label', default: 'Schedules')}"/>

            <g:sortableColumn property="start"
                              title="${message(code: 'scheduleFacade.start.label', default: 'Start')}"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${scheduleFacadeInstanceList}" status="i" var="scheduleFacadeInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                <td><g:link action="show"
                            id="${scheduleFacadeInstance.id}">${fieldValue(bean: scheduleFacadeInstance, field: "end")}</g:link></td>

                <td>${fieldValue(bean: scheduleFacadeInstance, field: "inputFeeds")}</td>

                <td>${fieldValue(bean: scheduleFacadeInstance, field: "processed")}</td>

                <td>${fieldValue(bean: scheduleFacadeInstance, field: "schedules")}</td>

                <td>${fieldValue(bean: scheduleFacadeInstance, field: "start")}</td>

            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${scheduleFacadeInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
