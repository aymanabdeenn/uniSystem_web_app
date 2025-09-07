"use strict";

const displayBtns = document.querySelectorAll(".displayBtn");

displayBtns.forEach(btn => {
    btn.addEventListener("click" , (e)=>{
        const announcement = btn.parentElement.parentElement;
        const content = announcement.querySelector(".content");

        if(content != null){
                if(content.style.display === "none"){
                    content.style.display = "flex";
                    content.style.opacity = 1;
                }
                else{
                    content.style.display = "none";
                    content.style.opacity = 0;
                }
        }
    });
});

const dropZone = document.getElementById("dropZone");
const fileInput = document.getElementById("fileInput");
const fileName = document.getElementById("fileName");
const filesNames = document.getElementById("filesNames");

// Highlight drop zone on drag
dropZone.addEventListener("dragover", (e) => {
  e.preventDefault(); // allow drop
  dropZone.classList.add("dragover");
});

dropZone.addEventListener("dragleave", () => {
  dropZone.classList.remove("dragover");
});

dropZone.addEventListener("drop", (e) => {
  e.preventDefault();
  dropZone.classList.remove("dragover");

  if (e.dataTransfer.files.length > 0) {
    fileInput.files = e.dataTransfer.files; // assign dropped files to input
    filesNames.innerHTML += `<div class="user-message">${fileInput.files[0].name}</div>`;
  }
});

// Update filename when browsing
fileInput.addEventListener("change", () => {
  if (fileInput.files.length > 0) {
    filesNames.innerHTML += `<div class="user-message">${fileInput.files[0].name}</div>`;
  }
});
