'use strict';

const searchForm = document.querySelector('.searchForm');
const makeSectionForm = document.querySelector('.makeSectionForm');

const courseIdInput = document.getElementById('courseId');
const errorCourseId = document.getElementById('errorCourseId');

const facultyInput = document.getElementById('faculty');
const errorFaculty = document.getElementById('errorFaculty');

const timePeriodInput = document.getElementById('timePeriod');
const errorTimePeriod = document.getElementById('errorTimePeriod');

const capacityInput = document.getElementById('capacity');
const errorCapacity = document.getElementById('errorCapacity');

if (searchForm !== null) {
    searchForm.addEventListener('submit', function(event) {
        const faculty = facultyInput.value;

        if (faculty === "") {
            event.preventDefault();
            errorFaculty.textContent = "Choose a faculty.";
            facultyInput.style.borderColor = "red";
        }
    });
}

if (makeSectionForm !== null) {
    makeSectionForm.addEventListener('submit', function(event) {
        const courseId = courseIdInput.value;
        const timePeriod = timePeriodInput.value;
        const capacity = capacityInput.value;

        if (courseId === "") {
            event.preventDefault();
            errorCourseId.textContent = "Choose a course.";
            courseIdInput.style.borderColor = "red";
        } else if (timePeriod === "") {
            event.preventDefault();
            errorTimePeriod.textContent = "Choose a time period.";
            timePeriodInput.style.borderColor = "red";
        } else if (capacity === "") {
            event.preventDefault();
            errorCapacity.textContent = "Enter a capacity";
            capacityInput.style.borderColor = "red";
        }
        else if (!/^\d+$/.test(capacity) || parseInt(capacity, 10) <= 0) {
            event.preventDefault();
            errorCapacity.textContent = "Capacity must be a whole number greater than 0.";
            capacityInput.style.borderColor = "red";
        }
    });
}
