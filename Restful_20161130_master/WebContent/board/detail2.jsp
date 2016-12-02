<%@ page contentType="text/html;charset=utf-8"%>
<%
	String msg=(String)request.getAttribute("msg");
	out.print(msg);
%>
<!doctype html>
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style>
#box{border:1px solid #CCCCCC}
#writer,#title,#content{font-size:9pt;font-weight:bold;color:#7F7F7F;돋움}
input{
	font-size:9pt;
	border-left:1px solid #C3C3C3;
	border-top:1px solid #C3C3C3;
	border-right:1px solid #C3C3C3;
	border-bottom:1px solid #C3C3C3;
	color:#7F7F7F;돋움
}
#title input{width:250px;}
#content textarea{
width:503px;
border:0;
height:153;
background:url("/images/write_bg.gif");
border:#C3C3C3 1px solid 
}
#copyright{font-size:9pt;}
a{text-decoration:none}
img{border:0px}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
function edit(){
	var sendData={
		_method:"PUT",	
		writer:form1.writer.value,
		title:form1.title.value,
		content:form1.content.value
	}	

	$.ajax({
		url:"/device/board",
		type:"POST",
		contentType:"application/x-www-form-urlencoded",
		data:JSON.stringify(sendData),
		success:function(result){
			alert(result);
		}
	});
}


function del(){
	var sendData={
		/* _method:"DELETE", */			
		writer:form1.writer.value,
		title:form1.title.value,
		content:form1.content.value
	}	

	$.ajax({
		url:"/device/board/77",
		type:"DELETE",
		data:JSON.stringify(sendData),
		success:function(result){
			alert(result);
		}
	});
}
</script>
</head>
<body>
<form name="form1" method="post">
<input type="hidden" name="_method">
<input type="hidden" name="board_id" value="1">

<table id="box" align="center" width="603" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/images/ceil.gif" width="603" height="25"></td>
  </tr>
  <tr>
    <td height="2" bgcolor="#6395FA"><img src="/images/line_01.gif"></td>
  </tr>
  <tr>
    <td height="1" bgcolor="#CCCCCC"></td>
  </tr>
	<tr>	
		<td id="list"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100">&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
          <tr id="writer">
            <td height="25" align="center">작성자</td>
            <td><input type="text" name="writer"></td>
          </tr>
          <tr id="title">
            <td height="25" align="center">제목</td>
            <td><input type="text" name="title"></td>
          </tr>
          <tr id="content">
            <td align="center">내용</td>
            <td><textarea name="content" style=""></textarea></td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
          </tr>
        </table></td>
	</tr>
  <tr>
    <td height="30" align="right" style="padding-right:2px;">
	<img src="/images/write_btin.gif" width="61" height="20" onClick="edit()">
	<img src="/images/delete_btn.gif" width="61" height="20" onClick="del()"> <a href="list.html"><img src="/images/list_btn.gif" width="61" height="20" border="0"></a> </td>
  </tr>
  <tr>
    <td height="1" bgcolor="#CCCCCC"></td>
  </tr>
  <tr>
    <td height="20" align="center" id="copyright">Copyright zino All Rights Reserved </td>
  </tr>
</table>
</form>
</body>
</html>
