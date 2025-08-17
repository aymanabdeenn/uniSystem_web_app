'use strict';

const exitBtn = document.querySelector(".exitButton");
const messageContainer = document.querySelector(".messageContainer");


exitBtn.addEventListener("click" , function(){
    messageContainer.classList.add('hidden');
});

function onRecaptchaSuccess(token){
    const form = document.getElementById("registrationForm");
    let input = document.createElement("input");
    input.type = "hidden";
    input.name = "g-recaptcha-response";
    input.value = token;
    form.appendChild(input);
}

function onLoadRecaptcha() {
   grecaptcha.render("recaptcha-container", {
   sitekey: "6Lc3n6grAAAAAKaFRmJX7TE-JaLdnjRESX3aroBt",
   callback: onRecaptchaSuccess
   });
}