package model;

import java.util.List;

public class Organizador extends Pessoa {
	
	private List<Evento> organizacaoEventos; 
	
	public Organizador() {
		
	}

	public List<Evento> getOrganizacaoEventos() {
		return organizacaoEventos;
	}

	public void setOrganizacaoEventos(List<Evento> organizacaoEventos) {
		this.organizacaoEventos = organizacaoEventos;
	}

	@Override
	public String toString() {
		
		return super.toString() + "Organizador [organizacaoEventos=" + organizacaoEventos + "]";
	}
	
	
}
