<%@page import="net.sourceforge.stripes.action.ActionBean"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="player.new">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="actionBean" />
        
        <s:link href="${actionBean.originPage}"><f:message key="user.back"/></s:link>
        
    </s:layout-component>
</s:layout-render>