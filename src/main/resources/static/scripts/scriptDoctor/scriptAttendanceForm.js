'use strict';

const exitBtn = document.querySelector(".exitButton");
const messageContainer = document.querySelector(".messageContainer");


exitBtn.addEventListener("click" , function(){
    messageContainer.classList.add('hidden');
});
