<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<%	User loggedinUser = (User)session.getAttribute("loggedInUser"); %>
<html>
  	<script>
	     $(function() {
		 	setActiveButton("accountDetails");
	     });
	</script>
<body>

<%@ include file="headerPrivate.jsp" %>
 
<div id="body" >
  	<div class ="container">
  	<%	List<String> errMessages =(List<String>) request.getAttribute("errMessages"); %>
	<c:if test="${errMessages != null}">
			<p style="padding:10px 10px 10px 10px;" class="ui-state-error">
			<c:forEach items="${errMessages}" var="msg">
				<c:out value="${msg}" /> </br>
			</c:forEach>
			</p>
	</c:if>
	<%	List<String> sccMessages =(List<String>) request.getAttribute("sccMessages"); %>
	<c:if test="${sccMessages != null}">
			<p style="padding:10px 10px 10px 10px;" class="ui-state-highlight">
			<c:forEach items="${sccMessages}" var="sccmsg">
				<c:out value="${sccmsg}" /> </br>
			</c:forEach>
			</p>
	</c:if>

	<%@ include file="myAccountSideBar.jsp"%>
<div class = "main-content">
	<div class = "panel panel-info">
		<div class="panel-heading">
	        <h3 class="panel-title">User Details</h3>
	    </div>
    	<table style="border:solid silver thin" width="100%">
	   		<tr>
	   			<td class="gameLabelTD">Full Name</td>
	   			<td class="gameValueTD"><%=loggedinUser.getFullName() %></td>
	   		</tr>
	   		<tr>
	   			<td class="gameLabelTD">Email Id</td>
	   			<td class="gameValueTD"><%=loggedinUser.getEmailId() %></td>
	   		</tr>
	   		<tr>
	   			<td class="gameLabelTD">User Name</td>
	   			<td class="gameValueTD"><%=loggedinUser.getUserName() %></td>
	   		</tr>
			<tr>
	   			<td class="gameLabelTD">User Badges</td>
	   			<td class="gameValueTD"><%=loggedinUser.getMySkillBadges() %></td>
	   		</tr>
			<tr>
	   			<td class="gameLabelTD">User Status</td>
	   			<td class="gameValueTD"><%=loggedinUser.getStatus()%></td>
	   		</tr>
	   	</table>
	 </div>
    	
	<%
		boolean hasOrg = true;
		if(loggedinUser.getOrganization() == null){
			hasOrg = false;
		}
	%>
   	<div class = "panel panel-info">
		<div class="panel-heading">
	        <h3 class="panel-title">Organization Details</h3>
	    </div>
   		<table style="border:solid silver thin;width:100%">
    		<tr>
    			<td class="gameLabelTD">Organization Names</td>
					<input type="hidden" id="orgSeq" name="seq"/>
					<td class="gameValueTD"><input id="organ" />	
					<input type="button" value="Save" id="saveOrg"/>					
					<input type="button" value="Add" id="showOrgButton"/></td>			
    		</tr>
			<tr>
	    			<td class="gameLabelTD">Organization Type</td>
	    			<td class="gameValueTD orgt"><%if(hasOrg){%><%=loggedinUser.getOrganization().getType().key()%><%}%></td>
			</tr>
			<%if(hasOrg){%>
				
	    		<tr>
	    			<td class="gameLabelTD">Organization</td>				
					<td class="gameValueTD orgName"><%=loggedinUser.getOrganization().getName() %></td>
	    		</tr>
			
	    		<tr>
	    			<td class="gameLabelTD">Address</td>
	    			<td class="gameValueTD orgAddress"><%=loggedinUser.getOrganization().getAddress() %></td>
	    		</tr>
	    		<tr>
	    			<td class="gameLabelTD">State</td>
	    			<td class="gameValueTD orgState"><%=loggedinUser.getOrganization().getState() %></td>
	    		</tr>
	    		<tr>
	    			<td class="gameLabelTD">Country</td>
	    			<td class="gameValueTD orgCountry"><%=loggedinUser.getOrganization().getCountry() %></td>
	    		</tr>
     		<%}%>
 
    	</table>
	</div>
    	<%@ include file="organizationFormInclude.jsp"%>
    	
		<form action="User?action=uploadImage" method="POST" enctype="multipart/form-data">
			<p style="margin-top:10px;">Upload Image</p>
			<table style="border:solid silver thin;margin-top:10px;" width="100%">
	    		<tr>
					<td class="gameLabelTD"><img src="images\userImages\<%=loggedinUser.getImageName()%>"/></td>
	    			<td class="gameValueTD">
							<input type="file" name="attachement" size="50">
					</td>
					
	    		</tr>
				<tr>
    			<td class=""></td>
    			<td>
					<input type="submit" class="btn">
					<input type="reset" class="btn">
				</td>
    		</tr>
	    	</table>
    	</form>	
    	</div>
   </Div>
   </div>
   <%@ include file="footerPublic.jsp"%>

    </body>
</html>
