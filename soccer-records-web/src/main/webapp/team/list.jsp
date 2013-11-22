<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="team.list.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" var="actionBean"/>

        <h2><f:message key="team.list.headline"/></h2>
        
        <p><s:link href="/teams/create"><img src="../images/add.png" alt=""/> <f:message key="team.list.add"/></s:link></p>

        <table class="list">
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
                            <img src="../images/pencil.png"/>
                            <f:message key="team.list.edit"/>
                        </s:link>
                    </td>
                    <td>
                        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" event="delete">
                            <s:param name="team.teamId" value="${team.teamId}"/>
                            <img src="../images/cross.png"/>
                            <f:message key="team.list.delete"/>
                        </s:link>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </s:layout-component>
</s:layout-render>