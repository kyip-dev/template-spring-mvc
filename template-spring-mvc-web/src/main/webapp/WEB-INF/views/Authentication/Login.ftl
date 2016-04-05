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
	<body class="login_wrapper">
		<div class="container">
			<div class="row">
				<div class="text-center">
					<p>Fake login, just use 123/123 to login</p>
				</div>

				<form class="" action="<@spring.url '/demo/login'/>" method="post">
					<div class="form-group">
						<input class="form-control " maxlength="255" required type="text" value="" placeholder="Email address" name="username">
					</div>
					<div class="form-group">
						<input class="form-control" maxlength="255" required type="password" placeholder="Password" name="password">
					</div>
					<div class="form-actions">
						<div class="row">
							<div class="col-md-7 col-sm-7 col-xs-7">
							</div>
							<div class="col-md-5 col-sm-5 col-xs-5 text-right"> 
								<input type="submit" class="form-control btn btn btn-primary" value="Submit">
							</div>
						</div>
						<div class="row">
							<@spring.message "title.login"/>
							<a href="javascript:void(0);" onclick="changeLocale('zh_TW')">zh_TW</a>
							<a href="javascript:void(0);" onclick="changeLocale('en_US')">en_US</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	<script src="<@spring.url resourceUrl.getForLookupPath('/assets/common/js/jquery.min.js')/>"></script>
	<script>
		function changeLocale(locale) {
			$.ajax({
				type: "get",
				url: "<@spring.url "/ChangeLocale/"/>",
				data: "locale="+locale,
				dataType:"json",
				error: function(data, error) {alert("change lang error!");},
				success: function(data) {
					window.location.reload();
				}
			});
		}
	</script>
	</body>
	</#escape>
</html>