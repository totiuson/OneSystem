/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.dao;

import br.com.onesystem.domain.TipoDespesa;

/**
 *
 * @author Rafael
 */
public class TipoDespesaDAO extends GenericDAO<TipoDespesa> {

    public TipoDespesaDAO() {
        super(TipoDespesa.class);
        limpar();
    }

    public TipoDespesaDAO ePorMoeda(TipoDespesa despesa) {
        where += "and tipoDespesa.nome = :dNome ";
        parametros.put("dNome", despesa.getNome());
        return this;
    }

    public TipoDespesaDAO porId(Long id) {
        where += " and tipoDespesa.id = :dId ";
        parametros.put("dId", id);
        return this;
    }

}
