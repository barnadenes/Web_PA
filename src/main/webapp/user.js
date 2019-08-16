function createUserInfo(newUser) {
    clearMessages();

    const userFormEl = document.forms['user-form'];

    const name = userFormEl.querySelector('input[name="name"]');
    name.value = newUser.name;

    const email = userFormEl.querySelector('input[name="email"]');
    email.value = newUser.email;

    const country = userFormEl.querySelector('input[name="country"]');
    country.value = newUser.country;

    const city = userFormEl.querySelector('input[name="city"]');
    city.value = newUser.city;

    const street = userFormEl.querySelector('input[name="street"]');
    street.value = newUser.street;

    const zip = userFormEl.querySelector('input[name="zip"]');
    zip.value = newUser.zipcode;

    const money = userFormEl.querySelector('input[name="money"]');
    money.value = newUser.money;
}

function onUserInfoResponse() {
    removeAllChildren(itemsContentDivEl);
    removeAllChildren(itemContentDivEl);
    removeAllChildren(checkoutContentDivEl);

    if(this.status === OK) {
        if(getAuthorization().status === true){
            showContents(['site-header', 'carousel', 'user-info-body', 'nav-add-button', 'nav-order-button']);
        }
        else{
            showContents(['site-header', 'carousel', 'user-info-body', 'nav-checkout-button']);
        }
        const newUser = JSON.parse(this.responseText);
        createUserInfo(newUser);
    } else {
        onOtherResponse(userInfoButtonDivEl, this);
    }
}

function onUserInfoClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUserInfoResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'userInfo');
    xhr.send();
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
        newMessage(userInfoButtonDivEl,'info','UserInfo Updated!');
    }
    else {
        onOtherResponse(userInfoButtonDivEl, this);
    }
}
