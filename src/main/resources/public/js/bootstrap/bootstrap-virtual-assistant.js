/*!
 * Bootstrap Virtual Assistant v0.1.0 (http://...)
 * Copyright (C) 2015 Ufuk Uzun (ufukuzun.ce@gmail.com), Batuhan Bayrakçı (batuhanbayrakci@gmail.com)
 * Licensed under MIT (https://github.com/expercise/.../LICENSE.md)
 */

/* TODO ufuk: move to different repo after incubation (with ../style/bootstrap-virtual-assistant.css file) */

/**
 * TODO ufuk:
 * - speaking animations (SVG animation: http://codepen.io/jlegosama/pen/LoBsD) + https://www.google.com.tr/search?q=type:svg+robot+animation
 * - paging for speech
 * - buttons with callbacks
 * - dismissible speak balloons
 * - multiple speakings
 * - multiple assistants
 * - ...
 */

/**
 * @constructor
 */
var Assistant = function () {
    this.defaultOptions = {
        // TODO ufuk: consider default options
    }
};

// TODO ufuk: extend option mechanism, get {top, left, position-class, z-index, animation... } from options
Assistant.prototype.init = function (options) {
    this.$container = $('<div><div class="virtual-assistant-content"></div></div>').hide();

    this.$container.addClass('virtual-assistant');
    this.$container.addClass('bottom-left');
    this.$container.addClass('hidden-xs');

    this.$container.css('width', options.width);
    this.$container.css('height', options.height);
    this.$container.css('background', 'url(' + options.image + ') no-repeat');
    this.$container.css('background-size', '100%');

    $(document.body).append(this.$container);
};

Assistant.prototype.show = function (callback) {
    if (this.$container.is(':hidden')) {
        this.$container.fadeIn('fast', function () {
            callback && callback();
        });
    }
};

Assistant.prototype.hide = function (timeBeforeHide) {
    var that = this;
    setTimeout(function () {
        that.$container.fadeOut('fast');
        that.silent();
    }, (timeBeforeHide || 0) * 1000);
};

Assistant.prototype.silent = function () {
    if (hopscotch.isActive) {
        hopscotch.endTour(false);
    }
};

Assistant.prototype.speak = function (options) {
    var that = this;

    that.silent();
    that.show();

    var tourOptions = {
        id: 'captain-coding-speaking',
        steps: [
            {
                title: options.title,
                content: options.message,
                target: document.querySelector('.virtual-assistant'),
                fixedElement: true,
                placement: 'top',
                arrowOffset: 144, // TODO ufuk: make calculated
                xOffset: 'center',
                showNextButton: false,
                showCTAButton: true,
                ctaLabel: options.buttonText,
                onCTA: function () {
                    if (options.dontSilentOnButtonClick != true) {
                        that.silent();
                    }
                    options.onButtonClick && options.onButtonClick();
                }
            }
        ],
        onEnd: function () {
            options.onEnd && options.onEnd();
        },
        onClose: function () {
            that.hide();
        }
    };

    hopscotch.startTour(tourOptions);
};
