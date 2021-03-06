<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:layout-definition>
    <!DOCTYPE html>
    <html lang="${pageContext.request.locale}">
        <head>
            <meta charset="utf-8"/>
            <title><f:message key="${titlekey}"/> - Soccer Records</title>
            <link href='http://fonts.googleapis.com/css?family=Roboto&subset=latin,latin-ext' rel='stylesheet' type='text/css'>
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/style.css" />
            <s:layout-component name="header"/>
        </head>
        <body>
            <div class="page">
                <header>
                    <h1>Soccer Records - REST client</h1>
                    <menu>
                        <li><s:link href="/"><f:message key="menu.home"/></s:link></li>
                        <li><s:link href="/teams/list"><f:message key="menu.teams"/></s:link></li>
                        <li><s:link href="/players/list"><f:message key="menu.players"/></s:link></li>
                        </menu>
                    </header>

                    <div class="content">
                    <s:messages/>
                    <s:layout-component name="body"/>
                </div>
                <footer>&copy; <s:link href="https://github.com/mamatoad">MaMa ToAd</s:link> 2013</footer>
                </div>
            </body>
        </html>
</s:layout-definition>