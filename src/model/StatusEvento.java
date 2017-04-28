package model;

public enum StatusEvento {

	EM_CRIACAO(0), INSCRICOES_ABERTAS(1), INSCRICOES_ENCERRADAS(2), EM_ANDAMENTO(3), ENCERRADO(4);
	
	private Integer codigoTipo;

	private StatusEvento(Integer codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public Integer getCodigoTipo() {
		return codigoTipo;
	}
	
}
