<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:layout-definition>
    <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="userActionBean"/>
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
                    <h1>Soccer Records</h1>
                    <menu>
                        <li><s:link href="/"><f:message key="menu.home"/></s:link></li>
                        <c:if test="${userActionBean.getUserRole() != 'none'}">
                            <li><s:link href="/teams/list"><f:message key="menu.teams"/></s:link></li>
                            <li><s:link href="/players/list"><f:message key="menu.players"/></s:link></li>
                            <li><s:link href="/matches/list"><f:message key="menu.matches"/></s:link></li>
                        </c:if>
                        <c:if test="${userActionBean.getUserRole() == 'admin'}">
                            <li><s:link href="/users/list"><f:message key="menu.users"/></s:link></li>
                        </c:if>
                        <div class="userInfo">
                        <c:choose>
                            <c:when test="${userActionBean.loggedInUser == ''}">
                                <li><s:link href="/users"><f:message key="menu.login"/></s:link></li>
                            </c:when>
                            <c:otherwise>
                                
                                    <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" event="userDetail">
                                        <f:message key="menu.account"/>
                                    </s:link>
                                    <c:out value="${userActionBean.loggedInUser} > "/>
                                    <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" event="doLogout">
                                        <f:message key="menu.logout"/>
                                    </s:link>
                                </c:otherwise>
                        </c:choose>
                        </div>
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
