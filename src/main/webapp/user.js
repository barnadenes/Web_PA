function onUserInfoButtonClicked() {
    showContents(['site-header', 'carousel', 'user-info-body']);
    const user = getAuthorization();
    const userFormEl = document.forms['user-form'];

    const name = userFormEl.querySelector('input[name="name"]');
    name.value = user.name;

    const email = userFormEl.querySelector('input[name="email"]');
    email.value = user.email;

    const country = userFormEl.querySelector('input[name="country"]');
    country.value = user.country;

    const city = userFormEl.querySelector('input[name="city"]');
    city.value = user.city;

    const street = userFormEl.querySelector('input[name="street"]');
    street.value = user.street;

    const zip = userFormEl.querySelector('input[name="zip"]');
    zip.value = user.zipcode;

    const money = userFormEl.querySelector('input[name="money"]');
    money.value = user.money;
}

function onUIUpdateButtonClicked() {
    const userFormEl = document.forms['user-form'];

    const name = userFormEl.querySelector('input[name="name"]').value;
    const email = userFormEl.querySelector('input[name="email"]').value;
    const country = userFormEl.querySelector('input[name="country"]').value;
    const city = userFormEl.querySelector('input[name="city"]').value;
    const street = userFormEl.querySelector('input[name="street"]').value;
    const zip = userFormEl.querySelector('input[name="zip"]').value;
    const money = userFormEl.querySelector('input[name="money"]').value;

    const params = new URLSearchParams();
    params.append('name', name);
    params.append('email', email);
    params.append('country', country);
    params.append('city', city);
    params.append('street', street);
    params.append('zip', zip);
    params.append('money', money);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUserResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'userInfo?' + params.toString());
    xhr.send();
}

function onUserResponse() {
    if (this.status === OK) {
        setUnauthorized();

        const user = JSON.parse(this.responseText);
        setAuthorization(user);
        onUserInfoButtonClicked();
        newMessage(userInfoButtonDivEl,'info','UserInfo Updated!');
    }
    else {
        onOtherResponse(userInfoButtonDivEl, this);
    }
}
