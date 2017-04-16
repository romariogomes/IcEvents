package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Pessoa;
import persistence.PessoaDao;

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
		
		if (url.equals("/usuario/busca")){
			
			consultarUsuario(request, response);
			
		}
		
		if (url.equals("/usuario/cadastro")){
			System.out.println(url);
			cadastrarUsuario(request, response);
		}
		
	}
	
	protected void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Pessoa p = new Pessoa(null, request.getParameter("nome"), request.getParameter("email"), request.getParameter("senha"), null);
		
		try {
			daoPessoa.inserir(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void consultarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			Integer codigo = Integer.parseInt(request.getParameter("codUsuario"));
			Pessoa p = daoPessoa.buscarPorId(codigo);
			request.getSession().setAttribute("pessoa", p);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../show_user.jsp").forward(request, response);
	}


}
