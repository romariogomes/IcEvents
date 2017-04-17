package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "evento")
public class Evento {
	
	@Id
	@GeneratedValue
	private Integer codigoEvento;
	
	@Column
	private String tema;
	
	@Column
	private String descricao;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private TipoEvento tipoEvento;
	
	@ManyToMany
	@JoinTable(name = "palestra", joinColumns={@JoinColumn(name = "evento_id")}, inverseJoinColumns={@JoinColumn(name = "palestrante_id")})
	private List<Palestrante> palestrantes;
	
	@ManyToMany
	@JoinTable(name = "participantes", joinColumns={@JoinColumn(name = "evento_id")}, inverseJoinColumns={@JoinColumn(name = "pessoa_id")})
	private List<Pessoa> participantes;
	
	@ManyToMany
	@JoinTable(name = "organizadores", joinColumns={@JoinColumn(name = "evento_id")}, inverseJoinColumns={@JoinColumn(name = "pessoa_id")})
	private List<Pessoa> organizadores;
	
	@OneToMany (mappedBy = "evento")
	private List<Reserva> reservas;
	
	public Evento() {
		palestrantes = new ArrayList<Palestrante>();
		participantes = new ArrayList<Pessoa>();
		organizadores = new ArrayList<Pessoa>();
		reservas = new ArrayList<Reserva>();
	}
	
	public Evento(Integer codigoEvento, String tema, String descricao, List<Palestrante> palestrantes,
			List<Pessoa> participantes, List<Pessoa> organizadores, List<Reserva> reservas) {
		super();
		this.codigoEvento = codigoEvento;
		this.tema = tema;
		this.descricao = descricao;
		this.palestrantes = new ArrayList<Palestrante>();
		this.participantes = new ArrayList<Pessoa>();
		this.organizadores = new ArrayList<Pessoa>();
		this.reservas = new ArrayList<Reserva>();
	}

	public Evento(Integer codigoEvento, String tema, String descricao, List<Palestrante> palestrantes) {
		super();
		this.codigoEvento = codigoEvento;
		this.tema = tema;
		this.descricao = descricao;
		this.palestrantes = new ArrayList<Palestrante>();
	}
	
	public Evento(Integer codigoEvento, String tema, String descricao, TipoEvento tipoEvento) {
		super();
		this.codigoEvento = codigoEvento;
		this.tema = tema;
		this.descricao = descricao;
		this.tipoEvento = tipoEvento;
	}

	public Evento(Integer codigoEvento, String tema, String descricao) {
		super();
		this.codigoEvento = codigoEvento;
		this.tema = tema;
		this.descricao = descricao;
	}

	public Integer getCodigoEvento() {
		return codigoEvento;
	}

	public void setCodigoEvento(Integer codigoEvento) {
		this.codigoEvento = codigoEvento;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<Palestrante> getPalestrantes() {
		return palestrantes;
	}

	public void setPalestrantes(List<Palestrante> palestrantes) {
		this.palestrantes = palestrantes;
	}

	public List<Pessoa> getParticipantes() {
		return participantes;
	}

	public void setParticipantes(List<Pessoa> participantes) {
		this.participantes = participantes;
	}

	public List<Pessoa> getOrganizadores() {
		return organizadores;
	}

	public void setOrganizadores(List<Pessoa> organizadores) {
		this.organizadores = organizadores;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	@Override
	public String toString() {
		return "Evento [codigoEvento=" + codigoEvento + ", tema=" + tema + ", descricao=" + descricao + ", tipoEvento="
				+ tipoEvento + ", palestrantes=" + palestrantes + ", participantes=" + participantes
				+ ", organizadores=" + organizadores + ", reservas=" + reservas + "]";
	}

}
