'use strict';

const form = document.querySelector('form');
const dobInput = document.getElementById('dob');
const facultyInput = document.getElementById('faculty');
const errorDob = document.getElementById('errorDob');
const errorFaculty = document.getElementById('errorFaculty');

form.addEventListener('submit', function(event) {
    const dobStr = dobInput.value;
    const dob = new Date(dobStr);
    const faculty = facultyInput.value;

    if(dobStr === ""){
        event.preventDefault();
        errorDob.textContent = "Choose a date of birth.";
        dobInput.style.borderColor = "red";
    }
    else if(faculty === ""){
        event.preventDefault();
        errorFaculty.textContent = "Choose a faculty.";
        facultyInput.style.borderColor = "red";
    }
});