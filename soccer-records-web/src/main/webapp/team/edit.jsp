
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/layout.jsp" titlekey="team.edit.title">
<s:layout-component name="body">

<s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean" var="actionBean"/>

<s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.TeamActionBean">
    <s:hidden name="team.teamId"/>
     
    <fieldset>
                <legend>
                    <f:message key="team.edit.legend"/>
                </legend>
                <s:errors/>
                <table>
                    <tr>
                        <th class="text-right">
                            <s:label for="name" name="team.name"/>:
                        </th>
                        <td class="text-left">
                            <s:text id="name" name="team.teamName" />
                        </td>
                    </tr>
                    <tr>
                        <td class="text-right">
                             <s:submit name="save"><f:message key="team.edit.save"/></s:submit>
                         </td>
                        <td class="text-left">
                            <s:submit name="cancel"><f:message key="team.edit.cancel"/></s:submit>
                        </td>
                    </tr>
                </table>
                </fieldset>
        </s:form>

</s:layout-component>
</s:layout-render>
