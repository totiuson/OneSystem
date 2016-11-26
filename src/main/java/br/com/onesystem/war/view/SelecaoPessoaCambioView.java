package br.com.onesystem.war.view;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.war.service.ConfiguracaoCambioService;
import br.com.onesystem.war.service.PessoaService;
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
public class SelecaoPessoaCambioView implements Serializable {

    private Pessoa pessoaSelecionada;
    private List<Pessoa> pessoaLista;
    private List<Pessoa> pessoasFiltradas;

    @ManagedProperty("#{configuracaoCambioService}")
    private ConfiguracaoCambioService service;

    @PostConstruct
    public void init() {
        pessoaLista = service.buscarPessoas();
    }

    public void abrirDialogo() {
        Map<String, Object> opcoes = new HashMap<>();
        opcoes.put("modal", true);
        opcoes.put("resizable", false);
        opcoes.put("contentWidth", 950);
        opcoes.put("draggable", false);
        opcoes.put("contentHeight", 500);

        RequestContext.getCurrentInstance().openDialog("selecaoPessoaCambio", opcoes, null);
    }

    public void selecionar() {
        RequestContext.getCurrentInstance().closeDialog(pessoaSelecionada);
    }

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public List<Pessoa> getPessoaLista() {
        return pessoaLista;
    }

    public void setPessoaLista(List<Pessoa> pessoaLista) {
        this.pessoaLista = pessoaLista;
    }

    public List<Pessoa> getPessoasFiltradas() {
        return pessoasFiltradas;
    }

    public void setPessoasFiltradas(List<Pessoa> pessoasFiltradas) {
        this.pessoasFiltradas = pessoasFiltradas;
    }

    public ConfiguracaoCambioService getService() {
        return service;
    }

    public void setService(ConfiguracaoCambioService service) {
        this.service = service;
    }
}
