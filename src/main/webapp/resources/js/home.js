expercise.Home = {

    constructor: function () {
        this.showAssistant();
    },

    showAssistant: function () {
        expercise.assistant.speak(
            {
                title: expercise.utils.i18n('assistant.home.welcome.title'),
                message: expercise.utils.i18n('assistant.home.welcome.content', [expercise.utils.urlFor('themes')]),
                buttonText: expercise.utils.i18n('assistant.home.welcome.button'),
                onButtonClick: function () {
                    expercise.utils.go(expercise.utils.urlFor('themes'));
                }
            }
        );
        expercise.assistant.hide(15);
    }

};