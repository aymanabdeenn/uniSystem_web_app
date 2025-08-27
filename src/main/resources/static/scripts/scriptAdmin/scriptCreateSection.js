'use strict';

const form = document.querySelector('form');
const facultyInput = document.getElementById('faculty');
const errorFaculty = document.getElementById('errorFaculty');
const courseIdInput = document.getElementById('courseId');
const errorCourseId = document.getElementById('errorCourseId');
const timePeriodInput = document.getElementById('timePeriod');
const errorTimePeriod = document.getElementById('errorTimePeriod');
const capacityInput = document.getElementById('capacity');
const errorCapacity = document.getElementById('errorCapacity');

if(form) {
    form.addEventListener('submit', function(event) {

        // Check each input exists before reading .value
        const faculty = facultyInput ? facultyInput.value : null;
        const courseId = courseIdInput ? courseIdInput.value : null;
        const timePeriod = timePeriodInput ? timePeriodInput.value : null;
        const capacity = capacityInput ? capacityInput.value : null;

        let prevent = false;

        if(facultyInput && faculty === "") {
            event.preventDefault();
            errorFaculty.textContent = "Choose a faculty.";
            facultyInput.style.borderColor = "red";
            prevent = true;
        } else if(facultyInput) {
            errorFaculty.textContent = "";
            facultyInput.style.borderColor = "";
        }

        if(courseIdInput && courseId === "") {
            event.preventDefault();
            errorCourseId.textContent = "Choose a course.";
            courseIdInput.style.borderColor = "red";
            prevent = true;
        } else if(courseIdInput) {
            errorCourseId.textContent = "";
            courseIdInput.style.borderColor = "";
        }

        if(timePeriodInput && timePeriod === "") {
            event.preventDefault();
            errorTimePeriod.textContent = "Choose a time period.";
            timePeriodInput.style.borderColor = "red";
            prevent = true;
        } else if(timePeriodInput) {
            errorTimePeriod.textContent = "";
            timePeriodInput.style.borderColor = "";
        }

        if(capacityInput && capacity === "") {
            event.preventDefault();
            errorCapacity.textContent = "Choose a section.";
            capacityInput.style.borderColor = "red";
            prevent = true;
        } else if(capacityInput) {
            errorCapacity.textContent = "";
            capacityInput.style.borderColor = "";
        }

    });
}
