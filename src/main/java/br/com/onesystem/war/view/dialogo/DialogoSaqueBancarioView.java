package br.com.onesystem.war.view.dialogo;

import br.com.onesystem.dao.AdicionaDAO;
import br.com.onesystem.dao.ContaDAO;
import br.com.onesystem.dao.CotacaoDAO;
import br.com.onesystem.domain.Configuracao;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.SaqueBancario;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.SessionUtil;
import br.com.onesystem.war.builder.SaqueBancarioBV;
import br.com.onesystem.war.service.ConfiguracaoService;
import br.com.onesystem.war.service.impl.BasicMBImpl;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class DialogoSaqueBancarioView extends BasicMBImpl<SaqueBancario, SaqueBancarioBV> implements Serializable {

    private Cotacao cotacaoPadrao;
    private List<Conta> contaComCotacaoBancaria;
    private List<Conta> contaComCotacaoEmpresa;
    private List<Cotacao> cotacaoBancariaLista;
    private List<Cotacao> cotacaoEmpresaLista;

    @Inject
    private Configuracao configuracao;

    @Inject
    private CotacaoDAO cotacaoDAO;

    @Inject
    private ContaDAO contaDAO;
    
    @PostConstruct
    public void init() {
        limparJanela();
        inicializar();
    }

    @Override
    public void limparJanela() {
        try {
            t = null;
            e = new SaqueBancarioBV();
            e.setFilial((Filial) SessionUtil.getObject("filial", FacesContext.getCurrentInstance()));
        } catch (DadoInvalidoException die) {
            die.print();
        }
    }

    public void inicializar() {
        e.setEmissao(new Date());
        cotacaoPadrao = cotacaoDAO.porMoeda(configuracao.getMoedaPadrao()).naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().resultado();
        cotacaoEmpresaLista = cotacaoDAO.naMaiorEmissao(e.getEmissao()).porCotacaoEmpresa().listaDeResultados();
        cotacaoBancariaLista = cotacaoDAO.naUltimaEmissao(e.getEmissao()).porCotacaoBancaria().listaDeResultados();
        contaComCotacaoEmpresa = contaDAO.semBanco().ePorMoedas(cotacaoEmpresaLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
        contaComCotacaoBancaria = contaDAO.comBanco().ePorMoedas(cotacaoBancariaLista.stream().map(c -> c.getConta().getMoeda()).collect(Collectors.toList())).listaDeResultados();
    }

    public void abrirDialogo() {
        exibeNaTela();
    }

    private void exibeNaTela() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("resizable", false);
        opcoes.put("width", 400);
        opcoes.put("draggable", true);
        opcoes.put("height", 475);
        opcoes.put("closable", false);
        opcoes.put("contentWidth", "100%");
        opcoes.put("contentHeight", "100%");
        opcoes.put("headerElement", "customheader");

        RequestContext.getCurrentInstance().openDialog("/dialogo/dialogoSaqueBancario", opcoes, null);
    }

    @Override
    public void selecionar(SelectEvent event) {
    }

    public void sacar() {
        try {
            t = e.construir();
            t.geraBaixaDeSaque(e.getCotacaoDeOrigem(), e.getCotacaoDeDestino());

            new AdicionaDAO<>().adiciona(t);
            RequestContext.getCurrentInstance().closeDialog(t);
            limparJanela();
        } catch (DadoInvalidoException ex) {
            ex.print();
        }
    }

    public void fechar() {
        limparJanela();
        RequestContext.getCurrentInstance().closeDialog(null);
    }

    public void selecionaCotacaoDeOrigemConformeConta() {
        if (e.getOrigem() != null) {
            e.setCotacaoDeOrigem(cotacaoBancariaLista.stream().filter(c -> c.getConta().equals(e.getOrigem())).findFirst().get());
        }
    }

    public void selecionaCotacaoDeDestinoConformeConta() {
        if (e.getDestino() != null) {
            e.setCotacaoDeDestino(cotacaoEmpresaLista.stream().filter(c -> c.getConta().equals(e.getDestino())).findFirst().get());
        }
    }

    public Cotacao getCotacaoPadrao() {
        return cotacaoPadrao;
    }

    public void setCotacaoPadrao(Cotacao cotacaoPadrao) {
        this.cotacaoPadrao = cotacaoPadrao;
    }

    public List<Conta> getContaComCotacaoBancaria() {
        return contaComCotacaoBancaria;
    }

    public void setContaComCotacaoBancaria(List<Conta> contaComCotacaoBancaria) {
        this.contaComCotacaoBancaria = contaComCotacaoBancaria;
    }

    public List<Conta> getContaComCotacaoEmpresa() {
        return contaComCotacaoEmpresa;
    }

    public void setContaComCotacaoEmpresa(List<Conta> contaComCotacaoEmpresa) {
        this.contaComCotacaoEmpresa = contaComCotacaoEmpresa;
    }

    public List<Cotacao> getCotacaoBancariaLista() {
        return cotacaoBancariaLista;
    }

    public void setCotacaoBancariaLista(List<Cotacao> cotacaoBancariaLista) {
        this.cotacaoBancariaLista = cotacaoBancariaLista;
    }

    public List<Cotacao> getCotacaoEmpresaLista() {
        return cotacaoEmpresaLista;
    }

    public void setCotacaoEmpresaLista(List<Cotacao> cotacaoEmpresaLista) {
        this.cotacaoEmpresaLista = cotacaoEmpresaLista;
    }

}
