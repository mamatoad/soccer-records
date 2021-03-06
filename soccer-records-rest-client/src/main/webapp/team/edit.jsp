<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout.jsp" titlekey="team.edit.title">
    <s:layout-component name="body">
        <s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.TeamActionBean" var="actionBean"/>

        <s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.TeamActionBean">
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
                            <s:text id="name" name="team.teamName"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="text-right">
                            <s:submit name="save">
                                <f:message key="team.edit.save"/>
                            </s:submit>
                        </td>
                        <td class="text-left">
                            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.client.TeamActionBean">
                                <f:message key="team.edit.cancel"/>
                            </s:link>
                        </td>
                    </tr>
                </table>
                </fieldset>
        </s:form>
        
        <%-- Stripes URL bug workaround --%>
        <script>
            var form = document.getElementsByTagName("form")[0];
            form.setAttribute("action", form.getAttribute("action").replace("/{$event}/{team.teamId}", ""));
        </script>

    </s:layout-component>
</s:layout-render>