function checkPassword() {
    let password = document.querySelector('#password').value,
        confirmPassword = document.querySelector('#passwordRepeat').value;

    if (password === "") {
        alert("Field cannot be empty.");
    } else if (password !== confirmPassword) {
        alert("Password didn't match try again");
    } else {
        alert("Password match")
    }
}