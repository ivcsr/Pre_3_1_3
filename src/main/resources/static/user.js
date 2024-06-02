$(async function () {
    await userInfo();
})

async function userInfo() {
    fetch("/user/getCurrentUser")
        .then((res) => res.json())
        .then((currentUser) => {
            console.log(currentUser);
            let temp = '';

            temp += `<tr>
            <td>${currentUser.id}</td>
            <td>${currentUser.name}</td>
            <td>${currentUser.age}</td>
            <td>${currentUser.job}</td>
            <td>${currentUser.email}</td>
            <td>${currentUser.roles.map(role => " " + role.name.substring(5))}</td> 
            </tr>`;
            document.getElementById("userInfo").innerHTML = temp;

            document.getElementById("user-header").innerHTML = `<h5>${currentUser.email} with roles: ${currentUser.roles.map(role => " " + role.name.substring(5))}</h5>`;
        });
}

