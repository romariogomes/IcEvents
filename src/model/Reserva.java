package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

@Transactional
@Entity
@Table(name = "reserva")
public class Reserva {
	
	@Id
	@GeneratedValue
	private Integer codigoReserva;
	
	@Temporal(TemporalType.DATE)
	@Column
	private Date data;
	
	@Column
	private String hora;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sala_id", nullable = false)
	private Sala salaReservada;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "evento_id", nullable = false)
	private Evento evento;
	
	public Reserva() {
		salaReservada = new Sala();
		evento = new Evento();
	}

	public Reserva(Integer codigoReserva, Date data, String hora) {
		super();
		this.codigoReserva = codigoReserva;
		this.data = data;
		this.hora = hora;
	}

	public Integer getCodigoReserva() {
		return codigoReserva;
	}

	public void setCodigoReserva(Integer codigoReserva) {
		this.codigoReserva = codigoReserva;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Sala getSalaReservada() {
		return salaReservada;
	}

	public void setsalaReservada(Sala salaReservada) {
		this.salaReservada = salaReservada;
	}
	
	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Reserva(Integer codigoReserva, Date data, String hora, Sala salaReservada, Evento evento) {
		super();
		this.codigoReserva = codigoReserva;
		this.data = data;
		this.hora = hora;
		this.salaReservada = salaReservada;
		this.evento = evento;
	}

}
