<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<script src="js/highcharts.js"></script>
  	<html>
  	<head>
  		<script type="text/javascript">
  		$(document).ready(function() {
  			setActiveButton("gamesHistory");
	  		var chart = new Highcharts.Chart({
	  		    chart: {
	  		        renderTo: 'graph',
	  		      	type: 'column'
	  		    },
	  		  	xAxis: {
	                categories: ['Speed', 'Flexibility', 'Problem Solving', 'Attention','Memory']
	            },
	  		    series: [{
	  		        data: []        
	  		    }]
	  		});
	  		
	  		$.getJSON("User?action=scoreChartJSON",function(data){
	  			chart.series[0].setData(data);
	  		});
	  		//chart.series[0].setData([129.2, 144.0, 176.0, 135.6] );
  		});
         
        </script>
  	</head> 
  	<body class="default">
  	
  		<%@ include file="headerPrivate.jsp" %> 
	  <%	
	  if(request.getAttribute("gameResults")!=null){
  		List<GameResult> gameResults =(List<GameResult>) request.getAttribute("gameResults"); %>
  	<div id="body">
  		<div class="container"> 
  		
  		<div id="graph" style="min-width: 400px; height: 300px; margin: 0 auto"></div>
  		  
    	<table id = "sample-table-1" class="bootstrapTable table table-striped table-bordered table-hover">
 		<c:if test="${gameResults != null}">
 			<thead>
	 			<tr>
	 				<th>Game</th>
	 				<th width="18%">Dated</th>
	 				<th width="5%">Score</th>
	 				<th width="5%">Correct</th>
	 				<th width="5%">levels</th>
	 				<th width="8%">Attempts</th>
	 				<th width="10%">Time(mins)</th>
	 				<th width="9%">Accuracy %</th>
	 			</tr>
	 		</thead>
			<c:forEach items="${gameResults}" var="result">
				<tr>
					<td><c:out value="${result.game.name}" /></td>
					<td><c:out value="${result.formattedResultDate}" /></td>
					<td><c:out value="${result.score}" /></td>
					<td><c:out value="${result.correct}" /></td>
					<td><c:out value="${result.levels}" /></td>
					<td><c:out value="${result.totalAttempts}" /></td>
					<td><c:out value="${result.timePlayedMinutes}" /></td>
					<td><c:out value="${result.accuratePercent}" /></td>
				</tr>
			</c:forEach>
		</c:if>
    </table>
    <%} %>
    
   
   	</div>
   </div>
    </body>
</html>