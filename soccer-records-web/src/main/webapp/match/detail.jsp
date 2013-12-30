<%@page import="org.joda.time.format.DateTimeFormatter"%>
<%@page import="cz.muni.fi.pa165.mamatoad.soccerrecords.dto.MatchTO"%>
<%@page import="org.joda.time.format.DateTimeFormat"%>
<%@page import="org.joda.time.format.DateTimeFormat"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="match.detail">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" var="actionBean"/>
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="userActionBean"/>

        <table class="list">
            <tr>
                <th><s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="detail">
                        <s:param name="team.teamId" value="${actionBean.match.homeTeamId}"/> 
                        <c:out value="${actionBean.match.homeTeamName}"/>
                    </s:link>
                </th>
                <td><c:out value="${actionBean.match.homeTeamScore}"/></td>
                <td> : </td>
                <td><c:out value="${actionBean.match.visitingTeamScore}"/></td>
                <th><s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="detail">
                        <s:param name="team.teamId" value="${actionBean.match.visitingTeamId}"/> 
                        <c:out value="${actionBean.match.visitingTeamName}"/>
                    </s:link>
                </th>
            </tr>
        </table>

        <p><b><f:message key="match.detail.eventDate"/></b>: <c:out value="${actionBean.formattedDate}"/></p>
        
        <c:if test="${userActionBean.getUserRole() == 'admin'}">
        <ul>
            <li><s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" event="edit">
                    <s:param name="match.id" value="${actionBean.match.matchId}"/> 
                    <img src="${pageContext.request.contextPath}/images/pencil.png"/>
                    <f:message key="match.list.edit"/>
                </s:link>
            </li><li>
                <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" event="delete">
                    <s:param name="match.id" value="${actionBean.match.matchId}"/>
                    <img src="${pageContext.request.contextPath}/images/cross.png"/>
                    <f:message key="match.list.delete"/>
                </s:link></li>
        </ul>
        </c:if>
        
        <h3><f:message key="match.detail.goalsList"/></h3>

        <c:if test="${userActionBean.getUserRole() == 'admin'}">
        <p>
            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean" event="list">
                <s:param name="goal.id" value="${actionBean.match.matchId}"/> 
                <f:message key="match.detail.manageGoals"/>
            </s:link>
        </p>
        </c:if>
        
  <c:choose>
            <c:when test="${empty actionBean.goals}">
                 <p><f:message key="player.list.noData"/></p>
            </c:when>
            <c:otherwise>
        <table class="list">
            <tr>
                <th><f:message key="match.detail.goalsTime"/></th>
                <th><f:message key="match.detail.goalsPlayerName"/></th>
                <th><f:message key="match.detail.goalsTeam"/></th>
            </tr>
            <c:forEach items="${actionBean.goals}" var="goal">
                <tr>
                    <td><c:out value="${goal.time}" /></td>
                    <td><c:out value="${goal.playerName}" /></td>
                    <td><c:out value="${goal.teamName}" /></td>
                </tr>
            </c:forEach>
        </tr>
    </table>
    </c:otherwise>
</c:choose>
</s:layout-component>
</s:layout-render>
