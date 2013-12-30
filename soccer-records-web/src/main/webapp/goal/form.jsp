<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:errors/>
<table>

<tr>
    <th><s:label for="b2" name="goal.playerName"/></th>
    <td>
        <s:select id="b2" name="goal.playerId">

    <c:forEach items="${actionBean.players}" var="player">
           
            <s:option value="${player.playerId}" label="${player.playerName}"/>
       
    </c:forEach>
</s:select>
</td>
</tr>
<tr>
<tr>
    <th><s:label for="b3" name="goal.time.minutes"/></th>
    <td>
        <s:select id="b3" name="minute">
            <c:forEach items="${actionBean.minutes}" var="minute">
                <s:option value="${minute}" label="${minute}"/>
            </c:forEach>
        </s:select>
    </td>
</tr>
<tr>
    <th><s:label for="b4" name="goal.time.hours"/></th>
    <td>
        <s:select id="b4" name="hour">
            <c:forEach items="${actionBean.hours}" var="hour">
                <s:option value="${hour}" label="${hour}"/>
            </c:forEach>
        </s:select>
    </td>
</tr>
</table>
