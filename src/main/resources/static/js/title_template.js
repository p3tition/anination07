document.addEventListener('DOMContentLoaded', function() {
    var progressElement = document.getElementById('score_bar');
    var progress = progressElement.textContent;
    var percent_width = progress*10;
    var barElement = document.querySelector(".bar_animate");
    barElement.style.width = percent_width+"%";
});

function insertLineBreaks() {
    var paragraphs = document.getElementById('synopsis');
    
    paragraphs.forEach(function(paragraph) {
        var text = paragraph.innerText.trim();
        var lines = text.split('\n');
        
        if (lines.length > 1) {
            paragraph.innerHTML = lines.join('<br><br><br>');
        }
    });
}