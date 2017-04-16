package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Sala;
import persistence.SalaDao;

@WebServlet("/SalaController")
public class SalaController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private SalaDao daoSala;
	
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
	
	protected void cadastrarSala(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			Sala sl = new Sala(null, request.getParameter("numero"), Integer.parseInt(request.getParameter("capacidade")));
			
			daoSala.inserir(sl);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../index.jsp").forward(request, response);
	}
	
	protected void consultarSala(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			Integer codigo = Integer.parseInt(request.getParameter("sala"));
			Sala sl = daoSala.buscarPorId(codigo);
			request.setAttribute("sala", sl);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../show_user.jsp").forward(request, response);
	}
	
	protected void listarSalas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
		
			List<Sala> Salas = new ArrayList<Sala>();
			Salas = daoSala.listarTodos();
			request.getSession().setAttribute("listaSalas", Salas);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../listusers.jsp").forward(request, response);
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
