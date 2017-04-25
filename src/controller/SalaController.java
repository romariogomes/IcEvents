package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Recurso;
import model.Sala;
import persistence.RecursoDao;
import persistence.SalaDao;

@WebServlet("/SalaController")
public class SalaController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private SalaDao daoSala;
	private RecursoDao daoRecurso;
	
    public SalaController() {
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
		
		if (daoSala == null) {
			daoSala = new SalaDao();
			daoRecurso = new RecursoDao();
		}
		
		if (url.equals("/sala/carregaCadastro")){
			carregarCadastroSala(request, response);
		}

		if (url.equals("/sala/cadastro")){
			cadastrarSala(request, response);
		}
		
		if (url.equals("/sala/busca")){
			consultarSala(request, response);
		}
		
		if (url.equals("/sala/lista")){
			listarSalas(request, response);
		}
		
		if (url.equals("/sala/atualizar")){
			atualizarSala(request, response);
		}
		
		if (url.equals("/sala/remover")){
			removerSala(request, response);
		}
		
	}
	
	protected void carregarCadastroSala(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		try {
			
			List<Recurso> recursos = new ArrayList<Recurso>();
			recursos = daoRecurso.listarTodos();
			request.setAttribute("listaRecursos", recursos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../cadastroSala.xhtml").forward(request, response);
	}

	protected void cadastrarSala(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		String [] teste = request.getParameterValues("recursoSala");
		List<Recurso> recursos = new ArrayList<Recurso>();

		try {
			
			for (int i = 0; i < teste.length; i++) {
				Recurso r = daoRecurso.buscarPorId(Integer.parseInt(teste[i]));
				recursos.add(r);
			}
			
			Sala sl = new Sala(null, request.getParameter("numero"), Integer.parseInt(request.getParameter("capacidade")));
			sl.setRecursos(recursos);
			
			daoSala.inserir(sl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("lista");
	}
	
	protected void consultarSala(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		try {
		
			Integer codigo = Integer.parseInt(request.getParameter("sala"));
			Sala sl = daoSala.buscarPorId(codigo);
			request.setAttribute("salaEspecifica", sl);
			
			List<Recurso> recursosDaSala = sl.getRecursos();
			
			request.setAttribute("listaRecursosDaSala", recursosDaSala);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../listarSala.xhtml").forward(request, response);
	}
	
	protected void listarSalas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			List<Sala> Salas = new ArrayList<Sala>();
			Salas = daoSala.listarTodos();
			request.getSession().setAttribute("listaSalas", Salas);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("../listarSalas.xhtml");
	}
	
	protected void atualizarSala(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Sala sl = new Sala(Integer.parseInt(request.getParameter("sala")), request.getParameter("numero"), Integer.parseInt(request.getParameter("capacidade")));
		
		try {
			
			daoSala.atualizar(sl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/lista").forward(request, response);
	}

	protected void removerSala(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			Integer codigo = Integer.parseInt(request.getParameter("sala"));
			Sala sl = daoSala.buscarPorId(codigo);
			daoSala.remover(sl);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../index.jsp").forward(request, response);
	}

}
