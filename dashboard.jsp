
<%@ include file="includeJars.jsp"%>
<%@ include file="includeJS.jsp"%>
<html>
<head>
<script>
	$(function() {
		setActiveButton("dashboard");
		getSuggestedGames();
		getRecentPlayedGames();
		showLeaderboard();
		$( "#skillSelect" ).change(function() {
			showLeaderboard();
		});
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
				gamesDiv += "<h5>" + value.gameDescription.substring(0, 180) + "...</h5>" ;
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
				gamesDiv += "<img width='90%' src='images/games/small/"+ value.gameseq +".jpg'>";
				gamesDiv += "</div>";
				gamesDiv += "<div style='padding:5px;'>";
				gamesDiv += "<h4>" + value.gamename + "</h4>" ;
				gamesDiv += "Played on : " +  value.playedDate + "<br>";
				gamesDiv += "Scored : " + value.score   + "<score><br>";
				gamesDiv += "Percentage : 20%<percent><br>";
				gamesDiv += "</div></div><div style='clear:both'></div>";
				$(".recentPlayedDiv").append(gamesDiv).hide().fadeIn('fast');
			});
		}).error(function(e) {
			location.reload();
		});
  				
  	}
		function showLeaderboard(){
			var selectedSkill = $( "#skillSelect" ).val()
			$(".leaderboradDiv").html("<img src='images/loading.gif'/>");
			$.getJSON("Games?action=showLeaderBoard&skillType=" + selectedSkill ,function(data){
				$(".leaderboradDiv").html("");
				var boardDiv = "";
				boardDiv += '<table id = "sample-table-1" class="bootstrapTable table table-striped table-bordered table-hover">';
				boardDiv += '<thead>';
				boardDiv += '<tr><th style="width:10px">Rank</th><th>Name</th><th>Score</th>';
				boardDiv += '</thead>';
				boardDiv += '<tbody>';
				var counter = 1;
				$.each(data,function(key,value){
					boardDiv += "<tr>";
					boardDiv += "<th>"+ counter++  + "</th>";
					boardDiv += "<th>" + value.userName + "</th>";
					boardDiv += "<th>" + value.avgScore + "</th>";
					boardDiv += "</tr>";
				});
				boardDiv += '</tbody>'
				boardDiv += '</table>'
				$(".leaderboradDiv").append(boardDiv).hide().fadeIn('fast');
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
			<!--  LEADERBOARD AREA STARTS -->
			<div id="recentGamesPlayedWidget" class="col-sm-12 widget-container-col ui-sortable">
				<div class="widget-box">
					<div class="widget-header">
						<h4 class="widget-title"><i class="menu-icon fa fa-thumbs-up"></i> LEADERBOARD - Show leaderboard for the
						selected skill </h4>
						
						<select id="skillSelect">
							<option value="all">ALL SKILLS</option>
							<option value="attention">ATTENTION</option>
							<option value="flexibility">FLEXIBILITY</option>
							<option value="memory">MEMORY</option>
							<option value="problemSolving">PROBLEM SOLVING</option>
							<option value="speed">SPEED</option>
						</select>
					</div>
					<div class="widget-body">
						<div class="widget-main padding-6 leaderboradDiv">
						</div>
					</div>
				</div>
			</div>
			<!--  LEADERBOARD AREA ENDS -->
			<div style="clear:both;padding:15px"></div>
			
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
					
			
						

		</div>
	</div>
<%@ include file="footerPublic.jsp"%>

</body>

</html>
