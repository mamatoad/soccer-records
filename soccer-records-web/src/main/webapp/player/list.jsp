<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="player.list">
    <s:layout-component name="body">
        <h2><f:message key="player.list"/></h2>

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean" var="actionBean"/>
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="userActionBean"/>

        <s:errors/>

        <%@include file="search.jsp"%>
        
        <c:if test="${userActionBean.getUserRole() == 'admin'}">
        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean">
            <s:param name="$event" value="create"/>
            <img src="${pageContext.request.contextPath}/images/add.png" alt=""/>
            <f:message key="player.new"/>
        </s:link>
        </c:if>    
            
        <c:if test="${empty actionBean.players}">
            <p><f:message key="player.list.noData"/></p>
        </c:if>
        <c:if test="${not empty actionBean.players}">
            <table class="list">
                <tr>
                    <th><f:message key="player.name"/></th>
                    <th><f:message key="player.goals"/></th>
                    <th><f:message key="player.team"/></th>
                    <th><f:message key="player.active"/></th>
                    <c:if test="${userActionBean.getUserRole() == 'admin'}">
                        <th><f:message key="player.action"/></th>
                    </c:if>
                </tr>

                <c:forEach items="${actionBean.players}" var="player">
                    <tr>
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean">
                                <s:param name="$event" value="detail"/>
                                <s:param name="player.playerId" value="${player.playerId}"/>
                                <c:out value="${player.playerName}"/>
                            </s:link>
                        </td>
                        <td><c:out value="${player.playerGoalsScored}"/></td>
                        <td>
                            <c:if test="${player.teamId != null}">
                                <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean">
                                    <s:param name="$event" value="detail"/>
                                    <s:param name="team.teamId" value="${player.teamId}"/>
                                    <c:out value="${player.teamName}"/>
                                </s:link>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${player.playerActive==true}">
                                <f:message key="player.isActive"/>
                            </c:if>
                            <c:if test="${player.playerActive==false}">
                                <f:message key="player.isNotActive"/>
                            </c:if>
                        </td>
                        
                        <c:if test="${userActionBean.getUserRole() == 'admin'}">
                        <td>
                            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean">
                                <s:param name="$event" value="edit"/>
                                <s:param name="player.playerId" value="${player.playerId}"/>
                                <img src="${pageContext.request.contextPath}/images/pencil.png" alt=""/>
                                <f:message key="player.edit"/>
                            </s:link>
                            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean">
                                <s:param name="$event" value="delete"/>
                                <s:param name="player.playerId" value="${player.playerId}"/>
                                <img src="${pageContext.request.contextPath}/images/cross.png" alt=""/>
                                <f:message key="player.delete"/>
                            </s:link>
                        </td>
                        </c:if>
                        
                    </tr>
                </c:forEach>
            </table>
        </c:if>

    </s:layout-component>
</s:layout-render>
