/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ItemDeNota;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Comanda;
import br.com.onesystem.domain.Condicional;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.exception.impl.FDadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.war.builder.CobrancaBV;
import br.com.onesystem.war.builder.ItemDeNotaBV;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaEmitidaBuilder {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemDeNota> itens;
    private ListaDePreco listaDePreco;
    private FormaDeRecebimento formaDeRecebimento;
    private List<Cobranca> cobrancas;
    private Moeda moedaPadrao;
    private Orcamento orcamento;
    private List<ValorPorCotacao> valorPorCotacao;
    private BigDecimal totalEmDinheiro;
    private BigDecimal acrescimo;
    private BigDecimal desconto;
    private BigDecimal despesaCobranca;
    private BigDecimal frete;
    private BigDecimal aFaturar;
    private Nota notaDeOrigem;
    private Comanda comanda;
    private Condicional condicional;

    public NotaEmitidaBuilder comId(Long id) {
        this.id = id;
        return this;
    }

    public NotaEmitidaBuilder comPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
        return this;
    }

    public NotaEmitidaBuilder comOperacao(Operacao operacao) {
        this.operacao = operacao;
        return this;
    }

    public NotaEmitidaBuilder comItens(List<ItemDeNota> itens) throws DadoInvalidoException {
        if (id == null) {
            List<ItemDeNota> itensCol = itens.stream().collect(Collectors.toList());
            if (itensCol != null && !itensCol.isEmpty()) {
                for (ItemDeNota i : itensCol) {
                    itensCol.set(itensCol.indexOf(i), new ItemDeNotaBV(i).construir());
                }
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Itens_Devem_Ser_Informados"));
            }
            this.itens = itensCol;
        } 
        return this;
    }

    public NotaEmitidaBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }

    public NotaEmitidaBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public NotaEmitidaBuilder comCheque(List<Cobranca> parcelas) {
        this.cobrancas = parcelas;
        return this;
    }

    public NotaEmitidaBuilder comMoedaPadrao(Moeda moedaPadrao) {
        this.moedaPadrao = moedaPadrao;
        return this;
    }

    public NotaEmitidaBuilder comOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
        return this;
    }

    public NotaEmitidaBuilder comCobrancas(List<Cobranca> cobrancas) throws DadoInvalidoException {
        if (id == null) {
            if (cobrancas != null && !cobrancas.isEmpty()) {
                for (Cobranca i : cobrancas) {
                    if (i.getId() != null) {
                        if (i instanceof Cheque) {
                            cobrancas.set(cobrancas.indexOf(i), new CobrancaBV(i).construirCheque());
                        }
                        if (i instanceof BoletoDeCartao) {
                            cobrancas.set(cobrancas.indexOf(i), new CobrancaBV(i).construirBoletoDeCartao());
                        }
                        if (i instanceof Titulo) {
                            cobrancas.set(cobrancas.indexOf(i), new CobrancaBV(i).construirTitulo());
                        }
                    }
                }
            }
            this.cobrancas = cobrancas;
        }
        return this;
    }

    public NotaEmitidaBuilder comValorPorCotacao(List<ValorPorCotacao> valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
        return this;
    }

    public NotaEmitidaBuilder comTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
        return this;
    }

    public NotaEmitidaBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public NotaEmitidaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }

    public NotaEmitidaBuilder comDespesaCobranca(BigDecimal despesaCobranca) {
        this.despesaCobranca = despesaCobranca;
        return this;
    }

    public NotaEmitidaBuilder comFrete(BigDecimal frete) {
        this.frete = frete;
        return this;
    }

    public NotaEmitidaBuilder comAFaturar(BigDecimal aFaturar) {
        this.aFaturar = aFaturar;
        return this;
    }

    public NotaEmitidaBuilder comNotaDeOrigem(Nota notaDeOrigem) {
        this.notaDeOrigem = notaDeOrigem;
        return this;
    }
    
    public NotaEmitidaBuilder comComanda(Comanda comanda) {
        this.comanda = comanda;
        return this;
    }
    
    public NotaEmitidaBuilder comCondicional(Condicional condicional) {
        this.condicional = condicional;
        return this;
    }

    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitida(id, pessoa, operacao, itens, formaDeRecebimento, listaDePreco, cobrancas, moedaPadrao, orcamento, valorPorCotacao, desconto, acrescimo, despesaCobranca, frete, aFaturar, totalEmDinheiro, notaDeOrigem, comanda, condicional);
    }

}
