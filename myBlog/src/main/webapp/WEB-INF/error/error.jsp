<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
    <h1>Error Page</h1>
    <%--
   	 error code : ${code}
    <br>error msg :${msg}
    <br>timestamp :${timestamp}
     --%>
 	error code : <%=request.getAttribute("code")%>
    <br>error msg : <%=request.getAttribute("msg")%>
    <br>timestamp : <%=request.getAttribute("timestamp")%>


</body>
</html>