<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="java.util.*,board.*" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<center>
<title>우편번호검색</title>
<link href="style.css" rel="stylesheet" type="text/css">

<script language="JavaScript" src="script.js"></script>
<script>

function dongCheck(){
	
if (document.zipForm.area4.value == ""){
alert("동이름을 입력하세요");
document.zipForm.area4.focus();
return;
}
document.zipForm.submit();
}


// ex) 역삼이라고 검색하고 하나를 셀렉했을 때 input에 박히게 하는 함수
function sendAddress(zipcode,area1,area2,area3,area4){
	
	
var address =area1+ " "+area2+ " " +area3+ " " +area4;


opener.document.userinput.zipcode.value=zipcode;
opener.document.userinput.address.value=address;
self.close();

}

</script>
</head>

<body bgcolor="#FFFFCC">

<b>우편번호 찾기</b>
<form name="zipForm" method="post" action="ZipCheck.do"><!-- 액션을 지정 안하면 현재 페이지를 다시 요청한다. -->
<table>

      <tr>
        <td><br>
          동이름 입력 : <input name="area4" type="text">
          <input type="button" value="검색" onclick= "dongCheck();">
        </td>
      </tr>
     <input type="hidden" name="check" value="n">
    




<c:if test="${zipcodeList == null && check =='n'}">
   <tr><td align="center"><br>검색된 결과가 없습니다.</td></tr>
</c:if>



<tr><td align="center"><br>
    ※검색 후, 아래 우편번호를 클릭하면 자동으로 입력됩니다.</td></tr>



<c:if test="${check == 'n' }">
<c:forEach var="zip" items="${zipcodeList}">
<tr><td>
<a href="javascript:sendAddress('${zip.zipcode}','${zip.area1}','${zip.area2}','${zip.area3}','${zip.area4}')"> 
${zip.zipcode}&nbsp;${zip.area1}&nbsp;${zip.area2}&nbsp;${zip.area3}&nbsp;${zip.area4}</a><br>
</c:forEach>
</c:if>

</td></tr>


<tr><td align="center"><br><a href="javascript:this.close();">닫기</a></td><tr>

</table>
</form>

</body>
</center>
</html>
