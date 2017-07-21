<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/global/plugins/bootstrap/css/bootstrap-rtl.min.css" rel="stylesheet" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/global/plugins/bootstrap-switch/css/bootstrap-switch-rtl.min.css" rel="stylesheet" type="text/css" />
<!-- END GLOBAL MANDATORY STYLES -->

<link href="${g.resource(dir:'/js/metronic/theme')}/assets/pages/css/profile.min.css" rel="stylesheet" type="text/css" />

<!-- BEGIN  DATA TABLE -->
<!-- dataTable theme-->
<link href="${g.resource(dir:'/js/metronic/theme_rtl/')}assets/global/plugins/datatables/datatables.min.css" rel="stylesheet" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme_rtl/')}assets/global/plugins/datatables/plugins/bootstrap/datatables.bootstrap-rtl.css" rel="stylesheet" type="text/css" />
%{--<link href="${g.resource(dir:'/js/metronic/theme_rtl/')}assets/global/plugins/datatables/fixedColumns.dataTables.min.css" rel="stylesheet" type="text/css" />--}%

<link href="${g.resource(dir:'/js/metronic/theme')}/assets/global/plugins/typeahead/typeahead.css" rel="stylesheet" type="text/css" />


<!-- BEGIN THEME GLOBAL STYLES -->
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/global/css/components-rtl.min.css" rel="stylesheet" id="style_components" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/global/css/plugins-rtl.min.css" rel="stylesheet" type="text/css" />
<!-- END THEME GLOBAL STYLES -->
<!-- BEGIN THEME LAYOUT STYLES -->
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/layouts/layout2/css/layout-rtl.min.css" rel="stylesheet" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/layouts/layout2/css/themes/blue-rtl.min.css" rel="stylesheet" type="text/css" id="style_color" />
%{--<link href="${g.resource(dir:'/js/metronic/theme_rtl')}/assets/layouts/layout2/css/custom-rtl.css" rel="stylesheet" type="text/css" />--}%
<!-- END THEME LAYOUT STYLES -->

<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap-toastr/2.1.2/toastr.css" rel="stylesheet" type="text/css" />
<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap-star-rating/css/star-rating.min.css" rel="stylesheet" type="text/css" />

<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/bootstrap-datepicker-3/css/bootstrap-datepicker3.css" rel="stylesheet" type="text/css" />

<link href="${g.resource(dir:'/js/metronic/theme/')}assets/global/plugins/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css" />

<link href="${g.resource(dir:'/js/metronic/theme_rtl/')}assets/global/plugins//bootstrap-toggle/css/bootstrap-toggle.css" rel="stylesheet" type="text/css" />


<link rel="shortcut icon" href="favicon.ico" />


<style>
div.dataTables_wrapper {
    direction: rtl;
}

/* Ensure that the demo table scrolls */
th, td { white-space: nowrap; }
div.dataTables_wrapper {
    width: 99%;
    margin: 0 auto;
}
</style>


<g:if test="${params?.lang == 'ar'}">
    <style>
    div.dataTables_wrapper {
        direction: rtl;
    }

    /* Ensure that the demo table scrolls */
    th, td { white-space: nowrap; }
    div.dataTables_wrapper {
        width: 99%;
        margin: 0 auto;
    }
    </style>
</g:if>

