package br.com.onesystem.war.builder;

import br.com.onesystem.domain.FormaDeRecebimento;
import br.com.onesystem.domain.NotaEmitida;
import br.com.onesystem.domain.FormaDeRecebimentoOuPagamento;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.builder.FormaDeRecebimentoOuPagamentoBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 *
 * @author Rafael
 */
public class FormaDeRecebimentoOuPagamentoBV implements Serializable {

    private Long id;
    private FormaDeRecebimento formaDeRecebimento;
    private Moeda moeda;
    private Integer parcelas;
    private BigDecimal dinheiro;
    private BigDecimal credito;
    private BigDecimal cheque;
    private BigDecimal cartao;
    private BigDecimal aFaturar;
    private NotaEmitida notaEmitida;

    public FormaDeRecebimentoOuPagamentoBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormaDeRecebimento getFormaDeRecebimento() {
        return formaDeRecebimento;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public void setFormaDeRecebimento(FormaDeRecebimento formaDeRecebimento) {
        this.formaDeRecebimento = formaDeRecebimento;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getDinheiro() {
        return dinheiro;
    }

    public String getDinheiroFormatado() {
        return moeda != null ? NumberFormat.getCurrencyInstance(moeda.getBandeira().getLocal()).format(dinheiro) : "";
    }

    public void setDinheiro(BigDecimal dinheiro) {
        this.dinheiro = dinheiro;
    }

    public BigDecimal getCredito() {
        return credito;
    }

    public void setCredito(BigDecimal credito) {
        this.credito = credito;
    }

    public BigDecimal getCheque() {
        return cheque;
    }

    public void setCheque(BigDecimal cheque) {
        this.cheque = cheque;
    }

    public BigDecimal getCartao() {
        return cartao;
    }

    public void setCartao(BigDecimal cartao) {
        this.cartao = cartao;
    }

    public BigDecimal getaFaturar() {
        return aFaturar;
    }

    public void setaFaturar(BigDecimal aFaturar) {
        this.aFaturar = aFaturar;
    }

    public NotaEmitida getNotaEmitida() {
        return notaEmitida;
    }

    public void setNotaEmitida(NotaEmitida notaEmitida) {
        this.notaEmitida = notaEmitida;
    }

    public FormaDeRecebimentoOuPagamento construir() throws DadoInvalidoException {
        return new FormaDeRecebimentoOuPagamentoBuilder().comAFaturar(aFaturar).comCartao(cartao)
                .comCheque(cheque).comCredito(credito).comDinheiro(dinheiro).comFormaDeRecebimento(formaDeRecebimento)
                .comNotaEmitida(notaEmitida).comParcelas(parcelas).comMoeda(moeda).construir();
    }

    public FormaDeRecebimentoOuPagamento construirComID() throws DadoInvalidoException {
        return new FormaDeRecebimentoOuPagamentoBuilder().comID(id).comAFaturar(aFaturar).comCartao(cartao)
                .comCheque(cheque).comCredito(credito).comDinheiro(dinheiro).comFormaDeRecebimento(formaDeRecebimento)
                .comNotaEmitida(notaEmitida).comParcelas(parcelas).comMoeda(moeda).construir();
    }

}
