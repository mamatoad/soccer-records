<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="goal.list">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean" var="actionBean"/>

        <p><f:message key="goal.list"/></p>
        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean">
            <s:hidden name="goal.id"/>
            <fieldset><legend><f:message key="goal.list.newgoal"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="confirm"><f:message key="goal.list.newGoal"/></s:submit>
            </fieldset>
        </s:form>
        <table class="list">
            <tr>
                <th><f:message key="goal.teamName"/></th>
                <th><f:message key="goal.playerName"/></th>
                <th><f:message key="goal.time"/></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${actionBean.goals}" var="goal">
                <tr>
                    <td><c:out value="${goal.teamName}"/></td>
                    <td><c:out value="${goal.playerName}"/></td>
                    <td><c:out value="${goal.time.getHourOfDay()}:${goal.time.getMinuteOfHour()}"/></td>
                    
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
                </tr>
            </c:forEach>
        </table>
                
    </s:layout-component>
</s:layout-render>