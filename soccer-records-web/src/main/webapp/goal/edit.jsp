<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="goal.edit.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean" var="actionBean"/>

        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.GoalActionBean">
            <s:hidden name="goal.id"/>
            <fieldset><legend><f:message key="goal.edit.edit"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="save"><f:message key="goal.edit.save"/></s:submit>
                <s:submit name="cancelEdit"><f:message key="goal.edit.cancel"/></s:submit>
            </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>
