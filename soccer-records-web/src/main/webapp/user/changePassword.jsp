<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="user.password.change">
    <s:layout-component name="body">

        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean" var="actionBean"/>

        <h2><f:message key="user.password.change"/></h2>

        <s:errors/>

        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.UserActionBean">
            <s:hidden name="user.id"/>
                 <fieldset>
            <table class="list">
                <tr>
                    <th><s:label for="oldPassword" name="user.password.old"/></th>
                    <td><s:password id="oldPassword" name="oldPassword"/></td>
                </tr>
                <tr>
                    <th><s:label for="newPassword" name="user.pass"/></th>
                    <td><s:password id="newPassword" name="newPassword"/></td>
                </tr>
                <tr>
                    <th><s:label for="passwordConfirmation" name="user.pass.confirm"/></th>
                    <td><s:password id="passwordConfirmation" name="passwordConfirmation"/></td>
                </tr> 
           </table>
           <s:submit name="savePassword"><f:message key="user.edit.save"/></s:submit>
           <s:submit name="cancelPassword"><f:message key="user.edit.cancel"/></s:submit>
                </fieldset>
        </s:form>

    </s:layout-component>
</s:layout-render>
