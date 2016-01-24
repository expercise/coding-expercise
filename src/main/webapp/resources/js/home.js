expercise.Home = {

    constructor: function () {
        this.showAssistant();
    },

    showAssistant: function () {
        expercise.assistant.show(function () {
            expercise.assistant.speak(
                expercise.utils.i18n('assistant.home.welcome.title'),
                expercise.utils.i18n('assistant.home.welcome.content', [expercise.utils.urlFor('themes')]),
                expercise.utils.i18n('assistant.home.welcome.button'),
                function () {
                    expercise.utils.go(expercise.utils.urlFor('themes'));
                }
            );
            expercise.assistant.hide(15);
        });
    }

};