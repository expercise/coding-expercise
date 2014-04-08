kodility.Challenge = {

    constructor: function () {
        this.initCodeEditorWithSolutionTemplate();
    },

    bindEvents: function () {
        $('#runButton').click(function () {
            var requestData = {
                solution: kodility.CodeEditor.getSolution(),
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

        $('#languageSelection').change(function() {
            var requestData = {
                language: $('#languageSelection').val(),
                challengeId: $('#challengeId').val()
            };

            $.ajax({
                type: 'POST', dataType: 'text', contentType: 'application/json; charset=utf-8',
                url: kodility.utils.urlFor('challenges/changeLanguage'),
                data: JSON.stringify(requestData),
                success: function (response) {
                    kodility.Challenge.resetConsole();
                    kodility.CodeEditor.setSolution(response);
                }
            });
        });
    },

    initCodeEditorWithSolutionTemplate: function () {
        kodility.CodeEditor.setSolution($('#codeEditor').data("solution-template"));
    },

    resetConsole: function () {
        var $resultsTextarea = $('#resultsTextarea');
        $resultsTextarea.val('');
        $resultsTextarea.removeClass('successResult');
        $resultsTextarea.removeClass('failedResult');
    }

};