function onBuyOrderClicked() {
    const buyOrderId = this.dataset.orderId;

    const param = new URLSearchParams();
    param.append('item_id' ,buyOrderId);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onBuyOrderResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'orders?' + param);
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
