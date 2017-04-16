package persistence;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Pessoa;

public class PessoaDao{
	
	Session s;
	Transaction t;
	
	public void inserir(Pessoa p) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.save(p);
		t.commit();
		s.close();
		
	}
	
	public Pessoa buscarPorId(Integer codigo) throws Exception {
		
		Pessoa p = new Pessoa();
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		p = (Pessoa) s.get(Pessoa.class, codigo); 
		s.close();
		
		return p;
		
	}
	
	public List<Pessoa> listarTodos() throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		
		@SuppressWarnings("unchecked")
		List<Pessoa> listaPessoas = s.createCriteria(Pessoa.class).list();
		s.close();
		
		return listaPessoas;
		
	}
	
	public void atualizar(Pessoa p) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.update(p);
		t.commit();
		s.close();
		
	}
	
	public void remover(Pessoa p) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.delete(p);
		t.commit();
		s.close();
		
	}
	
	
}
