kodility.locale = {

    constructor: function() {
        this.reloadValues()
        this.clearCookies();
    },

    clearCookies: function() {
        $.removeCookie("solution");
    },

    bindEvents: function () {
        var langs = $('#langSelectContainer').find('a');
        langs.click(this.keepValues);
    },

    keepValues: function() {
        $.cookie("solution", kodility.CodeEditor.getSolution());
    },

    reloadValues: function() {
        var savedSolution = $.cookie("solution");
        if (savedSolution !== undefined) {
            kodility.CodeEditor.setSolution(savedSolution);
        }
    }

};