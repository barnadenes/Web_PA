function onUploadButtonClicked() {
    const uploadFormEl = document.forms['add-form'];
    const title = uploadFormEl.querySelector('input[id="upload-title"]').value;
    const author = uploadFormEl.querySelector('input[id="upload-author"]').value;
    const url = uploadFormEl.querySelector('input[id="upload-url"]').value;
    const price = uploadFormEl.querySelector('input[id="upload-price"]').value;
    const plot = uploadFormEl.querySelector('textarea[id="upload-plot"]').value;

    const params = new URLSearchParams();
    params.append('title', title);
    params.append('author', author);
    params.append('url', url);
    params.append('price', price);
    params.append('plot', plot);

    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onUploadResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'orders');
    xhr.send(params);
}

function onUploadResponse() {
    if(this.status === OK ) {
        newInfo(carouselContentDivEl, 'Item Uploaded!');
    }
    else {
        onOtherResponse(carouselContentDivEl, this);
    }
}

function onUploadMenuClicked() {
    showContents(['site-header', 'carousel', 'admin-add', 'nav-add-button', 'nav-order-button']);
}
