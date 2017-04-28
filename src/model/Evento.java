package model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@ManagedBean
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
	private Integer vagas;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private TipoEvento tipoEvento;
	
	@ManyToMany
	@JoinTable(name = "palestra", joinColumns={@JoinColumn(name = "evento_id")}, inverseJoinColumns={@JoinColumn(name = "palestrante_id")})
	private List<Palestrante> palestrantes;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "evento_pessoa", joinColumns={@JoinColumn(name = "evento_id")}, inverseJoinColumns={@JoinColumn(name = "pessoa_id")})
	private List<Pessoa> pessoas;
	
	@OneToMany (mappedBy = "evento")
	private List<Reserva> reservas;
	
	public Evento() {
		palestrantes = new ArrayList<Palestrante>();
		pessoas = new ArrayList<Pessoa>();
		reservas = new ArrayList<Reserva>();
	}
	
	public Evento(Integer codigoEvento, String tema, String descricao, List<Palestrante> palestrantes,
			List<Pessoa> pessoas, List<Reserva> reservas) {
		super();
		this.codigoEvento = codigoEvento;
		this.tema = tema;
		this.descricao = descricao;
		this.palestrantes = new ArrayList<Palestrante>();
		this.pessoas = new ArrayList<Pessoa>();
		this.reservas = new ArrayList<Reserva>();
	}

	public Evento(Integer codigoEvento, String tema, String descricao, List<Palestrante> palestrantes) {
		super();
		this.codigoEvento = codigoEvento;
		this.tema = tema;
		this.descricao = descricao;
		this.palestrantes = new ArrayList<Palestrante>();
	}
	
	public Evento(Integer codigoEvento, String tema, String descricao, Integer vagas) {
		super();
		this.codigoEvento = codigoEvento;
		this.tema = tema;
		this.descricao = descricao;
		this.vagas = vagas;
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
	
	public Integer getVagas() {
		return vagas;
	}

	public void setVagas(Integer vagas) {
		this.vagas = vagas;
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

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
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
				+ tipoEvento + ", palestrantes=" + palestrantes + ", pessoas=" + pessoas
				+ ", reservas=" + reservas + "]";
	}

}
