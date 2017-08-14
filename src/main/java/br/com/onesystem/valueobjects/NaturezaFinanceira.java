/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

/**
 *
 * @author Rafael-Pc
 */
public enum NaturezaFinanceira {

    RECEITA(1,"Receita"),
    DESPESA(2,"Despesa");    
    
    private Integer id;
    private String nome;

    private NaturezaFinanceira(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
