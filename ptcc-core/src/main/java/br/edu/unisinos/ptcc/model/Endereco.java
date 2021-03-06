package br.edu.unisinos.ptcc.model;

public class Endereco {
	private String cidade;
	private String estado;
	private String pais;
	private String cep;
	private String logradouro;

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Override
	public String toString() {
		return "Endereco [cidade=" + cidade + ", estado=" + estado + ", pais=" + pais + ", cep=" + cep + ", logradouro=" + logradouro + "]";
	}
}
