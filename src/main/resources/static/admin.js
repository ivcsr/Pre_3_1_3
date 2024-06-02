$(async function () {
    await userInfo();
    await getAllUsers();
})

async function userInfo() {
    fetch("/admin/getCurrentUser")
        .then((res) => res.json())
        .then((currentUser) => {
            let user = '';

            user += `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.name}</td>
            <td>${currentUser.age}</td>
            <td>${currentUser.job}</td>
            <td>${currentUser.email}</td>
            <td>${currentUser.roles.map(role => " " + role.name.substring(5))}</td> 
            </tr>`;
            document.getElementById("userInfo").innerHTML = user;

            document.getElementById("admin-header").innerHTML = `<h5>${currentUser.email} with roles: ${currentUser.roles.map(role => " " + role.name.substring(5))}</h5>`;
        })
        .catch(function(error) {
            console.error('There has been a problem with your fetch operation:', error);
                document.getElementById("userInfo").innerHTML = 'Кто то удалил текущего пользователя';
                document.getElementById("admin-header").innerHTML = '';
        });
}

async function getAllUsers() {
    fetch("/admin/getAllUsers")
        .then(function (response) {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(function (users) {
            let placeholder = document.getElementById('all-users');
            let allUsers = "";

            users.forEach(user => {

                allUsers += `<tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.age}</td>
                <td>${user.job}</td>
                <td>${user.email}</td>
                <td>${user.roles.map(role => " " + role.name.substring(5))}</td>
                <td>
                    <button type="button" class="btn btn-info" data-bs-target="#editModal" data-bs-toggle="modal" onclick="getEditModal(${user.id})">Edit</button>
                </td>
                <td>
                    <button type="button" class="btn btn-danger" data-bs-target="#deleteModal" data-bs-toggle="modal" onclick="getDeleteModal(${user.id})">Delete</button>
                </td>
            </tr>`;
            });

            placeholder.innerHTML = allUsers;
        })
        .catch(function(error) {
            console.error('There has been a problem with your fetch operation:', error);
                let placeholder = document.getElementById('all-users');
                placeholder.innerHTML = 'Список пользователй пуст';

                userInfo();
        });
}

async function getEditModal(id) {
    try {
        const response = await fetch("/admin/user/" + id, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json;charset=UTF-8'
            }
        });

        if (response.ok) {
            const userEdit = await response.json();
            document.getElementById('edit_id').value = userEdit.id;
            document.getElementById('edit_name').value = userEdit.name;
            document.getElementById('edit_age').value = userEdit.age;
            document.getElementById('edit_job').value = userEdit.job;
            document.getElementById('edit_email').value = userEdit.email;
            document.getElementById('edit_password').value = userEdit.password;
            document.getElementById('edit_role').value = userEdit.roles.map(role => role.name);

            const select = document.querySelector('#edit_role').getElementsByTagName('option');
            for (let option of select) {
                option.selected = userEdit.roles.some(role => role.name === option.value);
                if (option.selected) {
                    option.setAttribute('data-id', userEdit.roles.find(role => role.name === option.value).id);
                }
            }
        } else {
            console.error('Failed to fetch user data');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function editUser(event) {
    event.preventDefault();

    const id = document.getElementById('edit_id').value;
    const name = document.getElementById('edit_name').value;
    const age = document.getElementById('edit_age').value;
    const job = document.getElementById('edit_job').value;
    const email = document.getElementById('edit_email').value;
    const password = document.getElementById('edit_password').value;
    const roles = Array.from(document.getElementById('edit_role').selectedOptions).map(option => {
        return {
            id: option.getAttribute('data-id'),
            name: option.value
        };
    });

    const user = {
        id: id,
        name: name,
        age: age,
        job: job,
        email: email,
        password: password,
        roles: roles
    };

    try {
        const response = await fetch("/admin/edit", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            console.log('User updated successfully');
            $('#editModal').modal('hide');
            await getAllUsers();
        } else {
            console.error('Failed to update user');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function createUser(event) {
    event.preventDefault();

    const name = document.getElementById('create_name').value;
    const age = document.getElementById('create_age').value;
    const job = document.getElementById('create_job').value;
    const email = document.getElementById('create_email').value;
    const password = document.getElementById('create_password').value;
    const roles = Array.from(document.getElementById('create_role').selectedOptions).map(option => {
        return {
            id: option.getAttribute('data-id'),
            name: option.value
        };
    });

    const user = {
        name: name,
        age: age,
        job: job,
        email: email,
        password: password,
        roles: roles
    };

    try {
        const response = await fetch("/admin/new/", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify(user)
        });

        if (response.ok) {
            console.log('User create successfully');
            document.getElementById('nav-user-table-tab').click()
            await getAllUsers();
            document.createUserForm.reset()
        } else {
            console.error('Failed to update user');
        }
    } catch (error) {
        console.error('Error:', error);
    }

}

async function getDeleteModal(id) {
    fetch("/admin/user/" + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(userDelete => {
            document.getElementById('delete_id').value = userDelete.id;
            document.getElementById('delete_name').value = userDelete.name;
            document.getElementById('delete_age').value = userDelete.age;
            document.getElementById('delete_job').value = userDelete.job;
            document.getElementById('delete_email').value = userDelete.email;
            document.getElementById('delete_password').value = userDelete.password;
            document.getElementById('delete_role').value = userDelete.roles;
        });
    });
}

async function deleteUser(event) {
    event.preventDefault();

    let id = document.getElementById('delete_id').value;

    fetch("/admin/delete/" + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    })
        .then(response => {
            if (response.ok) {
                $('#deleteModal').modal('hide');
                getAllUsers().catch(error => {
                    if (error.status === 404) {
                        console.error('Error getting users:', error);
                    }
                });
            } else {
                console.error('Error deleting user:', response.statusText);
            }
        })
        .catch(error => {
            console.error('Error deleting user:', error);
        });
}

document.addEventListener("DOMContentLoaded", function() {
    fetchRoles();
});

async function fetchRoles() {
    try {
        const response = await fetch("/admin/getRoles");
        if (response.ok) {
            const roles = await response.json();
            populateRolesSelect(document.getElementById('edit_role'), roles);
            populateRolesSelect(document.getElementById('create_role'), roles);
        } else {
            console.error('Failed to fetch roles');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

function populateRolesSelect(selectElement, roles) {
    roles.forEach(role => {
        const option = document.createElement('option');
        option.value = role.name;
        option.text = role.name.replace('ROLE_', '');
        option.setAttribute('data-id', role.id);
        selectElement.appendChild(option);
    });
}