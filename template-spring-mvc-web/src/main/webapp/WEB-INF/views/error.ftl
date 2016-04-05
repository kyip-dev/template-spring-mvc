<#import "spring.ftl" as spring />

<#escape x as x?html>
<div class="container">
	<div class="row">
		<div class="col-md-10 col-sm-12 col-md-offset-1 col-sm-offset-0 ">
			<div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
			</div>
			<div class="col-md-6 col-sm-6 col-md-offset-3 col-sm-offset-3">
				<div class="text-center">
				<p>
					<#if message??>
						<h5>
							<strong><#noescape>${message}</#noescape></strong>
						</h5>
					<#else>
						<h5>
							<strong> Error!</strong>
						</h5>
						Please come back later.
					</#if>
				</p>
			</div>
		</div>
	</div>
</div>

</#escape>