<%@ page contentType="text/html; charset=UTF-8"%>
<%
/*클라이언트의 요청을 받자, 이때 클라이언트는 서버입장에서 어떤 종류인지 관심이 없음, 알필요도 없음...*/
	System.out.println("클라이언트 요청 감지");

	StringBuffer sb = new StringBuffer();
	sb.append("{");
	sb.append("\"fruitList\":[");
	sb.append("{");
	sb.append("\"name\":\"딸기\",");
	sb.append("\"price\":1000");
	sb.append("},");
	sb.append("{");
	sb.append("\"name\":\"딸기\",");
	sb.append("\"price\":1000");
	sb.append("},");
	sb.append("{");
	sb.append("\"name\":\"딸기\",");
	sb.append("\"price\":1000");
	sb.append("}");
	sb.append("]");
	sb.append("}");
	
	out.print(sb.toString());
	
%>