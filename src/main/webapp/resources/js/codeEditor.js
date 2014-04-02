kodility.CodeEditor = {

    codeEditor: null,

    constructor: function () {
        this.initCodeEditor();
    },

    initCodeEditor: function() {
        this.codeEditor = CodeMirror.fromTextArea(document.getElementById('codeEditor'), {
            lineNumbers: true,
            mode: "javascript",
            indentUnit: 4,
            indentWithTabs: true
        });
    },

    setSolution: function(solution) {
        this.codeEditor.doc.setValue(solution);
    },

    getSolution: function() {
        return this.codeEditor.doc.getValue();
    }

};