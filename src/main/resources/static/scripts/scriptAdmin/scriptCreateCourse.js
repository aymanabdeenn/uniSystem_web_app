'use strict';

const form = document.querySelector('form');
const facultyInput = document.getElementById('faculty');
const errorFaculty = document.getElementById('errorFaculty');

form.addEventListener('submit', function(event) {
    const faculty = facultyInput.value;

    if(faculty === ""){
        event.preventDefault();
        errorFaculty.textContent = "Choose a faculty.";
        facultyInput.style.borderColor = "red";
    }
});