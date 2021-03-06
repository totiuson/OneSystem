package br.com.onesystem.war.service.impl;

import org.primefaces.context.RequestContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BasicCrudMBImpl<Bean> {

    protected Bean beanSelecionado;
    protected List<Bean> beans;
    protected List<Bean> beansFiltrados;

    public void exibirNaTela(String selecao) {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", true);
        opcoes.put("responsive", true);
//        opcoes.put("width", 950);
        opcoes.put("draggable", true);
        opcoes.put("height", 550);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/menu/" + selecao, opcoes, null);
    }

    public String getIcon() {
        return "";
    }
    
    public abstract void buscarDados();

    public abstract void abrirDialogo();

    public abstract List<Bean> complete(String query);

    public abstract String abrirEdicao();

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(beanSelecionado);
    }

    public Bean getBeanSelecionado() {
        return beanSelecionado;
    }

    public void setBeanSelecionado(Bean beanSelecionado) {
        this.beanSelecionado = beanSelecionado;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    public List<Bean> getBeansFiltrados() {
        return beansFiltrados;
    }

    public void setBeansFiltrados(List<Bean> beansFiltrados) {
        this.beansFiltrados = beansFiltrados;
    }

}
