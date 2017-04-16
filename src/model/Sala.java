package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "sala")
public class Sala {
	
	@Id
	@GeneratedValue
	private Integer codigoSala;
	
	@Column
	private String numero;
	
	@Column
	private Integer capacidade;
	
	@ManyToMany
	@JoinTable(name = "sala_recurso", joinColumns={@JoinColumn(name = "sala_id")}, inverseJoinColumns={@JoinColumn(name = "recurso_id")})
	private Set<Recurso> recursos;
	
	@OneToMany(mappedBy = "salaReservada", fetch = FetchType.EAGER)
	private List<Reserva> reservas;
	
	public Sala() {
//		recursos = new ArrayList<Recurso>();
		reservas = new ArrayList<Reserva>();
	}
	
	public Sala(Integer codigoSala, String numero, Integer capacidade, List<Recurso> recursos, List<Reserva> reservas) {
		super();
		this.codigoSala = codigoSala;
		this.numero = numero;
		this.capacidade = capacidade;
//		this.recursos = new ArrayList<Recurso>();
		this.reservas = new ArrayList<Reserva>();
	}

	public Sala(Integer codigoSala, String numero, Integer capacidade) {
		super();
		this.codigoSala = codigoSala;
		this.numero = numero;
		this.capacidade = capacidade;
	}

	public Integer getCodigoSala() {
		return codigoSala;
	}

	public void setCodigoSala(Integer codigoSala) {
		this.codigoSala = codigoSala;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Set<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(Set<Recurso> recursos) {
		this.recursos = recursos;
	}

	public Integer getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(Integer capacidade) {
		this.capacidade = capacidade;
	}

	@Override
	public String toString() {
		return "Sala [codigoSala=" + codigoSala + ", numero=" + numero + ", capacidade=" + capacidade + ", recursos="
				+ recursos + "]";
	}
}
