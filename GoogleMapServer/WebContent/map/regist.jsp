<%@page import="org.json.simple.JSONObject"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	String driver ="org.mariadb.jdbc.Driver";
	String url="jdbc:mariadb://localhost:3306/iot";
	String user="root";
	String password;
	Connection con;
	PreparedStatement pstmt;
%>
<%
	


	request.setCharacterEncoding("utf-8");
	String str = request.getParameter("str");
	System.out.println(str);
	
	JSONParser parser = new JSONParser();
	JSONObject jsonObj = (JSONObject) parser.parse(str); //파싱!!
	JSONArray jsonArray = (JSONArray)jsonObj.get("position");
	System.out.println(jsonArray.size());
	//배열의 크기 만큼 DB에 insert
	Class.forName(driver);
	con = DriverManager.getConnection(url,user,password);
	
	StringBuffer sb = new StringBuffer();
	for(int i=0;i<jsonArray.size();i++){
		JSONObject obj = (JSONObject)jsonArray.get(i);
		sb.append("insert into maps(lat,lng) values(?,?)");
		
		pstmt = con.prepareStatement(sb.toString());
		pstmt.setString(1, Double.toString((Double)obj.get("lat")));
		pstmt.setString(2, Double.toString((Double)obj.get("lng")));
		
		System.out.println((Double)obj.get("lng"));
		pstmt.executeUpdate();
		
		sb.delete(0, sb.length()); //기존 스트링버퍼 객체의 내용 삭제
	}
	
	out.print("ok");
	
	pstmt.close();
	con.close();
	
%>