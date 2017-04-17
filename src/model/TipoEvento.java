package model;

public enum TipoEvento {

	SEMINARIO(1), PALESTRA(2), WORKSHOP(3), OUTRO(4);
	
	private Integer codigoTipo;

	private TipoEvento(Integer codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public Integer getCodigoTipo() {
		return codigoTipo;
	}
	
}
