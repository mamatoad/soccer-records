<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>

<s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean">
    <fieldset>
        <legend><f:message key="match.filter"/></legend>
        <table>
             
            <tr>
                <th><s:label for="team" name="match.filter.team"/></th>
                <td><s:select id="team" name="searchTeamId">
                    <s:option value=""><f:message key="player.noTeamName"/></s:option>
                    <c:forEach items="${actionBean.teams}" var="team">
                        <s:option value="${team.teamId}" label="${team.teamName}"/> 
                    </c:forEach>
                </s:select></td>
            </tr>
        </table>
        <s:submit name="filter"><f:message key="match.filter.filterResults"/></s:submit>
        
    </fieldset>
</s:form>
