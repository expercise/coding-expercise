expercise.Header = {

    $header: $('#header'),

    bindEvents: function () {
        this.setUpHeaderScrollToFixed();
        this.menuToggles();
    },

    setUpHeaderScrollToFixed: function () {
        expercise.utils.scrollToFixed(this.$header, {
            zIndex: 1025,
            dontSetWidth: true,
            marginTop: 0
        });
    },

    menuToggles: function () {
        if (expercise.utils.isMobileClient()) {
            return;
        }

        $('#userMenuDropdown').mouseenter(function () {
            if ($('#userMenuDropdownContainer').hasClass('open') == false) {
                $(this).dropdown('toggle');
            }
        });

        $('#lingoMenuDropdown').mouseenter(function () {
            if ($('#lingoMenuDropdownContainer').hasClass('open') == false) {
                $(this).dropdown('toggle');
            }
        });
    },

    marginTopForScrollFixedElement: function ($element) {
        return 20 + this.$header.height() - parseInt($element.css('margin-top').replace('px', ''))
    }

};