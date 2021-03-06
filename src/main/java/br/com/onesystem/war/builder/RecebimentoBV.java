/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.builder.*;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Recebimento;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.services.BuilderView;
import br.com.onesystem.valueobjects.EstadoDeLancamento;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class RecebimentoBV implements BuilderView<Recebimento> {

    private Long id;
    private List<TipoDeCobranca> tiposDeCobranca;
    private List<FormaDeCobranca> formasDeCobranca;
    private BigDecimal totalEmDinheiro = BigDecimal.ZERO;
    private Cotacao cotacaoPadrao;
    private Date emissao;
    private EstadoDeLancamento estado;
    private Caixa caixa;
    private Filial filial;
    private List<ValorPorCotacao> valorPorCotacao;

    public RecebimentoBV() {
    }

    public RecebimentoBV(Date emissao) {
    }

    public RecebimentoBV(Date emissao, Caixa caixa, Filial filial) {
        this.emissao = emissao;
        this.caixa = caixa;
        this.filial = filial;
    }

    public RecebimentoBV(Recebimento r) {
        this.id = r.getId();
        this.tiposDeCobranca = r.getTipoDeCobranca();
        this.formasDeCobranca = r.getFormasDeCobranca();
        this.cotacaoPadrao = r.getCotacaoPadrao();
        this.emissao = r.getEmissao();
        this.totalEmDinheiro = r.getTotalEmDinheiro();
        this.estado = r.getEstado();
        this.caixa = r.getCaixa();
        this.filial = r.getFilial();
        this.valorPorCotacao = r.getValorPorCotacao();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TipoDeCobranca> getTiposDeCobranca() {
        return tiposDeCobranca;
    }

    public void setTiposDeCobranca(List<TipoDeCobranca> tiposDeCobranca) {
        this.tiposDeCobranca = tiposDeCobranca;
    }

    public List<FormaDeCobranca> getFormasDeCobranca() {
        return formasDeCobranca;
    }

    public void setFormasDeCobranca(List<FormaDeCobranca> formasDeCobranca) {
        this.formasDeCobranca = formasDeCobranca;
    }

    public BigDecimal getTotalEmDinheiro() {
        return totalEmDinheiro;
    }

    public void setTotalEmDinheiro(BigDecimal totalEmDinheiro) {
        this.totalEmDinheiro = totalEmDinheiro;
    }

    public Cotacao getCotacaoPadrao() {
        return cotacaoPadrao;
    }

    public void setCotacaoPadrao(Cotacao cotacaoPadrao) {
        this.cotacaoPadrao = cotacaoPadrao;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public EstadoDeLancamento getEstado() {
        return estado;
    }

    public void setEstado(EstadoDeLancamento estado) {
        this.estado = estado;
    }

    public Caixa getCaixa() {
        return caixa;
    }

    public void setCaixa(Caixa caixa) {
        this.caixa = caixa;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public List<ValorPorCotacao> getValorPorCotacao() {
        return valorPorCotacao;
    }

    public void setValorPorCotacao(List<ValorPorCotacao> valorPorCotacao) {
        this.valorPorCotacao = valorPorCotacao;
    }
    
    public Recebimento construir() throws DadoInvalidoException {
        return new RecebimentoBuilder().comCotacaoPadrao(cotacaoPadrao).comEmissao(emissao)
                .comFormasDeCobranca(formasDeCobranca).comTipoDeCobranca(tiposDeCobranca)
                .comTotalEmDinheiro(totalEmDinheiro).comEstadoDeLancamento(estado).comCaixa(caixa)
                .comFilial(filial).comValorPorCotacao(valorPorCotacao).construir();
    }

    @Override
    public Recebimento construirComID() throws DadoInvalidoException {
        return new RecebimentoBuilder().comId(id).comCotacaoPadrao(cotacaoPadrao).comEmissao(emissao)
                .comFormasDeCobranca(formasDeCobranca).comTipoDeCobranca(tiposDeCobranca)
                .comTotalEmDinheiro(totalEmDinheiro).comEstadoDeLancamento(estado).comCaixa(caixa)
                .comFilial(filial).comValorPorCotacao(valorPorCotacao).construir();
    }

}
