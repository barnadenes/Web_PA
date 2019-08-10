const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let loginButtonContentDivEl;
let itemsContentDivEl;
let logoutButtonContentDivEl;
let headerContentDivEl;
let carouselContentDivEl;
let registerButtonDivEl;
let userInfoButtonDivEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function showContents(ids) {
    const contentEls = document.getElementsByClassName('content');
    for (let i = 0; i < contentEls.length; i++) {
        const contentEl = contentEls[i];
        if (ids.includes(contentEl.id)) {
            contentEl.classList.remove('hidden');
        } else {
            contentEl.classList.add('hidden');
        }
    }
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloaing the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function hasAuthorization() {
    return localStorage.getItem('user') !== null;
}

function setAuthorization(user) {
    return localStorage.setItem('user', JSON.stringify(user));
}

function getAuthorization() {
    return JSON.parse(localStorage.getItem('user'));
}

function setUnauthorized() {
    return localStorage.removeItem('user');
}

function onLoad() {
    localStorage.clear();
    loginContentDivEl = document.getElementById('login-register-container');
    headerContentDivEl = document.getElementById('site-header');
    carouselContentDivEl = document.getElementById('carousel');
    itemsContentDivEl = document.getElementById('shop-body');
    loginButtonContentDivEl = document.getElementById('login-button');
    userInfoButtonDivEl = document.getElementById('update-user');
    registerButtonDivEl = document.getElementById('register-button');

    userInfoButtonDivEl.addEventListener('click', onUIUpdateButtonClicked);
    registerButtonDivEl.addEventListener('click', onRegisterButtonClicked);

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const logoutButtonEl = document.getElementById('nav-logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);

    const itemsButtonEl = document.getElementById('nav-shop-button');
    itemsButtonEl.addEventListener('click', onShopButtonClicked);

    const userButtonEl = document.getElementById('nav-user-button');
    userButtonEl.addEventListener('click', onUserInfoButtonClicked);

    const checkoutButtonEl = document.getElementById('nav-checkout-button');
    checkoutButtonEl.addEventListener('click', onCheckoutButtonClicked);

    // const tempEl = document.getElementById('1');
    // tempEl.addEventListener('click', onItemClicked);

    const backButtonEl = document.getElementById('back-button');
    backButtonEl.addEventListener('click', onBackButtonClicked);

    // const addItemButtonEl = document.getElementById('nav-add-button');
    // addItemButtonEl.addEventListener('click', onAddItemButtonClicked);

    if (hasAuthorization()) {
        onShopButtonClicked(getAuthorization());
    }
}

document.addEventListener('DOMContentLoaded', onLoad);
