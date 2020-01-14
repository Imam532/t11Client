function deleteUser(id) {
    $.ajax({
        url: '/api/user/' + id,
        method: 'DELETE',
        success: function () {
            getUsers();
        },
        error: function (error) {
            alert(error);
        }
    })
}