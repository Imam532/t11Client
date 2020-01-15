$(document).ready(function () {
    $('#saveChanges').click(function () {
        editUser();
    });

    function editUser() {
        var roles = [];
        if ($('#modalUserRoleCheck').is(':checked')) {
            roles.push("USER");
        }
        if ($('#modalAdminRoleCheck').is(':checked')) {
            roles.push("ADMIN");
        }

        var formData = {
            id :  $("#modalUserId").val(),
            name: $("#modalUserName").val(),
            password: $("#modalUserPassword").val(),
            authorities: roles
        };

        $.ajax({
            type: "PUT",
            contentType: "application/json;",
            url: "/api/user/" + $("#modalUserId").val(),
            data: JSON.stringify(formData),
            dataType: 'json',
            complete: [
                function () {
                    getUsers();
                    $('#editModal').modal('hide');
                }
            ]
        });
    }
});