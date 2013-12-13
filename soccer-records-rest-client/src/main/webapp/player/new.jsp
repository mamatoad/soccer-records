<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="player.new">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.PlayerActionBean" var="actionBean"/>

        <h2><f:message key="player.new"/></h2>
        
        <s:errors/>

        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.PlayerActionBean">
            <fieldset>
                <legend><f:message key="player.new"/></legend>
                <%@include file="form.jsp"%>
                <s:submit name="add"><f:message key="player.add"/></s:submit>
                <s:submit name="cancel"><f:message key="player.edit.cancel"/></s:submit>
                </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>
