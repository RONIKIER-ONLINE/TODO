// - /task page load processing scripts
window.onload = function() {

    var taskName = document.getElementById("taskName").value;
    document.getElementById("taskName").value = '';
    document.getElementById("taskName").value = taskName;

    document.getElementById("taskName").onchange = function() {
        document.getElementById("taskDescription").value = '';
        document.getElementById("taskInfoDialog").style.display = "none";
    };

    document.getElementById("taskName").focus();

};
