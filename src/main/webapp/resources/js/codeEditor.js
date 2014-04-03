kodility.CodeEditor = {

    codeEditor: null,

    theme: "default",

    constructor: function () {
        this.initCodeEditor();
    },

    bindEvents: function () {
        $('#themeButton').click(function () {
            kodility.CodeEditor.toggleTheme();
        });
        $('#fullScreenButton').click(function () {
            kodility.CodeEditor.codeEditor.focus();
            $('.CodeMirror').fullscreen();
        });
    },

    initCodeEditor: function () {
        var savedTheme = $.cookie("editorTheme");
        if (savedTheme) {
            this.theme = savedTheme;
        }
        this.codeEditor = CodeMirror.fromTextArea(document.getElementById('codeEditor'), {
            lineNumbers: true,
            theme: this.theme,
            mode: "javascript",
            indentUnit: 4,
            indentWithTabs: true
        });
    },

    setSolution: function (solution) {
        this.codeEditor.doc.setValue(solution);
    },

    getSolution: function () {
        return this.codeEditor.doc.getValue();
    },

    toggleTheme: function () {
        this.theme = this.theme == "default" ? "ambiance" : "default";
        this.codeEditor.setOption("theme", this.theme);
        $.cookie("editorTheme", this.theme);
    }

};