<html>
<body>
<h2>Hello World!</h2>

<a href="cadastro.jsp">Cadastrar</a>
<a href="usuario/lista">Listar</a>

<form method="post" action="usuario/busca?usuario=codUsuario">
	<input type="text" name="codUsuario">
	<input type="submit" value="buscar">
</form>

<form method="post" action="login">
	Email<input type="text" name="email">
	Senha<input type="text" name="senha">
	<input type="submit" value="Logar">
</form>
</body>
</html>
