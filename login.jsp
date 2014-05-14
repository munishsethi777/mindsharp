<%@ include file="includeJars.jsp"%>
<%@ include file="includeJS.jsp"%>
<html>
<head>
<%@ include file="headerPublic.jsp"%>
</head>
<body class="default">
	
	<link rel="stylesheet" href="assets/jqwidgets/styles/jqx.base.css" type="text/css" />
	<link rel="stylesheet" href="assets/jqwidgets/styles/jqx.bootstrap.css" type="text/css" />
    <script type="text/javascript" src="assets/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="assets/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="assets/jqwidgets/jqxvalidator.js"></script>
    <script type="text/javascript" src="assets/jqwidgets/jqxinput.js"></script>
    <script type="text/javascript" src="assets/jqwidgets/jqxexpander.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var theme = "bootstrap";
            $("#username").jqxInput({placeHolder: "Enter your username", height: 25, width: 200,theme:theme});
            $("#password").jqxInput({placeHolder: "Enter password", height: 25, width: 200,theme:theme});
            $("#loginButton").jqxButton({height:25,width:100,theme: theme});
            $("#loginDiv").jqxExpander({ toggleMode: 'none', width: '460px', showArrow: false, theme:theme });
            
            // add validation rules.
            $('#form').jqxValidator({
                rules: [
                       { input: '#username', message: 'Username is required!', action: 'keyup, blur', rule: 'required' },
                       { input: '#username', message: 'Your username must start with a letter!', action: 'keyup, blur', rule: 'startWithLetter' },
                       { input: '#username', message: 'Your username must be between 3 and 12 characters!', action: 'keyup, blur', rule: 'length=3,12' },
                       { input: '#password', message: 'Password is required!', action: 'keyup, blur', rule: 'required' },
                       //{ input: '#password', message: 'Your password must be between 4 and 12 characters!', action: 'keyup, blur', rule: 'length=4,12' }
                ]
                       
            });
            // validate form.
            $("#loginButton").click(function () {
                $('#form').jqxValidator('validate');
            });
            $("#form").on('validationSuccess', function () {
            	$('#form').submit();
            });
        });
    </script>
<div id="body">
    <div id="loginDiv" style="margin:auto;">
    	<div><h3>User Login</h3></div>
    	<div>
    	<table class="loginPageForm" align="center" width="450px" border="0" style="margin-top:10px;">
			<%
				List<String> errMessages = (List<String>) request
						.getAttribute("errMessages");
			%>
			<c:if test="${errMessages != null}">
				<tr>
					<td>
						<c:forEach items="${errMessages}" var="msg">
							<c:out value="${msg}" />
							</br>
						</c:forEach>					</td>
				</tr>
			</c:if>

			<tr>
				<td>
					<form class="form-horizontal"  id="form" name="frm1" method="post" action="User?action=login">
				
						<table border="0">
							<tr>
								<td>Username :</td>
								<td><input id="username" name="username"></td>
							</tr>
							<tr>
								<td>Password :</td>
								<td><input id="password" name="password" ></td>
							</tr>

							<tr>
								<td>&nbsp;</td>
								<td>
								<input id="loginButton" value="Login" />
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><a href="forgotPassword.php5">Forgot Password</a>
									&nbsp; &nbsp; &nbsp; <a href="signup.jsp">New User Signup</a></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
		</table>
		</div>
	</div>
	
	
	
	</div><!-- end body -->

<%@ include file="footerPublic.jsp"%>
</body>
</html>


