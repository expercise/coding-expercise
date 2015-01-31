expercise.Challenge = {

    solutionSignatures: {},

    constructor: function () {
        this.solutionSignatures = JSON.parse($('#solutionSignatures').val());
        var userSolutions = JSON.parse($('#userSolutions').val());
        this.populateUserSolutionTable(userSolutions);
        this.adjustProgrammingLanguage();
    },

    bindEvents: function () {
        var $challengeButtons = $('#challengeButtons');
        $challengeButtons.scrollToFixed({
            marginTop: expercise.Header.marginTopForScrollFixedElement($challengeButtons)
        });

        $('#runButton').click(function () {
            var $runButton = $(this);

            var requestData = {
                solution: expercise.CodeEditor.getSolution(),
                language: $('#languageSelection').val(),
                challengeId: $('#challengeId').val()
            };

            var loadingStateConfig = expercise.utils.setLoadingState({element: $runButton, icon: 'refresh'});
            expercise.utils.post(
                'challenges/eval',
                requestData,
                function (response) {
                    expercise.utils.resetLoadingState(loadingStateConfig);
                    expercise.Challenge.resetConsole();
                    var $resultsTextarea = $('#resultsTextarea');
                    $resultsTextarea.val(response.result);
                    if (response.success) {
                        $resultsTextarea.addClass('successResult');
                        expercise.Challenge.populateUserSolutionTable(response.userSolutionModels);
                    } else {
                        $resultsTextarea.addClass('failedResult');
                    }
                }
            );
        });

        $('#resetButton').click(function () {
            bootbox.dialog({
                message: expercise.utils.i18n('challenge.reset.dialog.confirm'),
                title: expercise.utils.i18n('challenge.reset.dialog.header'),
                buttons: {
                    no: {
                        label: expercise.utils.i18n('button.no')
                    },
                    yes: {
                        label: expercise.utils.i18n('button.yes'),
                        callback: expercise.Challenge.reset
                    }
                }
            });
        });

        $('#languageSelection').change(this.adjustProgrammingLanguage);
    },

    reset: function () {
        expercise.Challenge.adjustProgrammingLanguage();
        expercise.Challenge.resetConsole();
    },

    populateUserSolutionTable: function (userSolutionModels) {
        var $userSolutionsTable = $('.userSolutionsTable');
        if (userSolutionModels.length == 0) {
            $userSolutionsTable.hide();
            var noContent = $('<p id="noPreviousSolution"></p>').text(expercise.utils.i18n('challenge.noPreviousSolution'));
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
                    expercise.Challenge.changeProgrammingLanguage(clickedLanguage, value['solution']);
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
        expercise.Challenge.changeProgrammingLanguage(selectedLanguage, expercise.Challenge.solutionSignatures[selectedLanguage]);
    },

    changeProgrammingLanguage: function (langName, solution) {
        expercise.Challenge.resetConsole();
        expercise.CodeEditor.setSolution(solution);
        expercise.CodeEditor.changeMode(langName);
    },

    resetConsole: function () {
        var $resultsTextarea = $('#resultsTextarea');
        $resultsTextarea.val('');
        $resultsTextarea.removeClass('successResult');
        $resultsTextarea.removeClass('failedResult');
    }

};