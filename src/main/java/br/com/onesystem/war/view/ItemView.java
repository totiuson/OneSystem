package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.ItemDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.util.FatalMessage;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoDeEstoque;
import br.com.onesystem.services.CalculadoraDePreco;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import br.com.onesystem.war.builder.PrecoDeItemBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.PrecoDeItemService;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.exception.ConstraintViolationException;
import org.primefaces.event.SelectEvent;

@Named
@javax.faces.view.ViewScoped //javax.faces.view.ViewScoped;
public class ItemView implements Serializable {

    private ItemBV item;
    private Item itemSelecionada;
    private PrecoDeItemBV precoDeItemBV;
    private boolean precoPorMargem = true;
    private Configuracao configuracao;
    private List<SaldoDeEstoque> estoqueLista;
    private BigDecimal estoqueTotal;
    private List<PrecoDeItem> precoAtual;
    private List<PrecoDeItem> precos;
    private boolean tab = true;

    @Inject
    private ConfiguracaoService serviceConfigurcao;

    @Inject
    private EstoqueService serviceEstoque;

    @Inject
    private PrecoDeItemService servicePrecoDeItem;

    @PostConstruct
    public void init() {
        limparJanela();
        inicializarConfiguracoes();
    }

    private void inicializarConfiguracoes() {
        try {
            configuracao = serviceConfigurcao.buscar();
            if (configuracao.getMoedaPadrao() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
            }
        } catch (EDadoInvalidoException ex) {
            ex.print();
        }
    }

    public void add() {
        try {
            Item novoRegistro = item.construir();
            new AdicionaDAO<Item>().adiciona(novoRegistro);
            InfoMessage.adicionado();
            limparJanela();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addPreco() {
        try {
            validaMargem();
            PrecoDeItem novoRegistro = precoDeItemBV.construir();
            new AdicionaDAO<PrecoDeItem>().adiciona(novoRegistro);
            limparJanelaPreco();
            inicializaPrecos();
            InfoMessage.adicionado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    private void validaMargem() throws EDadoInvalidoException {
        if (precoPorMargem && item.getMargem() == null) {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("margem_not_null"));
        }
    }

    public void update() {
        try {
            if (itemSelecionada != null) {
                Item itemExistente = item.construirComID();
                new AtualizaDAO<Item>().atualiza(itemExistente);
                InfoMessage.atualizado();
                limparJanela();
            } else {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void delete() {
        try {
            if (itemSelecionada != null) {
                new RemoveDAO<Item>().remove(itemSelecionada, itemSelecionada.getId());
                InfoMessage.removido();
                limparJanela();
            }
        } catch (DadoInvalidoException di) {
            di.print();
        } catch (ConstraintViolationException pe) {
            FatalMessage.print(pe.getMessage(), pe.getCause());
        }
    }

    public List<TipoItem> getTipoItem() {
        return Arrays.asList(TipoItem.values());
    }

    public void limparJanela() {
        item = new ItemBV();
        itemSelecionada = null;
        estoqueLista = new ArrayList<SaldoDeEstoque>();
        limparJanelaPreco();
        tab = true;
    }

    public void limparJanelaPreco() {
        precoDeItemBV = new PrecoDeItemBV();
    }

    public void buscaPorId() {
        Long id = item.getId();
        if (id != null) {
            try {
                ItemDAO dao = new ItemDAO();
                Item c = dao.buscarItems().porId(id).resultado();
                itemSelecionada = c;
                inicializaDados();
                item = new ItemBV(itemSelecionada);
                tab = false;
            } catch (DadoInvalidoException die) {
                limparJanela();
                item.setId(id);
                die.print();
            }
        }
    }

    public void selecionaItem(SelectEvent event) {
        itemSelecionada = (Item) event.getObject();
        inicializaDados();
        item = new ItemBV(itemSelecionada);
        tab = false;
    }

    private void inicializaDados() {
        inicializaEstoque();
        inicializaPrecos();
    }

    private void inicializaPrecos() {
        precoDeItemBV.setItem(itemSelecionada);
        precoAtual = servicePrecoDeItem.buscaListaDePrecoAtual(itemSelecionada);
        precos = servicePrecoDeItem.buscaTodosPrecos(itemSelecionada);
    }

    private void inicializaEstoque() {
        estoqueLista = serviceEstoque.buscaListaDeSaldoDeEstoque(itemSelecionada, null);
    }

    public void selecionaListaDePreco(SelectEvent event) {
        try {
            if (item.getMargem() != null) {
                calculaPreco();
            }
            ListaDePreco listaDePreco = (ListaDePreco) event.getObject();
            precoDeItemBV.setListaDePreco(listaDePreco);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void selecionaMargem(SelectEvent event) {
        Margem g = (Margem) event.getObject();
        item.setMargem(g);
    }

    private void calculaPreco() throws DadoInvalidoException {
        if (configuracao.getTipoDeFormacaoDePreco() != null && configuracao.getTipoDeCalculoDeCusto() != null) {
            CalculadoraDePreco calculadora = new CalculadoraDePreco(itemSelecionada, configuracao.getTipoDeCalculoDeCusto());
            precoDeItemBV.setValor(configuracao.getTipoDeFormacaoDePreco() == TipoDeFormacaoDePreco.MARKUP
                    ? calculadora.getPrecoMarkup() : calculadora.getPrecoMargemContribuicao());
        } else {
            throw new EDadoInvalidoException(new BundleUtil().getMessage("Configuracao_nao_definida"));
        }
    }

    public void desfazer() {
        if (itemSelecionada != null) {
            item = new ItemBV(itemSelecionada);
        }
    }

    public void selecionaGrupoFiscal(SelectEvent event) {
        GrupoFiscal grupo = (GrupoFiscal) event.getObject();
        item.setGrupoFiscal(grupo);
    }

    public void selecionaGrupo(SelectEvent e) {
        item.setGrupo((Grupo) e.getObject());
    }

    public void selecionaMarca(SelectEvent e) {
        item.setMarca((Marca) e.getObject());
    }

    public void selecionaUnidadeMedida(SelectEvent e) {
        item.setUnidadeMedida((UnidadeMedidaItem) e.getObject());
    }

    public ItemBV getItem() {
        return item;
    }

    public void setItem(ItemBV item) {
        this.item = item;
    }

    public Item getItemSelecionada() {
        return itemSelecionada;
    }

    public void setItemSelecionada(Item itemSelecionada) {
        this.itemSelecionada = itemSelecionada;
    }

    public PrecoDeItemBV getPrecoDeItemBV() {
        return precoDeItemBV;
    }

    public void setPrecoDeItemBV(PrecoDeItemBV precoDeItemBV) {
        this.precoDeItemBV = precoDeItemBV;
    }

    public List<SaldoDeEstoque> getEstoqueLista() {
        return estoqueLista;
    }

    public void setEstoqueLista(List<SaldoDeEstoque> estoqueLista) {
        this.estoqueLista = estoqueLista;
    }

    public BigDecimal getEstoqueTotal() {
        return estoqueTotal;
    }

    public void setEstoqueTotal(BigDecimal estoqueTotal) {
        this.estoqueTotal = estoqueTotal;
    }

    public EstoqueService getServiceEstoque() {
        return serviceEstoque;
    }

    public boolean isPrecoPorMargem() {
        return precoPorMargem;
    }

    public void setPrecoPorMargem(boolean precoPorMargem) {
        this.precoPorMargem = precoPorMargem;
    }

    public void setServiceEstoque(EstoqueService serviceEstoque) {
        this.serviceEstoque = serviceEstoque;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public ConfiguracaoService getServiceConfigurcao() {
        return serviceConfigurcao;
    }

    public void setServiceConfigurcao(ConfiguracaoService serviceConfigurcao) {
        this.serviceConfigurcao = serviceConfigurcao;
    }

    public List<PrecoDeItem> getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(List<PrecoDeItem> precoAtual) {
        this.precoAtual = precoAtual;
    }

    public boolean isTab() {
        return tab;
    }

    public void setTab(boolean tab) {
        this.tab = tab;
    }

    public List<PrecoDeItem> getPrecos() {
        return precos;
    }

    public void setPrecos(List<PrecoDeItem> precos) {
        this.precos = precos;
    }

    public PrecoDeItemService getServicePrecoDeItem() {
        return servicePrecoDeItem;
    }

    public void setServicePrecoDeItem(PrecoDeItemService servicePrecoDeItem) {
        this.servicePrecoDeItem = servicePrecoDeItem;
    }

}
