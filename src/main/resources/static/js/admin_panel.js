google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart_1);
google.charts.setOnLoadCallback(drawChart_2);

function drawChart_1() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin_panel/data1');
    xhr.responseType = 'json';

    xhr.onload = function() {
        if (xhr.status === 200) {
            var responseData = xhr.response;

            // Process the response data and create the DataTable
            var data = new google.visualization.DataTable();
            data.addColumn('date', 'Year');
            data.addColumn('number', 'Users');

            for (var i = 0; i < responseData.length; i++) {
                var date = new Date(responseData[i].createdAt);
                var userCount = responseData[i].userCount;
                data.addRow([date, userCount]);
            }

            var options = {
                title: 'Users Amount Per Date',
                titleTextStyle: {color:'#A239CA', fontSize: 16},
                hAxis: {
                    title: 'Date',
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

            var chart = new google.visualization.AreaChart(document.getElementById('chart_1_div'));
            chart.draw(data, options);
        }
    };

    xhr.send();
}


function drawChart_2() {
    var xhr = new XMLHttpRequest();
    xhr.open('GET', '/admin_panel/data2');
    xhr.responseType = 'json';

    xhr.onload = function() {
        if (xhr.status === 200) {
            var responseData = xhr.response;

            // Process the response data and create the DataTable
            var data = new google.visualization.DataTable();
            data.addColumn('date', 'Date');
            data.addColumn('number', 'User Count');

            for (var i = 0; i < responseData.length; i++) {
                var date = new Date(responseData[i].date);
                var userCount = responseData[i].userCount;
                data.addRow([date, userCount]);
            }

            var options = {
                title: 'User Statistics',
                titleTextStyle: { color: '#A239CA', fontSize: 16 },
                hAxis: {
                    title: 'Date',
                    titleTextStyle: { color: '#A239CA', fontName: 'Roboto', fontSize: 16 },
                    textStyle: { color: '#E7DFDD', fontName: 'Roboto', fontSize: 14 }
                },
                vAxis: {
                    titleTextStyle: { color: '#A239CA', fontName: 'Roboto', fontSize: 16 },
                    textStyle: { color: '#E7DFDD', fontName: 'Roboto', fontSize: 14 },
                    minValue: 0
                },
                chartArea: { backgroundColor: '#0E0B16' },
                backgroundColor: '#0E0B16',
                colors: ['#A239CA'],
                legend: { position: 'none' }
            };

            var chart = new google.visualization.AreaChart(document.getElementById('chart_2_div'));
            chart.draw(data, options);
        }
    };

    xhr.send();
}