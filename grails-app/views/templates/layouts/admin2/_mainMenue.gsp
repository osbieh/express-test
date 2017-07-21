<g:if test="${params?.lang == 'ar'}">
    <div style="direction:rtl" class="hor-menu">
</g:if>
<g:else>
    %{--<div class="hor-menu">--}%
</g:else>

%{--<ul class="nav navbar-nav">--}%

    %{--<li class="menu-dropdown classic-menu-dropdown ">--}%
        %{--<a href="/palsafe/${params.lang}/main">--}%
            %{--<i class="icon-home">--}%
            %{--</i>--}%

            %{--<span class="title"><g:message code="employee.menu.home"/></span>--}%
            %{--<span class="selected"></span>--}%
        %{--</a>--}%
    %{--</li>--}%

%{--</ul>--}%

%{--<ul class="nav navbar-nav">--}%
    %{--<li class="menu-dropdown classic-menu-dropdown ">--}%
        %{--<a href="javascript:;"><g:message code="employee.menu.employees" default="Employees"/>--}%
            %{--<span class="arrow"></span>--}%
        %{--</a>--}%
        %{--<ul class="dropdown-menu pull-left">--}%
            %{--<li>--}%
                %{--<g:link class="nav-link"--}%
                        %{--params="[lang: params.lang]" controller="employee" action="listOfEmployees">--}%
                    %{--<i class="icon-home"></i>--}%
                    %{--<g:message code="employee.menu.employees.employeesList" default="Employees List"/>--}%
                %{--</g:link>--}%
            %{--<li class=" ">--}%
            %{--<a href="dashboard_2.html" class="nav-link  ">--}%
            %{--<i class="icon-bulb"></i> Contracts </a>--}%
            %{--</li>--}%
            %{--<li>--}%
                %{--<g:link class="nav-link"--}%
                        %{--params="[lang: params.lang]" controller="employee" action="dailyAttendance">--}%
                    %{--<i class="icon-home"></i>--}%
                    %{--<g:message code="employee.dailyAttendance.attendance" default="Employees Attendance"/>--}%
                %{--</g:link>--}%
            %{--</li>--}%
        %{--</ul>--}%
    %{--</li>--}%
%{--</ul>--}%

%{--<ul class="nav navbar-nav">--}%
    %{--<li class="menu-dropdown classic-menu-dropdown ">--}%
        %{--<a href="javascript:;"><g:message code="client.menu.clients" default="Clients"/>--}%
            %{--<span class="arrow"></span>--}%
        %{--</a>--}%
        %{--<ul class="dropdown-menu pull-left">--}%
            %{--<li>--}%
                %{--<g:link class="nav-link" params="[lang: params.lang]" controller="client" action="listOfClients">--}%
                    %{--<i class="icon-home"></i>--}%
                    %{--<g:message code="client.menu.clients.clientsList" default="Employees List"/>--}%
                %{--</g:link>--}%
            %{--<li class=" ">--}%
                %{--<g:link class="nav-link" params="[lang: params.lang]" controller="clientContract" action="listOfContracts">--}%
                    %{--<i class="icon-bulb"></i>--}%
                    %{--<g:message code="menu.client.contractsList" default="Contracts List"/>--}%
                %{--</g:link>--}%
            %{--</li>--}%
            %{--<li>--}%
            %{--<g:link class="nav-link" params="[lang: params.lang]" controller="client" action="locationSchedule">--}%
                %{--<i class="icon-bulb"></i>Location-Schedule</a>--}%
            %{--</g:link>--}%
            %{--</li>--}%
            %{--<li class=" ">--}%
                %{--<a href="dashboard_3.html" class="nav-link  ">--}%
                    %{--<i class="icon-graph"></i> Schedule--}%

                %{--</a>--}%
            %{--</li>--}%
        %{--</ul>--}%
    %{--</li>--}%
%{--</ul>--}%

%{--<ul class="nav navbar-nav">--}%
    %{--<li class="menu-dropdown classic-menu-dropdown ">--}%
        %{--<a href="javascript:;"><g:message code="employee.menu.settings" default="Users"/>--}%
            %{--<span class="arrow"></span>--}%
        %{--</a>--}%
        %{--<ul class="dropdown-menu pull-left">--}%
            %{--<li>--}%
                %{--<g:link class="nav-link" params="[lang: params.lang]" controller="user" action="listUsers">--}%
                    %{--<i class="icon-home"></i>--}%
                    %{--UsersList--}%
                %{--</g:link>--}%
            %{--</li>--}%
        %{--</li>--}%
            %{--<li class=" ">--}%
                %{--<a href="dashboard_2.html" class="nav-link  ">--}%
                    %{--<i class="icon-bulb"></i> Contracts</a>--}%
            %{--</li>--}%
            %{--<li class=" ">--}%
                %{--<a href="dashboard_3.html" class="nav-link  ">--}%
                    %{--<i class="icon-graph"></i> Schedule--}%
                %{--</a>--}%
            %{--</li>--}%
        %{--</ul>--}%
    %{--</li>--}%
%{--</ul>--}%

%{--</div>--}%

