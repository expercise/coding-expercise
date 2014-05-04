kodility.ChallengeManagement = {

    bindEvents: function () {
        $(document).on('click', '.removeInput', this.removeInputAction);

        $(document).on('click', '.removeTestCase', this.removeTestCaseAction);

        $('#addNewInput').click(this.addNewInputAction);

        $('#addNewTestCase').click(this.addNewTestCaseAction);
    },

    removeInputAction: function () {
        $(this).parents('tr').remove();

        kodility.ChallengeManagement.adjustInputValues();
    },

    addNewInputAction: function () {
        var inputRow = ''
            + '<tr>'
            + '   <td>'
            + '       <select name="inputType">'
            + '           <option value="java.lang.Integer">' + kodility.utils.i18n('Integer') + '</option>'
            + '           <option value="java.lang.String">' + kodility.utils.i18n('Text') + '</option>'
            + '       </select>'
            + '   </td>'
            + '   <td>'
            + '       <a class="removeInput">' + kodility.utils.i18n('button.remove') + '</a>'
            + '   </td>'
            + '</tr>';

        $(this).parents('table').find('tbody').append($(inputRow));

        kodility.ChallengeManagement.adjustInputValues();
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
                var inputValueRow = '<input class="form-control" type="text" name="inputValue"/>'
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
            testCaseRow += '<input class="form-control" type="text" name="inputValue"/>'
        }

        testCaseRow += ''
            + '     </td>'
            + '     <td>'
            + '         <input class="form-control" type="text" name="outputValue"/>'
            + '     </td>'
            + '     <td>'
            + '         <a class="removeTestCase">' + kodility.utils.i18n('button.remove') + '</a>'
            + '     </td>'
            + '</tr>';

        $(this).parents('table').find('tbody').append($(testCaseRow));
    }

};