let activeElement = null;

function openTab(evt, tabName) {
  // Declare all variables
  var i, tabcontent, tablinks;

  // Get all elements with class="tabcontent" and hide them
  tabcontent = document.getElementsByClassName("tabcontent");
  for (i = 0; i < tabcontent.length; i++) {
    tabcontent[i].style.display = "none";
  }

  // Get all elements with class="settings-options-element" and remove the class "active"
  tablinks = document.getElementsByClassName("settings-options-element");
  for (i = 0; i < tablinks.length; i++) {
    tablinks[i].classList.remove("active");
  }

  // Show the current tab, and add an "active" class to the button that opened the tab
  document.getElementById(tabName).style.display = "block";
  evt.currentTarget.classList.add("active");

  // Set the currently active element
  activeElement = evt.currentTarget;
}

// Add event listener to all settings options elements
let settingsOptionsElements = document.getElementsByClassName("settings-options-element");
for (let i = 0; i < settingsOptionsElements.length; i++) {
  settingsOptionsElements[i].addEventListener("click", function(evt) {
    if (activeElement) {
      activeElement.classList.remove("active");
    }
    openTab(evt, evt.currentTarget.getAttribute("data-tab"));
  });
}

function setDefaultTab() {
    var dft = document.getElementById("dft");
    var defaultTab = document.getElementById("account");
    defaultTab.style.display = "block";
    dft.classList.add("active");
}

let input = document.getElementById("inputTag");
let imageName = document.getElementById("imageName")

input.addEventListener("change", ()=>{
    let inputImage = document.querySelector("input[type=file]").files[0];

    imageName.innerText = inputImage.name;
})