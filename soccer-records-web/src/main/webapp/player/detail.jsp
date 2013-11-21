<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp">
<s:layout-component name="body">

<s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean" var="actionBean"/>

<p><f:message key="player.detail.title"/>${actionBean.player.playerName}</p>

<table>
    <tr>
        <th><f:message key="player.name"/></th>
        <th><f:message key="player.goals"/></th>
        <th><f:message key="player.team"/></th>
        <th><f:message key="player.active"/></th>
    </tr>
    <tr>
        <td><c:out value="${actionBean.player.playerName}"/></td>
        <td><c:out value="${actionBean.player.playerGoalsScored}"/></td>
        <td><a href="../../teams/detail/${actionBean.player.teamId}"><c:out value="${actionBean.player.teamName}"/></a></td>
        <td>
        <c:if test="${actionBean.player.playerActive==true}">
            <f:message key="player.isActive"/>
        </c:if>
        <c:if test="${actionBean.player.playerActive==false}">
            <f:message key="player.isNotActive"/>
        </c:if>
        </td>
    </tr>
</table>
        
</s:layout-component>
</s:layout-render>
