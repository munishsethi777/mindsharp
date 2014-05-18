<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>

<%	User loggedinUser = (User)session.getAttribute("loggedInUser"); %>
<html>
<head>
</head>
<body>
<%@ include file="headerPrivate.jsp" %> 
<div id="body">
  	<div class="container">
  	<%	Game game = null;
  		if(request.getAttribute("game") != null){
  			game = (Game) request.getAttribute("game");
  		}
  	%>
	  	<div style="border:silver solid thin;width:1185px;padding:5px;">
		 	<div class="gameObjectDiv" style="float:left;width:665px;height:550px;padding:5px;">
				<% if(game.getIsGameEnrolled(loggedinUser)) {%>
					<%@ include file="_includeGame.html" %> 
				<%}else{ %>
					You are a promotional user. You are not authroized to play this game.<br>
					<img style="margin-top:20px;border:#EEE solid 10px" src ="images/games/small/<%=game.getSeq() %>.jpg" />
				<%} %>
			</div>
			
			<div class="gameDescDiv" style="margin-top:6px;margin-right:6px;float:right;width:485px;padding:5px;text-align:left;border:#BEBEBE solid thin">
				<table class="gameInfoTable">
					<tr>
						<td class="gameLabelTD">Game Name:</td>
						<td class="gameValueTD"><%=game.getName()%></td>
					</tr>
					<tr>
						<td class="gameLabelTD">Game Description:</td>
						<td class="gameValueTD"> <%=game.getDescription()%></td>
					</tr>
					<tr>
						<td class="gameLabelTD">Game Skill:</td>
						<td class="gameValueTD"> <%=game.getGameSkillType().key()%></td>
					</tr>
					<tr>
						<td class="gameLabelTD">Game Tags:</td>
						<td class="gameValueTD"> <%=game.getTagsString()%></td>
					</tr>
					<tr>
						<td class="gameLabelTD">Best Score:</td>
						<td class="gameValueTD"><%=game.getMyBestScore()%></td>
					</tr>
					<tr>
						<td class="gameLabelTD">My Best Score:</td>
						<td class="gameValueTD"><%=game.getMyBestScore()%></td>
					</tr>
					<tr>
						<td class="gameLabelTD">Last Played On:</td>
						<td class="gameValueTD">12th December 2011</td>
					</tr>
				</table>
			</div>
			<div style="clear:both"></div>
		</div>
	</div>
</div>
<%@ include file="footerPublic.jsp"%>
</body>
</html>