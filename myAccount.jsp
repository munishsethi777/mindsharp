<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
   	<html>
  	<script>
     $(function() {
	 	resetAutocomplete($(".orgType").val());
     });
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
	 $(function() {
	        $( "#sortable1, #sortable2" ).sortable({
	        	placeholder: "ui-state-highlight",
	            connectWith: ".connectedSortable",
	            change: function( event, ui ) {
	            	enableSaveSkillButton();
	            }
	        }).disableSelection();
	        
	        $(".btn").button();
	        disableSaveSkillButton();
	        
	        
	    });
	    $.getJSON("User?action=getAvailableSkills",function(data){
	    	$.each(data,function(key,value){
	    		$("#sortable1").append("<li class='ui-state-default' id='"+ value +"'>"+ key +"</li>");
	    	});
	    });
	    $.getJSON("User?action=getMySkills",function(data){
	    	$.each(data,function(key,value){
	    		$("#sortable2").append("<li class='ui-state-default' id='"+ value +"'>"+ key +"</li>");
	    	});
	    });
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
	    function disableSaveSkillButton(){
	    	$("#saveSkills").attr("disabled", "true");
	        $("#saveSkills").addClass("ui-state-disabled");
	    }
	    function enableSaveSkillButton(){
	    	$("#saveSkills").removeAttr("disabled");
	        $("#saveSkills").removeClass("ui-state-disabled");
	    }
	    function saveSkills(){
	    	$(".saveSkillLoading").show();
	    	var sortedIDs = $( "#sortable2" ).sortable( "toArray" );
	    	$.getJSON("User?action=saveMySkills&skills="+sortedIDs,function(data){
		    	$.each(data,function(key,value){
		    		disableSaveSkillButton();
		    		$(".saveSkillLoading").hide();
		    	});
		    });
	    }
     </script>
  	<body>
  	<div class="bodyDiv" style="margin:auto">
  	
  	<%@ include file="header.jsp" %> 
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
	<style>
    	#sortable1, #sortable2 {height:40px; list-style-type: none; margin: 0; padding: 5px 0px 5px 0px; float: left; background:#EEE;border:thin silver solid ;width:100%;text-transform: uppercase;}
    	#sortable1 li, #sortable2 li {cursor:pointer; margin: 5px; padding: 5px; font-size: 1.2em; float:left; }
    	.ui-state-highlight { height: 20px;width:80px;}
    	.ui-autocomplete-loading {background: white url('images/loadingsmall.gif') right center no-repeat;}
    </style>
    <script>
	    
	    
    </script>
	<table width="670px" style="border:solid silver thin;float:right">
   		<tr>
   			<td style="padding:6px;">
   					<div style="border:solid silver thin;padding:5px;">
   						<p>Available Skills</p>
						<ul id="sortable1" class="connectedSortable">
						    
						</ul>
						<div style="clear:both"></div>
   					</div>	
   					
   					<div style="clear:both;height:6px;"></div>
   					
   					<div style="border:solid silver thin;padding:5px;">
   						<p>My Skills</p>
						<ul id="sortable2" class="connectedSortable">
						   
						</ul>
						<div style="clear:both"></div>
   					</div>
   					
   					<input type="submit" class="btn" id="saveSkills" style="margin:12px 0px 0px 10px;" value="Save Skills" onClick="javascript:saveSkills();">
   					<img src="images/loading.gif" class="saveSkillLoading" style="display:none;float:right;padding-top:10px;">
   				
   			</td>
   		</tr>
    </table>
    	
	
  		<table width="500px" style="border:solid silver thin">
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
    	
		<%
			boolean hasOrg = true;
			if(loggedinUser.getOrganization() == null){
				hasOrg = false;
			}
		%>
   
   		<p style="margin-top:10px;">Organization Details</p>
   		<table width="500px" style="border:solid silver thin;margin-top:10px;">
    		<tr>
    			<td class="gameLabelTD">Organization Names</td>
					<input type="hidden" id="orgSeq" name="seq"/>
					<td class="gameValueTD"><input id="organ" /></td>	
					<td class="gameValueTD"><input type="button" value="Save" id="saveOrg"/>					
					<td class="gameValueTD"><input type="button" value="Add" id="showOrgButton"/></td>				
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
		<script>
			$("#saveOrg").click(function(){
				orgSeq = $("#orgSeq").val();
				$.post("User?action=setOrgOnUser&seq=" + orgSeq, function(json){
					 location.reload();
				});
			});
		</script>
    	<%@ include file="organizationFormInclude.jsp"%>
    	<form action="User?action=changePassword" method="POST">
   		<p style="margin-top:10px;">Change Password</p>
   		<table width="500px" style="border:solid silver thin;margin-top:10px;">
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
		<form action="User?action=uploadImage" method="POST" enctype="multipart/form-data">
			<p style="margin-top:10px;">Upload Image</p>
			<table width="500px" style="border:solid silver thin;margin-top:10px;">
	    		<tr>
					<td class="gameLabelTD"><img src="images\userImages\<%=loggedinUser.getImageName()%>"</td>
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
   </Div>
    </body>
</html>
