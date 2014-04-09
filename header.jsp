
<%@ page import ="com.satya.BusinessObjects.*"%>
<%
	User loggedinUser = (User)session.getAttribute("loggedInUser");
%>
<script>
	$(".button").button();
</script>
 <% if (loggedinUser != null){ %>
 		
 		<div align="right">
	 		Welcome <label style="font-weight:bold"><%=loggedinUser.getUserName()%></label>
	 	 	
	 	 	<% if(loggedinUser.getIsLimited()){ %>
	 	 		(Promotional Id)
	 	 	<% } %>
	 	 	<a class="button" style="margin-left:10px;padding:3px;" href="User?action=logout">logout</a>
 	 	</div>
 	 	
 		<table width="100%" style="border:thin silver solid;margin-bottom:10px" cellspacing="3" cellpadding="3">
		  <tr>
		    <!-- <td class="ui-widget-header"><a href="Games?action=showGames">All Games</a></td> -->
		    <td class="ui-widget-header"><a href="games.jsp">All Games</a></td>
		    <% if(loggedinUser.getIsLimited()){ %>
		    	<td class="ui-widget-header"><a href="Games?action=showMyGames">My Games</a></td>
		    <% } %>	
		    <td class="ui-widget-header"><a href="Games?action=history">Games History</a></td>
		    <td class="ui-widget-header" width="100px;"><a href="User?action=myAccount">My Account</a></td>
		  </tr>
		</table>
<%}%>