<#import "../spring.ftl" as spring />

<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="refresh" content="1800">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Login</title>
		<link href="<@spring.url resourceUrl.getForLookupPath('/assets/common/css/font-awesome.min.css')/>" rel="stylesheet">
		<link href="<@spring.url resourceUrl.getForLookupPath('/assets/common/css/bootstrap.min.css')/>" rel="stylesheet">
	</head>
	<#escape x as x?html>
	<body class="">
		<div class="container">
			<div class="row">
				<span class="text-center">Other Function demo</span><br/>
				<span class="text-center">List</span><br/>
				<span class="text-center">Generate csv and download</span><br/>
				<span class="text-center">Generate pdf and download</span><br/>
			</div>
			<div class="row">
				<form class="" action="<@spring.url '/demo/login'/>" method="post">
					<table class="responsive no-wrap form-control" id="userList">
						<thead>
							<tr>
								<th class="table-checkbox">
									<input type="checkbox" onClick="triggerCheckBoxSelectAll(this)" class="group-checkable" data-set="#userList .checkboxes"/>
								</th>
								<th>User name</th>
								<th>Company</th>
								<th>Date of birth</th>
							</tr>
						</thead>
						<tbody>
							<#if users??>
								<#list users as u>
									<tr>
										<td>
											<input type="checkbox" class="checkboxes" name="chbox" value="<#if u.userId??>${u.userId}</#if>" />
										</td>
										<td>${u.name}</td>
										<td><#if u.company??>${u.company}</#if></td>
										<td>${u.dob}</td>
									</tr>
								</#list>
							<#else>
							no users
							</#if>
						</tbody>
					</table>			
				</form>
			</div>
			<div class="row">
				<button id="downSelectedUser" class="form-control btn btn-primary" style="cursor: pointer;">Download Selected Users PDF</button>
				<button id="downAllUser" class="form-control btn btn-primary" style="cursor: pointer;">Download All Users PDF</button>
				<a href="<@spring.url '/demo/downloadCsv'/>" name="expcsv" class="form-control btn btn-primary">Download CSV</a>
				
				<!-- hidden btns to trigger download credit note -->
				<a id="realDownSelectUser" href="#" class="btn btn-sm" style="display:none;">Download Selected User PDF</a>	
				<a id="realDownAllUser" href="#" class="btn btn-sm" style="display:none;">Download All User PDF</a>
			</div>
		</div>
		
		<script type="text/javascript" src="<@spring.url resourceUrl.getForLookupPath('/assets/common/js/jquery.min.js')/>"></script>
		<script type="text/javascript" src="<@spring.url resourceUrl.getForLookupPath('/assets/common/js/bootstrap.min.js')/>"></script>
		<script>
			var selectedUserIds = [];
			var tableName = "userList";
			
			$('input[type="checkbox"][name="chbox"]').change(function() {
				updateUserId(this);
		 	});
		 
		</script>
		<script>
			function triggerCheckBoxSelectAll(source) {
				checkboxes = document.getElementsByName('chbox');
				for(var i=0, n=checkboxes.length;i<n;i++) {
					checkboxes[i].checked = source.checked;
					updateUserId(checkboxes[i]);
				}
			}
			
			function updateUserId(element) {
				if(element.checked && element.value !== "") {
					selectedUserIds.push(element.value);
				}
				else {
					var index = selectedUserIds.indexOf(element.value);
					if (index > -1) {
						selectedUserIds.splice(index, 1);
					}
				}
			}
			
			$("#downSelectedUser").click(function(event) {
				if (selectedUserIds !== undefined && selectedUserIds.length > 0) {
					userIdsJson = JSON.stringify(jQuery.unique(selectedUserIds));
					url = '<@spring.url "/demo/downloadUser"/>' + "?userIds=" + userIdsJson;
					$("#realDownSelectUser").attr("href", url);
					$("#realDownSelectUser")[0].click();
				}
			});
			
			$("#downAllUser").click(function(event) {
				url = '<@spring.url "/demo/downloadAllUser"/>';
				$("#realDownAllUser").attr("href", url);
				$("#realDownAllUser")[0].click();
			});
		
		</script>
	</body>
	</#escape>
</html>