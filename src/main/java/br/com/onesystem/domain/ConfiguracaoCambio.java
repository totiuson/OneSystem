package br.com.onesystem.domain;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.inject.Any;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Any
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1,
        sequenceName = "SEQ_CONFCAMBIO", name = "SEQ_CONFCAMBIO")
public class ConfiguracaoCambio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONFCAMBIO")
    private Long id;

    private boolean ativo;

    @OneToOne
    private Conta contaCaixa;

    @OneToOne
    private Pessoa pessoaCaixa;

    @OneToOne
    private TipoDespesa despesaDivisaoLucro;

    @OneToMany(mappedBy = "configuracaoCambio", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private List<Pessoa> pessoaDivisaoLucro;

    public ConfiguracaoCambio() {
    }

    public ConfiguracaoCambio(Long id, TipoDespesa despesaDivisaoLucro, boolean ativo, Pessoa pessoaCaixa, Conta contaCaixa) {
        this.id = id;
        this.despesaDivisaoLucro = despesaDivisaoLucro;
        this.ativo = ativo;
        this.pessoaCaixa = pessoaCaixa;
        this.contaCaixa = contaCaixa;
    }

    public Long getId() {
        return id;
    }

    public TipoDespesa getDespesaDivisaoLucro() {
        return despesaDivisaoLucro;
    }

    public List<Pessoa> getPessoaDivisaoLucro() {
        return pessoaDivisaoLucro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public Pessoa getPessoaCaixa() {
        return pessoaCaixa;
    }

    public Conta getContaCaixa() {
        return contaCaixa;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof ConfiguracaoCambio)) {
            return false;
        }
        ConfiguracaoCambio outro = (ConfiguracaoCambio) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

    @Override
    public String toString() {
        return "ConfiguracaoCambio{" + "id=" + id + ", ativo=" + ativo + ", contaCaixa=" + contaCaixa + ", despesaDivisaoLucro=" + despesaDivisaoLucro + ", pessoaDivisaoLucro=" + pessoaDivisaoLucro + '}';
    }

}
