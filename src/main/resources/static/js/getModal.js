function getModal(id) {
    $.ajax({
        url: '/api/user/' + id,
        method: "GET",
        dataType: "json",
        success: function (data) {
            $('#modalUserId').val(data.id);
            $('#modalUserName').val(data.name);
            /*$('#modalUserPassword').val(data.password);*/
            $('#modalUserEmail').val(data.email);
            $('#modalUserAddress').val(data.address);
            for (let role of data.authorities) {
                if (role === "ADMIN") {
                    document.getElementById("modalAdminRoleCheck").checked = true;
                    break;
                } else {
                    document.getElementById("modalAdminRoleCheck").checked = false;
                };
            }
        },
        error: function (error) {
            alert(error);
        }
    })
}