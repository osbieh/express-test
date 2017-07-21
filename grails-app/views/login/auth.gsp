<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	%{--<link rel="shortcut icon" href="${g.resource(dir:'/')}assets/icons/palSafeLogo.png" type="image/x-icon" />--}%
	<link rel="shortcut icon" href="/express/assets/icons/express.png"/>

	<title>Express| User Login</title>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<!-- BEGIN GLOBAL MANDATORY STYLES -->
	<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />

	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css" />
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap-switch/css/bootstrap-switch.css" rel="stylesheet" type="text/css" />
	<!-- END GLOBAL MANDATORY STYLES -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL STYLES -->
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/css/plugins.min.css" rel="stylesheet" type="text/css" />
	<!-- END THEME GLOBAL STYLES -->
	<!-- BEGIN PAGE LEVEL STYLES -->
	<link href="${g.resource(dir:'/js/metronic/theme/')}assets/pages/css/login-4.min.css" rel="stylesheet" type="text/css" />
	<!-- END PAGE LEVEL STYLES -->
	<!-- BEGIN THEME LAYOUT STYLES -->
	<!-- END THEME LAYOUT STYLES -->
<style>
	.login_message {
	padding: 6px 25px 20px 25px;
	color: #c33;
	}

	</style>

	%{--<link rel="shortcut icon" href="ar/favicon.ico" /> --}%
<!-- END HEAD -->
</head>
<body class="login">
<!-- BEGIN LOGO -->
<br>
	<div class="row">
	<div class="col-md-1">
	<div class="col-md-4">
	%{--<input type="checkbox" name="somebox" id="bootstrapswitch" data-size="mini" data-handle-width="55" data-label-width="30" data-on-text="عربي" data-off-text="English" checked>--}%
	</div>
	</div>
	<div class="logo">
	<a href="http://express.ps" target="_blank">
		%{--<img src="${g.resource(dir:'/js/metronic/theme/')}assets/pages/img/logo-big.png" alt="" />--}%

		<asset:image src="/icons/express.png" alt="logo" class="logo-default" style="width:15%;"/>
	</a>
</div>
<!-- END LOGO -->
<!-- BEGIN LOGIN -->
<div class="content" dir="rtl">



	<!-- BEGIN LOGIN FORM -->
	<g:if test='${flash.message}'>
		<div class="login_message">${flash.message}</div>
	</g:if>

	<form action="${postUrl?:'http://localhost:8001/express/ar/login/authenticate'}" method="POST" id="loginForm"  autocomplete="off">
		<h3 class="form-title"> <g:message code="login.form.header" default="Login to your account"/> </h3>
		<div class="alert alert-danger display-hide">
			<button class="close" data-close="alert"></button>
			<span> <g:message code="login.form.checkMSG" default="Enter any username and password."/> </span>
		</div>
		<div class="form-group">
			<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
			<label class="control-label  visible-ie8 visible-ie9"><g:message code="login.form.userName" default="UserName"/></label>
			<div class="input-icon">
				<i class="fa fa-user"></i>
				<input class="form-control placeholder-no-fix" type="text" autocomplete="off" placeholder="<g:message code="login.form.userName" default="UserName"/>" name="username" /> </div>
		</div>
		<div class="form-group">
			<label class="control-label visible-ie8 visible-ie9"><g:message code="login.form.password" default="password"/></label>
			<div class="input-icon">
				<i class="fa fa-lock"></i>
				<input  class="form-control placeholder-no-fix" type="password" autocomplete="off" placeholder="<g:message code="login.form.password" default="password"/>" name="password" /> </div>
		</div>
		<div style="padding-bottom:0px;" class="form-actions">
			<label class="rememberme mt-checkbox mt-checkbox-outline">
				<input style="margin-right: 5px;" type="checkbox" class="chk" name="${rememberMeParameter ?: 'remember-me'}" id="remember_me" <g:if test='${hasCookie}'>checked="checked"</g:if>/> <g:message code="login.form.rememberMe" default="Remember me"/> </label>
			<button  type="submit" class="btn green pull-right"> <g:message code="login.form.submit" default="Login"/> </button>
		</div>


		<div style="margin-top: 0;" class="forget-password">
			<h4><g:message code="login.form.forgetPassword" default="Forgot your password ?"/></h4>
			<p> <g:message code="login.form.reset1" default="no worries, click"/>
				<a href="javascript:;" id="forget-password"> <g:message code="login.form.reset2" default="here"/> </a> <g:message code="login.form.reset3" default="to reset your password."/> </p>
		</div>
	</form>
		</div>
		<g:set var="today" value="${new Date()}"/>
		%{--<link rel="shortcut icon" href="${g.resource(dir:'/')}assets/icons/fav.png" type="image/x-icon" />--}%
		<div class="copyright"> Express Technologies &copy; ${today[Calendar.YEAR]}</div>
	<!-- END LOGIN FORM -->
	<!-- BEGIN CORE PLUGINS -->
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/jquery.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
	%{--<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/uniform/jquery.uniform.min.js" type="text/javascript"></script>--}%
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap-switch/js/bootstrap-switch.js" type="text/javascript"></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/jquery-validation/js/additional-methods.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN THEME GLOBAL SCRIPTS -->
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/global/scripts/app.min.js" type="text/javascript"></script>
	<!-- END THEME GLOBAL SCRIPTS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${g.resource(dir:'/js/metronic/theme/')}assets/pages/scripts/login-4.js" type="text/javascript"></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<!-- BEGIN THEME LAYOUT SCRIPTS -->
	<!-- END THEME LAYOUT SCRIPTS -->
<script>
	$(document).ready(function() {

		$(document).on("keyup", 'input', function(e){
			if(e.keyCode == 13){
				$("form").submit();
			}

		});
	});

	(function() {

		$("#bootstrapswitch").bootstrapSwitch('state','${params?.lang}'=='en'?true:false);
         if('${params?.lang}'=='en'){
			 $(".content").attr("dir", "ltr");
		 }else{
			 $(".content").attr("dir", "rtl");
		 }


		$("#bootstrapswitch").bootstrapSwitch().on("switchChange.bootstrapSwitch", function (event, state) {

			if(state){
				document.location ="http://localhost:8001/palsafe/en/login/auth";
					$(".content").attr("dir", "ltr");
					$("#bootstrapswitch").bootstrapSwitch('state', true);
			}else{
				document.location ="http://localhost:8001/express/ar/login/auth";
					$(".content").attr("dir", "rtl");
					$("#bootstrapswitch").bootstrapSwitch('state', false);
			}
	});

		document.forms['loginForm'].elements['${usernameParameter ?: 'username'}'].focus();
	})();
</script>

</body>

</html>