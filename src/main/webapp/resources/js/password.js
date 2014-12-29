kodility.Password = {

    bindEvents: function () {
        $('.resetBtn').click(this.sendResetEmail);
    },

    sendResetEmail: function () {
        var emailVal = $('#email').val();
        kodility.utils.post("forgotMyPassword/sendForgotMyPasswordEmail", {email: emailVal}, kodility.Password.responseCallback);
    },

    responseCallback: function (response) {
        var resultContainer = $('.forgotMyPasswordResult');
        resultContainer.removeClass('alert-success').removeClass('alert-danger');
        resultContainer.addClass('alert');
        if (response['success']) {
            resultContainer.addClass('alert-success');
            $('#email').val('');
        } else {
            resultContainer.addClass('alert-danger');
        }
        var message = kodility.utils.i18n(response['messageKey']);
        resultContainer.find('.forgotMyPasswordResultMessage').text(message);
    }

};