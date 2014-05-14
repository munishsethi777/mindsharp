	<div id="divdeps">
	  <form  id="orgForm" name="orgForm" method="POST">	  
		<table width="500px" style="border:solid silver thin;margin-top:10px;">
	    		<tr>
				<td class="gameLabelTD">Organization Type</td>
	    			<td class="gameValueTD orgType"></td>
				<tr>
	    			<td class="gameLabelTD">Organization Name</td>				
					<td class="gameValueTD"><input type="text" id="name" name="name"/></td>
	    		</tr>
			
	    		<tr>
	    			<td class="gameLabelTD">Address</td>
	    			<td class="gameValueTD"><input type="text" name="address"/></td>
	    		</tr>
	    		<tr>
	    			<td class="gameLabelTD">State</td>
	    			<td class="gameValueTD"><input type="text" name="state"/></td>
	    		</tr>
	    		<tr>
	    			<td class="gameLabelTD">Country</td>
	    			<td class="gameValueTD"><input type="text" name="country"/></td>
	    		</tr>
				 
				<tr>
	    			<td class=""></td>
		    			<td>
							<input type="button" id="saveBtn" value="Save" class="btn">
							<input type="button" id="closeBtn" value="Close" class="btn">
						</td>
				</tr>
		</table>
		</form>
	</div>
		 <script type="text/javascript">
				$("#divdeps").dialog({
				    autoOpen: false,
				    show: 'slide',
				    resizable: false,
				    position: 'center',
				    stack: true,
				    height: 'auto',
				    width: 'auto',
				    modal: true
				});
				$('#showOrgButton').click(function () {
                  $("#divdeps").dialog('open');
                });
				$('#closeBtn').click(function () {
                  $("#divdeps").dialog('close');
                });
				$("#saveBtn").click(function(){
					dataRow = $("#orgForm").serializeArray();
					$.getJSON("User?action=addOrganization", dataRow, function(json){
						if (json['status'] == 'success'){
							$(".orgt").val(dataRow[0].value);
							$("#organ").val(dataRow[1].value);
							$(".orgName").text(dataRow[1].value);
							$(".orgAddress").text(dataRow[2].value);
							$(".orgState").text(dataRow[3].value);
							$(".orgCountry").text(dataRow[4].value);
							$("#orgSeq").val(json['seq'])
							alert("saved successfully")
							$("#divdeps").dialog('close');
						}
					});
				});
		</script>