package model;

import java.util.ArrayList;
import java.util.List;

public class Participante extends Pessoa {
	
	private List<Evento> participacaoEventos;

	public Participante() {
		participacaoEventos = new ArrayList<Evento>();
	}

	public List<Evento> getParticipacaoEventos() {
		return participacaoEventos;
	}

	public void setParticipacaoEventos(List<Evento> participacaoEventos) {
		this.participacaoEventos = participacaoEventos;
	}

	@Override
	public String toString() {
		return super.toString() + "Participante [participacaoEventos=" + participacaoEventos + "]";
	}

	

}
