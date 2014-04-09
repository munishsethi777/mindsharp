<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
  	<html>
  	<head>
  		<%@ include file="header.jsp" %> 
  	</head> 
  	<body>
  	<table align="center" width="40%" border="0">
     <%	User user =(User) request.getAttribute("user"); %>  		
       		<%	List<String> errMessages =(List<String>) request.getAttribute("errMessages"); %>
			<c:if test="${errMessages != null}">
				<tr>       
        		<td style="padding:10px 10px 10px 10px;" class="ui-state-error">
					<c:forEach items="${errMessages}" var="msg">
						<c:out value="${msg}" /> </br>
					</c:forEach>
				</td>
       			</tr>
			</c:if>

		  
      <tr>
        <td class="ui-widget-header" style="padding:10px 10px 10px 10px;">New user Signup	</td>
        </tr>
      <tr>
        <td class="ui-widget-content">
            <form name="frm1" method="post" action="User?action=signup">        
                <table width="100%" border="0" style="padding:10px 10px 10px 10px;">
                  <tr>
                    <td width="22%">Email Id</td>
                    <td width="78%"><input name="emailId" type="text" size="30" value='<c:out value="${user.emailId}" />'></td>
                  </tr>
                  <tr>
                    <td width="22%">Username</td>
                    <td width="78%"><input name="userName" type="text" size="30" value='<c:out value="${user.userName}" />'></td>
                  </tr>
                  <tr>
                    <td width="22%">Password</td>
                    <td width="78%"><input name="password" type="password" size="30"></td>
                  </tr>
                  <tr>
                    <td width="22%">Confirm Password</td>
                    <td width="78%"><input name="cpassword" type="password" size="30"></td>
                  </tr>
                  
                  <tr>
                    <td width="22%">Full Name</td>
                    <td width="78%"><input name="fullName" type="text" size="30" value='<c:out value="${user.fullName}" />'></td>
                  </tr>
                  <tr>
                    <td width="22%">Promotion Code</td>
                    <td width="78%"><input name="refCode" type="text" size="30" value='<c:out value="${user.refCode}" />'></td>
                  </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td><input type="submit" name="submit" value="Register" />
                         
                        <input type="reset" name="Reset" value="Reset">
                        
                    
                    </td></tr>
                </table>
              </form> 
         </td>
        </tr>
        
    </table>
    </body>
</html>