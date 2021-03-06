<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="home.title">
    <s:layout-component name="body">
        
        <h2><f:message key="home.headline"/></h2>
        
        <p><f:message key="home.intro"/></p>
        <p><f:message key="home.dev"/></p>
        <p><f:message key="home.start"/></p>

    </s:layout-component>
</s:layout-render>