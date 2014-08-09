kodility = {

    bindEvents: function () {
        $('#header .row').scrollToFixed({
            zIndex: 1025,
            dontSetWidth: true,
            marginTop: 0,
            preFixed: function () {
                $(this).addClass('scrolledHeader');
            },
            postFixed: function () {
                $(this).removeClass('scrolledHeader');
            }
        });
    }

};