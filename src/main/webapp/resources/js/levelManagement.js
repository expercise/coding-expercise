expercise.LevelManagement = {

    bindEvents: function () {
        $(document).on('click', '.updateButton', this.setUpdatingLevelContentToForm);
        $(document).on('click', '#resetButton', this.resetForm);
    },

    setUpdatingLevelContentToForm: function () {
        var $that = $(this);
        var levelId = $that.data("level-id");
        $('form input[name="levelId"]').val(levelId);

        var levelContentColumns = $that.closest('tr').find('td');
        $('form input[name="priority"]').val($(levelContentColumns[0]).text());
        $('form input[name="turkishName"]').val($(levelContentColumns[1]).text());
        $('form input[name="englishName"]').val($(levelContentColumns[2]).text());
    },

    resetForm: function () {
        $('form input').val("");
        $('form .alert.alert-danger').remove();
        $('form div.form-group.has-error').removeClass('has-error');
    }

};