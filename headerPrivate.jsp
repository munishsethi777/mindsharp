    <script>
    	function setActiveButton(pageId){
    		$("#"+pageId).addClass("active");
    	}
    </script>
    <!-- Fixed navbar -->
    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">MIND-SHARP</a>
        </div>
        <div class="navbar-collapse collapse">
	          <ul class="nav navbar-nav navbar-right">
	            <li id="dashboard"><a href="dashboard.jsp"><i class="fa fa-tachometer"></i> DASHBOARD</a></li>
	            <li id="games"><a href="Games?action=showMyGames"><i class="fa fa-rocket"></i> GAMES</a></li>
	            <li id="gamesHistory"><a href="Games?action=history"><i class="fa fa-pencil"></i> GAMES HISTORY</a></li>
	            <li id="myAccount">
	            	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
	            		<i class="fa fa-1g fa-user"></i> ACCOUNT
	            		<i class="fa fa-1g fa-angle-down" style="margin-left:8px;"></i>
	            	</a>
					  <ul class="dropdown-menu nav">
					    <li><a href="User?action=myAccount"><i class="fa fa-pencil fa-cogs"></i> Settings</a></li>
					    <li><a href="#"><i class="fa fa-trash-o fa-medkit"></i> Support</a></li>
					    <li class="divider"></li>
					    <li><a href="User?action=logout"><i class="fa fa-trash-o fa-sign-out"></i> Logout</a></li>
					  </ul>
	            
	            
	            </li>
	         </ul>        
        </div><!--/.nav-collapse -->
      </div>
    </div>

	