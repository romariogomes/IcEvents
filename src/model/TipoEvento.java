package model;

public enum TipoEvento {

	SEMINARIO(0), PALESTRA(1), WORKSHOP(2), OUTRO(3);
	
	private Integer codigoTipo;

	private TipoEvento(Integer codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public Integer getCodigoTipo() {
		return codigoTipo;
	}
	
}
