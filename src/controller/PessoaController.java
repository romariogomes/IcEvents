package controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Pessoa;
import model.Tipo;
import persistence.PessoaDao;
import sun.misc.BASE64Encoder;

@WebServlet("/PessoaController")
public class PessoaController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private PessoaDao daoPessoa;
	
    public PessoaController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verificarUrl(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		verificarUrl(request, response);
	}
	
	protected void verificarUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getServletPath();
		
		if (daoPessoa == null) {
			daoPessoa = new PessoaDao();
		}

		if (url.equals("/usuario/cadastro")){
			cadastrarUsuario(request, response);
		}
		
		if (url.equals("/usuario/busca")){
			consultarUsuario(request, response);
		}
		
		if (url.equals("/usuario/lista")){
			listarUsuarios(request, response);
		}
		
		if (url.equals("/usuario/atualizar")){
			atualizarUsuario(request, response);
		}
		
		if (url.equals("/usuario/remover")){
			removerUsuario(request, response);
		}
		
		if (url.equals("/usuario/editarDados")){
			editarDadosUsuario(request, response);
		}
	}
	
	protected void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String senha = request.getParameter("senha");
		String confirmacaoSenha = request.getParameter("confsenha");
		
		HttpSession sessao = request.getSession();
		
		try {
			
			if (senha.equals(confirmacaoSenha)) {
				
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] crip = md.digest(confirmacaoSenha.getBytes());
				BASE64Encoder enc = new BASE64Encoder();
				
				Pessoa p = new Pessoa(null, request.getParameter("nome"), request.getParameter("email"), enc.encode(crip), null);
				
				if (sessao.getAttribute("user") == null) {
					p.setTipo(Tipo.PARTICIPANTE);
					
				} else {
					
					Pessoa userSessao = (Pessoa) sessao.getAttribute("user"); 
					if (userSessao.getTipo().equals(Tipo.ADMIN)) {
						p.setTipo(Tipo.valueOf(request.getParameter("tipo")));
					}
					
				}
				
				daoPessoa.inserir(p);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("../index.xhtml");

	}
	
	/* Método para editar os dados de usuários, logado como ADMIN*/
	
	protected void consultarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			Integer codigo = Integer.parseInt(request.getParameter("usuario"));
			Pessoa p = daoPessoa.buscarPorId(codigo);
			request.setAttribute("pessoa", p);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		response.sendRedirect("../listarUsuario_Admin.xhtml");
		request.getRequestDispatcher("../listarUsuario_Admin.xhtml").forward(request, response);
	}
	
	protected void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			List<Pessoa> usuarios = new ArrayList<Pessoa>();
			usuarios = daoPessoa.listarTodos();
			request.getSession().setAttribute("listaUsuarios", usuarios);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("../listarUsuarios.xhtml");
	}
	
	protected void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Pessoa p = new Pessoa(Integer.parseInt(request.getParameter("usuario")), request.getParameter("nome"), request.getParameter("email"), Tipo.valueOf(request.getParameter("tipo")));
			daoPessoa.atualizar(p);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("lista");
	}

	protected void removerUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			Integer codigo = Integer.parseInt(request.getParameter("usuario"));
			Pessoa p = daoPessoa.buscarPorId(codigo);
			daoPessoa.remover(p);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../index.jsp").forward(request, response);
	}
	
	
	/* Método para editar os dados do usuário logado*/
	
	protected void editarDadosUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		try {
		
			Pessoa p = (Pessoa) sessao.getAttribute("user");
			p.setNome(request.getParameter("nome"));
			p.setEmail(request.getParameter("email"));
			daoPessoa.atualizar(p);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("../index.xhtml");
	}

}
