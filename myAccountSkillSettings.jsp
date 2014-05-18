<%@ include file="includeJars.jsp" %>
<%@ include file="includeJS.jsp" %>
<%	User loggedinUser = (User)session.getAttribute("loggedInUser"); %>
<html>
  	<script>
     $(function() {
	 	setActiveButton("selectSkills");
     });
     
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
		$("#saveOrg").click(function(){
			orgSeq = $("#orgSeq").val();
			$.post("User?action=setOrgOnUser&seq=" + orgSeq, function(json){
				 location.reload();
			});
		});
     </script>
	<style>
    	#sortable1, #sortable2 {height:50px; list-style-type: none; margin: 0; padding: 5px 0px 5px 0px; float: left; background:#EEE;border:thin silver solid ;width:100%;text-transform: uppercase;}
    	#sortable1 li, #sortable2 li {cursor:pointer; margin: 5px; padding: 2px 5px 2px 5px; float:left; }
    	.ui-state-highlight { height: 20px;width:80px;}
    	.ui-autocomplete-loading {background: white url('images/loadingsmall.gif') right center no-repeat;}
    </style>

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
	<table style="border:solid silver thin" width="100%">
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
    	

	   </div>
	 </div>
</div>
   <%@ include file="footerPublic.jsp"%>

    </body>
</html>
