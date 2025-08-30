"use strict";

const slider = document.querySelector(".feed");
const slides = document.querySelectorAll(".slide");

const slideFeed = function () {
  let currentIndex = 0;
  let totalSlides = slides.length;

  const changeSlide = function () {
    if (currentIndex === totalSlides - 1) {
      currentIndex = 0;
    } else {
      currentIndex++;
    }
    slides.forEach((slide, index) => {
      slide.style.transform = `translateX(${(index - currentIndex) * 100}%)`;
    });
  };

  slides.forEach((slide, index) => {
    slide.style.transform = `translateX(${index * 100}%)`;
  });
  const timer = setInterval(changeSlide, 5000);
};

slideFeed();

// Closing the changing password form
const exitBtn = document.querySelector(".exitButton");
const messageContainer = document.querySelector(".messageContainer");

exitBtn.addEventListener("click" , function(){
    messageContainer.classList.add('hidden');
});