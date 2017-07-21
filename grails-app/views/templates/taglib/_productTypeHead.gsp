
<style>

.typeahead.dropdown-menu{
    right:10px;

}

.tt-query, /* UPDATE: newer versions use tt-input instead of tt-query */
.tt-hint {
    width: 396px;
    height: 30px;
    padding: 8px 12px;
    font-size: 24px;
    line-height: 30px;
    border: 2px solid #ccc;
    border-radius: 8px;
    outline: none;
}

.tt-query { /* UPDATE: newer versions use tt-input instead of tt-query */
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}

.tt-hint {
    color: #999;
}

.tt-menu { /* UPDATE: newer versions use tt-menu instead of tt-dropdown-menu */
    width: 422px;
    margin-top: 12px;
    padding: 8px 0;
    background-color: #fff;
    border: 1px solid #ccc;
    border: 1px solid rgba(0, 0, 0, 0.2);
    border-radius: 8px;
    box-shadow: 0 5px 10px rgba(0,0,0,.2);
}

.tt-suggestion {
    padding: 3px 20px;
    font-size: 18px;
    line-height: 24px;
}

.tt-suggestion.tt-is-under-cursor { /* UPDATE: newer versions use .tt-suggestion.tt-cursor */
    color: #fff;
    background-color: #0097cf;

}

.tt-suggestion p {
    margin: 0;
}

.glyphicon-th-list::before {
    content: "";
    padding-top: 30%;
    position: relative;
    vertical-align: middle;
}
</style>


%{--<div class="has-feedback">--}%
    <input style="direction:${direction} "  id="${id}" name="${name}"  type="text" data-provide="typeahead" class="typeahead form-control">
    <input id="${hidden}" name="${hidden}"  type="hidden">
    %{--<span class="glyphicon glyphicon-th-list form-control-feedback"></span>--}%
%{--</div>--}%

<script>



    $(document).ready(function () {
        $(".typeahead.dropdown-menu").css("right","1px");
        function _productTypeHead() {
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
                                    name: data.productName,
                                    productId: data.productId,
                                    price:data.price,
                                    defaultUnit:data.defaultUnit,
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
                    $("#hiddenProductId-"+id).val(item.productId);
                    $("#unit-"+id).val(item.defaultUnit);
                    $("#unitPrice-"+id).val(item.price).change();

                    return item.name;
                }
            });


        };

        _productTypeHead();
    });


</script>
