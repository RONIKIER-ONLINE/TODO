// - /task page modal processing scripts - https://www.w3schools.com/howto/howto_css_modals.asp
var modal = document.getElementById("taskInfoDialog");

var span = document.getElementsByClassName("close")[0];

span.onclick = function() {
    modal.style.display = "none";
}

window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}