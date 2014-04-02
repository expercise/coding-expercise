var modules = [
    kodility,
    kodility.utils,
    kodility.CodeEditor,
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