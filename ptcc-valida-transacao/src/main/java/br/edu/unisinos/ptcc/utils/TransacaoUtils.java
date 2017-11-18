package br.edu.unisinos.ptcc.utils;

import br.edu.unisinos.ptcc.model.TipoEstabelecimento;
import br.edu.unisinos.ptcc.model.TransacaoCartao;

public class TransacaoUtils {

    public static boolean validaTransacao(TransacaoCartao transacao) {
        boolean isTransacaoValida = false;

        double valorTransacao = transacao.getValor();
        TipoEstabelecimento tipoEstabelecimento = transacao.getEstabelecimento().getTipoEstabelecimento();
        switch (tipoEstabelecimento) {
            case BAR_RESTAURANTE:
                isTransacaoValida = valorTransacao < 1000;
                break;
            case FARMACIA:
                isTransacaoValida = valorTransacao < 250;
                break;
            case PROVEDOR_INTERNET:
                isTransacaoValida = valorTransacao < 400;
                break;
            case TRANSPORTE_PUBLICO:
                isTransacaoValida = valorTransacao < 150;
                break;
            case VAREJO:
                isTransacaoValida = valorTransacao < 5000;
                break;
        }

        return isTransacaoValida;
    }
}
