kodility.Challenge = {

    constructor: function () {
        this.initCodeEditorWithSolutionTemplate();
    },

    bindEvents : function () {
        $('#runButton').click(function () {
            var requestData = {
                solution: $('#codeEditor').val(),
                language: $('#languageSelection').val(),
                challengeId: $('#challengeId').val()
            };

            $.ajax({
                type: 'POST', dataType: 'json', contentType: 'application/json; charset=utf-8',
                url: kodility.utils.urlFor('challenges/eval'),
                data: JSON.stringify(requestData),
                success: function (response) {
                    kodility.Challenge.resetConsole();
                    var $resultsTextarea = $('#resultsTextarea');
                    $resultsTextarea.val(response.result);
                    if (response.success) {
                        $resultsTextarea.addClass('successResult');
                    } else {
                        $resultsTextarea.addClass('failedResult');
                    }
                }
            });
        });

        $('#resetButton').click(function () {
            kodility.Challenge.initCodeEditorWithSolutionTemplate();
            kodility.Challenge.resetConsole();
        });
    },

    initCodeEditorWithSolutionTemplate : function () {
        var $codeEditor = $('#codeEditor');
        $codeEditor.val($codeEditor.data("solution-template"));
    },

    resetConsole : function () {
        var $resultsTextarea = $('#resultsTextarea');
        $resultsTextarea.val('');
        $resultsTextarea.removeClass('successResult');
        $resultsTextarea.removeClass('failedResult');
    }

};