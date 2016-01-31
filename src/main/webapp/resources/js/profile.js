expercise.Profile = {

    bindEvents: function () {
        $('#avatarsCollapse a').click(this.selectAvatar);
    },

    selectAvatar: function (event) {
        var avatarType = $(event.target).closest('a').data('avatar-type');
        var url = expercise.utils.urlFor("user/selectAvatar/" + avatarType);
        expercise.utils.go(url);
    }

};