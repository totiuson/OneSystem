/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.TipoDespesa;
import br.com.onesystem.domain.DespesaProvisionada;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.TipoReceita;
import br.com.onesystem.domain.ReceitaProvisionada;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.service.BaixaService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class BaixaView extends BasicMBImpl<Baixa, BaixaBV> implements Serializable {

    private boolean panel;
    private BaixaBV baixa;
    private Baixa baixaSelecionada;
    private List<Baixa> baixaLista;
    private List<Baixa> baixasFiltradas;
    private String statusButton = "RedButton";

    @Inject
    private BaixaService service;

    @PostConstruct
    public void init() {
        limparJanela();
        panel = false;
        baixaLista = service.buscarBaixas();
    }

    @Override
    public void limparJanela() {
        e = new BaixaBV();
        baixa = new BaixaBV();
        baixaSelecionada = new Baixa();
    }

    @Override
    public void selecionar(SelectEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void update() {
        try {
            Baixa baixaExistente = baixa.construirComID();
            atualizaNoBanco(baixaExistente);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void atualizaNoBanco(Baixa baixaExistente) throws ConstraintViolationException, DadoInvalidoException {
        if (baixaExistente.getId() != null) {
            new AtualizaDAO<>().atualiza(baixaExistente);
            baixaLista.set(baixaLista.indexOf(baixaSelecionada),
                    baixaExistente);
            if (baixasFiltradas != null && baixasFiltradas.contains(baixaExistente)) {
                baixasFiltradas.set(baixasFiltradas.indexOf(baixaExistente), baixaExistente);
            }
            InfoMessage.print("¡Baixa '" + baixaExistente.getId() + "' cambiado con éxito!");
            limparJanela();
        } else {
            throw new EDadoInvalidoException("!La baixa no se encontra registrada!");
        }
    }

    public void fecharEdicao() {
        panel = false;
    }

    public void abrirEdicaoComDados() {
        panel = true;
        baixa = new BaixaBV(baixaSelecionada);
    }

    public void cancelar() {
        try {
            Baixa baixa = this.baixa.construirComID();
            baixa.cancelar();
            atualizaNoBanco(baixa);
            this.baixa.setCancelada(true);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void atuStatusButton() {
        if (this.baixa.getNaturezaFinanceira().equals(OperacaoFinanceira.ENTRADA)) {
            statusButton = "GreenButton";
        } else {
            statusButton = "RedButton";
        }
        RequestContext.getCurrentInstance().update("conteudo:statusButton");
    }

    public void selecionaPessoa(SelectEvent event) {
        Pessoa pessoaSelecionada = (Pessoa) event.getObject();
        this.baixa.setPessoa(pessoaSelecionada);
    }

    public void selecionaDespesa(SelectEvent event) {
        TipoDespesa despesaSelecionada = (TipoDespesa) event.getObject();
        this.baixa.setDespesa(despesaSelecionada);
    }

    public void selecionaReceita(SelectEvent event) {
        TipoReceita receitaSelecionada = (TipoReceita) event.getObject();
        this.baixa.setReceita(receitaSelecionada);
    }

    public void selecionaCambio(SelectEvent event) {
        Cambio cambioSelecionado = (Cambio) event.getObject();
        this.baixa.setCambio(cambioSelecionado);
    }

    public void selecionaDespesaProvisionada(SelectEvent event) {
        DespesaProvisionada despesaSelecionada = (DespesaProvisionada) event.getObject();
        this.baixa.setMovimentoFixo(despesaSelecionada);
    }

    public void selecionaReceitaProvisionada(SelectEvent event) {
        ReceitaProvisionada receitaSelecionada = (ReceitaProvisionada) event.getObject();
        this.baixa.setMovimentoFixo(receitaSelecionada);
    }

    public void selecionaTitulo(SelectEvent event) {
        Titulo tituloSelecionado = (Titulo) event.getObject();
        this.baixa.setParcela(tituloSelecionado);
    }

    public void selecionaRecepcao(SelectEvent event) {
        Recepcao recepcaoSelecionado = (Recepcao) event.getObject();
        this.baixa.setRecepcao(recepcaoSelecionado);
    }

    public void selecionaTransferencia(SelectEvent event) {
        Transferencia transferenciaSelecionada = (Transferencia) event.getObject();
        this.baixa.setTransferencia(transferenciaSelecionada);
    }

    public void selecionaConta(SelectEvent event) {
        Conta contaSelecionada = (Conta) event.getObject();
//        this.baixa.setConta(contaSelecionada);
    }

    public boolean isPanel() {
        return panel;
    }

    public void setPanel(boolean panel) {
        this.panel = panel;
    }

    public BaixaBV getBaixa() {
        return baixa;
    }

    public void setBaixa(BaixaBV baixa) {
        this.baixa = baixa;
    }

    public Baixa getBaixaSelecionada() {
        return baixaSelecionada;
    }

    public void setBaixaSelecionada(Baixa baixaSelecionada) {
        this.baixaSelecionada = baixaSelecionada;
    }

    public List<Baixa> getBaixaLista() {
        return baixaLista;
    }

    public void setBaixaLista(List<Baixa> baixaLista) {
        this.baixaLista = baixaLista;
    }

    public List<Baixa> getBaixasFiltradas() {
        return baixasFiltradas;
    }

    public void setBaixasFiltradas(List<Baixa> baixasFiltradas) {
        this.baixasFiltradas = baixasFiltradas;
    }

    public BaixaService getService() {
        return service;
    }

    public void setService(BaixaService service) {
        this.service = service;
    }

}
