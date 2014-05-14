<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<%	User loggedinUser = (User)session.getAttribute("loggedInUser"); %>
<html>
<head>
 		<%@ include file="headerPrivate.jsp" %>
 		<script>
 		$(document).ready(function() {
  			setActiveButton("games");
  		}); 
 		</script>
</head> 
<body>
	<div id ="body">
		<div class="container">
	 	<% 	if(request.getAttribute("userGames") != null){
		  	List<UserGame> userGames =(List<UserGame>) request.getAttribute("userGames"); %>   
		   	<ul class="thumbnails">
		 		<c:if test="${userGames != null}">
					<c:forEach items="${userGames}" var="userGame">
						<li class="span3 gameDiv thumbnail">
							<a href="Games?action=showGame&id=<c:out value="${userGame.game.seq}" />">
							<img width="100%" src="images/games/small/<c:out value="${userGame.game.seq}" />.jpg">
							</a>
							<c:out value="${userGame.game.name}" /><br>
							<p>Enrolled on: <br><c:out value="${userGame.enrollmentDate}"/></p>
			 			</li>
					</c:forEach>
				</c:if>
		   </ul>
		<%} %>
		</div>
	</div>
<%@ include file="footerPublic.jsp"%>
</body>
</html>