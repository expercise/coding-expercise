expercise.Locale = {

    bindEvents: function () {
        $('#lingoSelection').find('a').click(function () {
            expercise.CodeEditor.resetMode();
        });
    }

};