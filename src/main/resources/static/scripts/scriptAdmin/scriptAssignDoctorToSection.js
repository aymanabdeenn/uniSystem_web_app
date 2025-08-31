'use strict';

const searchForm = document.querySelector('.searchForm');
const getSectionsForm = document.querySelector('.getSectionsForm');
const assignForm = document.querySelector('.assignForm');

const facultyInput = document.getElementById('faculty');
const errorFaculty = document.getElementById('errorFaculty');

const courseIdInput = document.getElementById('courseId');
const errorCourseId = document.getElementById('errorCourseId');

const doctorIdInput = document.getElementById('doctorId');
const errorDoctorId = document.getElementById('errorDoctorId');

const sectionIdInput = document.getElementById('sectionId');
const errorSectionId = document.getElementById('errorSectionId');

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

if (getSectionsForm !== null) {
    getSectionsForm.addEventListener('submit', function(event) {
        const courseId = courseIdInput.value;

        if (courseId === "") {
            event.preventDefault();
            errorCourseId.textContent = "Choose a course.";
            courseIdInput.style.borderColor = "red";
        }
    });
}

if(assignForm !== null){
    assignForm.addEventListener('submit' , function(event) {
        const doctorId = doctorIdInput.value;
        const sectionId = sectionIdInput.value;

        if(doctorId === ""){
            event.preventDefault();
            errorDoctorId.textContent = "Choose a doctor";
            doctorIdInput.style.borderColor = "red";
        }
        else if(sectionId === ""){
            event.preventDefault();
            errorSectionId.textContent = "Choose a section";
            sectionIdInput.style.borderColor = "red";
        }
    });
}
