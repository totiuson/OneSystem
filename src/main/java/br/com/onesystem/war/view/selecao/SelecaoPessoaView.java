package br.com.onesystem.war.view.selecao;

import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.war.service.PessoaService;
import br.com.onesystem.war.service.impl.BasicCrudMBImpl;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class SelecaoPessoaView extends BasicCrudMBImpl<Pessoa> implements Serializable {

    @ManagedProperty("#{pessoaService}")
    private PessoaService service;

    @PostConstruct
    public void init() {
        beans = service.buscarPessoas();
    }

    @Override
    public void abrirDialogo() {
        exibirNaTela("selecaoPessoa");
    }

    public PessoaService getService() {
        return service;
    }

    public void setService(PessoaService service) {
        this.service = service;
    }

}
