'use strict';

document.addEventListener("DOMContentLoaded", () => {
  const exitBtn = document.querySelector(".exitButton");
  const messageContainer = document.querySelector(".container1");
  const backBtn = document.querySelector(".backBtn");
  const sectionsContainer = document.querySelector(".container2");

  if (exitBtn && messageContainer) {
    exitBtn.addEventListener("click", () => {
      messageContainer.classList.add("hidden");
    });
  }

  if (backBtn && sectionsContainer) {
    backBtn.addEventListener("click", () => {
      sectionsContainer.classList.add("hidden");
      document.querySelector(".container").classList.remove("hidden"); // if you want to re-show course list
    });
  }
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