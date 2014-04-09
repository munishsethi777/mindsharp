<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
  	<html>
  	<head>
  		<%@ include file="header.jsp" %> 
  	</head> 
  	<body>
 <%	
  	if(request.getAttribute("userGames") != null){
	  	List<UserGame> userGames =(List<UserGame>) request.getAttribute("userGames"); %>   
	   	<Div style ="width:100%">
	 		<c:if test="${userGames != null}">
				<c:forEach items="${userGames}" var="userGame">
					<div class="ui-widget-header gameDiv">
						<a href="Games?action=showGame&id=<c:out value="${userGame.game.seq}" />">
						<img height="225px" width="300px" src="images/games/small/<c:out value="${userGame.game.seq}" />.jpg">
						</a>
						<c:out value="${userGame.game.name}" /><br>
						<p>Enrolled on: <br><c:out value="${userGame.enrollmentDate}"/></p>
		 			</div>
				</c:forEach>
			</c:if>
	   </Div>
	<%} %>
    </body>
</html>