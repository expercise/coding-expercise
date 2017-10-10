expercise.ChallengeManagement = {

    challengeModel: {},

    constructor: function () {
        this.challengeModel = JSON.parse($('#challengeModel').val());
        if (!jQuery.isEmptyObject(this.challengeModel)) {
            this.prepareForUpdate(this.challengeModel);
        }
    },

    bindEvents: function () {
        $(document).on('click', '.removeInput', this.removeInputAction);

        $(document).on('click', '.removeTestCase', this.removeTestCaseAction);

        $('#addNewInput').click(this.addNewInputAction);

        $('#addNewTestCase').click(this.addNewTestCaseAction);

        var $saveButton = $('#saveButton');
        $saveButton.click(this.saveChallenge);

        expercise.utils.scrollToFixed($('#manageChallengeButtons'), {
            marginTop: expercise.Header.marginTopForScrollFixedElement($saveButton)
        });

        $('#testCasesTable').find('tbody').sortable({
            helper: function(e, tr) {
                var $originals = tr.children();
                var $rowClone = tr.clone();
                $rowClone.children().each(function(index) {
                    $(this).width($originals.eq(index).width())
                });
                return $rowClone;
            }
        });
    },

    prepareForUpdate: function (challengeModel) {
        $('select[name="challengeType"]').val(challengeModel.challengeType);

        $('input[name="title"]').each(function () {
            var $that = $(this);
            var lingo = $that.parent('div').data('lingo');
            var title = challengeModel.titles.filter(function (element) {
                return element.lingo == lingo;
            })[0].text;
            $that.val(title);
        });

        $('textarea[name="description"]').each(function () {
            var $that = $(this);
            var lingo = $that.parent('div').data('lingo');
            var description = challengeModel.descriptions.filter(function (element) {
                return element.lingo == lingo;
            })[0].text;
            $that.val(description);
        });

        $('textarea[name="signature"]').each(function () {
            var $that = $(this);
            var lingo = $that.parent('div').data('lingo');
            var filteredSignatures = challengeModel.signatures.filter(function (element) {
                return element.lingo == lingo;
            });
            if (filteredSignatures.length > 0) {
                $that.val(filteredSignatures[0].text);
            }
        });

        var $inputsTableBody = $('#inputsTable > tbody');
        $inputsTableBody.find('tr').remove();
        challengeModel.inputTypes.forEach(function (inputType) {
            expercise.ChallengeManagement.addNewInputAction();
            $inputsTableBody.find('tr:last').find('select > option[value=' + inputType + ']').attr('selected', 'selected');
        });

        $('#outputTable').find('select > option[value=' + challengeModel.outputType + ']').attr('selected', 'selected');

        var $testCasesTableBody = $('#testCasesTable').find('tbody');
        $testCasesTableBody.find('tr').remove();
        challengeModel.testCases.forEach(function () {
            expercise.ChallengeManagement.addNewTestCaseAction();
        });
        var $testCaseRows = $testCasesTableBody.find('tr');
        for (var testCaseIndex = 0; testCaseIndex < challengeModel.testCases.length; testCaseIndex++) {
            var $testCaseRowInputs = $($testCaseRows[testCaseIndex]).find('input[name="inputValue"]');
            $testCaseRowInputs.each(function (inputIndex, $input) {
                $($input).val(challengeModel.testCases[testCaseIndex].inputValues[inputIndex]);
            });
            $($testCaseRows[testCaseIndex]).find('input[name="outputValue"]').val(challengeModel.testCases[testCaseIndex].outputValue);
        }

        if (challengeModel.approved) {
            $('input[name="approved"]').attr('checked', 'checked');
        }

        var $levelSelection = $('select[name="level"]');
        if ($levelSelection.length == 1 && challengeModel.level) {
            $levelSelection.val(challengeModel.level);
        }
    },

    removeInputAction: function () {
        $(this).parents('tr').remove();

        expercise.ChallengeManagement.adjustInputValues();
    },

    addNewInputAction: function () {
        var inputRow = ''
            + '<tr>'
            + '   <td>'
            + '       <select name="inputType" class="form-control">'
            + '           <option value="Integer">' + expercise.utils.i18n('Integer') + '</option>'
            + '           <option value="Text">' + expercise.utils.i18n('Text') + '</option>'
            + '           <option value="Array">' + expercise.utils.i18n('Array') + '</option>'
            + '       </select>'
            + '   </td>'
            + '   <td>'
            + '       <a class="removeInput btn btn-danger"><span class="glyphicon glyphicon-minus"></span></a>'
            + '   </td>'
            + '</tr>';

        $('#inputsTable > tbody').append($(inputRow));

        expercise.ChallengeManagement.adjustInputValues();
    },

    adjustInputValues: function () {
        var currentInputsCount = $('#inputsTable > tbody > tr').length;

        $('#testCasesTable').find('tbody > tr').each(function () {
            var $tr = $(this);
            var inputValues = $tr.find("input[name='inputValue']");
            var inputValuesCount = inputValues.length;

            if (currentInputsCount < inputValuesCount) {
                inputValues.last().remove();
            } else if (currentInputsCount > inputValuesCount) {
                var inputValueRow = '<input class="form-control" type="text" name="inputValue" maxlength="1024"/>';
                $($tr.find('td')[0]).append(inputValueRow);
            }
        });
    },

    removeTestCaseAction: function () {
        $(this).parents('tr').remove();
    },

    addNewTestCaseAction: function () {
        var currentInputsCount = $('#inputsTable > tbody > tr').length;

        var testCaseRow = ''
            + '<tr>'
            + '     <td>';

        for (var i = 1; i <= currentInputsCount; i++) {
            testCaseRow += '<input class="form-control" type="text" name="inputValue" maxlength="1024"/>'
        }

        testCaseRow += ''
            + '     </td>'
            + '     <td>'
            + '         <input class="form-control" type="text" name="outputValue" maxlength="1024"/>'
            + '     </td>'
            + '     <td>'
            + '         <a class="removeTestCase btn btn-danger"><span class="glyphicon glyphicon-minus"></span></a>'
            + '     </td>'
            + '</tr>';

        $('#testCasesTable').find('tbody').append($(testCaseRow));
    },

    saveChallenge: function () {
        var $saveButton = $(this);

        var challengeType = $('select[name="challengeType"]').val();

        var titles = [];
        $('input[name="title"]').each(function () {
            var lingo = $(this).parent('div').data('lingo');
            var title = $(this).val();
            titles.push({
                lingo: lingo,
                text: title
            });
        });

        var descriptions = [];
        $('textarea[name="description"]').each(function () {
            var lingo = $(this).parent('div').data('lingo');
            var description = $(this).val();
            descriptions.push({
                lingo: lingo,
                text: description
            });
        });

        var signatures = [];
        $('textarea[name="signature"]').each(function () {
            var lingo = $(this).parent('div').data('lingo');
            var signature = $(this).val();
            signatures.push({
                lingo: lingo,
                text: signature
            });
        });

        var inputTypes = [];
        $('#inputsTable select[name="inputType"]').each(function () {
            inputTypes.push($(this).val());
        });

        var outputType = $('select[name="outputType"]').val();

        var testCases = [];
        $('#testCasesTable tbody tr').each(function () {
            var $columns = $(this).find('td');

            var inputValues = [];
            $($columns[0]).find('input').each(function () {
                inputValues.push($(this).val());
            });

            var outputValue = $($columns[1]).find('input').val();

            testCases.push({
                inputValues: inputValues,
                outputValue: outputValue
            });
        });

        var requestData = {
            challengeId: expercise.ChallengeManagement.challengeModel.challengeId,
            challengeType: challengeType,
            titles: titles,
            descriptions: descriptions,
            signatures: signatures,
            inputTypes: inputTypes,
            outputType: outputType,
            testCases: testCases
        };

        var $approveCheckbox = $('input[name="approved"]');
        if ($approveCheckbox.length == 1) {
            requestData.approved = $approveCheckbox.is(':checked');
        }

        var $levelSelection = $('select[name="level"]');
        if ($levelSelection.length == 1) {
            requestData.level = $levelSelection.val();
        }

        var loadingStateConfig = expercise.utils.setLoadingState({element: $saveButton});
        expercise.utils.resetHash();
        expercise.utils.post(
            'challenges/saveChallenge',
            requestData,
            function (response) {
                expercise.utils.resetLoadingState(loadingStateConfig);
                if (response.success) {
                    expercise.utils.go(expercise.utils.urlFor('challenges/' + response.challengeId));
                } else {
                    var $validationMessages = $('#validationMessages');
                    $validationMessages.removeClass('hide');

                    var $messageList = $validationMessages.find('ul');
                    $messageList.find('li').remove();
                    response.errorCodes.forEach(function (errorCode) {
                        $messageList.append('<li>' + expercise.utils.i18n(errorCode) + '</li>');
                    });

                    expercise.utils.scrollTop();
                }
            }
        );
    }

};