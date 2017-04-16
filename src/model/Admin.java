package model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Pessoa{
	
	private List<Evento> participacaoEventos;
	private List<Evento> organizacaoEventos;
	
	public Admin() {
		participacaoEventos = new ArrayList<Evento>();
		organizacaoEventos = new ArrayList<Evento>();
	}

	public Admin(List<Evento> participacaoEventos, List<Evento> organizacaoEventos) {
		super();
		this.participacaoEventos = new ArrayList<Evento>();
		this.organizacaoEventos = new ArrayList<Evento>();
	}

	public List<Evento> getParticipacaoEventos() {
		return participacaoEventos;
	}

	public void setParticipacaoEventos(List<Evento> participacaoEventos) {
		this.participacaoEventos = participacaoEventos;
	}

	public List<Evento> getOrganizacaoEventos() {
		return organizacaoEventos;
	}

	public void setOrganizacaoEventos(List<Evento> organizacaoEventos) {
		this.organizacaoEventos = organizacaoEventos;
	}

	@Override
	public String toString() {
		return super.toString() + "Admin [participacaoEventos=" + participacaoEventos + ", organizacaoEventos=" + organizacaoEventos + "]";
	}
	
}
