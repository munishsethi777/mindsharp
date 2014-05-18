<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<%	User loggedinUser = (User)session.getAttribute("loggedInUser"); %>
<html>
<head>
  		<Script>
  		$(document).ready(function() {
  			setActiveButton("games");
  			getGames("all");
  			getAllTags();
  		});

  			
			var skillType = "all";
  			function getGames(skillName){
  				$(".sidebar a").removeClass("active");
  				$("#"+skillName).addClass("active");
				skillType = skillName
  				$(".gamesDiv").html("<img src='images/loading.gif'/>");
  				$.getJSON('Games?action=showGamesBySkill&skill='+ skillName,function(data){
  					$(".gamesDiv").html("");
  					$.each(data,function(key,value){
						
		 				var gamesDiv = "";
		 				gamesDiv += "<li class='span3 gameDiv thumbnail'>";
		 				gamesDiv += "<a href='Games?action=showGame&id="+ value.seq +"/>'>";
		 				gamesDiv += "<img width='100%' src='images/games/small/"+ value.seq +".jpg'></a>";
		 				gamesDiv += value.gameName +"<br>";
  						gamesDiv += "<br>";
  						$.each(value.tags,function(key,value){
  							gamesDiv += "<span style='margin-right:3px;' class='label label-xlg label-primary arrowed arrowed-right'>"+ value +"</span>  ";
  						});
  						gamesDiv += "</div></li>";
  						$(".gamesDiv").append(gamesDiv).hide().fadeIn('fast');
  					});
  				}).error(function(e) {
  					location.reload();
  				});
  				
  			}
			//Later on i will make these methods common. For now its working. --  Baljeet
			
			function getGamesByTags(tagSeq){
  				$(".games").html("<img src='images/loading.gif'/>");
  				$.getJSON('Games?action=showGamesByTag&id='+ tagSeq + '&skill='+ skillType,function(data){
  					$(".games").html("");
  					$.each(data,function(key,value){
  						var gamesDiv = "";
  						gamesDiv += "<div class='ui-widget-header gameDiv'>";
  						gamesDiv += "<a href='Games?action=showGame&id="+ value.seq +"'>";
  						gamesDiv += "<img height='225px' width='300px' src='images/games/small/"+ value.seq +".jpg'></a>";
  						gamesDiv += value.gameName;
  						gamesDiv += "<br>Tags:";
  						$.each(value.tags,function(key,value){
  							gamesDiv += value +" ";
  						});
  						gamesDiv += "</div>";
  						$(".games").append(gamesDiv);
  					});
  				}).error(function(e) {
  					location.reload();
  				});
  				
  			}
  			function getAllTags(){
  				$(".tags").html("<img src='images/loading.gif'/>");
  				$.getJSON('Games?action=getAllTags',function(data){
  					$(".tags").html("");
  					$.each(data,function(key,value){
  						var gamesDiv = "";
  						gamesDiv += "<div style='padding:3px 6px 3px 6px;float:left;background:white;margin:3px 6px 3px 6px;border:#BBB solid thin'>";
  						gamesDiv += "<a href='javascript:getGamesByTags(" + value.seq + ")'>";
  						gamesDiv += value.tag + "</a>";
  						gamesDiv += "</div>";
  						$(".tags").append(gamesDiv);
  					});
  				}).error(function(e) {
  					location.reload();
  				});
  				
  			}
  		
  		</Script>
  	</head> 
<body>
<%@ include file="headerPrivate.jsp" %>
<div id="body" align="center" style="margin:auto">
	
	<div class="container">
  	
  	<%	List<Game> games =(List<Game>) request.getAttribute("games"); %>
	  	<!--<Div class="skillDivs">
	  		<a class="skillDiv" href="javascript:getGames('all')">All</a>
	  		<a class="skillDiv" href="javascript:getGames('speed')">Speed</a>
	  		<a class="skillDiv" href="javascript:getGames('flexibility')">Flexibility</a>
	  		<a class="skillDiv" href="javascript:getGames('problemSolving')">Problem Solving</a>
	  		<a class="skillDiv" href="javascript:getGames('attention')">Attention</a>
	  		<a class="skillDiv" href="javascript:getGames('memory')">Memory</a>
	  		
	  	</Div>
  		 <div class="tags" style="width:1200px;height:45px;clear:both"></div> -->
  	<div style="clear:both"></div>
  	 <div id = "sidebar" class="sidebar">
		<ul class="nav nav-list" style="top: 0px;">
			<li>
				<a id="all" href="javascript:getGames('all')"><i class="menu-icon fa fa-user"></i>
					<span class="menu-text"> ALL </span>
				</a>
			</li>
			<li>
				<a id="speed" href="javascript:getGames('speed')"><i class="menu-icon fa fa-puzzle-piece"></i>
					<span class="menu-text"> SPEED </span>
				</a>
			</li>
			<li>
				<a id="flexibility" href="javascript:getGames('flexibility')"><i class="menu-icon fa fa-gear"></i>
					<span class="menu-text"> FLEXIBILITY </span>
				</a>
			</li>
			<li>
				<a id="problemSolving" href="javascript:getGames('problemSolving')"><i class="menu-icon fa fa-gamepad"></i>
					<span class="menu-text"> PROBLEM SOLVING </span>
				</a>
			</li>
			<li>
				<a id="attention" href="javascript:getGames('attention')"><i class="menu-icon fa fa-gamepad"></i>
					<span class="menu-text"> ATTENTION </span>
				</a>
			</li>
			<li>
				<a id="memory" href="javascript:getGames('memory')"><i class="menu-icon fa fa-gamepad"></i>
					<span class="menu-text"> MEMORY </span>
				</a>
			</li>
		</ul>
	</div>
	<div class="main-content gamesDiv">
  	  	<ul class="thumbnails">
	 		<c:if test="${games != null}">
				<c:forEach items="${games}" var="game">
					<li class="span3 gameDiv thumbnail">
						<a href="Games?action=showGame&id=<c:out value="${game.seq}" />">
						<img width="100%" src="images/games/small/<c:out value="${game.seq}" />.jpg">
						</a>
						<c:out value="${game.name}" /><br>
		 			</li>
				</c:forEach>
			</c:if>
	   </ul>
	</div>
   </Div>
</div>
<%@ include file="footerPublic.jsp"%>
</body>
</html>