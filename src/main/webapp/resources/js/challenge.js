kodility.Challenge = {

    solutionSignatures: {},

    constructor: function () {
        this.solutionSignatures = JSON.parse($('#solutionSignatures').val());
        var userSolutions = JSON.parse($('#userSolutions').val());
        this.populateUserSolutionTable(userSolutions);
        this.adjustProgrammingLanguage();
    },

    bindEvents: function () {
        $('#challengeButtons').scrollToFixed({
            marginTop: 10
        });

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
                        kodility.Challenge.populateUserSolutionTable(response.userSolutionModels);
                    } else {
                        $resultsTextarea.addClass('failedResult');
                    }
                }
            );
        });

        $('#resetButton').click(function () {
            bootbox.dialog({
                message: kodility.utils.i18n('challenge.reset.dialog.confirm'),
                title: kodility.utils.i18n('challenge.reset.dialog.header'),
                buttons: {
                    no: {
                        label: kodility.utils.i18n('button.no')
                    },
                    yes: {
                        label: kodility.utils.i18n('button.yes'),
                        callback: kodility.Challenge.reset
                    }
                }
            });
        });

        $('#languageSelection').change(this.adjustProgrammingLanguage);
    },

    reset: function () {
        kodility.Challenge.adjustProgrammingLanguage();
        kodility.Challenge.resetConsole();
    },

    populateUserSolutionTable: function (userSolutionModels) {
        var $userSolutionsTable = $('.userSolutionsTable');
        if (userSolutionModels.length == 0) {
            $userSolutionsTable.hide();
            var noContent = $('<p id="noPreviousSolution"></p>').text(kodility.utils.i18n('challenge.noPreviousSolution'));
            noContent.insertAfter($userSolutionsTable);
            return;
        }

        $userSolutionsTable.show();
        $('#noPreviousSolution').remove();
        $('.userSolutionsTable tbody').remove();

        var tbody = $('<tbody>');
        $.each(userSolutionModels, function (i, value) {
            var languageAnchor = $('<a href="#"></a>')
                .data('langName', value['languageShortName'])
                .click(function (e) {
                    var clickedLanguage = $(this).data('langName');
                    kodility.Challenge.changeProgrammingLanguage(clickedLanguage, value['solution']);
                    $('#languageSelection').val(clickedLanguage);
                    e.preventDefault();
                })
                .html(value['programmingLanguage']);

            var solutionDateCell = $('<td></td>').html(value['solutionDate']);
            var languageCell = $('<td></td>').html(languageAnchor);
            var row = $('<tr></tr>').append(solutionDateCell, languageCell);
            tbody.append(row);
        });

        $userSolutionsTable.append(tbody);
    },

    adjustProgrammingLanguage: function () {
        var selectedLanguage = $('#languageSelection').val();
        kodility.Challenge.changeProgrammingLanguage(selectedLanguage, kodility.Challenge.solutionSignatures[selectedLanguage]);
    },

    changeProgrammingLanguage: function (langName, solution) {
        kodility.Challenge.resetConsole();
        kodility.CodeEditor.setSolution(solution);
        kodility.CodeEditor.changeMode(langName);
    },

    resetConsole: function () {
        var $resultsTextarea = $('#resultsTextarea');
        $resultsTextarea.val('');
        $resultsTextarea.removeClass('successResult');
        $resultsTextarea.removeClass('failedResult');
    }

};