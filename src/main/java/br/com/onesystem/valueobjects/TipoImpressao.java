/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.valueobjects;

import br.com.onesystem.util.BundleUtil;

/**
 *
 * @author Rafael
 */
public enum TipoImpressao {
    
    VISUALIZAR(new Long(1), new BundleUtil().getLabel("Visualizar")),
    IMPRIMIR(new Long(2), new BundleUtil().getLabel("Imprimir")),
    NADA_A_FAZER(new Long(3), new BundleUtil().getLabel("Nada_A_Fazer"));    
    
    private Long id;
    private String nome;

    private TipoImpressao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
}
