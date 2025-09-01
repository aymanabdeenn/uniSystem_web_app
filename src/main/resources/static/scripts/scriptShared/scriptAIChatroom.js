"use strict";

const sendBtn = document.getElementById("sendBtn");
const chatInput = document.getElementById("chatInput");
const chatMessages = document.getElementById("chatMessages");
const emptyCard = document.getElementById("empty-card");
let isCardVisible = true;

async function handleEvent() {
    if (isCardVisible) {
        isCardVisible = false;
        emptyCard.style.display = "none";
        chatMessages.style.display = "block";
    }

    const message = chatInput.value.trim();
    if (!message) return;

    chatMessages.innerHTML += `<div class="user-message">${message}</div>`;
    chatMessages.scrollTop = chatMessages.scrollHeight;
    chatInput.value = "";

    try {
        const response = await fetch("/uniSystem/chat/send", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ message }),
        });

        const data = await response.json();
        chatMessages.innerHTML += `<div class="ai-message">${data.response}</div>`;
        chatMessages.scrollTop = chatMessages.scrollHeight;
    } catch (err) {
        console.error("Error:", err);
    }
}

sendBtn.addEventListener("click", handleEvent);
chatInput.addEventListener("keydown", (e) => {
    if (e.key === "Enter") handleEvent();
});
