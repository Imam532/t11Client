$(document).ready(function () {
    $('#addUserButton').click(function () {
        ajaxPost();
    }).preventDefault();

    function ajaxPost() {
        var roles = [];
        if ($('#userRoleCheck').is(':checked')) {
            roles.push("USER");
        }
        if ($('#adminRoleCheck').is(':checked')) {
            roles.push("ADMIN");
        }
        var user = {
            name: $("#addUserName").val(),
            password: $("#addPassword").val(),
            email: $("#addEmail").val(),
            address: $("#addAddress").val(),
            authorities: roles
        };

        $.ajax({
            type: "POST",
            contentType: "application/json;",
            url: "/api/new-user",
            data: JSON.stringify(user),
            dataType: 'json',
            complete: [
                function () {
                    getUsers();
                    $(document).ready(function () {
                        $("#usersTable").tab('show');
                        reset();
                    });
                }
            ]
        });
    }
});