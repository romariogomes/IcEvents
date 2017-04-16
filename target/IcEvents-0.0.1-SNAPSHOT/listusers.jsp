<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Hello World!</h2>

<a href="cadastro.jsp">Cadastrar</a>

<c:forEach items="${listaUsuarios}" var="u">
	
	Nome: ${u.nome}
	Email: ${u.email}
	
	
	<a href="busca?usuario=${u.codigoPessoa}"><button>Atualizar dados</button></a>
	<a href="remover?usuario=${u.codigoPessoa}"><button>Remover</button></a>
	
	<br><br>
</c:forEach>
</body>
</html>
