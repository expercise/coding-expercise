kodility.Challenge = {

    constructor: function () {
        this.initCodeEditorWithSolutionTemplate();
    },

    bindEvents : function () {
        $('#runButton').click(function () {
            var requestData = {
                solution: $('#codeEditor').val(),
                language: $('#languageSelection').val(),
                challengeId: 1
            };

            $.ajax({
                type: 'POST', dataType: 'json', contentType: 'text/plain; charset=utf-8',
                url: kodility.utils.urlFor('challenges/eval'),
                data: requestData,
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