expercise.Header = {

    $header: $('#header'),

    bindEvents: function () {
        this.menuToggles();
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
    }

};