package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "palestrante")
public class Palestrante {
	
	@Id
	@GeneratedValue
	private Integer codigoPalestrante;
	
	@Column
	private String nome;
	
	@Column
	private String descricao;
	
	@ManyToMany(mappedBy = "palestrantes")
	private List<Evento> eventos;
	
	public Palestrante() {
		eventos = new ArrayList<Evento>();
	}
	
	public Palestrante(Integer codigoPalestrante, String nome, String descricao, List<Evento> eventos) {
		super();
		this.codigoPalestrante = codigoPalestrante;
		this.nome = nome;
		this.descricao = descricao;
		this.eventos = new ArrayList<Evento>();
	}
	
	public Palestrante(Integer codigoPalestrante, String nome, String descricao) {
		super();
		this.codigoPalestrante = codigoPalestrante;
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public Integer getCodigoPalestrante() {
		return codigoPalestrante;
	}

	public void setCodigoPalestrante(Integer codigoPalestrante) {
		this.codigoPalestrante = codigoPalestrante;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
	public String toString() {
		return "Palestrante [codigoPalestrante=" + codigoPalestrante + ", nome=" + nome + ", descricao=" + descricao
				+ ", eventos=" + eventos + "]";
	}

}
