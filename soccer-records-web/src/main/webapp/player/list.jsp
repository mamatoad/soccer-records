<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp">
<s:layout-component name="body">

<s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean" var="actionBean"/>

<%@include file="search.jsp"%>

<c:if test="${empty actionBean.players}">
    <p><f:message key="player.list.noData"/></p>
</c:if>
<c:if test="${not empty actionBean.players}">
    <table>
    <tr>
        <th><f:message key="player.name"/></th>
        <th><f:message key="player.goals"/></th>
        <th><f:message key="player.team"/></th>
        <th><f:message key="player.active"/></th>
        <th><f:message key="player.action"/></th>
    </tr>
  
    <c:forEach items="${actionBean.players}" var="player">
    <tr>
        <td><a href="detail/${player.playerId}"><c:out value="${player.playerName}"/></a></td>
        <td><c:out value="${player.playerGoalsScored}"/></td>
        <td><a href="../teams/detail/${player.teamId}"><c:out value="${player.teamName}"/></a></td>
        <td>
            <c:if test="${player.playerActive==true}">
                <f:message key="player.isActive"/>
            </c:if>
            <c:if test="${player.playerActive==false}">
                <f:message key="player.isNotActive"/>
            </c:if>
        </td>
        <td>
            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean">
                <s:param name="$event" value="edit"/>
                <s:param name="player.playerId" value="${player.playerId}"/>
                <f:message key="player.edit"/>
            </s:link>
        </td>
    </tr>
    </c:forEach>
    </table>
</c:if>

</s:layout-component>
</s:layout-render>
