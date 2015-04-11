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

    bindEvents: function() {
        $('.themeContainer').click(function () {
            expercise.utils.go($(this).find('a').attr('href'));
        });
    }

};