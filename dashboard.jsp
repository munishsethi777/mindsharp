<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
  	<html>
  	<head>
  		<%@ include file="header.jsp" %> 
  	</head> 
  	<body>
  	<%if(request.getAttribute("games")!= null){	
  	List<Game> games =(List<Game>) request.getAttribute("games"); %>   
    <table align="left" width="400px" border="0">
 		<c:if test="${games != null}">
			<c:forEach items="${games}" var="game">
				<tr><td align="left" class="ui-widget-header" style="font-size:16px">
			 		<c:out value="${game.name}" />
	 			</td></tr>
			</c:forEach>
		</c:if>
    </table>
    <%}%>
   </Div>
    </body>
</html>
