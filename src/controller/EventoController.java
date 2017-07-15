package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import model.Evento;
import model.Palestrante;
import model.Pessoa;
import model.Reserva;
import model.Sala;
import model.StatusEvento;
import model.Tipo;
import model.TipoEvento;
import persistence.EventoDao;
import persistence.PessoaDao;
import persistence.ReservaDao;
import persistence.SalaDao;

@Transactional
@WebServlet("/EventoController")
public class EventoController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private EventoDao daoEvento;
	private PessoaDao daoPessoa;
	private SalaDao daoSala;
	private ReservaDao daoReserva;
	
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
			daoSala = new SalaDao();
			daoReserva = new ReservaDao();
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
		
		if (url.equals("/evento/comReserva")){
			listarEventosComReserva(request, response);
		}
		
		if (url.equals("/evento/agendar")){
			agendarEvento(request, response);
		}
		
		if (url.equals("/evento/finalizarReserva")){
			finalizarReserva(request, response);
		}
		
		if (url.equals("/evento/meusEventos")){
			consultarMeusEventos(request, response);
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
			List<Reserva> eventosReservados = new ArrayList<Reserva>();
			
			if (p == null || p.getTipo().equals(Tipo.PARTICIPANTE)) {
				
				eventosReservados = daoReserva.listarTodos();
				Iterator<Reserva> iterator = eventosReservados.iterator();
				
				while (iterator.hasNext()) {
					Reserva r = iterator.next();
					if (r.getEvento().getStatusEvento().equals(StatusEvento.RESERVADO)) {
						iterator.remove();
					}
					
				}
				
			}
			
			if (p != null) {
				
				if (p.getTipo().equals(Tipo.ADMIN) || p.getTipo().equals(Tipo.ORGANIZADOR)) {
					eventos = daoEvento.listarTodos();
				}
				
			}
			
			if (p == null || p.getTipo().equals(Tipo.PARTICIPANTE)) {
				
				request.getSession().setAttribute("listaEventos", eventosReservados);
				pagina = "../listarEventos.xhtml";
				
			}
			else {
				request.getSession().setAttribute("listaEventos", eventos);
				pagina = "../listarEventos.xhtml";
				
				if (p.getTipo().equals(Tipo.ADMIN)) {
					pagina = "../listarEventos_Admin.xhtml";
				}
			}

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

	protected void listarEventosComReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
				
		Pessoa p = new Pessoa();
		
		String pagina = new String();
		
		p = (Pessoa) sessao.getAttribute("user");
		
		try {
			
			List<Reserva> eventosReservados = new ArrayList<Reserva>();
			eventosReservados = daoReserva.listarTodos();
			
			request.getSession().setAttribute("listaEventosReservados", eventosReservados);
			pagina = "../listarEventosComReserva.xhtml";

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect(pagina);

	}

	
	protected void agendarEvento(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
			
			List<Sala> Salas = new ArrayList<Sala>();
			Salas = daoSala.listarTodos();
			
			request.getSession().setAttribute("listaSalas", Salas);
			request.setAttribute("evento", ev);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../adicionarReserva.xhtml").forward(request, response);
	}
	
	protected void finalizarReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		Integer codigo = Integer.parseInt(request.getParameter("evento"));
		Integer codigoSala = Integer.parseInt(request.getParameter("numeroSala"));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Reserva> reservas = new ArrayList<Reserva>();
		List<Palestrante> palestrantes = new ArrayList<Palestrante>();
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		Reserva r = new Reserva();
		
		try {
			
			Evento ev = daoEvento.buscarPorId(codigo);
			
			r.setData(df.parse(request.getParameter("data")));
			r.setHora(request.getParameter("horaIni"));
			r.setEvento(ev);
			
			ev.setStatusEvento(StatusEvento.RESERVADO);
		    daoEvento.atualizar(ev);
		    
			reservas.add(r);
			
			Sala s = daoSala.buscarPorId(codigoSala);
			
			ev.setPessoas(pessoas);
			ev.setPalestrantes(palestrantes);			
			ev.setReservas(reservas);
			
			r.setsalaReservada(s);
			
			daoReserva.inserir(r);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.sendRedirect("comReserva");
	}
	
	protected void consultarMeusEventos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sessao = request.getSession();
		
		List<Evento> listaEventos = new ArrayList<Evento>();
//		List<Reserva> reservas = new ArrayList<Reserva>();
//		List<Palestrante> palestrantes = new ArrayList<Palestrante>();
//		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		Pessoa p = new Pessoa();
		
		try {
			
			p = (Pessoa) sessao.getAttribute("user");
			
			listaEventos = p.getEventos();
			
//			Integer codigo = Integer.parseInt(request.getParameter("evento"));
//			Evento ev = daoEvento.buscarPorId(codigo);
			
//			ev.setPessoas(pessoas);
//			ev.setPalestrantes(palestrantes);
//			ev.setReservas(reservas);
			
			request.setAttribute("listaEventos", listaEventos);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("../listarMeusEventos.xhtml").forward(request, response);
	}
	
}
