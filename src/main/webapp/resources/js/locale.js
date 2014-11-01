kodility.Locale = {

    bindEvents: function () {
        $('#lingoSelection').find('a').click(function () {
            kodility.CodeEditor.resetMode();
        });
    }

};