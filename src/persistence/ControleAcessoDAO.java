package persistence;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import model.Pessoa;

public class ControleAcessoDAO {
	
	Session s;
	Transaction t;
	
	public Pessoa buscarPorEmailSenha(String email, String senha) throws Exception {
		
		Pessoa p = new Pessoa();
		
		s = HibernateUtil.getSessionFactory().openSession();
		
		Criteria c = s.createCriteria(Pessoa.class);
		c.add(Restrictions.and(Restrictions.eq("email", email), Restrictions.eq("senha", senha)));
		p = (Pessoa) c.uniqueResult();
		
		s.close();
		
		return p;
		
	}

}
