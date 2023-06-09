google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin_panel');
    xhr.responseType = 'json';

    xhr.onload = function() {
        if (xhr.status === 200) {
            var response = xhr.response;
            var data = google.visualization.arrayToDataTable([
                ['Year', 'Users'],
                [new Date(2023, 4, 6), 1],
                [new Date(2023, 4, 7), 7],
                [new Date(2023, 4, 8), 10],
                [new Date(2023, 4, 9), 15],
                [new Date(2023, 4, 10), 16],
                [new Date(2023, 4, 11), 20],
                [new Date(2023, 4, 12), 17],
                [new Date(2023, 4, 13), 35]
            ]);

            var options = {
                title: 'users amount',
                titleTextStyle: {color:'#A239CA', fontSize: 16},
                hAxis: {
                    title: 'Year',
                    titleTextStyle: {color: '#A239CA', fontName: 'Roboto', fontSize: 16},
                    textStyle: {color: '#E7DFDD', fontName: 'Roboto', fontSize: 14}
                },
                vAxis: {
                    titleTextStyle: {color: '#A239CA', fontName: 'Roboto', fontSize: 16},
                    textStyle: {color: '#E7DFDD', fontName: 'Roboto', fontSize: 14},
                    minValue: 0
                },
                chartArea: {backgroundColor: '#0E0B16'},
                backgroundColor: '#0E0B16',
                colors: ['#A239CA'],
                legend: {position: 'none'}
            };

            var chart = new google.visualization.AreaChart(document.getElementById('chart_div'));
            chart.draw(data, options);
        }
    };

    xhr.send();
}