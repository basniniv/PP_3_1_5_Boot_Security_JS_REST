$(async function () {
    await allUsers();
    await newUser();
    deleteUser();
    editCurrentUser();

    const editModal = new bootstrap.Modal(document.getElementById('editUserModal'))
    const deleteModal = new bootstrap.Modal(document.getElementById('delete'))


    $(document).on('click', '#buttonEdit', function () {
        const userId = $(this).data('id');
        console.log("Открытие модального окна юзера", userId)
        viewEditModal(userId)
        editModal.show();
    })

    $(document).on('click', '#buttonDelete', function () {
        const userId = $(this).data('id')
        console.log("ID пользователя", userId);
        viewDeleteModal(userId);
        deleteModal.show();
    })
    $('a[data-toggle="pill"]').on('click', function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    const links = document.querySelectorAll(".nav-link");
    const alluser = document.getElementById("users_table");
    const adduser = document.getElementById('add_user')

    links.forEach(link => {
        link.addEventListener("click", function (event) {
            event.preventDefault();

            // Удаление класса "active" у всех ссылок
            links.forEach(l => l.classList.remove("active"));

            // Добавление класса "active" к нажатой ссылке
            this.classList.add("active");

            // Скрытие/показа блоков
            if (this.id === "usertable") {
                alluser.style.display = "block";
                adduser.style.display = "none";
            } else if (this.id === "newuser") {
                alluser.style.display = "none";
                adduser.style.display = "block";
            }
        });
    });

})

async function allUsers() {
    const table = $('#bodyAllUserTable');
    table.empty()
    fetch("http://localhost:8080/api/admin/users")
        .then(r => r.json())
        .then(data => {
            data.forEach(user => {
                let users = `$(
                    <tr>
                         <td>${user.id}</td>
                         <td>${user.username}</td>
                         <td>${user.lastName}</td>
                         <td>${user.age}</td>
                         <td>${user.email}</td>
                         <td>${user.roles.map(role => " " + role.rolename.replace('ROLE_',''))}</td>
                         <td>
                         <button type="button" class="btn btn-info" data-toggle="modal" id="buttonEdit" data-action="edit" data-id="${user.id}" data-target="#editUserModal">Edit</button>
                         </td>
                         <td>
                         <button type="button" class="btn btn-danger" data-toggle="modal" id="buttonDelete" data-action="delete" data-id="${user.id}" data-target="#delete">Delete</button>
                         </td>
                    </tr>)`;
                table.append(users);
            })
        })
}

async function newUser() {
    await fetch("http://localhost:8080/api/admin/roles")
        .then(r => r.json())
        .then(roles => {
            roles.forEach((role => {
                let element = document.createElement("option");
                element.text = role.rolename.replace("ROLE_", "");
                element.value = role.id;
                $('#rolesNewUser')[0].appendChild(element);
            }))
        })
    const formAddNewUser = document.forms["formAddNewUser"];
    formAddNewUser.addEventListener('submit', function (event) {
        event.preventDefault();
        let rolesNewUser = [];
        for (let i = 0; i < formAddNewUser.roles.options.length; i++) {
            if (formAddNewUser.roles.options[i].selected) rolesNewUser.push({
                id: parseInt(formAddNewUser.roles.options[i].value, 10),
                name: formAddNewUser.roles.options[i].name
            })
        }
        fetch("http://localhost:8080/api/admin/users/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: formAddNewUser.username.value,
                lastName: formAddNewUser.lastName.value,
                age: parseInt(formAddNewUser.age.value, 10),
                email: formAddNewUser.email.value,
                password: formAddNewUser.password.value,
                roles: rolesNewUser
            })
        }).then(() => {
            formAddNewUser.reset();
            document.getElementById('usertable').click();
            allUsers()
        })
            .catch((error) => {
                alert(error)
            })
    })
}


async function getUser(id) {
    let url = "http://localhost:8080/api/admin/users/" + id;
    let response = await fetch(url);
    return await response.json();
}

function editCurrentUser() {
    const editForm = document.forms['formEditUser'];
    editForm.addEventListener("submit", function (event) {
        console.log('Нажатие на кнопку изменений')
        event.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.roles.options.length; i++) {
            if (editForm.roles.options[i].selected) editUserRoles.push({
                id: parseInt(editForm.roles.options[i].value, 10),
                name: editForm.roles.options[i].name,
            })
        }
        console.log('Отправка формы')
        fetch('http://localhost:8080/api/admin/users/', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: parseInt(editForm.id.value, 10),
                username: editForm.username.value,
                lastName: editForm.lastName.value,
                age: parseInt(editForm.age.value, 10),
                email: editForm.email.value,
                password: editForm.password.value,
                roles: editUserRoles
            })
        }).then(() => {
            $('#editFormCloseButton').click();
            allUsers();
        })
            .catch((error) => {
                alert(error)
            })
    })

}

async function viewEditModal(id) {
    let userEdit = await getUser(id);
    let form = document.forms['formEditUser'];
    form.id.value = userEdit.id;
    form.username.value = userEdit.username;
    form.lastName.value = userEdit.lastName;
    form.age.value = userEdit.age;
    form.email.value = userEdit.email;
    form.password.value = userEdit.password;
    $('#editUserRole').empty();
    await fetch("http://localhost:8080/api/admin/roles/")
        .then(r => r.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < userEdit.roles.length; i++) {
                    if (userEdit.roles[i].rolename === role.rolename) {
                        selectedRole = true;
                        break;
                    }
                }
                console.log(selectedRole);
                let element = document.createElement("option");
                element.text = role.rolename.replace('ROLE_', '');
                element.value = role.id;
                if (selectedRole) element.selected = true;
                $('#editUserRole')[0].appendChild(element);
            });
        })
        .catch((error) => {
            alert(error);
        });

}


async function viewDeleteModal(id) {
    let userDelete = await getUser(id);
    let formDelete = document.forms["formDeleteUser"];
    formDelete.id.value = userDelete.id;
    formDelete.username.value = userDelete.username;
    formDelete.lastName.value = userDelete.lastName;
    formDelete.age.value = userDelete.age;
    formDelete.email.value = userDelete.email;
    $('#deleteRolesUser').empty();
    await fetch("http://localhost:8080/api/admin/roles/")
        .then(r => r.json())
        .then(roles => {
            roles.forEach(role => {
                let selectedRole = false;
                for (let i = 0; i < userDelete.roles.length; i++) {
                    if (userDelete.roles[i].rolename === role.rolename) {
                        selectedRole = true;
                        break;
                    }
                    console.log(selectedRole)
                }
                let element = document.createElement("option");
                element.text = role.rolename.replace('ROLE_', '');
                element.value = role.id;
                if (selectedRole) element.selected = true;
                $('#deleteRolesUser')[0].appendChild(element);
            })
        })
        .catch((error) => {
            alert(error);
        })
}
function deleteUser() {
    const deleteForm = document.forms['formDeleteUser'];
    deleteForm.addEventListener('submit', event => {
        event.preventDefault();
        fetch('http://localhost:8080/api/admin/users/' + deleteForm.id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(() => {
                $('#deleteFormCloseButton').click();
                allUsers();
            })
            .catch((error) => {
                alert(error);
            })
    })

}