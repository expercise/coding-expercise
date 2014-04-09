kodility.locale = {

    bindEvents: function () {
        $('#langSelectContainer').find('a').click(function () {
            kodility.CodeEditor.resetMode();
        });
    }

};