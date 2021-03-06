/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.util.BundleUtil;
import br.com.onesystem.valueobjects.ModalidadeDeCobranca;
import br.com.onesystem.valueobjects.OperacaoFinanceira;
import br.com.onesystem.valueobjects.SituacaoDeCobranca;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Rafael Fernando Rauber
 */
@Entity
@DiscriminatorValue("CONTA_CORRENTE")
public class Credito extends CobrancaVariavel implements Serializable {

    public Credito() {
    }

    public Credito(Long id, Date emissao, Pessoa pessoa, Cotacao cotacao, String historico, OperacaoFinanceira operacaoFinanceira,
            BigDecimal valor, Date vencimento, Nota nota, Boolean entrada, SituacaoDeCobranca situacaoDeCobranca, Filial filial, Integer parcela) throws DadoInvalidoException {
        super(id, emissao, pessoa, cotacao, historico, operacaoFinanceira, valor, vencimento, nota, entrada, situacaoDeCobranca, filial, parcela);
    }

    @Override
    public ModalidadeDeCobranca getModalidade() {
        return ModalidadeDeCobranca.CREDITO;
    }

    @Override
    public String getDetalhes() {
        return "";
    }

}
