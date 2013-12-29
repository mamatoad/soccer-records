<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="user.editing">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="actionBean"/>

        <h2><f:message key="user.editing"/></h2>
        
        <s:errors/>

        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean">
            <s:hidden name="user.id"/>
            <fieldset>
                <legend><f:message key="user.editing"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="save"><f:message key="user.edit.save"/></s:submit>
                <s:submit name="cancel"><f:message key="user.edit.cancel"/></s:submit>
                </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>
