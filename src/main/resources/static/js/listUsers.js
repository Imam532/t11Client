$(document).ready(function () {
    getUsers();
});

function getUsers() {
    $.ajax({
        url: "/api/all-users",
        method: "GET",
        dataType: "json",
        success: function (data) {
            var tableBody = $('#usersTable tbody');
            tableBody.empty();
            $(data).each(function (i, user) {
                var stringRoles = "";
                $(user.authorities).each(function (i, role) {
                    stringRoles += role.valueOf() + "</br>";
                });
                tableBody.append(`<tr>
                    <td>${user.id}</td> 
                    <td>${user.name}</td> 
                    <td>${user.password}</td>
                    <td>${user.email}</td>
                    <td>${user.address}</td>
                    <td><i>${stringRoles}</i></td>
                    <td><button id="userButton" class="btn btn-info" role="button" data-toggle="modal" 
                    data-target="#editModal" onclick = "getModal(${user.id})"> 
                    Update 
                    </button></td>
                    <td><button  id="deleteButton" class="btn btn-danger" role="button" onclick = "deleteUser(${user.id})"> 
                    Delete 
                    </button></td> 
                    </tr>`);
            })
        },
        error: function (error) {
            alert(error);
        }
    })
}