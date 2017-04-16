<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="atualizar?usuario=${pessoa.codigoPessoa}" method="post">

		Nome<input type="text" name="nome" value="${pessoa.nome}">
		Email<input type="text" name="email" value="${pessoa.email}">
		Senha<input type="text" name="senha" value="${pessoa.senha}">
		
		
		<input type="submit" value="Salvar">
	</form>
	
</body>
</html>