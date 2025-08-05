"use strict";

const allAbsencesBtn = document.getElementById("allAbsences");
const allAttendancesBtn = document.getElementById("allAttendances");

const allAbsencesCheckbox = document.getElementById("allAbsences-checkbox");
const allAttendancesCheckbox = document.getElementById(
  "allAttendances-checkbox"
);

const allStudentsCheckboxes = document.querySelectorAll(".checkbox");

// Add event listeners to the "All Absences" button
allAbsencesBtn.addEventListener("click", function () {
  allAbsencesCheckbox.checked = true;
  allAbsencesCheckbox.dispatchEvent(new Event("change"));
});

// Add event listeners to the "All Attendances" button
allAttendancesBtn.addEventListener("click", function () {
  allAttendancesCheckbox.checked = true;
  allAttendancesCheckbox.dispatchEvent(new Event("change"));
});

// Checking all students when "All Absences" checkbox is checked
allAbsencesCheckbox.addEventListener("change", function () {
  allAttendancesCheckbox.checked = false; // Uncheck "All Attendances" when "All Absences" is checked
  allStudentsCheckboxes.forEach((checkbox) => {
    checkbox.checked = this.checked;
    checkbox.dispatchEvent(new Event("change"));
  });
});

// Unchecking all students when "All Attendances" checkbox is checked
allAttendancesCheckbox.addEventListener("change", function () {
  allAbsencesCheckbox.checked = false; // Uncheck "All Absences" when "All Attendances" is checked
  allStudentsCheckboxes.forEach((checkbox) => {
    checkbox.checked = !this.checked;
    checkbox.dispatchEvent(new Event("change"));
  });
});

// Update the status of each student based on their checkbox state
allStudentsCheckboxes.forEach((checkbox) => {
  checkbox.addEventListener("change", function () {
    if (!checkbox.checked) {
      // If the checkbox is checked, set status to "Present"
      checkbox.parentElement.nextElementSibling.textContent = "Present";
      //   // checkbox.nextElementSibling.classList.add("present");
      //   // checkbox.nextElementSibling.classList.remove("absent");
    } else {
      // If the checkbox is unchecked, set status to "Absent"
      checkbox.parentElement.nextElementSibling.textContent = "Absent";
      //  // checkbox.nextElementSibling.classList.add("absent");
      //  // checkbox.nextElementSibling.classList.remove("present");
    }
  });
});
