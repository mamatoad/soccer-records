<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="goal.list">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean" var="actionBean"/>
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="userActionBean"/>
        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" event="detail">
            <s:param name="match.id" value="${actionBean.matchIdUrl}"/>
            <f:message key="goal.list.backToMatch"/>
       </s:link> 
        <p><f:message key="goal.list"/></p>
        
        <c:if test="${userActionBean.getUserRole() == 'admin'}">
        <c:if test="${not empty actionBean.players}">
        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean">
            <s:hidden name="goal.id"/>
            <fieldset><legend><f:message key="goal.list.newgoal"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="confirm"><f:message key="goal.list.newGoal"/></s:submit>
            </fieldset>
        </s:form>
        </c:if>
        <c:if test="${empty actionBean.players}">
            <th><f:message key="goal.noplayers"/></th>
        </c:if>
        </c:if>
            
        <c:if test="${empty actionBean.goals}">
            <p><f:message key="goal.list.noData"/></p>
        </c:if>
        <c:if test="${not empty actionBean.goals}">
        <table class="list">
            <tr>
                <th><f:message key="goal.teamName"/></th>
                <th><f:message key="goal.playerName"/></th>
                <th><f:message key="goal.time"/></th>
                <c:if test="${userActionBean.getUserRole() == 'admin'}">
                    <th></th>
                    <th></th>
                </c:if>
            </tr>
            <c:forEach items="${actionBean.goals}" var="goal">
                <tr>
                    <td><c:out value="${goal.teamName}"/></td>
                    <td><c:out value="${goal.playerName}"/></td>
                    <td><c:out value="${goal.time.getHourOfDay()}:${goal.time.getMinuteOfHour()}"/></td>
                    
                    <c:if test="${userActionBean.getUserRole() == 'admin'}">
                    <td>
                        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean" event="edit">
                            <s:param name="goal.id" value="${goal.goalId}"/>
                            <img src="${pageContext.request.contextPath}/images/pencil.png"/>
                            <f:message key="goal.list.edit"/>
                        </s:link>
                    </td>
                    <td>
                        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean" event="delete">
                            <s:param name="goal.id" value="${goal.goalId}"/>
                            <img src="${pageContext.request.contextPath}/images/cross.png"/>
                            <f:message key="goal.list.delete"/>
                        </s:link>
                    </td>
                    </c:if>
                    
                </tr>
            </c:forEach>
        </table>
        </c:if>          
    </s:layout-component>
</s:layout-render>