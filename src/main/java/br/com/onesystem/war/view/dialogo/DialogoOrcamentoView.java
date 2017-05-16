package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.Orcamento;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.ErrorMessage;
import br.com.onesystem.war.builder.ItemOrcadoBV;
import br.com.onesystem.war.builder.QuantidadeDeItemPorDeposito;
import br.com.onesystem.war.service.ConfiguracaoEstoqueService;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped
public class DialogoOrcamentoView extends BasicMBImpl<Orcamento, ItemOrcadoBV> implements Serializable {

    private Orcamento orcamento;
    private List<ItemOrcadoBV> itensOrcados;
    private ItemOrcadoBV itemOrcadoBV;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private ConfiguracaoEstoqueService serviceConfiguracaoEstoque;

    @PostConstruct
    public void init() {
        limparJanela();
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        orcamento = (Orcamento) session.getAttribute("onesystem.orcamento.token");
        if (orcamento != null) {
            orcamento.getItensOrcados().forEach((io) -> {
                ItemOrcadoBV iobv = new ItemOrcadoBV(io);
                List<SaldoDeEstoque> listaDeEstoque = serviceEstoque.buscaListaDeSaldoDeEstoque(iobv.getItem(), null);
                List<QuantidadeDeItemPorDeposito> lista = criaLista(listaDeEstoque, iobv);
                iobv.setQuantidadePorDeposito(lista);
                itensOrcados.add(iobv);
            });
        }
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 800);
        opcoes.put("draggable", false);
        opcoes.put("height", 600);
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("dialogo/dialogoOrcamento", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
        Object obj = event.getObject();
        if (obj instanceof List) {
            List<QuantidadeDeItemPorDeposito> list = (List<QuantidadeDeItemPorDeposito>) event.getObject();
            itemOrcadoBV.setQuantidadePorDeposito((List<QuantidadeDeItemPorDeposito>) event.getObject());
        }
    }

    public void atribuiItemASessao(ItemOrcadoBV itemOrcado) {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.quantidadeLista.token");
        session.setAttribute("onesystem.quantidadeLista.token", itemOrcado.getQuantidadePorDeposito());

        itemOrcadoBV = itemOrcado;
        abrirJanelaQuantidade();
    }

    public List<QuantidadeDeItemPorDeposito> criaLista(List<SaldoDeEstoque> listaDeEstoque, ItemOrcadoBV itemOrcado) {
        BigDecimal quantidade = itemOrcado.getQuantidade();

        List<QuantidadeDeItemPorDeposito> lista = new ArrayList<QuantidadeDeItemPorDeposito>();
        for (SaldoDeEstoque saldo : listaDeEstoque) {
            if (saldo.getDeposito().getId() == serviceConfiguracaoEstoque.buscar().getDepositoPadrao().getId()) {
                QuantidadeDeItemPorDeposito quantidadeDeItem = new QuantidadeDeItemPorDeposito(new Long(lista.size() + 1), saldo, BigDecimal.ZERO);
                if (saldo.getSaldo().compareTo(quantidade) >= 0) {
                    quantidadeDeItem.setQuantidade(quantidade);
                    quantidade = BigDecimal.ZERO;
                } else {
                    quantidadeDeItem.setQuantidade(saldo.getSaldo());
                    quantidade = quantidade.subtract(saldo.getSaldo());
                }
                lista.add(quantidadeDeItem);
                listaDeEstoque.remove(saldo);
                break;
            }
        }
        if (quantidade.compareTo(BigDecimal.ZERO) > 0) {
            for (SaldoDeEstoque saldo : listaDeEstoque) {
                QuantidadeDeItemPorDeposito quantidadeDeItem = new QuantidadeDeItemPorDeposito(new Long(lista.size() + 1), saldo, itemOrcado.getQuantidade());
                lista.add(quantidadeDeItem);
            }
        }
        return lista;
    }

    private void abrirJanelaQuantidade() {
        RequestContext.getCurrentInstance().execute("document.getElementById(\"tempDialog:tabs:itensOrcadosOrcamento:0:exibeQuantidade-btn\").click();");
    }

    public void salvar() {
        for (ItemOrcadoBV ib : itensOrcados) {
            if (ib.getQuantidadeDeFaturamento() != 0) {
                ErrorMessage.print(new BundleUtil().getMessage("Existem_Quantidade_Diferente_Da_Orcada"));
                return;
            }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.removeAttribute("onesystem.orcamento.token");
        RequestContext.getCurrentInstance().closeDialog(itensOrcados);
    }

    @Override
    public void limparJanela() {
        itemOrcadoBV = new ItemOrcadoBV();
        itensOrcados = new ArrayList<>();
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public List<ItemOrcadoBV> getItensOrcados() {
        return itensOrcados;
    }

    public void setItensOrcados(List<ItemOrcadoBV> itensOrcados) {
        this.itensOrcados = itensOrcados;
    }

    public ItemOrcadoBV getItemOrcadoBV() {
        return itemOrcadoBV;
    }

    public void setItemOrcadoBV(ItemOrcadoBV itemOrcadoBV) {
        this.itemOrcadoBV = itemOrcadoBV;
    }

    public EstoqueService getServiceEstoque() {
        return serviceEstoque;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

    public ConfiguracaoEstoqueService getServiceConfiguracaoEstoque() {
        return serviceConfiguracaoEstoque;
    }

    public void setServiceConfiguracaoEstoque(ConfiguracaoEstoqueService serviceConfiguracaoEstoque) {
        this.serviceConfiguracaoEstoque = serviceConfiguracaoEstoque;
    }

}
