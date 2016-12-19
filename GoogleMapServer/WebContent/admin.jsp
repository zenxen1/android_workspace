<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<style type="text/css">
      html, body { height: 100%; margin: 0; padding: 0; }
      #map { height: 100%; }
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script>
var myLatlng = {lat: 37.501, lng: 127.040};
//var markers = new Array();
var jsonArray={
		position:[]
};
var count =0;

function initMap() {
	  

	  var map = new google.maps.Map(document.getElementById('map'), {
	    zoom: 15,
	    center: myLatlng
	  });

	  

	  map.addListener("click", function(event) {
		  var x = event.latLng.lat();
		  var y = event.latLng.lng();
		  //alert(x+","+y);
		  var obj ={lat : x, lng : y};
		  jsonArray.position[count]=obj;
		  var marker = new google.maps.Marker({
			    position: obj,
			    map: map,
			    title: '나 눌러줘'
			  });
		  count++;
		  //alert(jsonArray.position.length);
		  //markers[count++]="{lat:"+ x+", lng:"+ y+"}";
	  });
	}
	//클릭시마다, 좌표를 저장해 놓고, 서버에 등록할때 한꺼번에 전송하자!!!
	function regist(){
		/* for(var i=0;i<jsonArray.position.length;i++){
			alert(jsonArray.position[i].lat+","+jsonArray.position[i].lng);
		} */
		
		$.ajax({
			url:"/map/regist.jsp",
			type:"POST",
			data:"str="+JSON.stringify(jsonArray),
			success:function(result){
				if(result=="ok"){
					alert("지역정보를 등록하였습니다.");
				}
			}
		});
	}

</script>

</head>
<body>
	<div>
		<input type="button" value="서버에전송" onClick="regist()">
	</div>
	 <div id="map"></div>
</body>
</html>
<script async defer
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBeuDIblDXYrmpkxemEFvf1CFRDfUah6j0&callback=initMap">
</script>