<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="player.detail.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.PlayerActionBean" var="actionBean"/>

        <h2><f:message key="player.detail.title"/></h2>

        <table class="list">
            <tr>
                <td><f:message key="player.name"/></td>
                <td><c:out value="${actionBean.player.playerName}"/></td>
            </tr>
            <tr> 
                <td><f:message key="player.goals"/></td>
                <td><c:out value="${actionBean.player.playerGoalsScored}"/></td>
            </tr>
            <tr>    
                <td><f:message key="player.team"/></td>
                <td><a href="../../teams/detail/${actionBean.player.teamId}"><c:out value="${actionBean.player.teamName}"/></a></td>
            </tr>
            <tr>    
                <td><f:message key="player.active"/></td>
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
        <p>

            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.PlayerActionBean">
                <s:param name="$event" value="edit"/>
                <s:param name="player.playerId" value="${actionBean.player.playerId}"/>
                <img src="${pageContext.request.contextPath}/images/pencil.png" alt=""/>
                <f:message key="player.edit"/>
            </s:link>
            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.PlayerActionBean">
                <s:param name="$event" value="delete"/>
                <s:param name="player.playerId" value="${actionBean.player.playerId}"/>
                <img src="${pageContext.request.contextPath}/images/cross.png" alt=""/>
                <f:message key="player.delete"/>
            </s:link>
        </p>
        <p>
            <a href="../../players/list/${actionBean.player.teamId}"><f:message key="player.teammates"/></a>
        </p>
    </s:layout-component>
</s:layout-render>
