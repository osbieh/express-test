<!-- BEGIN SIDEBAR -->
<div class="page-sidebar-wrapper">
    <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
    <!-- DOC: Change data-auto-speed="200" to adjust the sub menu slide up/down speed -->
    <div class="page-sidebar navbar-collapse collapse">
        <!-- BEGIN SIDEBAR MENU -->
        <!-- DOC: Apply "page-sidebar-menu-light" class right after "page-sidebar-menu" to enable light sidebar menu style(without borders) -->
        <!-- DOC: Apply "page-sidebar-menu-hover-submenu" class right after "page-sidebar-menu" to enable hoverable(hover vs accordion) sub menu mode -->
        <!-- DOC: Apply "page-sidebar-menu-closed" class right after "page-sidebar-menu" to collapse("page-sidebar-closed" class must be applied to the body element) the sidebar sub menu mode -->
        <!-- DOC: Set data-auto-scroll="false" to disable the sidebar from auto scrolling/focusing -->
        <!-- DOC: Set data-keep-expand="true" to keep the submenues expanded -->
        <!-- DOC: Set data-auto-speed="200" to adjust the sub menu slide up/down speed -->
        <ul class="page-sidebar-menu page-sidebar-menu-hover-submenu " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
            <li class="start active ">
                <a href="/express/ar/main">
                    <i class="icon-home"></i>
                    <span class="title"><g:message code="express.sidebar.main"/></span>
                    <span class="selected"></span>
                </a>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-users"></i>
                    <span class="title"><g:message code="express.sidebar.contacts"/></span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <g:link  params="[lang: params.lang]" controller="contact" action="list">
                            <i class="icon-home"></i>
                            <g:message code="express.sidebar.contacts.contactList"/>
                        </g:link>
                    </li>

                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-rocket"></i>
                    <span class="title"><g:message code="express.sidebar.accounts"/></span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <g:link params="[lang: params.lang]" controller="account" action="list">
                            <i class="icon-home"></i>
                            Accounts List
                        </g:link>
                    </li>
                    <li>
                        <g:link params="[lang: params.lang]" controller="account" action="balance">
                            <i class="icon-user"></i>
                            Account Balance
                        </g:link>
                    </li>

                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-diamond"></i>
                    <span class="title">   <g:message code="express.sidebar.dailyMovement"/></span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <g:link params="[lang: params.lang]" controller="dailyMovement" action="list">
                            <i class="icon-home"></i>
                           <g:message code="express.sidebar.dailyMovement.list"/>
                        </g:link>
                    </li>
                    <li>

                        <g:link params="[lang: params.lang]" controller="dailyMovement" action="dailyMovementByCustomer">
                            <i style="-webkit-transform: rotate(90deg)" class="fa fa-exchange"></i>
                                <g:message code="express.sidebar.dailyMovement.byCustomer"/>
                        </g:link>
                    </li>



                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-puzzle"></i>
                    <span class="title"><g:message code="express.sidebar.orders"/></span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <g:link params="[lang: params.lang]" controller="OrderP" action="list" >
                            List
                        </g:link>
                    </li>


                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-wallet"></i>
                    <span class="title"><g:message code="express.sidebar.payments"/></span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <g:link params="[lang: params.lang]" controller="payment" action="list" >
                            List
                        </g:link>
                    </li>

                </ul>
            </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-briefcase"></i>
                    <span class="title"><g:message code="express.sidebar.checks"/></span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <g:link params="[lang: params.lang]" controller="cheque" action="list" >
                            List
                        </g:link>
                    </li>
                    <li>
                        <g:link params="[lang: params.lang]" controller="cheque" action="chequesCalendar" >
                            <i class="glyphicon glyphicon-calendar"></i>

                            Cheques Calendar
                        </g:link>
                    </li>

                </ul>
            </li>
    <li>
        <a href="javascript:;">
            <i class="icon-social-dribbble"></i>
            <span class="title"><g:message code="express.sidebar.product"/></span>
            <span class="arrow "></span>
        </a>
        <ul class="sub-menu">
            <li>
                <g:link params="[lang: params.lang]" controller="product" action="list" >
                    List
                </g:link>
            </li>

        </ul>
    </li>
            <li>
                <a href="javascript:;">
                    <i class="icon-layers"></i>
                    <span class="title"><g:message code="express.sidebar.inventory"/></span>
                    <span class="arrow "></span>
                </a>
                <ul class="sub-menu">
                    <li>
                        <g:link params="[lang: params.lang]" controller="product" action="list" >
                            List
                        </g:link>
                    </li>

                </ul>
            </li>
    <li>
        <a href="javascript:;">
            <i class="icon-settings"></i>
            <span class="title"><g:message code="express.sidebar.settings"/></span>
            <span class="arrow "></span>
        </a>
        <ul class="sub-menu">
            <li>
                <g:link controller="user" params="[lang: params.lang]" >
                    Users
                </g:link>
            </li>
            <li>
                <g:link params="[lang: params.lang]" >
                    Definitions
                </g:link>
            </li>
            <li>
                <g:link params="[lang: params.lang]" >
                    BackUps
                </g:link>
            </li>

        </ul>
    </li>
        </ul>
        <!-- END SIDEBAR MENU -->
    </div>
</div>
<!-- END SIDEBAR -->