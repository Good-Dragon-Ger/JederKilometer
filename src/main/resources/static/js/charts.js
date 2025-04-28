var CHARTS = CHARTS || (function () {
    var id = {}; // private
    var data = {};
    var labels = {};

    return {
        init: function (Arg) {
            id = Arg;
        },
        setData: function (Arg) {
            data = Arg;
        },

        setLabels: function (Arg) {
            labels = Arg;
        },

        getHorizontalBarChart: function () {
            var ctx = document.getElementById(id).getContext('2d');
            var barChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        axis: 'y',
                        label: 'KM',
                        data: data,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(255, 205, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(201, 203, 207, 0.2)'
                        ],
                        borderColor: [
                            'rgb(255, 99, 132)',
                            'rgb(255, 159, 64)',
                            'rgb(255, 205, 86)',
                            'rgb(75, 192, 192)',
                            'rgb(54, 162, 235)',
                            'rgb(153, 102, 255)',
                            'rgb(201, 203, 207)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        xAxes: [{
                            ticks: {
                                beginAtZero: true
                            },

                        }],
                        x:{
                            title: {
                                display: true,
                                text: 'KM'
                            }
                        }

                    },
                    plugins: {
                        legend: {
                            display: false
                        }
                    },
                    indexAxis: 'y',
                }
            });
        },
        getPieChart: function () {
            var ctx = document.getElementById(id).getContext('2d');
            var pieChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: labels,
                    datasets: [{
                        axis: 'y',
                        label: 'KM',
                        data: data,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(255, 159, 64, 0.2)',
                            'rgba(255, 205, 86, 0.2)',
                            'rgba(75, 192, 192, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(153, 102, 255, 0.2)',
                            'rgba(201, 203, 207, 0.2)'
                        ],
                        borderColor: [
                            'rgb(255, 99, 132)',
                            'rgb(255, 159, 64)',
                            'rgb(255, 205, 86)',
                            'rgb(75, 192, 192)',
                            'rgb(54, 162, 235)',
                            'rgb(153, 102, 255)',
                            'rgb(201, 203, 207)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    plugins: {
                        legend: {
                            labels: {
                                font: {
                                    weight: 'bold'    // Fettgedruckt für bessere Lesbarkeit
                                },
                                color: '#333',         // Dunkleres Grau für besseren Kontrast
                                padding: 20             // Mehr Abstand zwischen den Einträgen
                            }
                        }
                    },
                    layout: {
                        padding: {
                            top: 30,
                            bottom: 30
                        }
                    },
                    elements: {
                        arc: {
                            borderWidth: 2, // Schöner dünner Rand
                        }
                    },
                    cutout: '40%', // macht aus Pie ein bisschen Donut-Style, wirkt edler
                    radius: '70%' // Diagramm wird kleiner (default wäre 100%)
                }
            });
        },
    };
}());
