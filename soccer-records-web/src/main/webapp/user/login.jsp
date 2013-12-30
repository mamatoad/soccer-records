<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="user.login">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="actionBean" />
 <fieldset>
        <legend><f:message key="user.login"/></legend>
        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" action="/users/doLogin">
            
            <s:errors />
            <p><f:message key="login.name"/>
            <br/>
            <s:text id="login" name="user.login" />
            <br/>
            <f:message key="login.password"/>
            <br/>
            <s:password id="password" name="user.password" />
            <br/>
            </p>
            <s:submit name="doLogin" value="Login"><f:message key="login.login"/></s:submit>
            
        </s:form>
 </fieldset>
    </s:layout-component>
</s:layout-render>