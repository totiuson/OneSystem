/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view;

import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.domain.Baixa;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.war.builder.BaixaBV;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Rafael
 */
@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class BaixaView extends BasicMBImpl<Baixa, BaixaBV> implements Serializable {

    private Baixa baixa;
    private String statusButton = "RedButton";

    @Inject
    private AtualizaDAO<Baixa> atualizaDAO;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    @Override
    public void limparJanela() {
        e = new BaixaBV();
        baixa = new Baixa();
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof Baixa) {
            baixa = (Baixa) obj;
            e = new BaixaBV(baixa);
        }
    }

    public void update() {
        try {
            Baixa b = e.construirComID();
            updateNoBanco(b);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void delete() {
        try {
            Baixa b = e.construirComID();
            deleteNoBanco(b, b.getId());
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void cancela() {
        try {
            Baixa b = e.construirComID();
            b.cancela();
            atualizaDAO.atualiza(b);
            InfoMessage.atualizado();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public Baixa getBaixa() {
        return baixa;
    }

    public void setBaixa(Baixa baixa) {
        this.baixa = baixa;
    }
}
