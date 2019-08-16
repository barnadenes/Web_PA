function onRegisterButtonClicked() {
    const registerFormEl = document.forms['register-form'];
    const name = registerFormEl.querySelector('input[id="name"]').value;
    const password = registerFormEl.querySelector('input[id="password"]').value;
    const email = registerFormEl.querySelector('input[id="email"]').value;
    const country = registerFormEl.querySelector('input[id="country"]').value;
    const city = registerFormEl.querySelector('input[id="city"]').value;
    const street = registerFormEl.querySelector('input[id="street"]').value;
    const zip = registerFormEl.querySelector('input[id="zip"]').value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('password', password);
    params.append('email', email);
    params.append('country', country);
    params.append('city', city);
    params.append('street', street);
    params.append('zip', zip);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onLoginResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'register');
    xhr.send(params);
}

