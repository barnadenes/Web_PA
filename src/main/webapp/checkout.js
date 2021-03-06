function onCheckoutButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onCheckoutResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'cart');
    xhr.send();
}

function onCheckoutResponse() {
    removeAllChildren(unusedDivEls);
    if(this.status === OK) {
        const cartItems = JSON.parse(this.responseText);
        createCartBody(cartItems);
        showContents(['site-header', 'carousel', 'checkout-body', 'nav-checkout-button']);
    }
    else {
        emptyCartResponse();
        showContents(['site-header', 'carousel', 'checkout-body', 'nav-checkout-button']);
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
        deleteButtonEl.dataset.deleteId = item.id;
        deleteButtonEl.textContent = 'X';
        deleteButtonEl.title = 'Cancel Order';
        deleteButtonEl.addEventListener('click', onDeleteButtonClicked);
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

        const buyOrderButtonEl = document.createElement('button');
        buyOrderButtonEl.setAttribute('class', 'buy-order-button');
        buyOrderButtonEl.textContent = 'Order';
        buyOrderButtonEl.dataset.orderId = item.id;
        buyOrderButtonEl.addEventListener('click', onBuyOrderClicked);
        itemDivEl.appendChild(buyOrderButtonEl);

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

    return footerDivEl;
}

function onDeleteButtonClicked() {
    const deleteButtonId = this.dataset.deleteId;

    const param = new URLSearchParams();
    param.append('delete_id', deleteButtonId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onDeleteButtonResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('DELETE', 'cart?' + param);
    xhr.send();
}

function onDeleteButtonResponse() {
    if(this.status === OK ){
        onCheckoutButtonClicked();
        setTimeout(function(){
            newMessage(carouselContentDivEl, 'info', 'Item removed');
        }, 1000);
    } else {
        onOtherResponse(carouselContentDivEl ,this);
    }
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
    errorDivEl.textContent = 'Empty Cart';
    errorDivEl.setAttribute('class', 'error');
    errorDivEl.style.fontWeight = '900';
    checkoutContentDivEl.appendChild(errorDivEl);
}
