<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<s:form beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.PlayerActionBean">
    <fieldset>
        <legend><f:message key="player.search"/></legend>
        <table class="list">
            <tr>
                <th><s:label for="auto" name="player.name"/></th>
                <td><s:text id="auto" name="searchPlayerName"/></td>
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
<script>
    $(function() {
        $("#auto").autocomplete({
            source: '${pageContext.request.contextPath}/players/find',
            minLength: 1,
            delay: 100,
            select: function(event, ui) {
                /* set on selection */
                $("#searchPlayerName").val(ui.item.player.playerName);
                $('#searchTeamId').select(ui.item.player.teamId);
            }
        });
    });
</script>