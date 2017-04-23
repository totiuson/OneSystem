package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemEmitido;
import br.com.onesystem.domain.ItemRecebido;
import br.com.onesystem.domain.OperacaoDeEstoque;
import br.com.onesystem.exception.DadoInvalidoException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Rafael
 */
public class EstoqueBuilder {

    private Long id;
    private Deposito deposito;
    private Item item;
    private BigDecimal saldo;
    private OperacaoDeEstoque operacaoDeEstoque;
    private Date emissao = new Date();
    private ItemEmitido itemEmitido;
    private ItemRecebido itemRecebido;
    private AjusteDeEstoque ajusteDeEstoque;

    public EstoqueBuilder comID(Long ID) {
        this.id = ID;
        return this;
    }

    public EstoqueBuilder comDeposito(Deposito deposito) {
        this.deposito = deposito;
        return this;
    }

    public EstoqueBuilder comItem(Item item) {
        this.item = item;
        return this;
    }

    public EstoqueBuilder comSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public EstoqueBuilder comOperacaoDeEstoque(OperacaoDeEstoque operacaoDeEstoque) {
        this.operacaoDeEstoque = operacaoDeEstoque;
        return this;
    }

    public EstoqueBuilder comEmissao(Date emissao) {
        this.emissao = emissao;
        return this;
    }

    public EstoqueBuilder comItemEmitido(ItemEmitido itemEmitido) {
        this.itemEmitido = itemEmitido;
        return this;
    }

    public EstoqueBuilder comAjusteDeEstoque(AjusteDeEstoque ajusteDeEstoque) {
        this.ajusteDeEstoque = ajusteDeEstoque;
        return this;
    }

    public EstoqueBuilder comItemRecebido(ItemRecebido itemRecebido) {
        this.itemRecebido = itemRecebido;
        return this;
    }

    public Estoque construir() throws DadoInvalidoException {
        return new Estoque(id, item, saldo, deposito, emissao, itemEmitido, itemRecebido, ajusteDeEstoque, operacaoDeEstoque);
    }

}
