let dynamic = document.querySelector('.dynamic-table');

const getUsersList = () => {
    fetch('/api/users')
        .then(response=>response.json())

        .then(users => {
            let li='';
            users.forEach(user => {
                li +=`
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.password}</td>
                    <td>${user.roles.map(role=>role.role)}</td>
                    <td><button class="btn btn-primary" onclick="editUser(${user.id})" data-toggle="modal" data-target="#edit-user-modal" data-action="edit" data-user-id="${user.id}" type="button">Edit</button></td>
                    <td><button onclick="deleteUser(${user.id})" class="btn btn-danger"  data-action="delete" data-user-id="${user.id}" type="button">Delete</button></td>
                </tr>`
            });

            $("#dynamic-table").html(li);
        });
};

getUsersList();

function deleteUser(id) {

        fetch(`/rest/users/id/` + id, {
            method: 'DELETE'
        })
            .then(response => {
                getUsersList();
            });
}

function editUser(id) {
    fetch(`/api/users/id/` + id)
        .then(response => response.json())
        .then(user => {
            const formElements = document.forms['change-user'].elements;

            for (let roles of user.roles.map(role=>role.role)) {
                if (roles === "ADMIN") {
                    $("#change-admin").prop("checked", true);
                } else {
                    $("#change-admin").prop("checked", false);
                } if (roles === "USER") {
                    $("#change-user").prop("checked", true);
                } else {
                    $("#change-user").prop("checked", false);
                }
            }

            const name = formElements.name,
                id = formElements['user-id'];

            name.value = user.name;
            id.value = user.id;

        })
        .then(response => {
            getUsersList();
        });
}

const changeUserForm = document.forms['change-user'];

changeUserForm.addEventListener('submit', event => {
    event.preventDefault();

    const body = new FormData(changeUserForm);

    let roles = [];
    if ($("#change-admin").prop("checked")) {
        roles.unshift("ADMIN");
    }
    if ($("#change-user").prop("checked")) {
        roles.unshift("USER");
    }

    fetch('/api/users/id', {
        method: 'PUT',
        body: JSON.stringify({
            ...Object.fromEntries(body),
            roles
        }),
        headers: {
            'content-type': 'application/json'
        }
    }).then(response => {
        $('#edit-user-modal').modal('hide');
        getUsersList();
    });
});

const newUserForm = document.forms.user;

newUserForm.addEventListener('submit', event => {
    event.preventDefault();

    const body = new FormData(newUserForm);

    let roles = [];
    if ($("#admin").prop("checked")) {
        roles.unshift("ADMIN");
    }
    if ($("#user").prop("checked")) {
        roles.unshift("USER");
    }

    fetch('/rest/users/', {
        method: 'POST',
        body: JSON.stringify({
            ...Object.fromEntries(body),
            roles
        }),
        headers: {
            'content-type': 'application/json'
        }
    }).then(response => {
        $('#home-tab').tab('show');
        getUsersList();
    });
});