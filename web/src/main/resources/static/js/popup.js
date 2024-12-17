function launchPopup(id, message) {
    launchPopupWithContainerId('', id, message)
}

function launchPopupWithContainerId(popupContainerId, id, message) {
    let sel = '.confirm-popup-container';
    if (popupContainerId != ''){
        sel = '#' + popupContainerId + ' ' + sel;
    }
    let popup = document.querySelector(sel);

    let pathname = getCurrentPathName();

    let deleteForm = popup.querySelector('.confirm-popup form');
    let h4 = deleteForm.querySelector('h4');
    let inputId = deleteForm.querySelector('#objectId');
    let btnNo = deleteForm.querySelector('.btn-no a');

    popup.style.display="unset";
    deleteForm.setAttribute("action", pathname + "/delete")
    h4.innerText = message;
    inputId.value = id;
    btnNo.setAttribute("onclick","exitPopup('" + sel + "');");// o możliwe, że zbędne
}

function exitPopup(sel) {
  let popup = document.querySelector(sel);
  popup.style.display="none";
}

function getCurrentPathName () {
  return window.location.pathname
}