package model;

import java.util.List;

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
import javax.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa {
	
	@Id
	@GeneratedValue
	private Integer codigoPessoa;
	
	@Column
	private String nome;
	
	@Column
	private String email;
	
	@Column
	private String senha;
	
	@Column
	@Enumerated(EnumType.ORDINAL)
	private Tipo tipo;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "pessoas")
	List<Evento> eventos;
	
	public Pessoa() {
		
	}

	public Pessoa(Integer codigoPessoa, String nome, String email, String senha, Tipo tipo) {
		super();
		this.codigoPessoa = codigoPessoa;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.tipo = tipo;
	}
	
	public Pessoa(Integer codigoPessoa, String nome, String email, Tipo tipo) {
		super();
		this.codigoPessoa = codigoPessoa;
		this.nome = nome;
		this.email = email;
		this.tipo = tipo;
	}

	public Integer getCodigoPessoa() {
		return codigoPessoa;
	}

	public void setCodigoPessoa(Integer codigoPessoa) {
		this.codigoPessoa = codigoPessoa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	@Override
	public String toString() {
		return "Pessoa [codigoPessoa=" + codigoPessoa + ", nome=" + nome + ", email=" + email + ", senha=" + senha
				+ ", tipo=" + tipo + ", eventos=" + eventos + "]";
	}

}
