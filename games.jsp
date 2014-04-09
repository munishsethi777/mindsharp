<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
  	<html>
  	<head>
  		<Style>
  			
  		</Style>
  		<Script>
  			getGames("all");
  			getAllTags();
  			function getGames(skillName){
  				$(".games").html("<img src='images/loading.gif'/>");
  				$.getJSON('Games?action=showGamesBySkill&skill='+ skillName,function(data){
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
  						gamesDiv += "<a href='Games?action=showGame&id="+ value.tag +"'>";
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
  	<div class="bodyDiv" align="center" style="margin:auto">
  	<%@ include file="header.jsp" %>
  	
  	<%	List<Game> games =(List<Game>) request.getAttribute("games"); %>
	  	<Div class="skillDivs">
	  		<a class="skillDiv" href="javascript:getGames('all')">All</a>
	  		<a class="skillDiv" href="javascript:getGames('speed')">Speed</a>
	  		<a class="skillDiv" href="javascript:getGames('flexibility')">Flexibility</a>
	  		<a class="skillDiv" href="javascript:getGames('problemSolving')">Problem Solving</a>
	  		<a class="skillDiv" href="javascript:getGames('attention')">Attention</a>
	  		<a class="skillDiv" href="javascript:getGames('memory')">Memory</a>
	  		
	  	</Div>
  		<div class="tags" style="width:1200px;height:45px;clear:both"></div>
  	
  	  
	    <Div style ="width:100%" class="games">
	 		<c:if test="${games != null}">
				<c:forEach items="${games}" var="game">
					<div class="ui-widget-header gameDiv">
						<a href="Games?action=showGame&id=<c:out value="${game.seq}" />">
						<img height="225px" width="300px" src="images/games/small/<c:out value="${game.seq}" />.jpg">
						</a>
						<c:out value="${game.name}" />
		 			</div>
				</c:forEach>
			</c:if>
	    </Div>
   </Div>
    </body>
</html>