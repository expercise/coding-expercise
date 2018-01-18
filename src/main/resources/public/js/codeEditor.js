expercise.CodeEditor = {

    KEYMAPS: {
        DEFAULT: 'default',
        VIM: 'vim'
    },

    codeEditor: null,

    theme: "default",

    mode: "javascript",

    keyMap: "default",

    constructor: function () {
        this.initCodeEditor();
    },

    bindEvents: function () {
        $('#themeButton').click(function () {
            expercise.CodeEditor.toggleTheme();
        });

        $('#fullScreenButton').click(function () {
            expercise.CodeEditor.fullscreen();
        });

        this.bindRunChallengeShortcut();
    },

    bindRunChallengeShortcut: function () {
        this.codeEditor.setOption("extraKeys", {
                "Ctrl-Alt-R": function () {
                    expercise.Challenge.runChallenge();
                },
                "Ctrl-Space": "autocomplete"
            }
        );
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

        var savedKeyMap = $.cookie("keyMap");
        if (savedKeyMap) {
            this.keyMap = savedKeyMap;
        }

        this.codeEditor = CodeMirror.fromTextArea(document.getElementById('codeEditor'), {
            lineNumbers: true,
            theme: this.theme,
            mode: this.mode,
            keyMap: this.keyMap,
            indentUnit: 4,
            indentWithTabs: false
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
        } else if (mode.startsWith("py")) {
            this.mode = "python";
        } else if (mode == "java") {
            this.mode = "text/x-java";
        }

        this.codeEditor.setOption("mode", this.mode);
        $.cookie("editorMode", this.mode);
    },

    resetMode: function () {
        $.removeCookie("editorMode");
    },

    fullscreen: function () {
        expercise.CodeEditor.codeEditor.focus();
        $('.CodeMirror').fullscreen();
    },

    isVimKeyMap: function () {
        return $.cookie("keyMap") == expercise.CodeEditor.KEYMAPS.VIM;
    },

    changeKeyMap: function (keyMap) {
        this.keyMap = keyMap;
        this.codeEditor.setOption("keyMap", this.keyMap);
        $.cookie("keyMap", this.keyMap);
    }

};