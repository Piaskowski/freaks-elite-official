// == hidden ==
const observer = new IntersectionObserver((entries) => {
    entries.forEach((entry) => {
        if (entry.isIntersecting) {
            entry.target.classList.add('show');
        }
    });
});

const hiddenElements = document.querySelectorAll('.hidden');

hiddenElements.forEach((el) => observer.observe(el));



// == drag & drop ==

let isDragging = false;
let currentItem = null;
let containerOffsetX = 0;
let initX = 0;

const container = document.querySelector(".arrange-bandMember");

container.style.height = container.offsetHeight + "px";
//container.style.width = container.offsetWidth + "px";

document.addEventListener("mousedown", (e) => {
    const item = e.target.closest(".band-member");
    if (item){
        console.log("mousedown")
        isDragging = true;
        currentItem = item;
        containerOffsetX = currentItem.offsetLeft;
        currentItem.classList.add("dragging");
        document.body.style.userSelect = "none";
        currentItem.classList.add("insert-animation");
        currentItem.style.left = containerOffsetX + "px";
                console.log("click: " + containerOffsetX + " : " + e.clientX)
        initX = e.clientX;
    }
});

document.addEventListener("mousemove", (e) => {
    if(isDragging && currentItem){
        currentItem.classList.remove("insert-animation");
        console.log("mousemove")
        let newLeft = containerOffsetX - (initX - e.clientX);
        if (newLeft < -50){
            newLeft = -50;
        }
        else if (newLeft > container.offsetWidth - 200){
            newLeft = container.offsetWidth - 200;
        }
        currentItem.style.left = newLeft + "px";

        let itemSiblings = [...document.querySelectorAll(".band-member:not(.dragging)")];
        let nextItem = itemSiblings.find((sibling) => {
            return(e.clientX - container.getBoundingClientRect().left <= sibling.offsetLeft + sibling.offsetWidth / 2);
        });

        itemSiblings.forEach((sibling) => {
            sibling.style.marginLeft = "10px";
        });

        if(nextItem) {
            nextItem.style.marginLeft = currentItem.offsetWidth + 2 + "px";
        }

        container.insertBefore(currentItem, nextItem);
    }
});

document.addEventListener("mouseup", (e) => {
    if(currentItem){
        currentItem.classList.remove("dragging");
        currentItem.style.left = "auto";
        currentItem = null;
        isDragging = false;

        document.body.style.userSelect = "auto";
        updateArrangement();
    }

    console.log("mouseup");
    let itemSiblings = [...document.querySelectorAll(".band-member:not(.dragging)")];

    itemSiblings.forEach((sibling) => {
        sibling.style.marginLeft = "10px";
    });

});

function updateArrangement() {
    let arrangementInputs = [...document.querySelectorAll(".band-member input#iArrangement")];

    arrangementInputs.forEach((iArrangement) => {
        iArrangement.value = arrangementInputs.indexOf(iArrangement);
        console.log("Arrangement: " + iArrangement.value);
    });
}


