<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="team.list.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" var="actionBean"/>

        <p><f:message key="team.list.allteams"/></p>

        <table>
            <tr>
                <th>id</th>
                <th><f:message key="team.list.name"/></th>
                <th><f:message key="team.list.wins"/></th>
                <th><f:message key="team.list.ties"/></th>
                <th><f:message key="team.list.losses"/></th>
                <th><f:message key="team.list.score"/></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${actionBean.teams}" var="team">
                <tr>
                    <td>${team.teamId}</td>
                    <td>
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
                            <f:message key="team.list.edit"/>
                        </s:link>
                    </td>
                    <td>
                        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean">
                            <s:hidden name="team.teamId" value="${team.teamId}"/>
                            <s:submit name="delete">
                                <f:message key="team.list.delete"/>
                            </s:submit>
                        </s:form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean">
            <fieldset>
                <legend>
                    <f:message key="team.list.newteam"/>
                </legend>
                <%@include file="form.jsp"%>
                <s:submit name="add">
                    <f:message key="team.list.addbutton"/>
                </s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>