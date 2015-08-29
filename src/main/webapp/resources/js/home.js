expercise.Home = {

    constructor: function () {
        this.showAssistant();
    },

    showAssistant: function () {
        expercise.assistant.show(function () {
            expercise.assistant.speak(expercise.utils.i18n(
                'assistant.home.welcome',
                [expercise.utils.urlFor('themes')]
            ));

            expercise.assistant.hide(13000);
        });
    }

};