package model;

public enum Tipo {

	ADMIN(0), ORGANIZADOR(1), PARTICIPANTE(2);
	
	private Integer codigoTipo;

	private Tipo(Integer codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public Integer getCodigoTipo() {
		return codigoTipo;
	}
	
}
