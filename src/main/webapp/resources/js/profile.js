expercise.Profile = {

    bindEvents: function () {
        $('#avatarsCollapse a').click(this.selectAvatar);

        $('#connectTwitterAccount').click(function (e) {
            e.preventDefault();

            $(e.target).parents('form').submit();
        });
    },

    selectAvatar: function (event) {
        var avatarType = $(event.target).closest('a').data('avatar-type');
        var url = expercise.utils.urlFor("user/selectAvatar/" + avatarType);
        expercise.utils.go(url);
    }

};