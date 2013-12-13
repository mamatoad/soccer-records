<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="player.delete.title">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.PlayerActionBean" var="actionBean"/>

        <h2><f:message key="player.delete.title"/></h2>

        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.PlayerActionBean">
            <s:hidden name="player.playerId"/>
            <fieldset>
                <legend><f:message key="player.deleting"/></legend>
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
                <s:submit name="remove"><f:message key="player.delete.save"/></s:submit>
                <s:submit name="cancel"><f:message key="player.delete.cancel"/></s:submit>
                </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>
