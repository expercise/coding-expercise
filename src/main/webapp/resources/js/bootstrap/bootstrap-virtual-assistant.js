/*!
 * Bootstrap Virtual Assistant v0.1.0 (http://...)
 * Copyright (C) 2015 Ufuk Uzun (ufukuzun.ce@gmail.com), Batuhan Bayrakçı (batuhanbayrakci@gmail.com)
 * Licensed under MIT (https://github.com/expercise/.../LICENSE.md)
 */

/* TODO ufuk: move to different repo after incubation (with ../style/bootstrap-virtual-assistant.css file) */

/**
 * TODO ufuk:
 * - speaking animations
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

    this.$container.css('width', options.width);
    this.$container.css('height', options.height);
    this.$container.css('background', 'url(' + options.image + ') no-repeat');
    this.$container.css('background-size', '100%');

    $(document.body).append(this.$container);

    this.$container.draggable({
        containment: 'body',
        scroll: false
    });
};

Assistant.prototype.show = function (callback) {
    this.$container.fadeIn('slow', function () {
        callback && callback();
    });
};

Assistant.prototype.hide = function (timeBeforeHide) {
    var that = this;
    setTimeout(function () {
        that.$container.fadeOut('slow');
    }, timeBeforeHide || 0);
};

Assistant.prototype.speak = function (message) {
    var $content = this.$container.find('.virtual-assistant-content');
    $content.popover({
        content: message,
        placement: 'top',
        html: true
    });

    $content.popover('show');

    var $popover = this.$container.find('.popover');

    // TODO ufuk: refactor static values, calculate them
    // FIXME ufuk: position wrong for short messages

    var popoverWidth = parseInt($popover.css('width'), 10)

    $popover.css('left', (parseInt($popover.css('left'), 10) - 150 - parseInt(popoverWidth / 2)) + 'px');
    $popover.css('margin-right', '15px');
    $popover.offset({top: parseFloat($('.virtual-assistant').offset().top) - $popover.height() - 20});
    $popover.find('.arrow').css('left', '88%');
};