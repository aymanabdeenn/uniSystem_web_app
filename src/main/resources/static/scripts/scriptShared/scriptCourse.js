"use strict";

const displayBtn = document.getElementById("displayBtn");
const content = document.querySelector(".content");

displayBtn.addEventListener("click", (e)=>{
    if(content.style.display === "none"){
        content.style.display = "flex";
        content.style.opacity = 1;
    }
    else{
        content.style.display = "none";
        content.style.opacity = 0;
    }
})