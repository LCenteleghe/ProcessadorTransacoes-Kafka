package br.edu.unisinos.ptcc.model;

public class Estabelecimento {
	private String nome;
	private Endereco endereco;

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

	@Override
	public String toString() {
		return "Estabelecimento [nome=" + nome + ", endereco=" + endereco + "]";
	}
}
