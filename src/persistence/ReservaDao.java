package persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.mysql.fabric.xmlrpc.base.Array;

import model.Recurso;
import model.Reserva;
import model.Sala;

public class ReservaDao{
	
	Session s;
	Transaction t;
	
	public void inserir(Reserva r) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.save(r);
		t.commit();
		s.close();
		
	}
	
	public Reserva buscarPorId(Integer codigo) throws Exception {
		
		Reserva r = new Reserva();
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		r = (Reserva) s.get(Reserva.class, codigo); 
		s.close();
		
		return r;
		
	}
	
	public List<Reserva> listarTodos() throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		
		Criteria c = s.createCriteria(Reserva.class);
		
		c.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		@SuppressWarnings("unchecked")
		List<Reserva> listaReservas = c.list();
		
		for (int i = 0; i < listaReservas.size(); i++) {
			Sala sl = new Sala();
			sl.setCodigoSala(listaReservas.get(i).getSalaReservada().getCodigoSala());
			sl.setNumero(listaReservas.get(i).getSalaReservada().getNumero());
			sl.setCapacidade(listaReservas.get(i).getSalaReservada().getCapacidade());
			sl.setRecursos(listaReservas.get(i).getSalaReservada().getRecursos());
			listaReservas.get(i).setsalaReservada(sl);
		}

		s.close();
		
		return listaReservas;
		
	}
	
	public void atualizar(Reserva r) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.update(r);
		t.commit();
		s.close();
		
	}
	
	public void remover(Reserva r) throws Exception {
		
		s = HibernateUtil.getSessionFactory().openSession();
		t = s.beginTransaction();
		s.delete(r);
		t.commit();
		s.close();
		
	}
	
}
