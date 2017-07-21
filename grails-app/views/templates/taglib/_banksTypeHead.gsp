<style>
body {
    padding: 40px;
}
.twitter-typeahead .tt-query, .twitter-typeahead .tt-hint {
    margin-bottom: 0;
}
.twitter-typeahead .tt-hint {
    display: block;
    background-color:white;
    height: 34px;
    padding: 6px 12px;
    font-size: 14px;
    line-height: 1.428571429;
    border: 1px solid transparent;
    border-radius:4px;
}
.twitter-typeahead .hint-small {
    height: 30px;
    padding: 5px 10px;
    font-size: 12px;
    border-radius: 3px;
    line-height: 1.5;
}
.twitter-typeahead .hint-large {
    height: 45px;
    padding: 10px 16px;
    font-size: 18px;
    border-radius: 6px;
    line-height: 1.33;
}
.tt-menu {
    min-width: 160px;
    margin-top: 2px;
    padding: 5px 0;
    background-color: #fff;
    border: 1px solid #ccc;
    border: 1px solid rgba(0, 0, 0, .2);
    *border-right-width: 2px;
    *border-bottom-width: 2px;
    -webkit-border-radius: 6px;
    -moz-border-radius: 6px;
    border-radius: 6px;
    -webkit-box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
    -moz-box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
    box-shadow: 0 5px 10px rgba(0, 0, 0, .2);
    -webkit-background-clip: padding-box;
    -moz-background-clip: padding;
    background-clip: padding-box;
}
.tt-suggestion {
    display: block;
    padding: 3px 20px;
}
.tt-suggestion.tt-cursor {
    color: #fff;
    background-color: #0081c2;
    background-image: -moz-linear-gradient(top, #0088cc, #0077b3);
    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#0088cc), to(#0077b3));
    background-image: -webkit-linear-gradient(top, #0088cc, #0077b3);
    background-image: -o-linear-gradient(top, #0088cc, #0077b3);
    background-image: linear-gradient(to bottom, #0088cc, #0077b3);
    background-repeat: repeat-x;
    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff0088cc', endColorstr='#ff0077b3', GradientType=0)
}
.tt-suggestion.tt-is-under-cursor a {
    color: #fff;
}
.tt-suggestion p {
    margin: 0;
}
</style>

<input style="direction:${direction} "  id="${id}" name="${name}" type="text" data-provide="typeahead" class="typeahead form-control"> <input id="${hidden}" name="${hidden}"  type="hidden">




<script>



    $(document).ready(function () {
        $(".typeahead.dropdown-menu").css("right","1px");
        function addTypeHead() {
            $('#'+'${id}').typeahead({
                property: 'name',
                items: 10,
                // minLength: 2,
                source: function (query, process) {
                    var $url ='${url}';
                    var $items = new Array;
                    $items = [""];
                    $.ajax({
                        url: $url,
                        data: {regionalArea:"ALL",query: query},
                        dataType: "json",
                        type: "POST",
                        success: function (data) {
                            data = data.aaData;
                            $.map(data, function (data) {
                                var group;
                                group = {
                                    name: data.bankName,
                                    bankId: data.bankId,
                                    toString: function () {


                                        return JSON.stringify(this);

                                    },
                                    toLowerCase: function () {
                                        return this.name.toLowerCase();
                                    },
                                    indexOf: function (string) {

                                        return String.prototype.indexOf.apply(this.name, arguments);
                                    },
                                    replace: function (string) {
                                        var value = '';
                                        value += this.name;
                                        if (typeof(this.level) != 'undefined') {
                                            value += ' <span class="pull-right muted">';
                                            value += this.level;
                                            value += '</span>';
                                        }

                                        return String.prototype.replace.apply('<div style="padding: 10px; font-size: 1.5em;">' + value + '</div>',   arguments);
                                    }
                                };
                                $items.push(group);
                            });

                            process($items);
                        },

                    });
                },
                highlighter: function(items){

                    return "<div>"+items+"</div>";
                },
                updater: function (item) {
                    var item = JSON.parse(item);
                    var id='${id}'.split("-")[1];
                    $("#hiddenBankId-"+id).val(item.bankId);
                    return item.name;
                }
            });


        };

        addTypeHead();
    });


</script>
