<!DOCTYPE html>
<!--
Template Name: Metronic - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.3.6
Version: 4.5.6
Author: KeenThemes
Website: http://www.keenthemes.com/
Contact: support@keenthemes.com
Follow: www.twitter.com/keenthemes
Dribbble: www.dribbble.com/keenthemes
Like: www.facebook.com/keenthemes
Purchase: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
Renew Support: http://themeforest.net/item/metronic-responsive-admin-dashboard-template/4021469?ref=keenthemes
License: You must have a valid license purchased only from themeforest(the above link) in order to legally use the theme for your project.
-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->


<g:if test="${params?.lang == 'ar'}">
    <html lang="ar" dir="rtl">
</g:if>
<g:if test="${params?.lang == 'en'}">
    <html lang="en" dir="ltr">
</g:if>
<!--<![endif]-->
<!-- BEGIN HEAD -->

<head>
    <meta charset="utf-8"/>
    <link rel="shortcut icon" href="/palsafe/assets/icons/express.png"/>
    <title>Express | Main</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <meta content="" name="description"/>
    <meta content="" name="author"/>
    <!-- BEGIN GLOBAL MANDATORY STYLES -->
    <asset:javascript src="application.js"/>
    <g:if test="${params?.lang == 'ar'}">
        <g:applyLayout template="/templates/layouts/admin2/rtl/style"/>
    </g:if>

    <g:if test="${params?.lang == 'en'}">
        <g:applyLayout template="/templates/layouts/admin2/ltr/style"/>
    </g:if>
    <g:layoutHead/>
    <!-- END THEME LAYOUT STYLES -->

</head>
<!-- END HEAD -->

<body class="page-header-fixed page-sidebar-closed-hide-logo page-content-white">
<!-- BEGIN HEADER -->
<g:applyLayout template="/templates/layouts/admin2/header"/>
<!-- END HEADER -->
<!-- BEGIN CONTAINER -->
<div class="page-container">
    <!-- BEGIN SIDEBAR -->
    <g:applyLayout template="/templates/layouts/admin2/sidebar"/>
    <!-- END SIDEBAR -->
    <!-- BEGIN CONTENT -->
    <div class="page-content-wrapper">
        <!-- BEGIN CONTENT BODY -->
        <div class="page-content">
            <g:layoutBody/>
        </div>
        <!-- END CONTENT BODY -->
    </div>
</div>
<!-- END CONTAINER -->


<!-- BEGIN INNER FOOTER -->
<g:applyLayout template="/templates/layouts/admin2/footer"/>
<!-- END FOOTER -->

<g:if test="${params?.lang == 'ar'}">
    <g:applyLayout template="/templates/layouts/admin2/rtl/lib"/>
</g:if>

<g:if test="${params?.lang == 'en'}">
    <g:applyLayout template="/templates/layouts/admin2/ltr/lib"/>
</g:if>

<script>

    var common = {
        langPath: "/express/assets/datatableLanguages/",
        currentLang: '${params?.lang}',
        projectPath: '${grailsApplication.mainContext.applicationName +"/"+params?.lang}',
    };

    function defaultLayoutSetting() {

        $('.sidebar-toggler').click(); //ToDO manage it with cookies to keep status of sidebar Open or Close

        toastr.options = {
            "closeButton": true,
            "debug": false,
            "positionClass": "toast-bottom-right",
            "preventDuplicates": false,
            "onclick": null,
            "showDuration": "300",
            "hideDuration": "1000",
            "timeOut": "3000",
            "extendedTimeOut": "1000",
            "showEasing": "swing",
            "hideEasing": "linear",
            "showMethod": "fadeIn",
            "hideMethod": "fadeOut"
        }


    }
    ;
    function selectedSideBar(page) {

        var sidebar = $(".page-sidebar-menu");
        var children = $(sidebar).children();
        $(children[0]).removeClass("active");
        $(children[page]).addClass("active");

        $($(children[page]).children()[0]).prepend('<span class="selected"></span>');
    }
    ;
    $(document).ready(function () {
        defaultLayoutSetting();
        var path = '${request.requestURI}'.split("/");
        selectedSideBar(projectMapping.Pages[path[3]]);

    });


</script>

</body>

</html>