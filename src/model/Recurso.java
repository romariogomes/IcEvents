package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table (name = "recurso")
public class Recurso {
	
	@Id
	@GeneratedValue
	private Integer codigoRecurso;
	
	@Column
	private String descricao;
	
	@ManyToMany(mappedBy = "recursos", fetch = FetchType.LAZY)
	private List<Sala> salas;
	
	public Recurso() {
		salas = new ArrayList<Sala>();
	}

	public Recurso(Integer codigoRecurso, String descricao, List<Sala> salas) {
		super();
		this.codigoRecurso = codigoRecurso;
		this.descricao = descricao;
		this.salas = new ArrayList<Sala>();
	}

	public Recurso(Integer codigoRecurso, String descricao) {
		super();
		this.codigoRecurso = codigoRecurso;
		this.descricao = descricao;
	}

	public Integer getCodigoRecurso() {
		return codigoRecurso;
	}

	public void setCodigoRecurso(Integer codigoRecurso) {
		this.codigoRecurso = codigoRecurso;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

	@Override
	public String toString() {
		return "Recurso [codigoRecurso=" + codigoRecurso + ", descricao=" + descricao + ", salas=" + salas + "]";
	}
	
}
