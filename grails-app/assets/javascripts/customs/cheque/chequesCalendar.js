/**
 * Created by osbie on 6/10/2017.
 */
var chequesCalendar = function () {

    return {

        addChequesCalendar:function(){
            var calendar =  $('#calendar').fullCalendar({
                height: 600,
                weekNumbers:true,
                weekNumbersWithinDays:true,
                firstDay:6,
                isRTL:true,

               // contentHeight: 300,
                header: {
                    left: 'title',
                    center: 'agendaDay,agendaWeek,month',
                    right: 'prev,next today'
                },
                themeButtonIcons:  {
                prev: 'circle-triangle-w',
                    next: 'circle-triangle-e',
                prevYear: 'seek-prev',
                nextYear: 'seek-next'
            },

            });
        },

        init:function(){
            chequesCalendar.addChequesCalendar();
        }
    }
}();
