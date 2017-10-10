expercise.utils = {

    urlFor: function (uri) {
        return $('#contextPath').val() + uri;
    },

    go: function (url) {
        window.location = url;
    },

    i18n: function (key, args) {
        var value = window['messages'][key];

        (args || []).forEach(function (each) {
            value = value.replace(/{[0-9]}/g, each);
        });

        return value;
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
        var $icon = config.element.find('i');
        config.oldIcon = $icon.attr('class');
        $icon.addClass('fa fa-hourglass-half');
        config.element.addClass('disabled');
        return config;
    },

    resetLoadingState: function (config) {
        config.element.removeClass('disabled');
        var $icon = config.element.find('i');
        $icon.removeClass('fa fa-hourglass-half');
        $icon.addClass(config.oldIcon);
    },

    isMobileClient: function () {
        return $('#mobileClient').val() == "true";
    },

    scrollToFixed: function ($element, config) {
        if (expercise.utils.isMobileClient()) {
            return;
        }

        $element.scrollToFixed(config);
    },

    scrollTop: function () {
        $(window).scrollTop(0);
    }

};