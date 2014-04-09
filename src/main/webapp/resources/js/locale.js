kodility.locale = {

    bindEvents: function () {
        $('#langSelectContainer').find('a').click(function () {
            // TODO ufuk: add a warning message about losing current solution (I know, there is an issue for this) :)
            kodility.CodeEditor.resetMode();
        });
    }

};