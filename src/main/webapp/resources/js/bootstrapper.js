var coreModules = [
    kodility,
    kodility.utils,
    kodility.Header,
    kodility.Locale
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
            initModule(kodility[eachModule]);
        });
    }
}

function initModule(module) {
    module.constructor && module.constructor();
    module.bindEvents && module.bindEvents();
}