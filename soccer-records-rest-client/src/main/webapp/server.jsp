<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="server.unavailable.title">
    <s:layout-component name="body">

        <h2><f:message key="server.unavailable.title"/></h2>
        
        <s:errors/>

        <p><f:message key="server.unavailable.message"/></p>

    </s:layout-component>
</s:layout-render>