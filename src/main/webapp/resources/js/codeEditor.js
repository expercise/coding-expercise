kodility.CodeEditor = {

    codeEditor: null,

    theme: "default",

    mode: "javascript",

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

        var savedMode = $.cookie("editorMode");
        if (savedMode) {
            this.mode = savedMode;
        }

        this.codeEditor = CodeMirror.fromTextArea(document.getElementById('codeEditor'), {
            lineNumbers: true,
            theme: this.theme,
            mode: this.mode,
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
    },

    changeMode: function (mode) {
        if (mode == "js") {
            this.mode = "javascript";
        } else if (mode == "py") {
            this.mode = "python";
        } else if (mode == "java") {
            this.mode = "text/x-java";
        }

        this.codeEditor.setOption("mode", this.mode);
        $.cookie("editorMode", this.mode);
    },

    resetMode: function () {
        $.removeCookie("editorMode");
    }

};