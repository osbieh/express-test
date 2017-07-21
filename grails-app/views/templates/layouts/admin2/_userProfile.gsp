<div class="top-menu">

    <ul class="nav navbar-nav pull-right">
        <!-- BEGIN USER LOGIN DROPDOWN -->

        <li class="dropdown dropdown-user dropdown-dark">

            <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown" data-close-others="true">
                %{--<img alt="" style="  margin-top: -21px;padding-top: 10px; vertical-align: top;" class="img-circle"--}%
                     %{--src="${g.resource(dir:'/js/metronic/theme/')}assets/layouts/layout3/img/avatar9.jpg">--}%

                <span style="color:#c6cfda; " class="username white username-hide-mobile"><sec:loggedInUserInfo field="username"/></span>
            </a>
            <ul class="dropdown-menu dropdown-menu-default">
                <li>
                    <a href="page_user_profile_1.html">
                        <i class="icon-user"></i> My Profile </a>
                </li>
                <li>
                    <a href="app_calendar.html">
                        <i class="icon-calendar"></i> My Calendar </a>
                </li>
                <li>
                    <g:link method="POST" controller="logout" action="index" params="[lang:'en']">
                        <i class="icon-key"></i> Log Out </a>
                    </g:link>
                </li>
            </ul>
        </li>
        <!-- END USER LOGIN DROPDOWN -->
    </ul>
</div>