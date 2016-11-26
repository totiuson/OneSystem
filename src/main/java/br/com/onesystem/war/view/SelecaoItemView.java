package br.com.onesystem.war.view;

import br.com.onesystem.domain.Item;
import br.com.onesystem.war.service.ItemService;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;

@ManagedBean
@ViewScoped
public class SelecaoItemView implements Serializable {

    private Item itemSelecionada;
    private List<Item> itemLista;
    private List<Item> itensFiltradas;

    @ManagedProperty("#{itemService}")
    private ItemService service;

    @PostConstruct
    public void init() {
        itemLista = service.buscarItems();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoItem", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(itemSelecionada);
    }

    public Item getItemSelecionada() {
        return itemSelecionada;
    }

    public void setItemSelecionada(Item itemSelecionada) {
        this.itemSelecionada = itemSelecionada;
    }

    public List<Item> getItemLista() {
        return itemLista;
    }

    public void setItemLista(List<Item> itemLista) {
        this.itemLista = itemLista;
    }

    public List<Item> getItensFiltradas() {
        return itensFiltradas;
    }

    public void setItensFiltradas(List<Item> itensFiltradas) {
        this.itensFiltradas = itensFiltradas;
    }

    public ItemService getService() {
        return service;
    }

    public void setService(ItemService service) {
        this.service = service;
    }
}
