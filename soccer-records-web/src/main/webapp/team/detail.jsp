<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="team.detail.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" var="actionBean"/>
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="userActionBean"/>

        <h2><c:out value="${actionBean.team.teamName}"/></h2>

        <table class="info">
            <tr>
                <th><f:message key="team.detail.wins"/>:</th>
                <td>${actionBean.team.numberOfWins}</td>
            </tr>
            <tr>
                <th><f:message key="team.detail.ties"/>:</th>
                <td>${actionBean.team.numberOfTies}</td>
            </tr>
            <tr>
                <th><f:message key="team.detail.losses"/>:</th>
                <td>${actionBean.team.numberOfLosses}</td>
            </tr>
            <tr>
                <th><f:message key="team.detail.score"/>:</th>
                <td>${actionBean.team.numberOfGoalsShot}:${actionBean.team.numberOfGoalsReceived}</td>
            </tr>
        </table>

        <c:if test="${userActionBean.getUserRole() == 'admin'}">    
        <ul>
            <li>
                <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="edit">
                    <s:param name="team.teamId" value="${actionBean.team.teamId}"/>
                    <img src="${pageContext.request.contextPath}/images/pencil.png"/>
                    <f:message key="team.list.edit"/>
                </s:link>
            </li><li>
                <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="delete">
                    <s:param name="team.teamId" value="${actionBean.team.teamId}"/>
                    <img src="${pageContext.request.contextPath}/images/cross.png"/>
                    <f:message key="team.list.delete"/>
                </s:link>
            </li>
        </ul>
        </c:if>
            
        <h3><f:message key="team.detail.players"/></h3>
        
        <c:if test="${userActionBean.getUserRole() == 'admin'}">
        <p>
            <a href="../../players/list/${actionBean.team.teamId}"><f:message key="team.detail.players.manage"/></a>
        </p>
        </c:if>
        
        <c:choose>
            <c:when test="${empty actionBean.players}">
                 <p><f:message key="team.players.empty"/></p>
            </c:when>
            <c:otherwise>
        <table class="list">
            <tr>
                <th><f:message key="team.detail.player.name"/></th>
                <th><f:message key="team.detail.player.goals"/></th>
                <th><f:message key="team.detail.player.activity"/></th>
            </tr>
            <c:forEach items="${actionBean.players}" var="player">
                <tr>
                    <td class="name">
                        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean" event="detail">
                            <s:param name="player.playerId" value="${player.playerId}"/>
                            <c:out value="${player.playerName}"/>
                        </s:link>
                    <td><c:out value="${player.playerGoalsScored}"/></td>
                    <td>
                        <c:if test="${player.playerActive==true}">
                            <f:message key="team.detail.player.active"/>
                        </c:if>
                        <c:if test="${player.playerActive==false}">
                            <f:message key="team.detail.player.retired"/>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
    </s:layout-component>
</s:layout-render>