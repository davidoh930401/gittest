<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	//세션값 제어
	String id = (String)session.getAttribute("id");

%>
	<h2> 게시판(수정) 기본틀 </h2>
<fieldset>
<legend>글수정</legend>
<form action="./BoardUpdateAction.me" method="post">
<!-- 글제목 입력란   value값은 틀 pix 후에 제거 -->
시간:<select name="when_name"><option value="now">지금</option><option value="30min">30분</option><option value="1hour">
1시간</option></select>

음식 카테고리 종류:<select name="what_name"><option value="양식">양식</option><option value="일식">일식</option><option value="중식">중식</option><option value="치킨">
치킨</option><option value="디저트">디저트</option>
</select><br>
가게이름:<input type="text" name="where_name" value="where_name"><br>
<!-- 작성사 info 닉네임 + 프로필 사진 -->
작성자:<input type="text" name="id" placeholder="사용자ID" value="${dto.id }" readonly><img alt="프로필사진" src="프로필사진"><br>

사진업로드:<input type="text" name="upload_image" value="upload_image"><input type="button" value="file업로드"></button><br>
<!-- 본문 -->
<textarea name="content" rows="5" cols="33"></textarea><br>

<input type="reset" value="초기화">
<input type="submit" value="고치기">
<!-- <input type="button" value="신고" onclick=" location.href='./미정.me';"> -->
<input type="button" value="삭제" onclick=" location.href='./미정.me';">
<input type="button" value="뒤로가기" onclick=" location.href='./Main.me';">
</form>

</fieldset>

</body>
</html>