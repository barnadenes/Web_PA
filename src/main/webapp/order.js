function onBuyOrderClicked() {
    const buyOrderId = this.dataset.orderId;

    const param = new URLSearchParams();
    param.append('item_id' ,buyOrderId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onBuyOrderResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('PUT', 'orders?' + param);
    xhr.send();
}

function onBuyOrderResponse() {
    if(this.status === OK) {
        newMessage(carouselContentDivEl, 'info', 'Purchase Complete!');
    }
    else {
        onOtherResponse(carouselContentDivEl, this);
    }
}

function onOrderButtonClicked() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onOrderResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'orders');
    xhr.send();
}

function onOrderResponse() {
    removeAllChildren(orderContentDivEl);
    removeAllChildren(itemContentDivEl);
    removeAllChildren(itemsContentDivEl);
    if(this.status === OK) {
        if(getAuthorization().status === true){
            showContents(['site-header', 'carousel', 'order-body', 'nav-add-button', 'nav-order-button']);
        }
        else{
            showContents(['site-header', 'carousel', 'order-body']);
        }
        const orders = JSON.parse(this.responseText);
        createOrderBody(orders);
    }
    else {
        if(getAuthorization().status === true){
            showContents(['site-header', 'carousel', 'order-body', 'nav-add-button', 'nav-order-button']);
        }
        else{
            showContents(['site-header', 'carousel', 'order-body']);
        }
        emptyCartResponse();
    }
}

function createOrderHead() {
    const headerEl = document.createElement('h1');
    headerEl.setAttribute('class', 'checkout-h1');
    headerEl.textContent = 'Complete Orders';

    return headerEl;
}

function createOrderBody(orderContent) {
    clearMessages();
    orderContentDivEl.append(createOrderHead());

    for(let i = 0; i < orderContent.length; i++) {
        const item = orderContent[i];

        const itemDivEl = document.createElement('div');
        itemDivEl.setAttribute('class', 'checkout-item');

        const titleEl = document.createElement('p');
        titleEl.innerHTML = `<b>Title: </b> ${item.bookTitle}`;
        itemDivEl.appendChild(titleEl);

        const buyerEl = document.createElement('p');
        buyerEl.innerHTML = `<b>Buyer: </b> ${item.buyer}`;
        itemDivEl.appendChild(buyerEl);

        const priceEl = document.createElement('p');
        priceEl.innerHTML = `<b>Price: </b> ${item.price}`;
        itemDivEl.appendChild(priceEl);

        orderContentDivEl.appendChild(itemDivEl);
    }
    orderContentDivEl.appendChild(createOrderFooter(orderContent));
}

function createOrderFooter(orderContent) {
    const footerDivEl = document.createElement('div');
    footerDivEl.setAttribute('class', 'checkout-footer');

    const checkoutPriceEl = document.createElement('p');
    checkoutPriceEl.innerHTML = '<b>Shop Income: </b>' + getOrderPrice(orderContent) + ' USD';
    footerDivEl.appendChild(checkoutPriceEl);

    return footerDivEl;
}

function emptyCartResponse() {
    const errorDivEl = document.createElement('div');
    errorDivEl.textContent = 'Empty Cart';
    errorDivEl.setAttribute('class', 'error');
    errorDivEl.style.fontWeight = '900';
    orderContentDivEl.appendChild(errorDivEl);
}
