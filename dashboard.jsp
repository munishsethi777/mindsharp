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
		Recomended Games : - <br/>
			<c:forEach items="${games}" var="game">
				<tr><td align="left" class="ui-widget-header" style="font-size:16px">
					<div class='ui-widget-header gameDiv'>
						<a href='Games?action=showGame&id=${game.seq}'>
							<img height='225px' width='300px' src='images/games/small/${game.seq}.jpg'>
						</a>
						<c:out value="${game.name}" />
						<br>Tags: 
						<c:forEach items="${game.tags}" var="tag">
							<c:out value="${tag.tag}" />
						</c:forEach>
					</div>
	 			</td></tr>
			</c:forEach>
		</c:if>
    </table>
    <%}%>
   </Div>
    </body>
</html>
