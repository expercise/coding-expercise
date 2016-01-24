expercise.ThemeListing = {

    constructor: function () {
        var that = this;

        if ($('#newMember').val() == "true") {
            setTimeout(function () {
                bootbox.dialog({
                    title: expercise.utils.i18n('login.register.welcome'),
                    message: expercise.utils.i18n('login.register.welcome.message'),
                    closeButton: false,
                    buttons: {
                        ok: {
                            label: expercise.utils.i18n('button.continue'),
                            callback: function () {
                                setTimeout(function () {
                                    that.showAssistant();
                                }, 1000);
                            }
                        }
                    }
                });
            }, 500);
        } else {
            this.showAssistant();
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
                $that.find('.themeItemDescription').css('color', '#333');
            }
        );
    },

    showAssistant: function () {
        expercise.assistant.show(function () {
            expercise.assistant.speak(
                expercise.utils.i18n('assistant.themes.whatIsThemes.title'),
                expercise.utils.i18n('assistant.themes.whatIsThemes.content'),
                expercise.utils.i18n('assistant.themes.whatIsThemes.button')
            );
            expercise.assistant.hide(30);
        });
    }

};