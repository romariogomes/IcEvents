package model;

public enum Tipo {

	ADMIN(1), ORGANIZADOR(2), PARTICIPANTE(3);
	
	private Integer codigoTipo;

	private Tipo(Integer codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public Integer getCodigoTipo() {
		return codigoTipo;
	}
	
}
