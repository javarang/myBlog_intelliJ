<%--
  Created by IntelliJ IDEA.
  User: evill
  Date: 2020-12-25
  Time: 오후 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>입력</title>
    <style>
        table{
            width:80%;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        textarea {
            width: 100%;
            height: 150px;
            padding: 12px 20px;
            box-sizing: border-box;
            border: 2px solid #ccc;
            border-radius: 4px;
            background-color: #f8f8f8;
            font-size: 16px;
            resize: none;
        }
    </style>
</head>
<body>
    <form action="/write" method="post" name="writeForm">
    <table align="center">
        <tr>
            <td>
                <input type="text" name="subjectText" maxlength="50" size="80%">
            </td>
        </tr>
        <tr>
            <td>
                <textarea name="contentsText"></textarea>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" name ="goSave" value="입력">
            </td>
        </tr>
    </table>
    </form>

</body>
</html>
