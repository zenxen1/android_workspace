<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	String driver ="org.mariadb.jdbc.Driver";
	String url="jdbc:mariadb://localhost:3306/iot";
	String user="root";
	String password;
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
%>
<%
	Class.forName(driver);
	con = DriverManager.getConnection(url,user,password);
	
	String sql = "select * from maps order by mapsid asc";
	
	//스크롤 가능한 읽기 전용 ResultSet 생성!!
	pstmt = con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
	rs = pstmt. executeQuery();
	
	//총 몇건이 있는지 조사하자
	rs.last(); //커서를 레코드의 제일 마지막 레코드로 귀향살이..
	int total = rs.getRow();
	rs.beforeFirst();
	
	
	//json으로 가공
	StringBuffer sb = new StringBuffer();
	
	
	sb.append("{");
	sb.append("\"position\":[");
	for(int i=0;i<total;i++){
		rs.next();
		sb.append("{");
		sb.append("\"lati\":"+rs.getString("lat")+",");
		sb.append("\"lng\":"+rs.getString("lng"));
		if(i<total-1){
			sb.append("},");
		}else{
			sb.append("}");
		}
	}
	sb.append("]");
	sb.append("}");
	
	out.print(sb.toString());

	rs.close();
	pstmt.close();
	con.close();
%>
