<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:errors/>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
   <script>
  $(function() {
    $( "#datepicker" ).datepicker({ dateFormat: "yy-mm-dd" });
  });
  </script>
  
<table>
    <tr>
        <th><s:label for="datepicker" name="match.form.eventDate"/></th>
        <td><s:text id="datepicker" name="date"/></td>
    </tr>
    <tr>
        <th><s:label for="team" name="match.form.homeTeam"/></th>
        <td><s:select id="team" name="match.homeTeamId">
        <c:forEach items="${actionBean.teams}" var="team">
        <c:set var="select" value=""/>
        <c:if test="${actionBean.homeTeamId == team.teamId}">
            <c:set var="select" value="selected"/>
        </c:if>
        
        <option value="${team.teamId}" <c:out value="${select}"/>>${team.teamName}</option>
            
        </c:forEach>
            
            </s:select>
        </td>
    </tr>
    <tr>
        <th><s:label for="b2" name="match.form.visitingTeam"/></th>
        <td><s:select id="b2" name="match.visitingTeamId">
        <c:forEach items="${actionBean.teams}" var="team">
             <c:set var="select" value=""/>
        <c:if test="${actionBean.visitingTeamId == team.teamId}">
            <c:set var="select" value="selected"/>
        </c:if>
        <option value="${team.teamId}" <c:out value="${select}"/> >${team.teamName}</option>
            
        </c:forEach>
            </s:select>
        </td>
    </tr>
   
</table>