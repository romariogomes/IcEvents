package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Evento;
import model.Palestrante;
import model.Pessoa;
import model.Reserva;
import model.StatusEvento;
import model.Tipo;
import model.TipoEvento;
import persistence.EventoDao;
import persistence.PessoaDao;

@WebServlet("/EventoController")
public class EventoController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private EventoDao daoEvento;
	private PessoaDao daoPessoa;
	
    public EventoController() {
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
		
		if (daoEvento == null) {
			daoEvento = new EventoDao();
			daoPessoa = new PessoaDao();
		}
		
		if (url.equals("/evento/cadastro")){
			cadastrarEvento(request, response);
		}
		
		if (url.equals("/evento/busca")){
			consultarEvento(request, response);
		}
		
		if (url.equals("/evento/lista")){
			listarEventos(request, response);
		}
		
		if (url.equals("/evento/atualizar")){
			atualizarEvento(request, response);
		}
		
		if (url.equals("/evento/remover")){
			removerEvento(request, response);
		}
		
		if (url.equals("/evento/participarDeEvento")){
			participarDeEvento(request, response);
		}
		
		if (url.equals("/evento/semReserva")){
			listarEventosSemReserva(request, response);
		}
		
		if (url.equals("/evento/agendar")){
			agendarEvento(request, response);
		}
		
	}

	protected void cadastrarEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		List<Reserva> reservas = new ArrayList<Reserva>();
		List<Palestrante> palestrantes = new ArrayList<Palestrante>();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		try {
				
				Evento ev = new Evento(null, request.getParameter("tema"), request.getParameter("descricao"), Integer.parseInt(request.getParameter("vagas")));
				
				ev.setTipoEvento(TipoEvento.valueOf(request.getParameter("tipoEvento")));
				ev.setStatusEvento(StatusEvento.EM_CRIACAO);
				
				ev.setPessoas(pessoas);
				ev.setPalestrantes(palestrantes);
				ev.setReservas(reservas);
				ev.setVagasDisponiveis(ev.getVagas());
				
				daoEvento.inserir(ev);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("lista");
	}
	
	protected void consultarEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		List<Reserva> reservas = new ArrayList<Reserva>();
		List<Palestrante> palestrantes = new ArrayList<Palestrante>();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		try {
		
			Integer codigo = Integer.parseInt(request.getParameter("evento"));
			Evento ev = daoEvento.buscarPorId(codigo);
			
			ev.setPessoas(pessoas);
			ev.setPalestrantes(palestrantes);
			ev.setReservas(reservas);
			
			request.setAttribute("evento", ev);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../listarEvento_Admin.xhtml").forward(request, response);
	}
	
	protected void listarEventos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		Pessoa p = new Pessoa();
		
		String pagina = new String();
		
		p = (Pessoa) sessao.getAttribute("user");
		
		try {
		
			List<Evento> eventos = new ArrayList<Evento>();
			
			if (p == null || p.getTipo().equals(Tipo.PARTICIPANTE)) {
				
				eventos = daoEvento.buscarPorStatus(StatusEvento.EM_ANDAMENTO);
				eventos.addAll(daoEvento.buscarPorStatus(StatusEvento.INSCRICOES_ABERTAS));
				eventos.addAll(daoEvento.buscarPorStatus(StatusEvento.INSCRICOES_ENCERRADAS));
				eventos.addAll(daoEvento.buscarPorStatus(StatusEvento.ENCERRADO));
				
			}
			
			if (p != null) {
				
				if (p.getTipo().equals(Tipo.ADMIN) || p.getTipo().equals(Tipo.ORGANIZADOR)) {
					eventos = daoEvento.listarTodos();
				}
				
			}
			
			if (p == null || p.getTipo().equals(Tipo.PARTICIPANTE) || p.getTipo().equals(Tipo.ORGANIZADOR)) {
				
				pagina = "../listarEventos.xhtml";
				
			} else {
				
				if (p.getTipo().equals(Tipo.ADMIN)) {
					pagina = "../listarEventos_Admin.xhtml";
				}
			}
			
			request.getSession().setAttribute("listaEventos", eventos);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(pagina);
		
	}
	
	protected void atualizarEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		Integer codigo = Integer.parseInt(request.getParameter("evento"));
		
		try {
			
			Evento ev = daoEvento.buscarPorId(codigo);
			
			ev.setTema(request.getParameter("tema"));
			ev.setDescricao(request.getParameter("descricao"));
			ev.setVagas(Integer.parseInt(request.getParameter("vagas")));
			ev.setVagasDisponiveis(ev.getVagas()-ev.getPessoas().size());
			
			ev.setTipoEvento(TipoEvento.valueOf(request.getParameter("tipoEvento")));
			ev.setStatusEvento(StatusEvento.valueOf(request.getParameter("statusEvento")));
			
			daoEvento.atualizar(ev);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("lista").forward(request, response);
	}

	protected void removerEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		try {
			
			Integer codigo = Integer.parseInt(request.getParameter("evento"));
			Evento ev = daoEvento.buscarPorId(codigo);
			daoEvento.remover(ev);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("lista").forward(request, response);
	}
	
	protected void participarDeEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		Pessoa p = new Pessoa();
		
		String pagina = new String();
		
		p = (Pessoa) sessao.getAttribute("user");
		
		try {
			
			if (p == null) {
				pagina = "../cadastroParticipante.xhtml";
				response.sendRedirect(pagina);
				
			} else {
				
				if (p.getTipo().equals(Tipo.PARTICIPANTE)) {
					
					Integer codigo = Integer.parseInt(request.getParameter("evento"));
					Evento ev = daoEvento.buscarPorId(codigo);
					ev.getPessoas().add(p);
					ev.setVagasDisponiveis(ev.getVagas() - ev.getPessoas().size());
					p.getEventos().add(ev);
					
					daoEvento.atualizar(ev);
					
					pagina = "lista";
					
					request.getRequestDispatcher(pagina).forward(request, response);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	protected void listarEventosSemReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
				
		Pessoa p = new Pessoa();
		
		String pagina = new String();
		
		p = (Pessoa) sessao.getAttribute("user");
		
		try {
			
			List<Evento> eventos = new ArrayList<Evento>();
			
			if (p != null) {
				
				if (p.getTipo().equals(Tipo.ADMIN)) {
					eventos = daoEvento.buscarPorStatus(StatusEvento.EM_CRIACAO);
					pagina = "../listarEventosSemReserva.xhtml";
				}
				
			}
			
			request.getSession().setAttribute("listaEventos", eventos);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(pagina);

	}


	protected void agendarEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		Reserva r = new Reserva();
		
		Pessoa p = new Pessoa();
		
		String pagina = new String();
		
		p = (Pessoa) sessao.getAttribute("user");
		
		try {
			
			if (p == null) {
				pagina = "../cadastroParticipante.xhtml";
				response.sendRedirect(pagina);
				
			} else {
			
			Integer codigo = Integer.parseInt(request.getParameter("evento"));
			Evento ev = daoEvento.buscarPorId(codigo);
			
			r.setEvento(ev);
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("lista").forward(request, response);
	}

}
