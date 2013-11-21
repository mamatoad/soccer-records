<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<table>
    <tr>
        <th><s:label for="name" name="player.name"/></th>
        <td><s:text id="name" name="player.playerName"/></td>
    </tr>    
    <tr>   
        <th><s:label for="team" name="player.team"/></th>
        <td>
            <s:select id="team" name="player.teamId"> 
                <s:option value=""><f:message key="player.noTeamName"/></s:option>
                <c:forEach items="${actionBean.teams}" var="team">
                    <s:option value="${team.teamId}" label="${team.teamName}"/> 
                </c:forEach>
            </s:select> 
        </td>
    </tr>
    <tr>
        <th><s:label for="active" name="player.active"/></th>
        <td><s:checkbox id="active" name="player.playerActive"/></td>
    </tr>
</table>