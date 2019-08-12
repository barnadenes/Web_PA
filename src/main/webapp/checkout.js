function onCheckoutButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCheckoutResponse);
    xhr.open('GET', 'cart');
    xhr.send();
}

function onCheckoutResponse() {
    if(this.status === OK) {
        const cartItems = JSON.parse(this.responseText);
        createCartBody(cartItems);
        showContents(['site-header', 'carousel', 'checkout-body']);
    }
    else {
        emptyCartResponse();
        showContents(['site-header', 'carousel', 'checkout-body']);
    }
}

function createCartHead() {
    const headerEl = document.createElement('h1');
    headerEl.setAttribute('class', 'checkout-h1');
    headerEl.textContent = 'Checkout';

    return headerEl;
}

function createCartBody(cartContent) {
    clearMessages();
    checkoutContentDivEl.append(createCartHead());

    for(let i = 0; i < cartContent.length; i++) {
        const item = cartContent[i];

        const itemDivEl = document.createElement('div');
        itemDivEl.setAttribute('class', 'checkout-item');

        const deleteButtonEl = document.createElement('button');
        deleteButtonEl.setAttribute('class', 'cancel-order-button');
        deleteButtonEl.textContent = 'X';
        itemDivEl.appendChild(deleteButtonEl);

        const titleEl = document.createElement('p');
        titleEl.innerHTML = `<b>Title: </b> ${item.bookTitle}`;
        itemDivEl.appendChild(titleEl);

        const buyerEl = document.createElement('p');
        buyerEl.innerHTML = `<b>Buyer: </b> ${item.buyer}`;
        itemDivEl.appendChild(buyerEl);

        const priceEl = document.createElement('p');
        priceEl.innerHTML = `<b>Price: </b> ${item.price}`;
        itemDivEl.appendChild(priceEl);

        checkoutContentDivEl.appendChild(itemDivEl);
    }
    checkoutContentDivEl.appendChild(createCartFooter(cartContent));
}

function createCartFooter(cartContent) {
    const footerDivEl = document.createElement('div');
    footerDivEl.setAttribute('class', 'checkout-footer');

    const checkoutPriceEl = document.createElement('p');
    checkoutPriceEl.innerHTML = '<b>Total Price: </b>' + getOrderPrice(cartContent) + ' USD';
    footerDivEl.appendChild(checkoutPriceEl);

    const userMoneyEl = document.createElement('p');
    userMoneyEl.innerHTML = '<b>Money: </b>' + getMoneyFromLocalStorage() + ' USD';
    footerDivEl.appendChild(userMoneyEl);

    const buyOrderButtonEl = document.createElement('button');
    buyOrderButtonEl.setAttribute('class', 'buy-order-button');
    buyOrderButtonEl.textContent = 'Order';
    footerDivEl.appendChild(buyOrderButtonEl);

    return footerDivEl;
}

function getMoneyFromLocalStorage() {
    const localUserEl = JSON.parse(localStorage.getItem('user'));
    return localUserEl.money;
}

function getOrderPrice(cartContent) {
    let checkoutPriceNum = 0;

    for (let i = 0; i < cartContent.length; i++) {
        checkoutPriceNum += cartContent[i].price;
    }
    return checkoutPriceNum;
}

function emptyCartResponse() {
    const errorDivEl = document.createElement('div');
    errorDivEl.textContent = this;
    errorDivEl.setAttribute('class', 'error');
    errorDivEl.style.fontWeight = '900';
    checkoutContentDivEl.appendChild(errorDivEl);
}

// <h1 class="checkout-h1">Checkout</h1>
// <div class="checkout-item">
//     <button class="cancel-order-button">X</button>
//     <p><b>Title: </b>Harry Potter</p>
//     <p><b>Buyer: </b>Nyukk</p>
//     <p><b>Price: </b>20$</p>
// </div>
// <div class="checkout-footer">
//     <p class="checkout-price"><b>Total Price: </b></p>
//     <button class="buy-order-button">Order</button>
// </div>



