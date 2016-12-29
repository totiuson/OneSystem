/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Operacao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class NotaEmitidaBuilder {

    private Long id;
    private Pessoa pessoa;
    private Operacao operacao;
    private List<ItemEmitido> itensEmitidos;
    private List<Titulo> titulos;
    private ListaDePreco listaDePreco;
    private BigDecimal desconto = BigDecimal.ZERO;
    private BigDecimal acrescimo = BigDecimal.ZERO;
    private FormaDeRecebimento formaDeRecebimento;

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
    
    public NotaEmitidaBuilder comItensEmitidos(List<ItemEmitido> itensEmitidos) {
        this.itensEmitidos = itensEmitidos;
        return this;
    }

    public NotaEmitidaBuilder comTitulos(List<Titulo> titulos) {
        this.titulos = titulos;
        return this;
    }
    
    public NotaEmitidaBuilder comListaDePreco(ListaDePreco listaDePreco) {
        this.listaDePreco = listaDePreco;
        return this;
    }
    
    public NotaEmitidaBuilder comDesconto(BigDecimal desconto) {
        this.desconto = desconto;
        return this;
    }
    
    public NotaEmitidaBuilder comAcrescimo(BigDecimal acrescimo) {
        this.acrescimo = acrescimo;
        return this;
    }

    public NotaEmitidaBuilder comFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
        return this;
    }

    public NotaEmitida construir() throws DadoInvalidoException {
        return new NotaEmitida(id, pessoa, operacao, itensEmitidos, titulos, listaDePreco, desconto, acrescimo, formaDeRecebimento);
    }

}
