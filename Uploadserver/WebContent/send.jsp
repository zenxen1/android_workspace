<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<script>
function regist(){
	form1.action="/upload";
	form1.submit();
}
</script>
</head>
<body>
	<form name="form1" method="post" enctype="multipart/form-data">
		<input type="text" name="myName">
		<input type="file" name="myFile">
		<input type="button" value="업로드" onClick="regist()">
	</form>
</body>
</html>