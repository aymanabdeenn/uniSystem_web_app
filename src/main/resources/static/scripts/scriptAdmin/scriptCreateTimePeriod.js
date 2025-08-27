'use strict';

const form = document.querySelector('form');
const daysInput = document.getElementById('days');
const errorDays = document.getElementById('errorDays');
const timePeriodInput = document.getElementById('timePeriod');
const errorTimePeriod = document.getElementById('errorTimePeriod');

console.log("hi");

form.addEventListener('submit', function(event) {
    const days = daysInput.value;
    const timePeriod = timePeriodInput.value;

    if(days === ""){
        event.preventDefault();
        errorDays.textContent = "Choose the days.";
        daysInput.style.borderColor = "red";
    }
    else if(timePeriod === ""){
        event.preventDefault();
        errorTimePeriod.textContent = "Choose a time period.";
        timePeriodInput.style.borderColor = "red";
    }
});