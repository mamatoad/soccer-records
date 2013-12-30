<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="user.detail.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="actionBean"/>
        
        <h3><f:message key="user.detail"/></h3>
        
        <div align="center">
        <table class="info">
            <tr>
                <th><f:message key="login.name"/>:</th>
                <td>${actionBean.user.login}</td>
            </tr>
            <tr>
                <th><f:message key="login.role"/>:</th>
                <td>${actionBean.user.role.toString()}</td>
            </tr>
        </table>
        </div>
        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" event="editPassword">
                        <s:param name="user.id" value="${actionBean.user.id}"/> 
                        <img src="${pageContext.request.contextPath}/images/pencil.png" alt=""/>
                        <f:message key="user.password.change"/>
                    </s:link>
      
                <br/>
            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean">
                <s:param name="$event" value="deleteUser"/>
                <s:param name="user.id" value="${actionBean.user.id}"/>
                <img src="${pageContext.request.contextPath}/images/cross.png" alt=""/>
                <f:message key="user.list.delete"/>
            </s:link>                   
    </s:layout-component>
</s:layout-render>