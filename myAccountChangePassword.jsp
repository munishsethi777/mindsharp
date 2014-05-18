<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<%	User loggedinUser = (User)session.getAttribute("loggedInUser"); %>
<html>
  	<script>
     $(function() {
	 	setActiveButton("changePassword");
     });
     
     </script>


<body>

<%@ include file="headerPrivate.jsp" %>
 
<div id="body" >
  	<div class ="container">


	<%@ include file="myAccountSideBar.jsp"%>

	<div class = "main-content">
		  	<%	List<String> errMessages =(List<String>) request.getAttribute("errMessages"); %>
			<c:if test="${errMessages != null}">
					<p style="padding:10px 10px 10px 10px;" class="alert alert-danger">
					<c:forEach items="${errMessages}" var="msg">
						<c:out value="${msg}" /> </br>
					</c:forEach>
					</p>
			</c:if>
			<%	List<String> sccMessages =(List<String>) request.getAttribute("sccMessages"); %>
			<c:if test="${sccMessages != null}">
					<p style="padding:10px 10px 10px 10px;" class="alert alert-success">
					<c:forEach items="${sccMessages}" var="sccmsg">
						<c:out value="${sccmsg}" /> </br>
					</c:forEach>
					</p>
			</c:if>
    	<form action="User?action=changePassword" method="POST">
   		<p style="margin-top:10px;">Change Password</p>
   		<table style="border:solid silver thin;margin-top:10px;">
    		<tr>
    			<td class="gameLabelTD">Current Pass:</td>
    			<td class="gameValueTD"><input type="password" name="currentPassword"/></td>
    		</tr>
    		<tr>
    			<td class="gameLabelTD">New Pass:</td>
    			<td class="gameValueTD"><input type="password" name="newPassword"/></td>
    		</tr>
    		<tr>
    			<td class="gameLabelTD">Confirm Pass:</td>
    			<td class="gameValueTD"><input type="password" name="confirmPassword"/></td>
    		</tr>
    		<tr>
    			<td class=""></td>
    			<td>
					<input type="submit" class="btn">
					<input type="reset" class="btn">
				</td>
    		</tr>
    	</table>
		</Form>
		
    	</div>
   </Div>
   </div>
   <%@ include file="footerPublic.jsp"%>

    </body>
</html>
