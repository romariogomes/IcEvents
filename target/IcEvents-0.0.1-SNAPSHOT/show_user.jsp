<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


${pessoa}
${pessoa.nome}
	
		Nome<input type="text" name="nome" >
		Email<input type="text" name="email" value="${p.email}">
		Senha<input type="text" name="senha" value="${p.senha}">
		
		
	

</body>
</html>