<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: evill
  Date: 2020-12-25
  Time: 오전 12:21
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <style>
        table{
            width:80%;
        }
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 15px;
            text-align: left;
        }
        #t01 tr:nth-child(even) {
            background-color: #eee;
        }
        #t01 tr:nth-child(odd) {
            background-color: #fff;
        }
    </style>
    <title>Title</title>
</head>
<body>

<form action="/goWrite" method="post">
<table align="center" id="t01">
    <tr>
        <td colspan="2">
            <input type="submit" value="입력">
        </td>
    </tr>
<c:forEach var="item" items="${blogList}" >
    <tr>
        <th width="70%">
            <c:out value="${item.subject}"/>
        </th>
        <th style="font-size:12px; text-align:right;">
            <c:out value="${item.iptdate}"/>&nbsp;&nbsp;
        </th>
    </tr>
    <tr>
        <td colspan="2">
            <c:out value="${item.contents}"/>
        </td>
    </tr>
</c:forEach>

</table>


</form>
</body>
</html>
