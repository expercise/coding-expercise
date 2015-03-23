expercise.Profile = {

    bindEvents: function () {
        $('#avatarsCollapse a').click(this.selectAvatar);
    },

    selectAvatar: function (event) {
        var avatarType = $(event.target).closest('.avatar').data('avatar-type');
        var url = expercise.utils.urlFor("profile/selectAvatar/" + avatarType);
        expercise.utils.go(url);
    }

};