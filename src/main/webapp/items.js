function onShopButtonClicked() {
    removeAllChildren(unusedDivEls);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onItemsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'items');
    xhr.send();
}

function createItemsTable(items) {
    removeAllChildren(unusedDivEls);
    const bodyEl = document.getElementById('shop-body');

    for(let i = 0; i < items.length; i++) {
        const item = items[i];

        const divEl = document.createElement('div');
        const pictureEl = item.url;
        divEl.setAttribute('class', 'shop-head');
        divEl.dataset.itemId = item.id;
        divEl.style.backgroundImage = 'url(' + pictureEl + ')';
        divEl.addEventListener('click', onItemClicked);

        const pEl = document.createElement('p');
        pEl.setAttribute('class', 'shop-item-title');
        pEl.textContent = item.title;

        divEl.appendChild(pEl);
        bodyEl.appendChild(divEl);
    }

}

function onItemsResponse() {
    if(this.status === OK) {
        if(getAuthorization().status === true){
            showContents(['site-header', 'carousel', 'shop-body', 'nav-add-button', 'nav-order-button']);
        }
        else{
            showContents(['site-header', 'carousel', 'shop-body', 'nav-checkout-button']);
        }
        createItemsTable(JSON.parse(this.responseText));
    }
    else{
        onOtherResponse(itemsContentDivEl, this);
    }

}
