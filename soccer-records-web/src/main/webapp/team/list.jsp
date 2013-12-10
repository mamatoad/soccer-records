<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="team.list.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" var="actionBean"/>

        <h2><f:message key="team.list.headline"/></h2>

        <p><s:link href="/teams/create"><img src="${pageContext.request.contextPath}/images/add.png" alt=""/> <f:message key="team.list.add"/></s:link></p>

        <c:if test="${empty actionBean.teams}">
            <p><f:message key="team.list.empty"/></p>
        </c:if>

        <c:if test="${not empty actionBean.teams}">
            <table class="list">
                <tr>
                    <th><f:message key="team.list.name"/></th>
                    <th><f:message key="team.list.wins"/></th>
                    <th><f:message key="team.list.ties"/></th>
                    <th><f:message key="team.list.losses"/></th>
                    <th><f:message key="team.list.score"/></th>
                    <th><f:message key="team.list.action"/></th>
                </tr>
                <c:forEach items="${actionBean.teams}" var="team">
                    <tr>
                        <td class="name">
                            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="detail">
                                <s:param name="team.teamId" value="${team.teamId}"/>
                                <c:out value="${team.teamName}"/>
                            </s:link></td>
                        <td><c:out value="${team.numberOfWins}"/></td>
                        <td><c:out value="${team.numberOfTies}"/></td>
                        <td><c:out value="${team.numberOfLosses}"/></td>
                        <td><c:out value="${team.numberOfGoalsShot}"/>:<c:out value="${team.numberOfGoalsReceived}"/></td>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="edit">
                                <s:param name="team.teamId" value="${team.teamId}"/>
                                <img src="${pageContext.request.contextPath}/images/pencil.png"/>
                                <f:message key="team.list.edit"/>
                            </s:link>
                            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="delete">
                                <s:param name="team.teamId" value="${team.teamId}"/>
                                <img src="${pageContext.request.contextPath}/images/cross.png"/>
                                <f:message key="team.list.delete"/>
                            </s:link>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

    </s:layout-component>
</s:layout-render>