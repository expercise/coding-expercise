kodility.utils = {

    urlFor: function (uri) {
        return $('#contextPath').val() + uri;
    },

    goToMainPage: function () {
        window.location = this.urlFor("");
    },

    i18n: function (key) {
        return messages[key];
    },

    post: function (url, data, success) {
        $.ajax({
            type: 'POST', dataType: 'json', contentType: 'application/json; charset=utf-8',
            url: kodility.utils.urlFor(url),
            data: JSON.stringify(data),
            success: success
        });
    }

};