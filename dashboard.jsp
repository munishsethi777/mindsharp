
<%@ include file="includeJars.jsp"%>
<%@ include file="includeJS.jsp"%>
<html>
<head>
<script>
	$(function() {
		setActiveButton("dashboard");
		getSuggestedGames();
		getRecentPlayedGames();
	});
	function getSuggestedGames(){
		$(".gamesDiv").html("<img src='images/loading.gif'/>");
		$.getJSON('Games?action=getGamesBySkills' ,function(data){
			$(".gamesDiv").html("");
			$.each(data,function(key,value){				
				var gamesDiv = "";
				gamesDiv += "<div class='suggestedGameBlock'>";
				gamesDiv += "<div class='widget-gameimage'>";
				gamesDiv += "<a href='Games?action=showGame&id="+ value.seq +"'>";
				gamesDiv += "<img width='100%' src='images/games/small/"+ value.seq +".jpg'></a>";
				gamesDiv += "</div>";
				gamesDiv += "<div style='padding:5px;'>";
				gamesDiv += "<h4>" + value.gameName + "</h4>" ;
				gamesDiv += "<h5>" + value.gameDescription + "</h5>" ;
				gamesDiv += "<p class='widget-skills'>"
				$.each(value.tags,function(key,value){
					gamesDiv += "<span class='label label-xlg label-primary arrowed arrowed-right'>";
					gamesDiv += value; 
					gamesDiv += "</span>";
				});
				gamesDiv += "</p></div></div><div style='clear:both'></div>";
				$(".gamesDiv").append(gamesDiv).hide().fadeIn('fast');
			});
		}).error(function(e) {
			location.reload();
		});
  				
  	}
	function getRecentPlayedGames(){
		$(".recentPlayedDiv").html("<img src='images/loading.gif'/>");
		$.getJSON('Games?action=getLastPlayedGames' ,function(data){
			$(".recentPlayedDiv").html("");
			$.each(data,function(key,value){				
				var gamesDiv = "";
				gamesDiv += "<div class='suggestedGameBlock'>";
				gamesDiv += "<div class='widget-gameimage'>";
				gamesDiv += "<img width='90%' src='images/games/small/"+ value.seq +".jpg'>";
				gamesDiv += "</div>";
				gamesDiv += "<div style='padding:5px;'>";
				gamesDiv += "<h4>" + value.gameName + "</h4>" ;
				gamesDiv += "Played on : 12th May 2014<br>";
				gamesDiv += "Scored : 340<score><br>";
				gamesDiv += "Percentage : 20%<percent><br>";
				gamesDiv += "</div></div><div style='clear:both'></div>";
				$(".recentPlayedDiv").append(gamesDiv).hide().fadeIn('fast');
			});
		}).error(function(e) {
			location.reload();
		});
  				
  	}
</script>
<%@ include file="headerPrivate.jsp"%>
</head>
<body class="">
	<div id="body">
		<div class="container">
			<!--  SUGGESTED GAMES WIDGET STARTS -->
			<div id="suggestedGamesWidget" class="col-sm-6 widget-container-col ui-sortable">
				<div class="widget-box">
					<div class="widget-header">
						<h4 class="widget-title"><i class="menu-icon fa fa-bell"></i> SUGGESTED GAMES</h4>
					</div>
					<div class="widget-body">
						<div class="widget-main padding-6 gamesDiv">
						</div>
					</div>
				</div>
			</div>
			<!--  SUGGESTED GAMES WIDGET ENDS -->
			
			<!--  RECENT GAMES PLAYED WIDGET STARTS -->
			<div id="recentGamesPlayedWidget" class="col-sm-6 widget-container-col ui-sortable">
				<div class="widget-box">
					<div class="widget-header">
						<h4 class="widget-title"><i class="menu-icon fa fa-thumbs-up"></i> RECENTLY PLAYED GAMES</h4>
					</div>
					<div class="widget-body">
						<div class="widget-main padding-6 recentPlayedDiv">
							
							
			<!--  Repeated Blocks ends-->
						</div>
					</div>
				</div>
			</div>
			<!--  RECENT GAMES PLAYED WIDGET ENDS -->

			<div style="clear:both;padding:15px"></div>
					
			<!--  LEADERBOARD AREA STARTS -->
			<div id="recentGamesPlayedWidget" class="col-sm-12 widget-container-col ui-sortable">
				<div class="widget-box">
					<div class="widget-header">
						<h4 class="widget-title"><i class="menu-icon fa fa-thumbs-up"></i> LEADERBOARD</h4>
					</div>
					<div class="widget-body">
						<div class="widget-main padding-6">
						</div>
					</div>
				</div>
			</div>
			<!--  LEADERBOARD AREA ENDS -->
			<div style="clear:both;padding:15px"></div>
						

		</div>
	</div>
<%@ include file="footerPublic.jsp"%>

</body>

</html>
