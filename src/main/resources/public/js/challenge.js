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
        this.showWelcomeIfNewMember();
        this.showWelcomeIfNotAuthenticated();
    },

    bindEvents: function () {
        $('#runButton').click(function () {
            var $runButton = $(this);

            var requestData = {
                solution: expercise.CodeEditor.getSolution(),
                language: $('#languageSelection').val(),
                challengeId: $('#challengeId').val()
            };

            var loadingStateConfig = expercise.utils.setLoadingState({element: $runButton});
            expercise.utils.post(
                'challenges/eval',
                requestData,
                function (response) {
                    expercise.utils.resetLoadingState(loadingStateConfig);
                    expercise.assistant.speak({'message': response.result});
                    if (response.success) {
                        expercise.Challenge.populateUserSolutionTable(response.userSolutionModels);
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
        var showSignatureIfSourceCodeIsEmpty = function () {
            if (currentSourceCode.trim() === '') {
                expercise.Challenge.adjustProgrammingLanguage();
            } else {
                expercise.CodeEditor.setSolution(currentSourceCode);
            }
        };

        var decideTestCaseResultIcon = function (testCaseResult) {
            var iconClass = "glyphicon-question-sign";
            if (testCaseResult === 'PASSED') {
                iconClass = "glyphicon-ok-circle text-success";
            } else if (testCaseResult === 'FAILED') {
                iconClass = "glyphicon-remove-circle text-danger";
            }
            return iconClass;
        };

        showSignatureIfSourceCodeIsEmpty();

        var getOutputConsoleContentFrom = function (outputConsole, index) {
            if (outputConsole && outputConsole.trim() !== "") {
                return '<pre class="accordion-body collapse resultMessage consoleOutput' + index + '">' + outputConsole + '</pre>'
            }
            return '';
        };

        var $userTestCaseTable = $('#userTestCaseStatus');

        $userTestCaseTable.find('tbody').remove();
        var $tbody = $('<tbody>');

        var prepareTestCaseValueContainer = function (value, valueField) {
            var $testCaseValueContainer = $('<div class="testCaseValue"></div>');
            var val = value[valueField];
            if ($.isArray(val)) {
                val = val.join(", ");
            }
            if (val && val.length >= 25) {
                $testCaseValueContainer.attr('data-toggle', 'tooltip');
                $testCaseValueContainer.attr('title', val);
            }
            return $testCaseValueContainer.html(val);
        };

        $.each(testCaseModels, function (index, value) {
            var testInputsCell = $('<td></td>').append(prepareTestCaseValueContainer(value, 'inputs'));
            var testExpectedOutputCell = $('<td></td>').append(prepareTestCaseValueContainer(value, 'output'));
            var testActualValueCell = $('<td></td>').append(prepareTestCaseValueContainer(value, 'actualValue'));
            var testCaseResult = value['testCaseResult'];
            var testCaseStatus = $('<span class="glyphicon '+ decideTestCaseResultIcon(testCaseResult) +'"></span>');
            var testResultStatusCell = $('<td></td>').html(testCaseStatus);
            var testCaseOutputCell = $('<td class="text-right"><a>' + expercise.utils.i18n('challenge.testCase.table.showOutput') + ' <i class="fa fa-chevron-down"></i> </a></td>');
            var contentRow = $('<tr data-toggle="collapse" data-target=".consoleOutput' + index + '" class="testCaseRow accordion-toggle"></tr>')
                .append(testResultStatusCell, testInputsCell, testExpectedOutputCell, testActualValueCell, testCaseOutputCell);
            var outputConsoleRow = $('<tr></tr>').append(
                '<td colspan="5" class="hiddenRow">' + getOutputConsoleContentFrom(value['resultMessage'], index) + '</td>'
            );
            $tbody.append(contentRow).append(outputConsoleRow);
        });

        $userTestCaseTable.append($tbody);
        $(".testCaseValue").tooltip();
    },

    initializeKataChallenge: function () {
        var testCasesWithSourceModel = JSON.parse($('#testCasesWithSource').val());
        this.populateSourceAndTestCaseState(testCasesWithSourceModel.currentSourceCode, testCasesWithSourceModel.testCaseModels);
    },

    changeProgrammingLanguage: function (langName, solution) {
        expercise.CodeEditor.setSolution(solution);
        expercise.CodeEditor.changeMode(langName);
    },

    resetTestCases: function () {
        var requestData = {
            language: $('#languageSelection').val(),
            challengeId: $('#challengeId').val()
        };

        expercise.utils.post(
            'challenges/reset',
            requestData,
            function (response) {
                if (response.success) {
                    expercise.Challenge.populateUserSolutionTable(response.userSolutionModels);
                }
                expercise.Challenge.populateSourceAndTestCaseState(response.currentSourceCode, response.testCaseModels);
            }
        );
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
    },

    showWelcomeIfNewMember: function () {
        if ($('#newMember').val() == "true") {
            setTimeout(function () {
                bootbox.dialog({
                    title: expercise.utils.i18n('login.register.welcome'),
                    message: expercise.utils.i18n('login.register.welcome.messageForDefaultChallenge'),
                    closeButton: false,
                    buttons: {
                        ok: {
                            label: expercise.utils.i18n('button.continue')
                        }
                    }
                });
            }, 500);
        }
    },

    showWelcomeIfNotAuthenticated: function () {
        if ($('#notAuthenticated').val() == "true") {
            expercise.assistant.speak(
                {
                    title: expercise.utils.i18n('notAuthenticated.challengePage.title'),
                    message: expercise.utils.i18n('notAuthenticated.challengePage.content'),
                    buttonText: expercise.utils.i18n('button.login'),
                    onButtonClick: function () {
                        expercise.utils.go('/signin');
                    }
                }
            );
            expercise.assistant.hide(10);
        }
    }

};