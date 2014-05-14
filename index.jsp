<!DOCTYPE html>
<html lang="en">
  <head>
  	<%@ include file="includeJars.jsp" %>
  	<%@ include file="includeJS.jsp" %>
  	
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <title>MindSharp - Mind Games...</title>
  </head>

  <body>

    <!-- Fixed navbar -->
	<%@ include file="headerPublic.jsp" %>

	<div id="headerwrap">
		<div class="container">
			<div class="row centered">
				<div class="col-lg-8 col-lg-offset-2">
				<img src="assets/img/logoBig.png" width="60%" height="60%"/>
				</div>
			</div><!-- row -->
		</div><!-- container -->
	</div><!-- headerwrap -->


	<div class="container w">
		<div class="row centered">
			<br><br>
			<div class="col-lg-4">
				<i class="fa fa-heart"></i>
				<h4>TRAIN MEMORY & ATTENTION</h4>
				<p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even believable.</p>
			</div><!-- col-lg-4 -->

			<div class="col-lg-4">
				<i class="fa fa-laptop"></i>
				<h4>WEB BASED TRAINING</h4>
				<p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even believable.</p>
			</div><!-- col-lg-4 -->

			<div class="col-lg-4">
				<i class="fa fa-trophy"></i>
				<h4>TRACK YOUR PROGRESS</h4>
				<p>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even believable.</p>
			</div><!-- col-lg-4 -->
		</div><!-- row -->
		<br>
		<br>
	</div><!-- container -->


	<!-- PORTFOLIO SECTION -->
	<div id="dg">
		<div class="container">
			<div class="row centered">
				<h4>LATEST WORKS</h4>
				<br>
				<div class="col-lg-4">
					<div class="tilt">
					<a href="#"><img src="assets/img/p01.png" alt=""></a>
					</div>
				</div>

				<div class="col-lg-4">
					<div class="tilt">
					<a href="#"><img src="assets/img/p03.png" alt=""></a>
					</div>
				</div>

				<div class="col-lg-4">
					<div class="tilt">
					<a href="#"><img src="assets/img/p02.png" alt=""></a>
					</div>
				</div>
			</div><!-- row -->
		</div><!-- container -->
	</div><!-- DG -->


	<!-- FEATURE SECTION -->
	<div class="container wb">
		<div class="row centered">
			<br><br>
			<div class="col-lg-8 col-lg-offset-2">
				<h4>WE CREATE FIRST CLASS DESIGN</h4>
				<p>By being true to the brand we represent, we elevate the audiences’ relationship to it. Like becomes love becomes a passion. Passion becomes advocacy. And we see the brand blossom from within, creating a whole story the audience embraces. That’s when the brand can truly flex its muscles.</p>
			<p><br/><br/></p>
			</div>
			<div class="col-lg-2"></div>
			<div class="col-lg-10 col-lg-offset-1">
				<img class="img-responsive" src="assets/img/munter.png" alt="">
			</div>
		</div><!-- row -->
	</div><!-- container -->


	<div id="lg">
		<div class="container">
			<div class="row centered">
				<h4>OUR AWESOME CLIENTS</h4>
				<div class="col-lg-2 col-lg-offset-1">
					<img src="assets/img/c01.gif" alt="">
				</div>
				<div class="col-lg-2">
					<img src="assets/img/c02.gif" alt="">
				</div>
				<div class="col-lg-2">
					<img src="assets/img/c03.gif" alt="">
				</div>
				<div class="col-lg-2">
					<img src="assets/img/c04.gif" alt="">
				</div>
				<div class="col-lg-2">
					<img src="assets/img/c05.gif" alt="">
				</div>
			</div><!-- row -->
		</div><!-- container -->
	</div><!-- dg -->
	
	
	<div id="r">
		<div class="container">
			<div class="row centered">
				<div>
					<h4>MIND SHARP HUMAN CONNITION PROJECT IS A COLLABORATIVE EFFORT TO ACCELERATE THE PACE OF CONGNITIVE RESEARCH</h4>
					<p>The Human Cognition Project (HCP) is a collaboration between Mind-Sharp’s in-house science team and various academic scientists, clinicians, and educators interested in understanding and exploring human cognitive abilities.<br>

HCP researchers receive free access to Mind-Sharp’s tools and, in certain cases, limited access to select portions of Mind-Sharp’s database of cognitive game performance. Currently, there are 43 ongoing HCP studies exploring topics such as age-related cognitive decline, interventions for PTSD, and the relationship between physical exercise and Mind-Sharp training.</p>
				</div>
			</div><!-- row -->
		</div><!-- container -->
	</div><! -- r wrap -->
	
	
<%@ include file="footerPublic.jsp" %>


	<!-- MODAL FOR CONTACT -->
	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title" id="myModalLabel">contact us</h4>
	      </div>
	      <div class="modal-body">
		        <div class="row centered">
		        	<p>We are available 24/7, so don't hesitate to contact us.</p>
		        	<p>
		        		Somestreet Ave, 987<br/>
						London, UK.<br/>
						+44 8948-4343<br/>
						hi@blacktie.co
		        	</p>
		        	<div id="mapwrap">
		<iframe height="300" width="100%" frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://www.google.es/maps?t=m&amp;ie=UTF8&amp;ll=52.752693,22.791016&amp;spn=67.34552,156.972656&amp;z=2&amp;output=embed"></iframe>
					</div>	
		        </div>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-danger" data-dismiss="modal">Save & Go</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
  </body>
</html>
