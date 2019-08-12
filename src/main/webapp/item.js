function createItemView(item) {
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
    plotEl.innerHTML =`<strong>Author: </strong>${item.plot}`;
    itemContainerEl.appendChild(plotEl);

    const priceEl = document.createElement('p');
    const serverSidePrice = item.price;
    priceEl.innerHTML ='Price: ' + serverSidePrice + ' USD';
    priceEl.style.cursor = 'hover';
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

    const addButtonEl = document.createElement('button');
    addButtonEl.innerHTML = '<b>ADD TO CART</b>';
    addButtonEl.setAttribute('id', item.id);
    itemContainerEl.appendChild(addButtonEl);
    itemContainerEl.appendChild(brEl);

    const backButtonEl = document.createElement('button');
    backButtonEl.setAttribute('id', 'back-button');
    backButtonEl.setAttribute('class', 'back-button');
    backButtonEl.innerHTML = '<b>Back</b>';
    backButtonEl.addEventListener('click', onBackButtonClicked);
    itemContainerEl.appendChild(backButtonEl);

    bodyEl.appendChild(itemContainerEl);
    return bodyEl;
}

function onItemResponse() {
    if(this.status === OK) {
        const text = this.responseText;
        const item = JSON.parse(text);

        createItemView(item);
        showContents(['site-header', 'carousel', 'item-view-body']);
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
