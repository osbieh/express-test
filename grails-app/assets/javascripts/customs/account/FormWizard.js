/**
 * Created by osbie on 2/28/2017.
 */

var FormWizard = function () {
    return {
        addFormActions:function(){

            var last=


            $('#form_wizard_1').find('.button-previous').click(function(){

                $('.nav-pills .active').prev('li').find('a').trigger('click');
                !$('.nav-pills  li').last().hasClass("active")? $('#form_wizard_1').find('.button-submit').hide(): $('#form_wizard_1').find('.button-submit').show();
                 $('.nav-pills  li').last().hasClass("active")? $('#form_wizard_1').find('.button-next').hide(): $('#form_wizard_1').find('.button-next').show();
                $('.nav-pills  li').first().hasClass("active")? $('#form_wizard_1').find('.button-previous').hide(): $('#form_wizard_1').find('.button-previous').show();
            });

            $('#form_wizard_1').find('.button-next').click(function(){
                $('.nav-pills .active').next('li').find('a').trigger('click');

                !$('.nav-pills  li').last().hasClass("active")? $('#form_wizard_1').find('.button-submit').hide(): $('#form_wizard_1').find('.button-submit').show();
                $('.nav-pills  li').last().hasClass("active")? $('#form_wizard_1').find('.button-next').hide(): $('#form_wizard_1').find('.button-next').show();
                $('.nav-pills  li').first().hasClass("active")? $('#form_wizard_1').find('.button-previous').hide(): $('#form_wizard_1').find('.button-previous').show();
            });

            $('#form_wizard_1 li').click(function(){
                $('.nav-pills  li').last().hasClass("active")? $('#form_wizard_1').find('.button-submit').hide(): $('#form_wizard_1').find('.button-submit').show();
                //!$('.nav-pills  li').last().hasClass("active")? $('#form_wizard_1').find('.button-next').hide(): $('#form_wizard_1').find('.button-next').show();
                //!$('.nav-pills  li').first().hasClass("active")? $('#form_wizard_1').find('.button-previous').hide(): $('#form_wizard_1').find('.button-previous').show();

            });



            $('#form_wizard_1').find('.button-submit').click(function(){

                accountDetails.manageAccount();

            });


        },
        init: function () {
            $('#form_wizard_1 ul>li').first().addClass("active");
            $('#form_wizard_1').find('.button-submit').hide();
            $('#form_wizard_1').find('.button-previous').hide();
            FormWizard.addFormActions();
        }


    };

}();


