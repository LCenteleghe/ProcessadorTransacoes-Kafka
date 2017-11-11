package br.edu.unisinos.ptcc.model;

import java.util.Date;

public class TransacaoCartao {
	private String codigo;
	private Long numeroCartao;
	private Double valor;
	private Date data;
	private Estabelecimento estabelecimento;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getNumeroCartao() {
		return numeroCartao;
	}

	public void setNumeroCartao(Long numeroCartao) {
		this.numeroCartao = numeroCartao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}

	@Override
	public String toString() {
		return "TransacaoCartao [codigo=" + codigo + ", numeroCartao=" + numeroCartao + ", valor=" + valor + ", data=" + data + ", estabelecimento=" + estabelecimento + "]";
	}
}
