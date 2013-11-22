<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean">
    <fieldset>
        <legend><f:message key="player.search"/></legend>
        <table class="list">
            <tr>
                <th><s:label for="name" name="player.name"/></th>
                <td><s:text id="name" name="searchPlayerName"/></td>
            </tr>   
            <tr>
                <th><s:label for="team" name="player.team"/></th>
                <td><s:select id="team" name="searchTeamId">
                <s:option value=""><f:message key="player.noTeamName"/></s:option>
                <c:forEach items="${actionBean.teams}" var="team">
                    <s:option value="${team.teamId}" label="${team.teamName}"/> 
                </c:forEach>
            </s:select></td>
            </tr>
        </table>
        <s:submit name="search"><f:message key="player.searchButton"/></s:submit>

        </fieldset>
</s:form>
