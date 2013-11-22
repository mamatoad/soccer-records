 <%@page import="org.joda.time.format.DateTimeFormatter"%>
<%@page import="org.joda.time.format.DateTimeFormat"%>
<%@page import="cz.muni.fi.pa165.mamatoad.soccerrecords.dto.MatchTO"%>
<%@page import="org.joda.time.LocalDate"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<s:useActionBean beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" var="actionBean"/>
<s:layout-render name="/layout.jsp" titlekey="match.list">
    <s:layout-component name="body">
        <h2><f:message key="match.list"/></h2>
        <%@include file="filter.jsp"%>
        <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" event="add">
                          
            <img src="${pageContext.request.contextPath}/images/add.png" alt=""/> <f:message key="match.list.newMatch"/>
            </s:link>
            
<table  class="list">
    <tr>
        <th><f:message key="match.list.detail"/></th>
        <th><f:message key="match.list.date"/></th>
        <th><f:message key="match.list.score"/></th>
        <th><f:message key="match.list.action"/></th>
        
    </tr>
    <c:forEach items="${actionBean.matches}" var="match">
        <tr>
           <td><a href="/pa165/matches/detail/${match.matchId}">${match.homeTeamName} vs ${match.visitingTeamName}</a> </td>
           
            <td> <%
               MatchTO m = (MatchTO)pageContext.getAttribute("match");
               DateTimeFormatter dtf = DateTimeFormat.forPattern("dd.MM.yyyy");
                out.print(m.getEventDate().toString(dtf));
            %>  </td>
            <c:choose>
            <c:when test="${actionBean.date > match.eventDate}">
             <td> ${match.homeTeamScore} : ${match.visitingTeamScore} </td>
            </c:when>
            <c:otherwise>
                <td>_ : _ </td>
            </c:otherwise>
            </c:choose>
           <td><s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" event="edit">
            <s:param name="match.id" value="${match.matchId}"/> 
            <img src="${pageContext.request.contextPath}/images/pencil.png"/>
            <f:message key="match.list.edit"/>
            </s:link>
            <s:link beanclass="cz.muni.fi.pa165.mamatoad.soccerrecords.MatchActionBean" event="delete">
            <s:param name="match.id" value="${match.matchId}"/>
            <img src="${pageContext.request.contextPath}/images/cross.png"/>
            <f:message key="match.list.delete"/>
            </s:link>
            </td>
        </tr>
    </c:forEach>
</table>
</s:layout-component>
    </s:layout-render>
    