package br.com.onesystem.war.builder;

import br.com.onesystem.domain.AjusteDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Estoque;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.builder.AjusteDeEstoqueBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.OperacaoFisica;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AjusteDeEstoqueBV implements Serializable {

    private Long id;
    private String observacao;
    private Deposito deposito;
    private Item item;    
    private Date emissao = new Date();
    private BigDecimal quantidade;
    private OperacaoFisica operacao;
    private Estoque estoque;


    public AjusteDeEstoqueBV(AjusteDeEstoque ajusteDeEstoqueSelecionada) {
        this.id = ajusteDeEstoqueSelecionada.getId();
        this.observacao = ajusteDeEstoqueSelecionada.getObservacao();
        this.deposito = ajusteDeEstoqueSelecionada.getDeposito();
        this.item = ajusteDeEstoqueSelecionada.getItem();
        this.quantidade = ajusteDeEstoqueSelecionada.getQuantidade();
        this.emissao = ajusteDeEstoqueSelecionada.getEmissao();
        this.operacao = ajusteDeEstoqueSelecionada.getOperacao();
        this.estoque = ajusteDeEstoqueSelecionada.getEstoque();
    }

    public AjusteDeEstoqueBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public void setDeposito(Deposito deposito) {
        this.deposito = deposito;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public OperacaoFisica getOperacao() {
        return operacao;
    }

    public void setOperacao(OperacaoFisica operacao) {
        this.operacao = operacao;
    }

    public Estoque getEstoque() {
        return estoque;
    }

    public void setEstoque(Estoque estoque) {
        this.estoque = estoque;
    }
    
    public AjusteDeEstoque construir() throws DadoInvalidoException {
        return new AjusteDeEstoqueBuilder().comObservacao(observacao).comQuantidade(quantidade)
                .comItem(item).comDeposito(deposito).comEmissao(emissao).comEstoque(estoque).construir();
    }

    public AjusteDeEstoque construirComID() throws DadoInvalidoException {
        return new AjusteDeEstoqueBuilder().comID(id).comObservacao(observacao).comQuantidade(quantidade)
                .comItem(item).comDeposito(deposito).comEmissao(emissao).comEstoque(estoque).construir();
    }
}
