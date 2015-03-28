expercise.Header = {

    $header: $('#header'),

    bindEvents: function () {
        this.setUpHeaderScrollToFixed();
        this.userMenuToggle();
    },

    setUpHeaderScrollToFixed: function () {
        expercise.utils.scrollToFixed(this.$header, {
            zIndex: 1025,
            dontSetWidth: true,
            marginTop: 0
        });
    },

    userMenuToggle: function () {
        var $userMenuDropdown = $('#userMenuDropdown');
        $userMenuDropdown.mouseenter(function () {
            if ($('#userMenu').hasClass('open') == false) {
                $userMenuDropdown.dropdown('toggle');
            }
        });
    },

    marginTopForScrollFixedElement: function ($element) {
        return 20 + this.$header.height() - parseInt($element.css('margin-top').replace('px', ''))
    }

};