var coreModules = [
    expercise,
    expercise.utils,
    expercise.Avatar,
    expercise.Header,
    expercise.Locale
];

$(function () {
    initCoreModules();
    initPageSpecificModules();
});

function initCoreModules() {
    coreModules.forEach(function (eachModule) {
        initModule(eachModule);
    });
}

function initPageSpecificModules() {
    var $moduleNamesHolder = $('#javaScriptModules');
    if ($moduleNamesHolder.length) {
        var pageSpecificModules = $moduleNamesHolder.val().split(" ");
        pageSpecificModules.forEach(function (eachModule) {
            initModule(expercise[eachModule]);
        });
    }
}

function initModule(module) {
    module.constructor && module.constructor();
    module.bindEvents && module.bindEvents();
}