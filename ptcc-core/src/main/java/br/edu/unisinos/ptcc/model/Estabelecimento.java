package br.edu.unisinos.ptcc.model;

public class Estabelecimento {
	private String nome;
	private Endereco endereco;
	private TipoEstabelecimento tipoEstabelecimento;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public TipoEstabelecimento getTipoEstabelecimento() {
		return tipoEstabelecimento;
	}

	public void setTipoEstabelecimento(TipoEstabelecimento tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
	}

	@Override
	public String toString() {
		return "Estabelecimento [nome=" + nome + ", endereco=" + endereco + ", tipoEstabelecimento=" + tipoEstabelecimento + "]";
	}
}
