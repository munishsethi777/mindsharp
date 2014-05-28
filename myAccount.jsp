<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<%	User loggedinUser = (User)session.getAttribute("loggedInUser"); %>
<html>
  	<script>
	     $(function() {
	    	resetAutocomplete($(".orgType").val());
		 	setActiveButton("accountDetails");
		 	$(".orgTypeSel").change(resetAutocomplete);
			function resetAutocomplete(orgType) {
			   // $("#organ").autocomplete("destroy");
			    $("#organ").autocomplete({
			         source:"Organization?action=findOrganization&orgType="+orgType,
			         minLength: 2,
			         select: function( event, ui ) {
			           	 $(".orgName").text(ui.item.value);
			                $(".orgAddress").text(ui.item.orgAddress);
			                $(".orgState").text(ui.item.orgState);
			                $("#orgSeq").val(ui.item.seq);
			            }
			    });
			}
			$.getJSON("Organization?action=getOrganizationTypesJSON",function(data){
		    	$(".orgType").html("<select name='orgTypeSelect' class='orgTypeSel' onChange='orgTypeChange()'></select>");
		    	$.each(data,function(key,value){
		    		
		    		$('.orgTypeSel')
		            .append($("<option></option>")
		            .attr("value",value)
		            .text(key)); 
		    		
		    	});
		    });
		    function orgTypeChange(){
		    	var orgType = $(".orgTypeSel").val();
		    	$.getJSON("Organization?action=getOrganizationStreamsJSON&orgType="+ orgType,function(data){
			    	$(".orgTypeStream").html("<select class='orgStreamSel'></select>");
			    	$.each(data,function(key,value){
			    		$('.orgStreamSel')
			            .append($("<option></option>")
			            .attr("value",value)
			            .text(key)); 
			    		
			    	});
			    });
		    }
		    $("#saveOrg").click(function(){
				orgSeq = $("#orgSeq").val();
				$.post("User?action=setOrgOnUser&seq=" + orgSeq, function(json){
					 location.reload();
				});
			});
		    
		 	var theme = "bootstrap";
		 	$('.text-input').jqxInput({ height: 25, width: 200,theme:theme });		 	
		 	// add validation rules.
            $('#MyAccountForm').jqxValidator({
                rules: [
                       { input: '#fullNameInput', message: 'Fullname is required', action: 'keyup, blur', rule: 'required' },
                       { input: '#fullNameInput', message: 'Your FullName can not be more than 500 characters!',action: 'keyup, blur', rule: 'length=0,500' },
                       
                       { input: '#emailIdInput', message: 'email id is required', action: 'keyup, blur', rule: 'required' },
                       { input: '#emailIdInput', message: 'Your Email can not be more than 256 characters!',action: 'keyup, blur', rule: 'length=0,256' },
                       
                       { input: '#mobileInput', message: 'mobile is required',action: 'keyup, blur', rule: 'required' },
                       { input: '#mobileInput', message: 'Your mobile number can not be more than 20 characters!',action: 'keyup, blur', rule: 'length=0,20' },
                       
                       { input: '#addressInput', message: 'address is required',action: 'keyup, blur', rule: 'required' },
                       { input: '#addressInput', message: 'Your address can not be more than 500 characters!',action: 'keyup, blur', rule: 'length=0,256' },
                       
                       { input: '#countryInput', message: 'country is required',action: 'keyup, blur', rule: 'required' }
                ]
                       
            });
            // validate form.
            $("#SaveMyAccount").click(function () {
                $('#MyAccountForm').jqxValidator('validate');
            });
            $("#MyAccountForm").on('validationSuccess', function () {
            	$('#MyAccountForm').submit();
            });
            
	     });
	</script>
	<link rel="stylesheet" href="assets/jqwidgets/styles/jqx.base.css" type="text/css" />
	<link rel="stylesheet" href="assets/jqwidgets/styles/jqx.bootstrap.css" type="text/css" />
    <script type="text/javascript" src="assets/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="assets/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="assets/jqwidgets/jqxvalidator.js"></script>
    <script type="text/javascript" src="assets/jqwidgets/jqxinput.js"></script>
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
	    <form action="User?action=updateMyaccount" method="POST" id="MyAccountForm">
    	<table style="border:solid silver thin" width="100%">
    		<tr>
	   			<td class="gameLabelTD">User Name</td>
	   			<td class="gameValueTD"><%=loggedinUser.getUserName() %></td>
	   		</tr>
	   		<tr>
	   			<td class="gameLabelTD">Full Name</td>
	   			<td class="gameValueTD"><input type="text" name="fullName" id="fullNameInput" class="text-input"
	   				value ="<%=loggedinUser.getFullName() %>"  />						
	   			</td>
	   		</tr>
	   		<tr>
	   			<td class="gameLabelTD">Email Id</td>
	   			<td class="gameValueTD"><input type="text" name="emailId" id="emailIdInput" class="text-input" 
	   			value ="<%=loggedinUser.getEmailId() %>"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<% 	String mobile = "";
	   				if(loggedinUser.getMobile() != null){
	   					mobile = loggedinUser.getMobile();
	   				}
	   			%>
	   			<td class="gameLabelTD">Mobile Number</td>
	   			<td class="gameValueTD"><input type="text" name="mobile" id="mobileInput" class="text-input" 
	   			value ="<%=mobile%>"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<% 	String add = "";
	   				if(loggedinUser.getAddress() != null){
	   					add = loggedinUser.getAddress();
	   				}
	   			%>
	   			<td class="gameLabelTD">Address</td>
	   			<td class="gameValueTD"><input type="text" name="address" id="addressInput" class="text-input" 
	   			value ="<%=add%>"/>
	   			</td>
	   		</tr>
	   		<tr>
	   			<% 	String country = "";
	   				if(loggedinUser.getCountry() != null){
	   					country = loggedinUser.getCountry();
	   				}
	   			%>
	   			<td class="gameLabelTD">Country</td>
	   			<td class="gameValueTD"><input type="text" name="country" id="countryInput" class="text-input" 
	   			value ="<%=country%>"/>
	   			</td>
	   		</tr>
			<tr>
				<% 	String skills = "";
	   				if(loggedinUser.getMySkillBadges() != null){
	   					skills = loggedinUser.getMySkillBadges().toString();
	   				}
	   			%>
	   			<td class="gameLabelTD">User Badges</td>
	   			<td class="gameValueTD"><%=skills %></td>
	   		</tr>
			<tr>
	   			<td class="gameLabelTD">User Status</td>
	   			<td class="gameValueTD"><%=loggedinUser.getStatus()%></td>
	   		</tr>
	   		<tr>
	   			<td></td>
	   			<td><input type="button" id="SaveMyAccount" class="btn" value="Save"> <input type="reset" class="btn"></td>
	   		</tr>
	   	</table>
	   	</form>
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
    			<td class="gameLabelTD">Organization Name</td>
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
