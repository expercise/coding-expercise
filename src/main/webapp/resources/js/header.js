kodility.Header = {

    bindEvents: function () {
        $('#header').scrollToFixed({
            zIndex: 1025,
            dontSetWidth: true,
            marginTop: 0
        });
    },

    marginTopForScrollFixedElement: function ($element) {
        return 20 + $('#header').height() - parseInt($element.css('margin-top').replace('px', ''))
    }

};