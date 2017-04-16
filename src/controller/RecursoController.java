package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Recurso;
import persistence.RecursoDao;

@WebServlet("/RecursoController")
public class RecursoController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private RecursoDao daoRecurso;
	
    public RecursoController() {
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
		
		if (daoRecurso == null) {
			daoRecurso = new RecursoDao();
		}

		if (url.equals("/recurso/cadastro")){
			cadastrarRecurso(request, response);
		}
		
		if (url.equals("/recurso/busca")){
			consultarRecurso(request, response);
		}
		
		if (url.equals("/recurso/lista")){
			listarRecursos(request, response);
		}
		
		if (url.equals("/recurso/atualizar")){
			atualizarRecurso(request, response);
		}
		
		if (url.equals("/recurso/remover")){
			removerRecurso(request, response);
		}
		
	}
	
	protected void cadastrarRecurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			Recurso r = new Recurso(Integer.parseInt(request.getParameter("recurso")), request.getParameter("descricao"));
			
			daoRecurso.inserir(r);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../index.jsp").forward(request, response);
	}
	
	protected void consultarRecurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			Integer codigo = Integer.parseInt(request.getParameter("recurso"));
			Recurso r = daoRecurso.buscarPorId(codigo);
			request.setAttribute("recurso", r);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../show_user.jsp").forward(request, response);
	}
	
	protected void listarRecursos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			List<Recurso> recursos = new ArrayList<Recurso>();
			recursos = daoRecurso.listarTodos();
			request.getSession().setAttribute("listaRecursos", recursos);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../listusers.jsp").forward(request, response);
	}
	
	protected void atualizarRecurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Recurso r = new Recurso(Integer.parseInt(request.getParameter("recurso")), request.getParameter("descricao"));
		
		try {
			
			daoRecurso.atualizar(r);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/lista").forward(request, response);
	}

	protected void removerRecurso(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			Integer codigo = Integer.parseInt(request.getParameter("recurso"));
			Recurso r = daoRecurso.buscarPorId(codigo);
			daoRecurso.remover(r);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../index.jsp").forward(request, response);
	}

}
