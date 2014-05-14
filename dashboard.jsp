<%@ include file="includeJars.jsp"%>
<%@ include file="includeJS.jsp"%>
<html>
<head>
<script>
	$(function() {
		setActiveButton("dashboard");
	});
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
						<div class="widget-main padding-6">
							<div class="suggestedGameBlock">
								<div class="widget-gameimage">
									<img src = "images\games\small\1.jpg" width="100%">
								</div>
								<div style="padding:5px;">
									<h4>BootCamp Training New Game For Employees</h4>
									<h5>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, r including versions of Lorem Ipsum.</h5>
									<p class="widget-skills">
										<span class="label label-xlg label-primary arrowed arrowed-right">Estimation</span>
										<span class="label label-xlg label-primary arrowed arrowed-right">Mental Math</span>
										<span class="label label-xlg label-primary arrowed arrowed-right">Multi Tasking</span>
									</p>
								</div>
							</div>
							<div style="clear:both"></div>
			<!--  Repeated Blocks starts-->
							<div class="suggestedGameBlock">
								<div class="widget-gameimage">
									<img src = "images\games\small\2.jpg" width="100%">
								</div>
								<div style="padding:5px;">
									<h4>BootCamp Training New Game For Employees</h4>
									<h5>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, </h5>
									<p class="widget-skills">
										<span class="label label-xlg label-primary arrowed arrowed-right">Estimation</span>
										<span class="label label-xlg label-primary arrowed arrowed-right">Mental Math</span>
										<span class="label label-xlg label-primary arrowed arrowed-right">Multi Tasking</span>
									</p>
								</div>
							</div>
							<div style="clear:both"></div>
							
							<div class="suggestedGameBlock">
								<div class="widget-gameimage">
									<img src = "images\games\small\5.jpg" width="100%">
								</div>
								<div style="padding:5px;">
									<h4>BootCamp Training New Game For Employees</h4>
									<h5>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's </h5>
									<p class="widget-tags">
										<span class="label label-xlg label-primary arrowed arrowed-right">Estimation</span>
										<span class="label label-xlg label-primary arrowed arrowed-right">Mental Math</span>
										<span class="label label-xlg label-primary arrowed arrowed-right">Multi Tasking</span>
									</p>
								</div>
							</div>
							<div style="clear:both"></div>
			<!--  Repeated Blocks ends-->
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
						<div class="widget-main padding-6">
							<div class="suggestedGameBlock">
								<div class="widget-gameimage">
									<img src = "images\games\small\6.jpg" width="100%">
								</div>
								<div style="padding:5px;">
									<h4>BootCamp Training New Game For Employees</h4>
									Played on : 12th May 2014 <br>
									Scored : 340<score><br>
									Percentage: 20%<percent>
								</div>
							</div>
							<div style="clear:both"></div>
			<!--  Repeated Blocks starts-->
							<div class="suggestedGameBlock">
								<div class="widget-gameimage">
									<img src = "images\games\small\7.jpg" width="100%">
								</div>
								<div style="padding:5px;">
									<h4>BootCamp Training New Game For Employees</h4>
									Played on : 12th May 2014 <br>
									Scored : 340<score><br>
									Percentage: 20%<percent>
								</div>
							</div>
							<div style="clear:both"></div>
							
							<div class="suggestedGameBlock">
								<div class="widget-gameimage">
									<img src = "images\games\small\8.jpg" width="100%">
								</div>
								<div style="padding:5px;">
									<h4>BootCamp Training New Game For Employees</h4>
									Played on : 12th May 2014 <br>
									Scored : 340<score><br>
									Percentage: 20%<percent>
								</div>
							</div>
							<div style="clear:both"></div>
							
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
			
						

		</div>
	</div>
</body>
</html>
