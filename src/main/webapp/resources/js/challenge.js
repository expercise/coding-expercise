kodility.Challenge = {

    constructor: function () {
        this.initCodeEditorWithSolutionTemplate();
    },

    bindEvents : function () {
        $('#runButton').click(function () {
            $.ajax({
                type: 'POST', dataType: 'text', contentType: 'text/plain; charset=utf-8',
                url: kodility.utils.urlFor('challenges/eval'),
                data: $('#codeEditor').val(),
                success: function (response) {
                    $('#resultsTextarea').val(response);
                }
            });
        });

        $('#resetButton').click(function () {
            kodility.Challenge.initCodeEditorWithSolutionTemplate();
            $('#resultsTextarea').val('');
        });
    },

    initCodeEditorWithSolutionTemplate : function () {
        var $codeEditor = $('#codeEditor');
        $codeEditor.val($codeEditor.data("solution-template"));
    }

};