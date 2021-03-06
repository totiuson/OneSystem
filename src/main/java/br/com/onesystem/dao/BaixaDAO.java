package br.com.onesystem.dao;

import br.com.onesystem.domain.Baixa;
import br.com.onesystem.domain.Caixa;
import br.com.onesystem.domain.Cambio;
import br.com.onesystem.domain.Cobranca;
import br.com.onesystem.domain.Conta;
import br.com.onesystem.domain.DepositoBancario;
import br.com.onesystem.domain.FormaDeCobranca;
import br.com.onesystem.domain.Pessoa;
import br.com.onesystem.domain.Recepcao;
import br.com.onesystem.domain.TipoDeCobranca;
import br.com.onesystem.domain.Transferencia;
import br.com.onesystem.domain.ValorPorCotacao;
import br.com.onesystem.valueobjects.EstadoDeBaixa;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import java.math.BigDecimal;
import java.util.Date;

public class BaixaDAO extends GenericDAO<Baixa> {

    public BaixaDAO() {
        super(Baixa.class);
        limpar();
    }

    public BaixaDAO selectSomaBaixaValor() {
        query = "";
        where = "";
        parametros.put("pValor", BigDecimal.ZERO);
        where += "select sum(baixa.valor) from Baixa baixa where baixa.valor >= :pValor ";
        return this;
    }

    public BaixaDAO eDeCambio(Cambio cambio) {
        if (cambio != null) {
            parametros.put("pCambio", cambio);
            where += "and baixa.cambio = :pCambio ";
        }
        return this;
    }

    public BaixaDAO eComDespesa() {
        where += "and baixa.despesa is not null ";
        return this;
    }

    public BaixaDAO eComDespesaProvisionada() {
        where += "and baixa.despesaProvisionada is not null ";
        return this;
    }

    public BaixaDAO eComReceitaProvisionada() {
        where += "and baixa.receitaProvisionada is not null ";
        return this;
    }

    public BaixaDAO eComTitulo() {
        where += "and baixa.titulo is not null ";
        return this;
    }

    public BaixaDAO eComTituloPagoRecebido() {
        where += "and baixa.titulo.saldo < baixa.titulo.valor ";
        return this;
    }

    public BaixaDAO eNaoCancelada() {
        where += "and baixa.estado <> :pNaoCancelada ";
        parametros.put("pNaoCancelada", EstadoDeBaixa.CANCELADO);
        return this;
    }

    public BaixaDAO eCancelada() {
        where += "and baixa.estado = :pCancelada ";
        parametros.put("pCancelada", EstadoDeBaixa.CANCELADO);
        return this;
    }

    public BaixaDAO eSemTitulo() {
        where += "and baixa.titulo is null ";
        return this;
    }

    public BaixaDAO eSemDespesaProvisionada() {
        where += "and baixa.despesaProvisionada is null ";
        return this;
    }

    public BaixaDAO eSemReceitaProvisionada() {
        where += "and baixa.receitaProvisionada is null ";
        return this;
    }

    public BaixaDAO eDeRecepcao(Recepcao recepcao) {
        if (recepcao != null) {
            parametros.put("pRecepcao", recepcao);
            where = "and baixa.recepcao = :pRecepcao ";
        }
        return this;
    }

    public BaixaDAO ePorEmissaoEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataInicial", dataInicial);
        parametros.put("pDataFinal", dataFinal);
        where += "and baixa.emissao between :pDataInicial and :pDataFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDoTituloEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataETInicial", dataInicial);
        parametros.put("pDataETFinal", dataFinal);
        where += "and baixa.titulo.emissao between :pDataETInicial and :pDataETFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDaDespesaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataEDPInicial", dataInicial);
        parametros.put("pDataEDPFinal", dataFinal);
        where += "and baixa.despesaProvisionada.emissao between :pDataEDPInicial and :pDataEDPFinal ";
        return this;
    }

    public BaixaDAO ePorEmissaoDaReceitaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataEDPInicial", dataInicial);
        parametros.put("pDataEDPFinal", dataFinal);
        where += "and baixa.receitaProvisionada.emissao between :pDataEDPInicial and :pDataEDPFinal ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeTituloEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVTInicial", dataInicial);
        parametros.put("pDataVTFinal", dataFinal);
        where += "and (baixa.titulo.vencimento between :pDataVTInicial and :pDataVTFinal or baixa.titulo.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeDespesaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVDPInicial", dataInicial);
        parametros.put("pDataVDPFinal", dataFinal);
        where += "and (baixa.despesaProvisionada.vencimento between :pDataVDPInicial and :pDataVDPFinal or baixa.despesaProvisionada.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorVencimentoDeReceitaProvisionadaEntre(Date dataInicial, Date dataFinal) {
        parametros.put("pDataVDPInicial", dataInicial);
        parametros.put("pDataVDPFinal", dataFinal);
        where += "and (baixa.receitaProvisionada.vencimento between :pDataVDPInicial and :pDataVDPFinal or baixa.receitaProvisionada.vencimento is null) ";
        return this;
    }

    public BaixaDAO ePorEmissaoMenorIgualDa(Date data) {
        parametros.put("pData", data);
        where += "and baixa.emissao <= :pData ";
        return this;
    }

    public BaixaDAO ePorEmissaoMenorDa(Date data) {
        parametros.put("pMData", data);
        where += "and baixa.emissao < :pMData ";
        return this;
    }

    public BaixaDAO eEntrada() {
        parametros.put("pOperacaoFinanceira", OperacaoFinanceira.ENTRADA);
        where += "and baixa.operacaoFinanceira = :pOperacaoFinanceira ";
        return this;
    }

    public BaixaDAO eSaida() {
        parametros.put("pOperacaoFinanceiraSaida", OperacaoFinanceira.SAIDA);
        where += "and baixa.operacaoFinanceira = :pOperacaoFinanceiraSaida ";
        return this;
    }

    public BaixaDAO ePorConta(Conta conta) {
        if (conta != null) {
            parametros.put("pConta", conta);
            where += "and baixa.cotacao.conta = :pConta ";
        }
        return this;
    }

    public BaixaDAO ePorCaixa(Caixa caixa) {
        if (caixa != null) {
            parametros.put("pCaixa", caixa);
            where += "and baixa.caixa = :pCaixa ";
        }
        return this;
    }

    public BaixaDAO ePorEstadoDeBaixa(EstadoDeBaixa estado) {
        if (estado != null) {
            parametros.put("pEstado", estado);
            where += "and baixa.estado = :pEstado ";
        }
        return this;
    }

    public BaixaDAO ePorPessoa(Pessoa pessoa) {
        if (pessoa != null) {
            parametros.put("pPessoa", pessoa);
            where += "and baixa.pessoa = :pPessoa ";
        }
        return this;
    }

    public BaixaDAO ePorTransferencia(Transferencia transferencia) {
        if (transferencia != null) {
            parametros.put("pTransferencia", transferencia);
            where += "and baixa.transferencia = :pTransferencia ";
        }
        return this;
    }

    public BaixaDAO ePorDepositoBancario(DepositoBancario depositoBancario) {
        if (depositoBancario != null) {
            parametros.put("pDepositoBancario", depositoBancario);
            where += "and baixa.depositoBancario = :pDepositoBancario ";
        }
        return this;
    }

    public BaixaDAO ePorCobranca(Cobranca cobranca) {
        if (cobranca != null) {
            parametros.put("pCobranca", cobranca);
            where += "and baixa.tipoDeCobranca.cobranca = :pCobranca ";
        }
        return this;
    }

    public BaixaDAO ePorValorPorCotacao(ValorPorCotacao valorPorCotacao) {
        if (valorPorCotacao != null) {
            parametros.put("pValorPorCotacao", valorPorCotacao);
            where += "and baixa.valorPorCotacao = :pValorPorCotacao ";
        }
        return this;
    }

    public BaixaDAO ePorTipoDeCobranca(TipoDeCobranca tipo) {
        if (tipo != null) {
            parametros.put("pTipo", tipo);
            where += "and baixa.tipoDeCobranca = :pTipo ";
        }
        return this;
    }

    public BaixaDAO ePorFormaDeCobranca(FormaDeCobranca forma) {
        if (forma != null) {
            parametros.put("pForma", forma);
            where += "and baixa.formaDeCobranca = :pForma ";
        }
        return this;
    }

    public BaixaDAO orderByEmissaoEId() {
        where += "order by baixa.emissao,baixa.id asc ";
        return this;
    }

    public BaixaDAO orderByMoeda() {
        where += "order by baixa.conta.moeda asc";
        return this;
    }

    public BigDecimal buscarSaldoAnterior(Date data, Conta conta) {
        BigDecimal entradas = selectSomaBaixaValor().ePorEmissaoMenorDa(data).eEntrada().ePorConta(conta).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal saidas = selectSomaBaixaValor().ePorEmissaoMenorDa(data).eSaida().ePorConta(conta).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado;
    }

    public BigDecimal buscarSaldoAnterior(Date data, Conta conta, Caixa caixa, EstadoDeBaixa estado) {
        BigDecimal entradas = selectSomaBaixaValor().ePorEmissaoMenorDa(data).eEntrada().ePorConta(conta).ePorCaixa(caixa).ePorEstadoDeBaixa(estado).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal saidas = selectSomaBaixaValor().ePorEmissaoMenorDa(data).eSaida().ePorConta(conta).ePorCaixa(caixa).ePorEstadoDeBaixa(estado).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado;
    }

    public BigDecimal buscarSaldoFinal(Date dataFinal, Conta conta) {

        BigDecimal entradas = selectSomaBaixaValor().ePorEmissaoMenorIgualDa(dataFinal).eEntrada().ePorConta(conta).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal saidas = selectSomaBaixaValor().ePorEmissaoMenorIgualDa(dataFinal).eSaida().ePorConta(conta).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado == null ? BigDecimal.ZERO : resultado;
    }

    public BigDecimal buscarSaldoPorDataEConta(Date dataInicial, Date dataFinal, Conta conta) {
        BigDecimal entradas = selectSomaBaixaValor().ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal saidas = selectSomaBaixaValor().ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado == null ? BigDecimal.ZERO : resultado;
    }

    public BigDecimal buscarSaldoPorDataEConta(Date dataInicial, Date dataFinal, Conta conta, Caixa caixa) {
        BigDecimal entradas = selectSomaBaixaValor().ePorEmissaoEntre(dataInicial, dataFinal).eEntrada().ePorConta(conta).ePorCaixa(caixa).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal saidas = selectSomaBaixaValor().ePorEmissaoEntre(dataInicial, dataFinal).eSaida().ePorConta(conta).ePorCaixa(caixa).eNaoCancelada().resultadoOperacaoMatematica();
        BigDecimal resultado = entradas.subtract(saidas);

        return resultado == null ? BigDecimal.ZERO : resultado;
    }

}
