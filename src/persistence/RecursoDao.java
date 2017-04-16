package persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Recurso;

public class RecursoDao{
	
	Session s;
	Transaction t;
	
	public void inserir(Recurso r) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.save(r);
		t.commit();
		s.close();
		
	}
	
	public Recurso buscarPorId(Integer codigo) throws Exception {
		
		Recurso r = new Recurso();
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		r = (Recurso) s.get(Recurso.class, codigo); 
		s.close();
		
		return r;
		
	}
	
	public List<Recurso> listarTodos() throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Recurso> listaRecursos = s.createCriteria(Recurso.class).list();
		s.close();
		
		return listaRecursos;
		
	}
	
	public void atualizar(Recurso r) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.update(r);
		t.commit();
		s.close();
		
	}
	
	public void remover(Recurso r) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.delete(r);
		t.commit();
		s.close();
		
	}
	
}
