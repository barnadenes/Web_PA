function createItemView(item) {
    removeAllChildren(itemsContentDivEl);
    clearMessages();
    const bodyEl = document.getElementById('item-view-body');
    const brEl = document.createElement('br');
    removeAllChildren(bodyEl);

    const itemViewEl = document.createElement('div');
    itemViewEl.setAttribute('class', 'item-view');

    const imgEl = document.createElement('img');
    imgEl.setAttribute('src', item.url);
    itemViewEl.appendChild(imgEl);
    bodyEl.appendChild(itemViewEl);

    const itemContainerEl = document.createElement('div');
    itemContainerEl.setAttribute('class', 'item-view');

    const titleEl = document.createElement('p');
    titleEl.innerHTML =`<strong>Title: </strong>${item.title}`;
    itemContainerEl.appendChild(titleEl);

    const authorEl = document.createElement('p');
    authorEl.innerHTML =`<strong>Author: </strong>${item.author}`;
    itemContainerEl.appendChild(brEl);
    itemContainerEl.appendChild(authorEl);

    const plotEl = document.createElement('p');
    plotEl.innerHTML =`<strong>Plot: </strong>${item.plot}`;
    itemContainerEl.appendChild(brEl);
    itemContainerEl.appendChild(plotEl);

    const priceEl = document.createElement('p');
    const serverSidePrice = item.price;
    priceEl.innerHTML ='Price: ' + serverSidePrice + ' USD';
    priceEl.style.cursor = 'pointer';
    priceEl.setAttribute('class', 'item-price');
    priceEl.setAttribute('id', 'USD');
    priceEl.setAttribute('title', 'Convert to HUF');
    priceEl.addEventListener('click', function () {
        if(document.getElementById('HUF')) {
            priceEl.setAttribute('id', 'USD');
            priceEl.innerHTML = 'Price: ' + serverSidePrice + ' USD';
        }
        else {
            priceEl.setAttribute('id', 'HUF');
            priceEl.innerHTML = 'Price: ' + serverSidePrice * 290 + ' HUF';
        }
    });
    itemContainerEl.appendChild(brEl);
    itemContainerEl.appendChild(priceEl);

    if(getAuthorization().status === false) {
        const addButtonEl = document.createElement('button');
        addButtonEl.innerHTML = '<b>ADD TO CART</b>';
        addButtonEl.dataset.itemId = item.id;
        addButtonEl.setAttribute('id', 'buy-button');
        addButtonEl.addEventListener('click', onAddCartClicked);
        itemContainerEl.appendChild(addButtonEl);
        itemContainerEl.appendChild(brEl);
    }

    const backButtonEl = document.createElement('button');
    backButtonEl.setAttribute('id', 'back-button');
    backButtonEl.innerHTML = '<b>Back</b>';
    backButtonEl.addEventListener('click', onBackButtonClicked);
    itemContainerEl.appendChild(backButtonEl);

    bodyEl.appendChild(itemContainerEl);
    return bodyEl;
}

function onItemResponse() {
    if(this.status === OK) {
        if(getAuthorization().status === true){
            showContents(['site-header', 'carousel', 'item-view-body', 'nav-add-button', 'nav-order-button']);
        }
        else{
            showContents(['site-header', 'carousel', 'order-body']);
        }
        const text = this.responseText;
        const item = JSON.parse(text);

        createItemView(item);
    }
    else {
        onOtherResponse(itemsContentDivEl ,this);
    }
}

function onItemClicked() {
    removeAllChildren(itemsContentDivEl);
    const itemId = this.dataset.itemId;

    const params = new URLSearchParams();
    params.append('id', itemId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onItemResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'item?' + params);
    xhr.send();
}

function onAddCartClicked() {
    const itemId = this.dataset.itemId;

    const params = new URLSearchParams();
    params.append('item_id', itemId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onAddCartResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'cart?' + params.toString());
    xhr.send();
}

function onAddCartResponse() {
    if(this.status === OK) {
        newMessage(itemContentDivEl, 'info', 'Item has been Added to your Cart!');
    }
    else
        onOtherResponse(itemContentDivEl, this);
}

