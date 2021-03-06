/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.builder;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Banco;
import br.com.onesystem.domain.BoletoDeCartao;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cartao;
import br.com.onesystem.domain.Cheque;
import br.com.onesystem.domain.ConhecimentoDeFrete;
import br.com.onesystem.domain.Cotacao;
import br.com.onesystem.domain.Moeda;
import br.com.onesystem.domain.Nota;
import br.com.onesystem.domain.CobrancaVariavel;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.Credito;
import br.com.onesystem.domain.Fatura;
import br.com.onesystem.domain.Filial;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.Titulo;
import br.com.onesystem.domain.builder.BoletoDeCartaoBuilder;
import br.com.onesystem.domain.builder.ChequeBuilder;
import br.com.onesystem.domain.builder.CreditoBuilder;
import br.com.onesystem.domain.builder.TituloBuilder;
import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCartao;
import br.com.onesystem.valueobjects.EstadoDeCheque;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import br.com.onesystem.valueobjects.TipoFormaPagRec;
import br.com.onesystem.valueobjects.TipoLancamento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author Rafael Fernando Rauber
 */
public class CobrancaBV implements Serializable {

    private Long id;
    private ConhecimentoDeFrete conhecimentoDeFrete;
    private OperacaoFinanceira operacaoFinanceira;
    private BigDecimal valor;
    private Date emissao;
    private Date vencimento;
    private Banco banco;
    private String agencia;
    private String conta;
    private String numeroCheque;
    private EstadoDeCheque situacaoDeCheque;
    private BigDecimal multas;
    private BigDecimal juros;
    private BigDecimal descontos;
    private String emitente;
    private String observacao;
    private Cartao cartao;
    private String codigoTransacao;
    private SituacaoDeCartao situacaoDeCartao;
    private Moeda moeda;
    private Cambio cambio;
    private Recepcao recepcao;
    private ModalidadeDeCobranca modalidadeDeCobranca;
    private Integer dias;
    private Cotacao cotacao;
    private TipoLancamento tipoLancamento;
    private Pessoa pessoa;
    private Nota nota;
    private String historico;
    private Boolean entrada;
    private Fatura fatura;
    private SituacaoDeCobranca situacaoDeCobranca;
    private Filial filial;
    private Integer parcela;

    public CobrancaBV() {
        entrada = false;
    }

    public CobrancaBV(CobrancaBV p) {
        this.id = p.getId();
        this.nota = p.getNota();
        this.conhecimentoDeFrete = p.getConhecimentoDeFrete();
        this.operacaoFinanceira = p.getOperacaoFinanceira();
        this.valor = p.getValor();
        this.emissao = p.getEmissao();
        this.vencimento = p.getVencimento();
        this.banco = p.getBanco();
        this.agencia = p.getAgencia();
        this.conta = p.getConta();
        this.numeroCheque = p.getNumeroCheque();
        this.situacaoDeCheque = p.getSituacaoDeCheque();
        this.multas = p.getMultas();
        this.juros = p.getJuros();
        this.descontos = p.getDescontos();
        this.emitente = p.getEmitente();
        this.observacao = p.getObservacao();
        this.cartao = p.getCartao();
        this.codigoTransacao = p.getCodigoTransacao();
        this.situacaoDeCartao = p.getSituacaoDeCartao();
        this.moeda = p.getMoeda();
        this.cambio = p.getCambio();
        this.recepcao = p.getRecepcao();
        this.modalidadeDeCobranca = p.getModalidadeDeCobranca();
        this.dias = p.getDias();
        this.cotacao = p.getCotacao();
        this.tipoLancamento = p.getTipoLancamento();
        this.pessoa = p.getPessoa();
        this.entrada = p.getEntrada();
        this.historico = p.getHistorico();
        this.fatura = p.getFatura();
        this.situacaoDeCobranca = p.getSituacaoDeCobranca();
        this.filial = p.getFilial();
        this.parcela = p.getParcela();
    }

    public CobrancaBV(Long id, Nota notaEmitida, ConhecimentoDeFrete conhecimentoDeFrete,
            OperacaoFinanceira unidadeFinanceira, BigDecimal valor,
            Date emissao, Date vencimento, Banco banco, String agencia, String conta,
            String numeroCheque, EstadoDeCheque situacaoDeCheque, BigDecimal multas,
            BigDecimal juros, BigDecimal descontos, String emitente, String observacao,
            Cartao cartao, String codigoTransacao,
            SituacaoDeCartao tipoSituacaoCartao, Moeda moeda, Cambio cambio,
            Recepcao recepcao, ModalidadeDeCobranca tipoFormaDeRecebimentoParcela,
            Integer dias, Cotacao cotacao, TipoLancamento tipoLancamento, Pessoa pessoa, Boolean entrada, String historico, Fatura fatura,
            SituacaoDeCobranca situacaoDeCobranca, Filial filial, Integer parcela) {
        this.id = id;
        this.nota = notaEmitida;
        this.conhecimentoDeFrete = conhecimentoDeFrete;
        this.operacaoFinanceira = unidadeFinanceira;
        this.valor = valor;
        this.emissao = emissao;
        this.vencimento = vencimento;
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
        this.numeroCheque = numeroCheque;
        this.situacaoDeCheque = situacaoDeCheque;
        this.multas = multas;
        this.juros = juros;
        this.descontos = descontos;
        this.emitente = emitente;
        this.observacao = observacao;
        this.cartao = cartao;
        this.codigoTransacao = codigoTransacao;
        this.situacaoDeCartao = tipoSituacaoCartao;
        this.moeda = moeda;
        this.cambio = cambio;
        this.recepcao = recepcao;
        this.modalidadeDeCobranca = tipoFormaDeRecebimentoParcela;
        this.dias = dias;
        this.tipoLancamento = tipoLancamento;
        this.cotacao = cotacao;
        this.pessoa = pessoa;
        this.entrada = entrada;
        this.historico = historico;
        this.situacaoDeCobranca = situacaoDeCobranca;
        this.fatura = fatura;
        this.filial = filial;
        this.parcela = parcela;
    }

    public CobrancaBV(CobrancaVariavel p) {

        this.id = p.getId();
        this.valor = p.getValor();
        this.emissao = p.getEmissao();
        this.vencimento = p.getVencimento();
        this.pessoa = p.getPessoa();
        this.cotacao = p.getCotacao();
        this.historico = p.getHistorico();
        this.operacaoFinanceira = p.getOperacaoFinanceira();
        this.nota = p.getNota();
        this.entrada = p.getEntrada();
        this.moeda = p.getCotacao().getConta().getMoeda();
        this.dias = p.getDias() != null ? p.getDias().intValue() : 0;
        this.banco = p.getCotacao().getConta().getBanco();
        this.situacaoDeCobranca = p.getSituacaoDeCobranca();
        this.filial = p.getFilial();
        this.parcela = p.getParcela();

        if (p instanceof Cheque) {
            this.agencia = ((Cheque) p).getAgencia();
            this.conta = ((Cheque) p).getConta();
            this.numeroCheque = ((Cheque) p).getNumeroCheque();
            this.situacaoDeCheque = ((Cheque) p).getEstadoDeCheque();
            this.multas = ((Cheque) p).getMultas();
            this.juros = ((Cheque) p).getJuros();
            this.descontos = ((Cheque) p).getDescontos();
            this.emitente = ((Cheque) p).getEmitente();
            this.tipoLancamento = ((Cheque) p).getTipoLancamento();
            this.modalidadeDeCobranca = ModalidadeDeCobranca.CHEQUE;//((Cheque) p).getModalidade();

        } else if (p instanceof BoletoDeCartao) {
            this.cartao = ((BoletoDeCartao) p).getCartao();
            this.codigoTransacao = ((BoletoDeCartao) p).getCodigoTransacao();
            this.situacaoDeCartao = ((BoletoDeCartao) p).getSituacao();
            this.modalidadeDeCobranca = ((BoletoDeCartao) p).getModalidade();

        } else if (p instanceof Titulo) {
            this.recepcao = ((Titulo) p).getRecepcao();
            this.modalidadeDeCobranca = ((Titulo) p).getModalidade();
            this.fatura = ((Titulo) p).getFatura();
            this.conhecimentoDeFrete = ((Titulo) p).getConhecimentoDeFrete();
        } else if (p instanceof Credito) {
            this.modalidadeDeCobranca = ((Credito) p).getModalidade();
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SituacaoDeCobranca getSituacaoDeCobranca() {
        return situacaoDeCobranca;
    }

    public ConhecimentoDeFrete getConhecimentoDeFrete() {
        return conhecimentoDeFrete;
    }

    public void setConhecimentoDeFrete(ConhecimentoDeFrete conhecimentoDeFrete) {
        this.conhecimentoDeFrete = conhecimentoDeFrete;
    }

    public Cotacao getCotacao() {
        return cotacao;
    }

    public void setCotacao(Cotacao cotacao) {
        this.cotacao = cotacao;
    }

    public OperacaoFinanceira getOperacaoFinanceira() {
        return operacaoFinanceira;
    }

    public void setOperacaoFinanceira(OperacaoFinanceira operacaoFinanceira) {
        this.operacaoFinanceira = operacaoFinanceira;
    }

    public String getValorFormatado() {
        return NumberFormat.getCurrencyInstance(cotacao.getConta().getMoeda().getBandeira().getLocal()).format(valor);
    }

    public TipoLancamento getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(TipoLancamento tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Date getEmissao() {
        return emissao;
    }

    public void setEmissao(Date emissao) {
        this.emissao = emissao;
    }

    public Date getVencimento() {
        return vencimento;
    }

    public String getVencimentoFormatado() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(vencimento);
    }

    public String getDiaDaSemana() {
        if (vencimento != null) {
            DayOfWeek diaDaSemana = vencimento.toInstant().atZone(ZoneId.systemDefault()).getDayOfWeek();
            return new BundleUtil().getLabel(diaDaSemana.getDisplayName(TextStyle.FULL, Locale.US));
        }
        return "";
    }

    public void setVencimento(Date vencimento) {
        this.vencimento = vencimento;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getNumeroCheque() {
        return numeroCheque;
    }

    public void setNumeroCheque(String numeroCheque) {
        this.numeroCheque = numeroCheque;
    }

    public EstadoDeCheque getSituacaoDeCheque() {
        return situacaoDeCheque;
    }

    public void setSituacaoDeCheque(EstadoDeCheque situacaoDeCheque) {
        this.situacaoDeCheque = situacaoDeCheque;
    }

    public BigDecimal getMultas() {
        return multas;
    }

    public void setMultas(BigDecimal multas) {
        this.multas = multas;
    }

    public BigDecimal getJuros() {
        return juros;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public void setJuros(BigDecimal juros) {
        this.juros = juros;
    }

    public BigDecimal getDescontos() {
        return descontos;
    }

    public void setDescontos(BigDecimal descontos) {
        this.descontos = descontos;
    }

    public String getEmitente() {
        return emitente;
    }

    public void setEmitente(String emitente) {
        this.emitente = emitente;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Integer getDias() {
        return dias;
    }

    public void calcularVencimento() {
        LocalDate venc = LocalDate.now();
        venc = venc.plusDays(new Long(dias));
        vencimento = Date.from(venc.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public void calcularDiasDeVencimento() {
        LocalDate venc = vencimento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long dias = LocalDate.now().until(venc, ChronoUnit.DAYS);
        setDias(dias.intValue());
    }

    public void setDias(Integer dias) {
        this.dias = dias;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String codigoTransacao) {
        this.codigoTransacao = codigoTransacao;
    }

    public SituacaoDeCartao getSituacaoDeCartao() {
        return situacaoDeCartao;
    }

    public void setSituacaoDeCartao(SituacaoDeCartao situacaoDeCartao) {
        this.situacaoDeCartao = situacaoDeCartao;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public void setMoeda(Moeda moeda) {
        this.moeda = moeda;
    }

    public Cambio getCambio() {
        return cambio;
    }

    public void setCambio(Cambio cambio) {
        this.cambio = cambio;
    }

    public Recepcao getRecepcao() {
        return recepcao;
    }

    public String getDescricaoCheque() {
        return banco == null ? "" : banco.getNome() + " / " + agencia + " / " + conta + " Nº " + numeroCheque;
    }

    public String getDescricaoCartao() {
        return cartao == null ? "" : cartao.getNome() + " Nº " + codigoTransacao;
    }

    public void setRecepcao(Recepcao recepcao) {
        this.recepcao = recepcao;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Boolean getEntrada() {
        return entrada;
    }

    public void setEntrada(Boolean entrada) {
        this.entrada = entrada;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public ModalidadeDeCobranca getModalidadeDeCobranca() {
        return modalidadeDeCobranca;
    }

    public void setModalidadeDeCobranca(ModalidadeDeCobranca modalidadeDeCobranca) {
        this.modalidadeDeCobranca = modalidadeDeCobranca;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }

    public void setSituacaoDeCobranca(SituacaoDeCobranca situacaoDeCobranca) {
        this.situacaoDeCobranca = situacaoDeCobranca;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public Integer getParcela() {
        return parcela;
    }

    public void setParcela(Integer parcela) {
        this.parcela = parcela;
    }

    public BoletoDeCartao construirBoletoDeCartao() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comCartao(cartao).comCodigoTransacao(codigoTransacao).
                comVencimento(vencimento).comEmissao(emissao).comCotacao(cotacao).comPessoa(pessoa).comEntrada(entrada)
                .comTipoSituacao(SituacaoDeCartao.ABERTO).comValor(valor).comOperacaoFinanceira(operacaoFinanceira)
                .comSituacaoDeCobranca(situacaoDeCobranca).comFilial(filial).comParcela(parcela).construir();
    }

    public Cheque construirCheque() throws DadoInvalidoException {
        return new ChequeBuilder().comAgencia(agencia).comBanco(banco).comConta(conta).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa)
                .comEmissao(emissao).comEmitente(emitente).comNumeroCheque(numeroCheque)
                .comObservacao(observacao).comCotacao(cotacao).comTipoLancamento(tipoLancamento).comEntrada(entrada)
                .comTipoSituacao(EstadoDeCheque.ABERTO).comValor(valor).comVencimento(vencimento)
                .comSituacaoDeCobranca(situacaoDeCobranca).comParcela(parcela).comFilial(filial).construir();
    }

    public Titulo construirTitulo() throws DadoInvalidoException {
        return new TituloBuilder().comValor(valor).comSaldo(valor).comEmissao(emissao).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa)
                .comTipoFormaPagRec(TipoFormaPagRec.A_PRAZO).comCotacao(cotacao).comHistorico(observacao).comVencimento(vencimento)
                .comSituacaoDeCobranca(situacaoDeCobranca).comEntrada(entrada).comFatura(fatura).comParcela(parcela).comFilial(filial)
                .construir();
    }

    public Credito construirCredito() throws DadoInvalidoException {
        return new CreditoBuilder().comCotacao(cotacao).comEmissao(emissao).comEntrada(entrada)
                .comHistorico(historico).comNota(nota).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa).comSituacaoDeCobranca(situacaoDeCobranca)
                .comValor(valor).comVencimento(vencimento).comParcela(parcela).comFilial(filial).construir();
    }

    public BoletoDeCartao construirBoletoDeCartaoComId() throws DadoInvalidoException {
        return new BoletoDeCartaoBuilder().comID(id).comCartao(cartao).comCodigoTransacao(codigoTransacao)
                .comVencimento(vencimento).comEmissao(emissao).comCotacao(cotacao).comPessoa(pessoa).comEntrada(entrada).comSituacaoDeCobranca(situacaoDeCobranca)
                .comTipoSituacao(SituacaoDeCartao.ABERTO).comValor(valor).comOperacaoFinanceira(operacaoFinanceira).comNota(nota).comHistorico(historico)
                .comParcela(parcela).comFilial(filial).construir();
    }

    public Cheque construirChequeComID() throws DadoInvalidoException {
        return new ChequeBuilder().comID(id).comAgencia(agencia).comBanco(banco).comConta(conta).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa)
                .comEmissao(emissao).comEmitente(emitente).comNumeroCheque(numeroCheque).comSituacaoDeCobranca(situacaoDeCobranca)
                .comObservacao(observacao).comCotacao(cotacao).comTipoLancamento(tipoLancamento).comEntrada(entrada).comHistorico(historico)
                .comTipoSituacao(EstadoDeCheque.ABERTO).comValor(valor).comVencimento(vencimento).comNota(nota)
                .comParcela(parcela).comFilial(filial).construir();
    }

    public Titulo construirTituloComID() throws DadoInvalidoException {
        return new TituloBuilder().comId(id).comValor(valor).comSaldo(valor).comEmissao(emissao).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa)
                .comTipoFormaPagRec(TipoFormaPagRec.A_PRAZO).comCotacao(cotacao).comHistorico(historico).comVencimento(vencimento).comSituacaoDeCobranca(situacaoDeCobranca)
                .comFilial(filial).comEntrada(entrada).comNota(nota).comFatura(fatura)
                .comParcela(parcela).comConhecimentoDeFrete(conhecimentoDeFrete).construir();
    }

    public Credito construirCreditoComID() throws DadoInvalidoException {
        return new CreditoBuilder().comId(id).comCotacao(cotacao).comEmissao(emissao).comEntrada(entrada)
                .comHistorico(historico).comNota(nota).comOperacaoFinanceira(operacaoFinanceira).comPessoa(pessoa).comSituacaoDeCobranca(situacaoDeCobranca)
                .comParcela(parcela).comValor(valor).comVencimento(vencimento).comFilial(filial).construir();
    }

    public boolean equals(Object objeto) {
        if (objeto == null) {
            return false;
        }
        if (!(objeto instanceof CobrancaBV)) {
            return false;
        }
        CobrancaBV outro = (CobrancaBV) objeto;
        if (this.id == null) {
            return false;
        }
        return this.id.equals(outro.id);
    }

}
