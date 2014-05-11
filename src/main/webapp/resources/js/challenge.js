kodility.Challenge = {

    solutionSignatures: {},

    constructor: function () {
        this.solutionSignatures = JSON.parse($('#solutionSignatures').val());
        this.adjustProgrammingLanguage();
    },

    bindEvents: function () {
        $('#runButton').click(function () {
            var $runButton = $(this);

            var requestData = {
                solution: kodility.CodeEditor.getSolution(),
                language: $('#languageSelection').val(),
                challengeId: $('#challengeId').val()
            };

            var loadingStateConfig = kodility.utils.setLoadingState({element: $runButton, icon: 'refresh'});
            kodility.utils.post(
                'challenges/eval',
                requestData,
                function (response) {
                    kodility.utils.resetLoadingState(loadingStateConfig);
                    kodility.Challenge.resetConsole();
                    var $resultsTextarea = $('#resultsTextarea');
                    $resultsTextarea.val(response.result);
                    if (response.success) {
                        $resultsTextarea.addClass('successResult');
                    } else {
                        $resultsTextarea.addClass('failedResult');
                    }
                }
            );
        });

        $('#resetButton').click(function () {
            kodility.Challenge.adjustProgrammingLanguage();
            kodility.Challenge.resetConsole();
        });

        $('#languageSelection').change(this.adjustProgrammingLanguage);
    },

    adjustProgrammingLanguage: function () {
        kodility.Challenge.resetConsole();
        var selectedLanguage = $('#languageSelection').val();
        kodility.CodeEditor.setSolution(kodility.Challenge.solutionSignatures[selectedLanguage]);
        kodility.CodeEditor.changeMode(selectedLanguage);
    },

    resetConsole: function () {
        var $resultsTextarea = $('#resultsTextarea');
        $resultsTextarea.val('');
        $resultsTextarea.removeClass('successResult');
        $resultsTextarea.removeClass('failedResult');
    }

};