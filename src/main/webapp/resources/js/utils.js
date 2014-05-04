kodility.utils = {

    urlFor: function (uri) {
        return $('#contextPath').val() + uri;
    },

    goToMainPage: function () {
        window.location = this.urlFor("");
    },

    i18n: function (key) {
        return messages[key];
    }

};