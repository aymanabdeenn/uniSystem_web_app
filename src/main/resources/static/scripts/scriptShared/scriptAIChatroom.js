//"use strict";
//
//const sendBtn = document.getElementById("sendBtn");
//const chatInput = document.getElementById("chatInput");
//const chatMessages = document.getElementById("chatMessages");
//
//sendBtn.addEventListener("click", async () => {
//  const message = chatInput.value.trim();
//  if (!message) return;
//
//  chatMessages.innerHTML += `<div class="user-message">${message}</div>`;
//  chatInput.value = "";
//
//  const response = await fetch("/uniSystem/chat/send", {
//    method: "POST",
//    headers: { "Content-Type": "application/json" },
//    body: JSON.stringify({ message }),
//  });
//
//  const data = await response.json();
//  chatMessages.innerHTML += `<div class="ai-message">${data.response}</div>`;
//  chatMessages.scrollTop = chatMessages.scrollHeight;
//});

"use strict";

const sendBtn = document.getElementById("sendBtn");
const chatInput = document.getElementById("chatInput");
const chatMessages = document.getElementById("chatMessages");

sendBtn.addEventListener("click", async () => {
  const message = chatInput.value.trim();
  if (!message) return;

  chatMessages.innerHTML += `<div class="user-message">${message}</div>`;
  chatInput.value = "";

  try {
    const response = await fetch("/uniSystem/chat/send", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ message }), // matches the Spring Boot service
    });

    const data = await response.json();
    chatMessages.innerHTML += `<div class="ai-message">${data.response}</div>`;
    chatMessages.scrollTop = chatMessages.scrollHeight;
  } catch (err) {
    console.error("Error:", err);
    chatMessages.innerHTML += `<div class="ai-message">Error contacting server</div>`;
  }
});
