expercise.ThemeListing = {

    constructor: function () {
        if ($('#newMember').val() == "true") {
            setTimeout(function () {
                bootbox.dialog({
                    title: expercise.utils.i18n('login.register.welcome'),
                    message: expercise.utils.i18n('login.register.welcome.message'),
                    buttons: {
                        ok: {
                            label: expercise.utils.i18n('button.continue')
                        }
                    }
                });
            }, 500);
        }
    },

    bindEvents: function () {
        $('.themeItem').hover(
            function () {
                var $that = $(this);
                var $title = $that.find('.themeItemTitle');
                //$title.css('font-weight', 'bold');
                var fontColor = $title.css('background-color');
                $that.find('.themeItemDescription').css('color', fontColor);
            },
            function () {
                var $that = $(this);
                var $title = $that.find('.themeItemTitle');
                //$title.css('font-weight', 'normal');
                $that.find('.themeItemDescription').css('color', '#333');
            }
        );
    }

};