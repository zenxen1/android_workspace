
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	String url="jdbc:mariadb://localhost:3306/iot";
	String id="root";
	String password = "";
	
	Connection con;
	PreparedStatement pstmt;

%>
<%
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	System.out.println("등록을 원하세요"+name);
	
	//mariaDB에 연동하자
	Class.forName("org.mariadb.jdbc.Driver");
	con=DriverManager.getConnection(url,id,password);
	
	if(con==null){
		System.out.println("접속 실패");
	}else{
		System.out.println("접속 성공");
		String sql = "insert into member (name,age) values (?,?)";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, 23);
		
		int result = pstmt.executeUpdate();
		out.print("result="+result);
		System.out.println("result = "+result);
		
	}
	
	
%>