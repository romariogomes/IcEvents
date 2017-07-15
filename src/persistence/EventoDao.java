package persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import model.Evento;
import model.Pessoa;
import model.StatusEvento;

public class EventoDao{
	
	Session s;
	Transaction t;
	
	public void inserir(Evento ev) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.save(ev);
		t.commit();
		s.close();
		
	}
	
	public Evento buscarPorId(Integer codigo) throws Exception {
		
		Evento ev = new Evento();
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		ev = (Evento) s.get(Evento.class, codigo); 
		s.close();
		
		return ev;
		
	}
	
	public Evento buscarPorTema(String tema) throws Exception {
		
		Evento ev = new Evento();
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Evento> resultList = (List<Evento>) s.createQuery("FROM evento WHERE tema LIKE :temaBuscado ORDER BY tema").setString("temaBuscado", "%" + tema + "%").list();
		s.close();
		
		return ev;
		
	}
	
	public List<Evento> listarTodos() throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		
		Criteria c = s.createCriteria(Evento.class);
		
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<Evento> listaEventos = c.list();
		
		s.close();
		
		return listaEventos;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Evento> buscarPorStatus(StatusEvento status) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		
		List<Evento> listaEventos = new ArrayList<Evento>();
		
		listaEventos = s.createQuery("from Evento where statusEvento=" + status.ordinal()).list();	

		s.close();
		
		return listaEventos;
		
	}
	
	public void atualizar(Evento ev) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.update(ev);
		t.commit();
		s.close();
		
	}
	
	public void remover(Evento ev) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.delete(ev);
		t.commit();
		s.close();
		
	}
	
	public void removerParticipante(Pessoa p) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.delete("evento_pessoa", p);
		t.commit();
		s.close();
		
	}
	
}
