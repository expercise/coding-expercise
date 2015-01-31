expercise.utils = {

    urlFor: function (uri) {
        return $('#contextPath').val() + uri;
    },

    go: function (url) {
        window.location = url;
    },

    i18n: function (key) {
        return messages[key];
    },

    post: function (url, data, success) {
        $.ajax({
            type: 'POST', dataType: 'json', contentType: 'application/json; charset=utf-8',
            url: expercise.utils.urlFor(url),
            data: JSON.stringify(data),
            success: success
        });
    },

    resetHash: function () {
        location.hash = '';
    },

    setLoadingState: function (config) {
        config.element.addClass('glyphicon glyphicon-' + config.icon);
        config.element.addClass('disabled');
        config.oldText = config.element.text();
        config.element.text('');
        return config;
    },

    resetLoadingState: function (config) {
        config.element.removeClass('disabled');
        config.element.removeClass('glyphicon glyphicon-' + config.icon);
        config.element.text(config.oldText);
    }

};