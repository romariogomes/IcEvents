package persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Sala;

public class SalaDao{
	
	Session s;
	Transaction t;
	
	public void inserir(Sala sl) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.save(sl);
		t.commit();
		s.close();
		
	}
	
	public Sala buscarPorId(Integer codigo) throws Exception {
		
		Sala sl = new Sala();
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		sl = (Sala) s.get(Sala.class, codigo); 
		s.close();
		
		return sl;
		
	}
	
	public List<Sala> listarTodos() throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Sala> listaSalas = s.createCriteria(Sala.class).list();
		s.close();
		
		return listaSalas;
		
	}
	
	public void atualizar(Sala sl) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.update(sl);
		t.commit();
		s.close();
		
	}
	
	public void remover(Sala sl) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.delete(sl);
		t.commit();
		s.close();
		
	}
	
}
