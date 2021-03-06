const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let loginContentDivEl;
let loginFormDivEl;
let loginButtonContentDivEl;
let itemsContentDivEl;
let itemContentDivEl;
let checkoutContentDivEl;
let checkoutButtonEl;
let orderContentDivEl;
let orderButtonEl;
let uploadContentDivEl;
let logoutButtonContentDivEl;
let headerContentDivEl;
let carouselContentDivEl;
let registerButtonDivEl;
let userInfoButtonDivEl;
let unusedDivEls;

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

    targetEl.append(pEl);
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
    for(let i = 0; i < el.length; i++) {
        while (el[i].firstChild) {
            el[i].removeChild(el[i].firstChild);
        }
    }
}

function onNetworkError() {
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
    loginFormDivEl = document.getElementById('error-info-hr');
    headerContentDivEl = document.getElementById('site-header');
    carouselContentDivEl = document.getElementById('carousel');
    itemsContentDivEl = document.getElementById('shop-body');
    itemContentDivEl = document.getElementById('item-view-body');
    checkoutContentDivEl = document.getElementById('checkout-body');
    orderContentDivEl = document.getElementById('order-body');
    loginButtonContentDivEl = document.getElementById('login-button');
    userInfoButtonDivEl = document.getElementById('update-user');
    registerButtonDivEl = document.getElementById('register-button');
    orderButtonEl = document.getElementById('nav-order-button');
    uploadContentDivEl = document.getElementById('admin-add');
    checkoutButtonEl = document.getElementById('nav-checkout-button');

    unusedDivEls = [itemContentDivEl, itemsContentDivEl, orderContentDivEl, checkoutContentDivEl];
    userInfoButtonDivEl.addEventListener('click', onUIUpdateButtonClicked);
    registerButtonDivEl.addEventListener('click', onRegisterButtonClicked);
    orderButtonEl.addEventListener('click', onOrderButtonClicked);
    checkoutButtonEl.addEventListener('click', onCheckoutButtonClicked);

    const loginButtonEl = document.getElementById('login-button');
    loginButtonEl.addEventListener('click', onLoginButtonClicked);

    const logoutButtonEl = document.getElementById('nav-logout-button');
    logoutButtonEl.addEventListener('click', onLogoutButtonClicked);

    const itemsButtonEl = document.getElementById('nav-shop-button');
    itemsButtonEl.addEventListener('click', onShopButtonClicked);

    const userButtonEl = document.getElementById('nav-user-button');
    userButtonEl.addEventListener('click', onUserInfoClicked);

    const addItemButtonEl = document.getElementById('nav-add-button');
    addItemButtonEl.addEventListener('click', onUploadMenuClicked);

    const createPostButtonEl = document.getElementById('create-post');
    createPostButtonEl.addEventListener('click', onUploadButtonClicked);

    if (hasAuthorization()) {
        onShopButtonClicked(getAuthorization());
    }
}

document.addEventListener('DOMContentLoaded', onLoad);
