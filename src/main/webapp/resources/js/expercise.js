expercise = {

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