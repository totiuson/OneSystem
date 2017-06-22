package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Cidade;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.builder.FilialBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import java.io.Serializable;

public class FilialBV implements Serializable {

    private Long id;
    private String nome;
    private String razao_social;
    private String fantasia;
    private String ruc;
    private String endereco;
    private String bairro;
    private Cidade cidade;
    private String telefone;

    public FilialBV(Filial filialSelecionada) {
        this.id = filialSelecionada.getId();
        this.nome = filialSelecionada.getNome();
        this.razao_social = filialSelecionada.getRazao_social();
        this.fantasia = filialSelecionada.getFantasia();
        this.ruc = filialSelecionada.getRuc();
        this.endereco = filialSelecionada.getEndereco();
        this.bairro = filialSelecionada.getBairro();
        this.cidade = filialSelecionada.getCidade();
        this.telefone = filialSelecionada.getTelefone();
    }

    public FilialBV() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Filial construir() throws DadoInvalidoException {
        return new FilialBuilder().comNome(nome).comRazaoSocial(razao_social).comFantasia(fantasia).comRuc(ruc)
                .comEndereco(endereco).comBairro(bairro).comCidade(cidade).comTelefone(telefone).construir();
    }

    public Filial construirComID() throws DadoInvalidoException {
        return new FilialBuilder().comID(id).comNome(nome).comRazaoSocial(razao_social).comFantasia(fantasia).comRuc(ruc)
                .comEndereco(endereco).comBairro(bairro).comCidade(cidade).comTelefone(telefone).construir();
    }
}
