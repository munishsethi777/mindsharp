<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<script src="js/highcharts.js"></script>
  	<html>
  	<head>
  		<script type="text/javascript">
  		$(document).ready(function() {
	  		var chart = new Highcharts.Chart({
	  		    chart: {
	  		        renderTo: 'container',
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
  	<body>
  	<div class="bodyDiv" align="center" style="margin:auto">
  		<%@ include file="header.jsp" %> 
	  <%	
	  if(request.getAttribute("gameResults")!=null){
  		List<GameResult> gameResults =(List<GameResult>) request.getAttribute("gameResults"); %>   
    <table align="left" width="800px" border="0" style="border:solid silver thin">
 		<c:if test="${gameResults != null}">
 			<tr>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">Game</th>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">Dated</th>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">Score</th>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">Correct</th>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">levels</th>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">Attempts</th>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">Played secs</th>
 				<th align="left" class="ui-widget-header" style="font-size:12px;">Accuracy %</th>
 				
 			</tr>
			<c:forEach items="${gameResults}" var="result">
				<tr>
					<td><c:out value="${result.game.name}" /></td>
					<td><c:out value="${result.dated}" /></td>
					<td><c:out value="${result.score}" /></td>
					<td><c:out value="${result.correct}" /></td>
					<td><c:out value="${result.levels}" /></td>
					<td><c:out value="${result.totalAttempts}" /></td>
					<td><c:out value="${result.timePlayedSeconds}" /></td>
					<td><c:out value="${result.accuratePercent}" /></td>
				</tr>
			</c:forEach>
		</c:if>
    </table>
    <%} %>
    
   <div id="container" style="min-width: 400px; height: 400px; margin: 0 auto"></div>
    </body>
</html>