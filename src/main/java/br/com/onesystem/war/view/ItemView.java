package br.com.onesystem.war.view;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.AtualizaDAO;
import br.com.onesystem.dao.RemoveDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.ConfiguracaoEstoque;
import br.com.onesystem.domain.ContaDeEstoque;
import br.com.onesystem.domain.Deposito;
import br.com.onesystem.domain.Grupo;
import br.com.onesystem.domain.Margem;
import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.domain.Item;
import br.com.onesystem.domain.ItemImagem;
import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.domain.Marca;
import br.com.onesystem.domain.PrecoDeItem;
import br.com.onesystem.domain.UnidadeMedidaItem;
import br.com.onesystem.domain.LoteItem;
import br.com.onesystem.domain.SaldoDeEstoque;
import br.com.onesystem.util.InfoMessage;
import br.com.onesystem.valueobjects.TipoItem;
import br.com.onesystem.war.builder.ItemBV;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.exception.impl.ADadoInvalidoException;
import br.com.onesystem.exception.impl.EDadoInvalidoException;
import br.com.onesystem.reportTemplate.SaldoEmContaTemplate;
import br.com.onesystem.reportTemplate.SaldoEmDepositoTemplate;
import br.com.onesystem.reportTemplate.SaldoEmLoteTemplate;
import br.com.onesystem.services.CalculadoraDePreco;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.util.Model;
import br.com.onesystem.util.ModelList;
import br.com.onesystem.util.WarningMessage;
import br.com.onesystem.valueobjects.DetalhamentoDeItem;
import br.com.onesystem.valueobjects.TipoDeFormacaoDePreco;
import br.com.onesystem.war.builder.LoteItemBV;
import br.com.onesystem.war.builder.PrecoDeItemBV;
import br.com.onesystem.war.service.EstoqueService;
import br.com.onesystem.war.service.ItemService;
import br.com.onesystem.war.service.LoteItemService;
import br.com.onesystem.war.service.PrecoDeItemService;
import br.com.onesystem.war.service.SaldoDeEstoqueService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

@Named
@ViewScoped //javax.faces.view.ViewScoped;
public class ItemView extends BasicMBImpl<Item, ItemBV> implements Serializable {

    private PrecoDeItemBV precoDeItemBV;
    private boolean precoPorMargem = false;
    private List<SaldoEmDepositoTemplate> saldoEmDeposito;
    private List<SaldoEmContaTemplate> saldoEmConta;
    private List<SaldoEmLoteTemplate> saldoEmLote;
    private BigDecimal estoqueTotal;
    private List<PrecoDeItem> precoAtual;
    private List<PrecoDeItem> precos;
    private List<ItemImagem> imagens;
    private boolean tab = true;
    private boolean renderBotoes = true;
    private boolean tabLote = false;
    private ItemImagem itemImagem;
    private LoteItemBV loteDeItemBV;
    private Model<LoteItem> loteItemSelecionado;
    private ModelList<LoteItem> listaLoteItem;
    private ContaDeEstoque contaDeEstoque;

    @Inject
    private Configuracao configuracao;

    @Inject
    private ConfiguracaoEstoque configuracaoEstoque;

    @Inject
    private SaldoDeEstoqueService saldoDeEstoqueService;

    @Inject
    private PrecoDeItemService servicePrecoDeItem;

    @Inject
    private ItemService itemService;

    @Inject
    private EstoqueService estoqueService;
    
    @Inject
    private LoteItemService serviceLoteItem;

    @Inject
    private AdicionaDAO<PrecoDeItem> adicionaPrecoDAO;

    @Inject
    private AdicionaDAO<LoteItem> adicionaLoteDAO;

    @Inject
    private RemoveDAO<LoteItem> removeLoteItemDAO;

    @Inject
    private AtualizaDAO<LoteItem> atualizaLoteDAO;

    @Inject
    private AdicionaDAO<ItemImagem> adicionaImagemDAO;

    @Inject
    private RemoveDAO<ItemImagem> removeImagemDAO;

    @Inject
    private AtualizaDAO<ItemImagem> atualizaImagemDAO;

    @Inject
    private BundleUtil bundle;

    @Inject
    private CalculadoraDePreco calculadoraDePreco;

    @PostConstruct
    public void init() {
        limparJanela();
    }

    public void add() {
        try {
            Item f = e.construir();
            addNoBanco(f);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void update() {
        try {
            Item f = e.construirComID();
            List<LoteItem> removidos = listaLoteItem.getRemovidos().stream().filter(m -> ((LoteItem) m.getObject()).getId() != null).map(m -> (LoteItem) m.getObject()).collect(Collectors.toList());
            removidos.forEach(c -> f.remove(c));
            updateNoBanco(f);
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void addPreco() {
        try {
            validaMargem();
            PrecoDeItem novoRegistro = precoDeItemBV.construir();
            adicionaPrecoDAO.adiciona(novoRegistro);

            //atualiza margem no BD.
            ItemBV ibv = new ItemBV(t);
            ibv.setMargem(e.getMargem());
            updateNoBancoSemLimpar(ibv.construirComID());

            limparJanelaPreco();
            inicializaPrecos();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void limparJanela() {
        e = new ItemBV();
        t = null;
        saldoEmDeposito = new ArrayList<SaldoEmDepositoTemplate>();
        saldoEmConta = new ArrayList<SaldoEmContaTemplate>();
        saldoEmLote = new ArrayList<SaldoEmLoteTemplate>();
        imagens = new ArrayList<>();
        contaDeEstoque = null;
        tab = true;
        renderBotoes = true;
        listaLoteItem = new ModelList<>();
        limparJanelaPreco();
        tabLote = false;
        limparLoteItem();
    }

    @Override
    public void selecionar(SelectEvent event) {
        try {
            Object obj = (Object) event.getObject();
            if (obj instanceof Item) {
                e = new ItemBV((Item) obj);
                selecionaItem();
            } else if (obj instanceof GrupoFiscal) {
                e.setGrupoFiscal((GrupoFiscal) obj);
            } else if (obj instanceof Grupo) {
                e.setGrupo((Grupo) obj);
            } else if (obj instanceof Marca) {
                e.setMarca((Marca) obj);
            } else if (obj instanceof Margem) {
                e.setMargem((Margem) obj);
            } else if (obj instanceof UnidadeMedidaItem) {
                e.setUnidadeDeMedida((UnidadeMedidaItem) obj);
            } else if (obj instanceof ContaDeEstoque) {
                contaDeEstoque = (ContaDeEstoque) obj;
                inicializaEstoque();
            } else if (obj instanceof ListaDePreco) {
                if (precoDeItemBV == null) {
                    limparJanelaPreco();
                }
                precoDeItemBV.setListaDePreco((ListaDePreco) obj);
                calculaPreco();
            }
        } catch (DadoInvalidoException die) {
            die.print();

        }
    }

    public void selecionaItem() throws DadoInvalidoException {
        if (e.getId() != null) {
            tabLote = e.getDetalhamento().equals(DetalhamentoDeItem.LOTES);
            inicializaDados();
            tab = false;
        }
    }

    public void calculaPreco() throws DadoInvalidoException {
        if (!precoPorMargem && e.getMargem() != null) {
            if (configuracao.getTipoDeFormacaoDePreco() != null && configuracao.getTipoDeCalculoDeCusto() != null) {
                if (configuracao.getTipoDeFormacaoDePreco() == TipoDeFormacaoDePreco.MARKUP) {
                    precoDeItemBV.setValor(calculadoraDePreco.calculaMarkup(e.construirComID(), configuracao.getTipoDeCalculoDeCusto()));
                } else if (configuracao.getTipoDeFormacaoDePreco() == TipoDeFormacaoDePreco.MARGEM_BRUTA) {
                    precoDeItemBV.setValor(calculadoraDePreco.calculaMargemBruta(e.construirComID(), configuracao.getTipoDeCalculoDeCusto()));
                }
            } else {
                throw new EDadoInvalidoException(bundle.getMessage("Configuracao_nao_definida"));
            }
        } else if (!precoPorMargem && e.getMargem() == null) {
            WarningMessage.print(bundle.getMessage("Defina_Margem_Para_Calcular_Preco"));
        }
    }

    public void validaMargemView() {
        try {
            validaMargem();
            // Só calcula o preço se a lista de preço já estiver informada.
            if (precoDeItemBV.getListaDePreco() != null) {
                calculaPreco();
            }
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    private void validaMargem() throws DadoInvalidoException {
        if (!precoPorMargem && e.getMargem() == null) {
            throw new ADadoInvalidoException(bundle.getMessage("Defina_Margem_Para_Calcular_Preco"));
        }
    }

    private void inicializaDados() throws DadoInvalidoException {
        inicializaEstoque();
        inicializaPrecos();
        inicializaLotes();
    }

    private void inicializaImagens() {
        imagens = new ArrayList(itemService.buscarImagemDo(t));
    }

    private void inicializaPrecos() throws DadoInvalidoException {
        Item i = e.construirComID();
        precoDeItemBV.setItem(i);
        precoAtual = servicePrecoDeItem.buscaListaDePrecoAtual(i);
        precos = servicePrecoDeItem.buscaTodosPrecos(i);
    }

    private void inicializaLotes() {
        try {
            listaLoteItem = new ModelList<>(serviceLoteItem.buscarLotesPorItem(e.construirComID()));
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void onTabChange(TabChangeEvent event) {
        String str = event.getTab().getTitle();
        if (str != bundle.getLabel("Detalhes")) {
            renderBotoes = false;
            if (str == bundle.getLabel("Imagens")) {
                inicializaImagens();
            }
        } else {
            renderBotoes = true;
        }
    }

    public void inicializaEstoque() throws DadoInvalidoException {
        saldoEmDeposito = new ArrayList<SaldoEmDepositoTemplate>();
        saldoEmConta = new ArrayList<SaldoEmContaTemplate>();
        saldoEmLote = new ArrayList<SaldoEmLoteTemplate>();

        Item item = e.construirComID();

        List<SaldoDeEstoque> saldoDeEstoque = saldoDeEstoqueService.buscaSaldoDeEstoquePorItem(item);

        contaDeEstoque = contaDeEstoque == null ? configuracaoEstoque.getContaDeEstoqueEmpresa() : contaDeEstoque;

        List<SaldoDeEstoque> saldosPorContaPadrao = saldoDeEstoque.stream().filter(s -> s.getContaDeEstoque().equals(contaDeEstoque)).collect(Collectors.toList());

        // Agrupa depositos e soma saldo.
        Map<Deposito, BigDecimal> agrupaDepositosESomaSaldo = saldosPorContaPadrao.stream().collect(Collectors.groupingBy(SaldoDeEstoque::getDeposito, Collectors.mapping(SaldoDeEstoque::getSaldo, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        agrupaDepositosESomaSaldo.forEach((k, v) -> saldoEmDeposito.add(new SaldoEmDepositoTemplate(k, item, v)));

        // Agrupa contas e soma saldo.
        Map<ContaDeEstoque, BigDecimal> agrupaContasESomaSaldo = saldoDeEstoque.stream().collect(Collectors.groupingBy(SaldoDeEstoque::getContaDeEstoque, Collectors.mapping(SaldoDeEstoque::getSaldo, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
        agrupaContasESomaSaldo.forEach((k, v) -> saldoEmConta.add(new SaldoEmContaTemplate(k, item, v)));

        // Agrupa lotes e soma saldo - quando o lote é null, cria um lote para trazer o saldo.
        if (!saldosPorContaPadrao.isEmpty()) {
            LoteItem loteVazio = new LoteItem(new Long(0), new Date(), new Date(), "", tab, "", item);
            saldoEmLote.add(0, new SaldoEmLoteTemplate(loteVazio, item, BigDecimal.ZERO));

            for (SaldoDeEstoque s : saldosPorContaPadrao) {
                if (s.getLoteItem() == null) {
                    SaldoEmLoteTemplate saldo = saldoEmLote.get(0);
                    saldo.setSaldo(saldo.getSaldo().add(s.getSaldo()));
                    saldoEmLote.set(0, saldo);
                } else {
                    boolean encontrou = false;
                    for (SaldoEmLoteTemplate sel : saldoEmLote) {
                        if (sel.getLoteItem().getId().equals(s.getLoteItem().getId())) {
                            sel.setSaldo(sel.getSaldo().add(s.getSaldo()));
                            saldoEmLote.set(saldoEmLote.indexOf(sel), sel);
                            encontrou = true;
                            break;
                        }
                    }
                    if (!encontrou) {
                        saldoEmLote.add(new SaldoEmLoteTemplate(s.getLoteItem(), item, s.getSaldo()));
                    }
                }
            }
        }
    }

    public void favoritarImagem(ItemImagem itemImagem) {
        try {
            ItemImagem imgDesfavoritada = imagens.stream().filter(ItemImagem::isFavorita).filter(i -> (!i.getId().equals(itemImagem.getId()))).findAny().get();
            itemImagem.setFavorita(true);
            imgDesfavoritada.setFavorita(false);
            atualizaImagemDAO.atualiza(imgDesfavoritada);
            atualizaImagemDAO.atualiza(itemImagem);
            imagens.set(imagens.indexOf(imgDesfavoritada), imgDesfavoritada);
            imagens.set(imagens.indexOf(itemImagem), itemImagem);
            imagens.forEach(System.out::println);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void uploadImagens(FileUploadEvent event) {
        try {
            String fileName = event.getFile().getFileName();
            byte[] contents = event.getFile().getContents();
            String contentType = event.getFile().getContentType();
            boolean favorita = false;
            if (imagens.isEmpty()) {
                favorita = true;
            }
            ItemImagem itemImagem = new ItemImagem(null, fileName, contentType, contents, e.construirComID(), favorita);
            adicionaImagemDAO.adiciona(itemImagem);
            imagens.add(itemImagem);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void excluirImagem() {
        try {
            if (itemImagem.isFavorita() && imagens.size() > 1) {
                ItemImagem imgFavoritada = imagens.stream().filter(i -> (!i.getId().equals(itemImagem.getId()))).findFirst().get();
                imgFavoritada.setFavorita(true);
                atualizaImagemDAO.atualiza(imgFavoritada);
                imagens.set(imagens.indexOf(imgFavoritada), imgFavoritada);
            }
            removeImagemDAO.remove(itemImagem, itemImagem.getId());
            imagens.remove(itemImagem);
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void addLoteItem() {
        try {
            loteDeItemBV.setItem(e.construirComID());
            LoteItem novoRegistro = loteDeItemBV.construir();
            adicionaLoteDAO.adiciona(novoRegistro);
            listaLoteItem.add(novoRegistro);
            limparLoteItem();
            InfoMessage.adicionado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void updateLoteItem() {
        try {
            if (loteDeItemBV.getId() == null) {
                throw new EDadoInvalidoException(new BundleUtil().getMessage("registro_nao_encontrado"));
            }
            LoteItem lote = loteDeItemBV.construirComID();
            atualizaLoteDAO.atualiza(lote);
            loteItemSelecionado.setObject(lote);
            listaLoteItem.set(loteItemSelecionado);
            limparLoteItem();
            InfoMessage.atualizado();
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void removeLoteItem() {
        try {
            LoteItem lote = (LoteItem) loteItemSelecionado.getObject();
            if (lote.getId() != null) {
                removeLoteItemDAO.remove(lote, lote.getId());
                listaLoteItem.remove(loteItemSelecionado);
                limparLoteItem();
                InfoMessage.removido();
            }
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void selecionaLoteItem(SelectEvent event) {
        loteDeItemBV = new LoteItemBV(loteItemSelecionado.getObject());
    }

    public void limparLoteItem() {
        loteDeItemBV = new LoteItemBV();
        loteItemSelecionado = null;
        if (t != null) {
            loteDeItemBV.setItem(t);
        }
    }

    public List<DetalhamentoDeItem> getDetalhamentoDeItem() {
        return Arrays.asList(DetalhamentoDeItem.values());
    }

    public List<TipoItem> getTipoItem() {
        return Arrays.asList(TipoItem.values());
    }

    public void limparJanelaPreco() {
        precoDeItemBV = new PrecoDeItemBV();
    }

    public ItemBV getItem() {
        return e;
    }

    public void setItem(ItemBV e) {
        this.e = e;
    }

    public PrecoDeItemBV getPrecoDeItemBV() {
        return precoDeItemBV;
    }

    public void setPrecoDeItemBV(PrecoDeItemBV precoDeItemBV) {
        this.precoDeItemBV = precoDeItemBV;
    }

    public List<SaldoEmDepositoTemplate> getSaldoEmDeposito() {
        return saldoEmDeposito;
    }

    public void setSaldoEmDeposito(List<SaldoEmDepositoTemplate> saldoEmDeposito) {
        this.saldoEmDeposito = saldoEmDeposito;
    }

    public List<SaldoEmContaTemplate> getSaldoEmConta() {
        return saldoEmConta;
    }

    public void setSaldoEmConta(List<SaldoEmContaTemplate> saldoEmConta) {
        this.saldoEmConta = saldoEmConta;
    }

    public List<SaldoEmLoteTemplate> getSaldoEmLote() {
        return saldoEmLote;
    }

    public void setSaldoEmLote(List<SaldoEmLoteTemplate> saldoEmLote) {
        this.saldoEmLote = saldoEmLote;
    }

    public BigDecimal getEstoqueTotal() {
        return estoqueTotal;
    }

    public void setEstoqueTotal(BigDecimal estoqueTotal) {
        this.estoqueTotal = estoqueTotal;
    }

    public List<ItemImagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<ItemImagem> imagens) {
        this.imagens = imagens;
    }

    public boolean isPrecoPorMargem() {
        return precoPorMargem;
    }

    public void setPrecoPorMargem(boolean precoPorMargem) {
        this.precoPorMargem = precoPorMargem;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }

    public List<PrecoDeItem> getPrecoAtual() {
        return precoAtual;
    }

    public void setPrecoAtual(List<PrecoDeItem> precoAtual) {
        this.precoAtual = precoAtual;
    }

    public boolean isRenderBotoes() {
        return renderBotoes;
    }

    public void setRenderBotoes(boolean renderBotoes) {
        this.renderBotoes = renderBotoes;
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

    public ItemImagem getItemImagem() {
        return itemImagem;
    }

    public void setItemImagem(ItemImagem itemImagem) {
        this.itemImagem = itemImagem;
    }

    public LoteItemBV getLoteDeItemBV() {
        return loteDeItemBV;
    }

    public void setLoteDeItemBV(LoteItemBV loteDeItemBV) {
        this.loteDeItemBV = loteDeItemBV;
    }

    public Model<LoteItem> getLoteItemSelecionado() {
        return loteItemSelecionado;
    }

    public void setLoteItemSelecionado(Model<LoteItem> loteItemSelecionado) {
        this.loteItemSelecionado = loteItemSelecionado;
    }

    public ModelList<LoteItem> getListaLoteItem() {
        return listaLoteItem;
    }

    public void setListaLoteItem(ModelList<LoteItem> listaLoteItem) {
        this.listaLoteItem = listaLoteItem;
    }

    public ContaDeEstoque getContaDeEstoque() {
        return contaDeEstoque;
    }

    public void setContaDeEstoque(ContaDeEstoque contaDeEstoque) {
        this.contaDeEstoque = contaDeEstoque;
    }

    public boolean isTabLote() {
        return tabLote;
    }

    public EstoqueService getEstoqueService() {
        return estoqueService;
    }
    
}
