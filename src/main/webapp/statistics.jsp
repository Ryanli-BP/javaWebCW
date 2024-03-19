<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Age Statistics</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <canvas id="ageDistributionChart"></canvas>
    <script>

        var ageData = <%= request.getAttribute("ages") %>;

        // Function to group age data into bins for histogram
        function groupByRange(data, range) {
            var groups = {};
            for (var i = 0; i < data.length; i++) {
                var group = Math.floor(data[i] / range) * range;
                (groups[group] = groups[group] || []).push(data[i]);
            }
            return groups;
        }

        // Group age data into bins of 10 years
        var ageGroups = groupByRange(ageData, 10);

        // Prepare data for Chart.js
        var labels = Object.keys(ageGroups).map(function(key) { return key + "-" + (parseInt(key) + 10); });
        var data = Object.values(ageGroups).map(function(group) { return group.length; });

        // Create chart
        var ctx = document.getElementById('ageDistributionChart').getContext('2d');
        var chart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [{
                    label: 'Age Distribution',
                    data: data,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    </script>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>