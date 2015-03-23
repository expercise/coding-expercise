expercise.Avatar = {

    constructor: function () {
        this.initAvatars();
    },

    initAvatars: function () {
        $('span.avatar').each(function (iterationCount, element) {
            var $eachAvatar = $(element);
            for (var i = 1; i <= 40; i++) {
                $eachAvatar.append("<span class='path" + i + "'></span>");
            }
        });
    }

};