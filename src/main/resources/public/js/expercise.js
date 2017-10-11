expercise = {

    assistant: new Assistant(),

    constructor: function () {
        this.assistant.init({
            image: expercise.utils.urlFor('img/assistant-robot.svg'),
            width: '100px',
            height: '166px'
        });
    },

    bindEvents: function () {
        this.configureTooltips();
    },

    configureTooltips: function () {
        var $tooltips = $('[data-toggle="tooltip"]');
        $tooltips.tooltip();

        var $showOnLoadTooltips = $tooltips.filter('.showOnLoad');
        $showOnLoadTooltips.tooltip('show');
        setTimeout(function () {
            $showOnLoadTooltips.tooltip('hide');
        }, 2000);
    }

};