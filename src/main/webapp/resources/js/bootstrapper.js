var modules = [
    kodility,
    kodility.utils,
    kodility.Challenge,
    kodility.locale
];

$(function () {
    modules.forEach(function (eachModule) {
        if (eachModule.constructor) {
            eachModule.constructor();
        }

        if (eachModule.bindEvents) {
            eachModule.bindEvents();
        }
    });
});