<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.0/font/bootstrap-icons.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"
            type="text/javascript"></script>

    <script>
        $(document).ready(function () {
            getAllDir();
        });

        function getAllDir() {
            $.ajax({
                url: "/spb_iac/getAllDir",
                type: "GET",
                dataType: 'json'
            }).done(function (data) {
                let result = "";
                $.each(data, function (index) {
                    let date = getCorrectDate(data[index]['date']);
                    result += "<tr>" +
                        "<td>" + date + "</td>" +
                        "<td>" + data[index]['path'] + "</td>" +
                        "<td>" + data[index]['subdirectories'] + "</td>" +
                        "<td>" + data[index]['files'] + "</td>" +
                        "<td>" + data[index]['size'] + "</td>" +
                        "<td> <input type='button' value='Файлы' onclick='getDetails(" +
                        data[index]['id'] + ")' " + "</td>" +
                        "</tr>"
                });
                $('#directories').html(result)
            })
        }

        function create() {
            let path = document.getElementById('path').value;
            $.ajax({
                url: "/spb_iac/create",
                data: {path: path},
                type: "POST"
            }).done(function () {
                getAllDir();
            })
        }

        function getDetails(id) {
            $.ajax({
                url: "/spb_iac/details",
                data: {id: id},
                type: "GET",
                dataType: 'json'
            }).done(function (data) {
                let result = "";
                $.each(data, function (index) {
                    let size = data[index]['size'];
                    if (size == "DIR") {
                        size = "&lt" + size + "&gt";
                    }
                    result += "<tr>" +
                        "<td>" + data[index]['path'] + "</td>" +
                        "<td>" + size + "</td>" +
                        "</tr>";
                });
                $('#details').html(result)
                showModal();
            })
        }

        function showModal() {
            $('#myModel').modal();
        }

        function getCorrectDate(date) {
            let d = new Date(date),
                hour = d.getHours(),
                minute = d.getMinutes();
            minute = (minute < 10) ? '0' + minute : minute;
            hour = (hour < 10) ? '0' + hour : hour;
            let result = d.getDate() + "." + (d.getMonth() + 1) + "." + d.getFullYear()
                    + " " + hour + ":" + minute;
            return result;
        }

    </script>

    <title>Директории и файлы</title>
</head>
<body>

<div class="container">
    <div class="card-body">
        <h1>Директории и файлы</h1>
    </div>

    <div id="myModel" class="modal fade" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <table class="table table-hover table-bordered modal-body">
                    <thead>
                    <tr>
                        <th>Файл</th>
                        <th>Размер</th>
                    </tr>
                    </thead>
                    <tbody id="details">

                    </tbody>
                </table>
                <div class="modal-footer">
                    <button class="btn btn-danger" data-dismiss="modal">Закрыть</button>
                </div>
            </div>
        </div>
    </div>
    <div class="card-body">
        <form>
            <label for="path">Новая директория: </label>
            <input type="text" name="path" id="path">
            <button type="reset" onclick="create()">Добавить в список</button>
        </form>
    </div>

    <div class="card-body">
        <h2>Список директорий и файлов</h2>
    </div>

    <div class="card-body">
        <table class="table table-hover table-bordered text-center">
            <thead>
            <tr>
                <th class="col-md-2 text-center">Дата</th>
                <th class="col-md-5 text-center">Базовая директория</th>
                <th class="col-md-1 text-center">Директорий</th>
                <th class="col-md-1 text-center">Файлов</th>
                <th class="col-md-2 text-center">Суммарный размер файлов</th>
                <th class="col-md-1 text-center"></th>
            </tr>
            </thead>
            <tbody id="directories">

            </tbody>
        </table>
    </div>
</div>

</body>
</html>
