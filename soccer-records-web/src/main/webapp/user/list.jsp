<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="user.list">
    <s:layout-component name="body">
        <h2><f:message key="user.list"/></h2>
        
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="actionBean"/>
        <s:errors/>
        
        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean">
            <s:hidden name="user.id"/>
            <fieldset><legend><f:message key="user.list.newuser"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="add"><f:message key="user.list.adduser"/></s:submit>
            </fieldset>
        </s:form>
        
        <c:if test="${empty actionBean.users}">
            <p><f:message key="user.list.noData"/></p>
        </c:if>
        <c:if test="${not empty actionBean.users}">
        <table class="list">
            <tr>
                <th><f:message key="login.name"/></th>
                <th><f:message key="login.role"/></th>
            </tr>
            
            <c:forEach items="${actionBean.users}" var="user">
                <tr>
                    <td><c:out value="${user.login}"/></td>
                    <td><c:out value="${user.role.toString()}"/></td>
                    <td>
                        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" event="edit">
                            <s:param name="user.id" value="${user.id}"/>
                            <img src="${pageContext.request.contextPath}/images/pencil.png"/>
                            <f:message key="user.list.edit"/>
                        </s:link>
                        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" event="delete">
                            <s:param name="user.id" value="${user.id}"/>
                            <img src="${pageContext.request.contextPath}/images/cross.png"/>
                            <f:message key="user.list.delete"/>
                        </s:link>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </c:if>

    </s:layout-component>
</s:layout-render>