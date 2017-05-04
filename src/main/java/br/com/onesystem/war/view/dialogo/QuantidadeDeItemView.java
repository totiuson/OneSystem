/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.Item;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.war.builder.QuantidadeDeItemBV;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Named(value = "quantidadeDeItemView")
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class QuantidadeDeItemView extends BasicCrudMBImpl<QuantidadeDeItemBV> implements Serializable {

    private QuantidadeDeItemBV quantidadeDeItem = new QuantidadeDeItemBV();
    private QuantidadeDeItemBV quantidadeDeItemSelecionada;
    private List<QuantidadeDeItemBV> lista;
    private List<QuantidadeDeItemBV> listaFiltrada;
    private List<SaldoDeEstoque> listaDeEstoque;

    @Inject
    private EstoqueService serviceEstoque;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        HttpSession session = (HttpSession) ec.getSession(true);

        lista = (List<QuantidadeDeItemBV>) session.getAttribute("onesystem.quantidadeLista.token");
        if (lista == null || lista.isEmpty()) {
            Item item = (Item) session.getAttribute("onesystem.item.token");
            listaDeEstoque = serviceEstoque.buscaListaDeSaldoDeEstoque(item, null);
            criaLista();
        }
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoQuantidadeDeItem");
    }

    public void exibirNaTela(String selecao) {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", true);
        opcoes.put("width", 400);
        opcoes.put("draggable", true);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("closable", false);
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/selecao/" + selecao, opcoes, null);
    }

    @Override
    public String abrirEdicao() {
        return "item";
    }

    public void criaLista() {
        lista = new ArrayList<QuantidadeDeItemBV>();
        for (SaldoDeEstoque saldo : listaDeEstoque) {
            quantidadeDeItem = new QuantidadeDeItemBV();
            quantidadeDeItem.setSaldoDeEstoque(saldo);
            quantidadeDeItem.setId(new Long(lista.size() + 1));
            lista.add(quantidadeDeItem);
        }
    }

    @Override
    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(lista);
    }

    public QuantidadeDeItemBV getQuantidadeDeItem() {
        return quantidadeDeItem;
    }

    public void setQuantidadeDeItem(QuantidadeDeItemBV quantidadeDeItem) {
        this.quantidadeDeItem = quantidadeDeItem;
    }

    public List<QuantidadeDeItemBV> getLista() {
        return lista;
    }

    public void setLista(List<QuantidadeDeItemBV> lista) {
        this.lista = lista;
    }

    public List<SaldoDeEstoque> getListaDeEstoque() {
        return listaDeEstoque;
    }

    public void setListaDeEstoque(List<SaldoDeEstoque> listaDeEstoque) {
        this.listaDeEstoque = listaDeEstoque;
    }

    public EstoqueService getServiceEstoque() {
        return serviceEstoque;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

    public QuantidadeDeItemBV getQuantidadeDeItemSelecionada() {
        return quantidadeDeItemSelecionada;
    }

    public void setQuantidadeDeItemSelecionada(QuantidadeDeItemBV quantidadeDeItemSelecionada) {
        this.quantidadeDeItemSelecionada = quantidadeDeItemSelecionada;
    }

    public List<QuantidadeDeItemBV> getListaFiltrada() {
        return listaFiltrada;
    }

    public void setListaFiltrada(List<QuantidadeDeItemBV> listaFiltrada) {
        this.listaFiltrada = listaFiltrada;
    }

    @Override
    public List<QuantidadeDeItemBV> complete(String query) {
        return null;
    }

}
