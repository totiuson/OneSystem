/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.dao.DepositoBancarioDAO;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoLancamentoBancario;
import br.com.onesystem.war.builder.DepositoBancarioBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael Cordeiro
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ConsultaDepositoBancarioView extends BasicMBImpl<DepositoBancario, DepositoBancarioBV> implements Serializable {

    private DepositoBancarioBV depositoEstonado;
    private List<Cotacao> cotacaoBancariaLista;
    private List<Cotacao> cotacaoEmpresaLista;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void inicializar() {
        cotacaoEmpresaLista = new CotacaoDAO().naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().listaDeResultados();
        cotacaoBancariaLista = new CotacaoDAO().naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
    }

    @Override
    public void limparJanela() {
        e = new DepositoBancarioBV();
        depositoEstonado = new DepositoBancarioBV();
        cotacaoEmpresaLista = new ArrayList<>();
        cotacaoBancariaLista = new ArrayList<>();
    }

    @Override
    public void selecionar(SelectEvent event) {
        e = new DepositoBancarioBV((DepositoBancario) event.getObject());
        inicializar();
    }

    public void estorno() {
        try {
            depositoEstonado = new DepositoBancarioBV(e.construirComID());
            selecionaCotacao();
            depositoEstonado.setId(null);
            depositoEstonado.setBaixas(null);
            depositoEstonado.setCheques(null);
            depositoEstonado.setEmissao(new Date());
            depositoEstonado.setTipoLancamentoBancario(TipoLancamentoBancario.ESTORNO);
            depositoEstonado.setEstornado(true);
            depositoEstonado.setIdRelacaoEstorno(e.getId());
            DepositoBancario d = depositoEstonado.construir();
            d.geraEstornoDoDepositoCom(depositoEstonado.getCotacaoDeOrigem(), depositoEstonado.getCotacaoDeDestino());
            new AdicionaDAO<>().adiciona(d);
            atualizaDeposito(d);
            InfoMessage.atualizado();
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void selecionaCotacao() {
        if (depositoEstonado.getDestino() != null) {
            depositoEstonado.setCotacaoDeDestino(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(e.getDestino())).findFirst().get());
        }
        if (depositoEstonado.getOrigem() != null) {
            depositoEstonado.setCotacaoDeOrigem(cotacaoEmpresaLista.stream().filter(c -> c.getConta().equals(e.getOrigem())).findFirst().get());
        }
    }

    private void atualizaDeposito(DepositoBancario d) {
        try {
            e.setEstornado(true);
            t = e.construirComID();
            t.setIdRelacaoEstorno(d);
            new AtualizaDAO<>().atualiza(t);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void delete() {
        try {
            t = e.construirComID();
            if (t.getTipoLancamentoBancario().equals(TipoLancamentoBancario.ESTORNO)) {
                cancelaEstorno();
            }
            deleteNoBanco(t, t.getId());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void cancelaEstorno() throws ConstraintViolationException, DadoInvalidoException {
        DepositoBancarioBV de = new DepositoBancarioBV(new DepositoBancarioDAO().porId(t.getIdRelacaoEstorno()).resultado());
        de.setIdRelacaoEstorno(null);
        de.setEstornado(false);
        new AtualizaDAO<>().atualiza(de.construirComID());
    }

}