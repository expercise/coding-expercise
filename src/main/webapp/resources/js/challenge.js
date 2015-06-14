expercise.Challenge = {

    solutionSignatures: {},

    running: false,

    constructor: function () {
        this.solutionSignatures = JSON.parse($('#solutionSignatures').val());
        var userSolutions = JSON.parse($('#userSolutions').val());
        this.populateUserSolutionTable(userSolutions);
        this.adjustProgrammingLanguage();
        this.adjustEditorKeyMap();
        this.initializeKataChallenge();
    },

    bindEvents: function () {
        var $challengeButtons = $('#challengeButtons');
        expercise.utils.scrollToFixed($challengeButtons, {
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
                    var model = response.testCasesWithSourceModel;
                    expercise.Challenge.populateSourceAndTestCaseState(model.currentSourceCode, model.testCaseModels);

                    expercise.Challenge.toggleRunningState();
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

        this.bindRunChallengeShortcut();
        this.bindCodeEditorKeyMapChange();
    },

    bindRunChallengeShortcut: function () {
        $(document).on('keydown', null, 'alt+ctrl+r', function () {
            expercise.Challenge.runChallenge();
        });
    },

    bindCodeEditorKeyMapChange: function () {
        $('input:checkbox#useVimKeyMapCheckBox').change(function (e) {
            var vimKeyMapSelected = $(this).is(':checked');
            var newKeyMap = vimKeyMapSelected ?
                expercise.CodeEditor.KEYMAPS.VIM :
                expercise.CodeEditor.KEYMAPS.DEFAULT;
            expercise.CodeEditor.changeKeyMap(newKeyMap);
        });
    },

    reset: function () {
        expercise.Challenge.adjustProgrammingLanguage();
        expercise.Challenge.resetConsole();
        expercise.Challenge.resetTestCases();
    },

    populateUserSolutionTable: function (userSolutionModels) {
        if (userSolutionModels == null) {
            return;
        }
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

    adjustEditorKeyMap: function () {
        var $useVimKeyMapCheckBox = $('input:checkbox#useVimKeyMapCheckBox');
        $useVimKeyMapCheckBox.prop('checked', expercise.CodeEditor.isVimKeyMap());
    },

    populateSourceAndTestCaseState: function (currentSourceCode, testCaseModels) {
        var challengeType = $('#challengeType').val();
        if (challengeType != 'CODE_KATA') {
            return;
        }

        var showSignatureIfSourceCodeIsEmpty = function () {
            if (currentSourceCode.trim() === '') {
                expercise.Challenge.adjustProgrammingLanguage();
            } else {
                expercise.CodeEditor.setSolution(currentSourceCode);
            }
        };

        var decideTestCaseStyle = function (testCaseResult) {
            var rowStyleClass = "info";
            if (testCaseResult === 'PASSED') {
                rowStyleClass = "success";
            } else if (testCaseResult === 'FAILED') {
                rowStyleClass = "danger";
            }
            return "testCaseRow " + rowStyleClass;
        };

        showSignatureIfSourceCodeIsEmpty();

        $('.userTestCaseStatus tbody').remove();
        var $tbody = $('<tbody>');
        $.each(testCaseModels, function (i, value) {

            var testInputsCell = $('<td></td>').html(value['inputs'].join(", "));
            var testExpectedOutputCell = $('<td></td>').html(value['output']);
            var testActualValueCell = $('<td></td>').html(value['actualValue']);
            var testCaseResult = value['testCaseResult'];
            var testCaseStatus = $('<span class="glyphicon testCaseStatus"></span>');
            var testResultStatusCell = $('<td></td>').html(testCaseStatus);
            var row = $('<tr class="' + decideTestCaseStyle(testCaseResult) + '"></tr>').append(testInputsCell, testExpectedOutputCell, testActualValueCell, testResultStatusCell);
            $tbody.append(row);
        });

        var $userTestCaseTable = $('.userTestCaseStatus');
        $userTestCaseTable.append($tbody);
    },

    initializeKataChallenge: function () {
        if (expercise.Challenge.isNotChallengeCodeKata()) {
            return;
        }

        var testCasesWithSourceModel = JSON.parse($('#testCasesWithSource').val());
        this.populateSourceAndTestCaseState(testCasesWithSourceModel.currentSourceCode, testCasesWithSourceModel.testCaseModels);
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
    },

    resetTestCases: function () {
        if (expercise.Challenge.isNotChallengeCodeKata()) {
            return;
        }

        var requestData = {
            language: $('#languageSelection').val(),
            challengeId: $('#challengeId').val()
        };

        expercise.utils.post(
            'challenges/reset',
            requestData,
            function (response) {
                var $resultsTextarea = $('#resultsTextarea');
                $resultsTextarea.val(response.result);
                if (response.success) {
                    $resultsTextarea.addClass('successResult');
                    expercise.Challenge.populateUserSolutionTable(response.userSolutionModels);
                } else {
                    $resultsTextarea.addClass('failedResult');
                }
                expercise.Challenge.populateSourceAndTestCaseState(response.currentSourceCode, response.testCaseModels);
            }
        );
    },

    isNotChallengeCodeKata: function () {
        var challengeType = $('#challengeType').val();
        return challengeType !== 'CODE_KATA';
    },

    runChallenge: function () {
        if (expercise.Challenge.running) {
            return;
        }
        expercise.Challenge.toggleRunningState();
        $('#runButton').click();
    },

    toggleRunningState: function () {
        expercise.Challenge.running = !expercise.Challenge.running;
    }

};