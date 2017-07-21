<%--
  Created by IntelliJ IDEA.
  User: osbie
  Date: 6/10/2017
  Time: 7:33 PM
--%>
<meta name="layout" content="main"/>

<style>

.profile-sidebar ul.profile-nav li {
    color: #557386;
    display: block;
    font-size: 14px;
    padding: 8px 10px;
    margin-bottom: 1px;
    background: #f0f6fa;
    border-left: solid 2px #c4d5df;
    font-weight: bold;
}

.profile-sidebar label {
    font-weight: 100;
}

.fc-fri { color:blue;
         background-color:#FFEFD5 !important;
        }
.fc-today{
    background-color:#FFD700 !important;
}
.fc-past{background-color:	#fafafa;}

</style>


<div class="col-md-12" style="background-color: transparent;position: relative;">
    <div class="profile-sidebar">
       <div class="col-md-12">
           <ul class="list-unstyled profile-nav">

               <li>
                   <a href="javascript:;"> Projects </a>
               </li>
               <li>
                   <a href="javascript:;"> Messages
                       <span> 3 </span>
                   </a>
               </li>
               <li>
                   <a href="javascript:;"> Friends </a>
               </li>
               <li>
                   <a href="javascript:;"> Settings </a>
               </li>
               <li>
                   <a href="javascript:;"> Projects </a>
               </li>
               <li>
                   <a href="javascript:;"> Messages
                       <span> 3 </span>
                   </a>
               </li>
               <li>
                   <a href="javascript:;"> Friends </a>
               </li>
               <li>
                   <a href="javascript:;"> Settings </a>
               </li>
               <li>
                   <a href="javascript:;"> Projects </a>
               </li>
               <li>
                   <a href="javascript:;"> Messages
                       <span> 3 </span>
                   </a>
               </li>
               <li>
                   <a href="javascript:;"> Friends </a>
               </li>
               <li>
                   <a href="javascript:;"> Settings </a>
               </li>
           </ul>

        </div>
    </div>
     <div class="profile-content">
         <div class="row">

                 <div class="col-xs-12" id="calendar"></div>

          </div>
     </div>

</div>



%{--<div style="margin-top:-15px;" class="portlet box blue-madison">--}%

%{--<div class="portlet-title">--}%
%{--<div class="caption">--}%
%{--<span>--}%

%{--<i class="fa fa-book"></i> Cheques List</span>--}%
%{--</div>--}%

%{--<div class="tools">--}%
%{--<a href="javascript:;" class="collapse"></a>--}%
%{--</div>--}%
%{--</div>--}%

%{--<div class="portlet-body">--}%


%{--<div id='calendar'></div>--}%

%{--</div>--}%
%{--</div>--}%

<div id="fullCalModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span> <span
                        class="sr-only">close</span></button>
                <h4 id="modalTitle" class="modal-title"></h4>
            </div>

            <div id="modalBody" class="modal-body">

            </div>

        </div>
    </div>
</div>

<script>

    $(document).ready(function () {
        chequesCalendar.init();

    });



</script>