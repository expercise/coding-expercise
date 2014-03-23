var modules = [
    kodility,
    kodility.utils,
    kodility.Challenge
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