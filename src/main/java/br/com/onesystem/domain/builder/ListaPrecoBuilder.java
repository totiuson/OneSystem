package br.com.onesystem.domain.builder;

import br.com.onesystem.domain.ListaDePreco;
import br.com.onesystem.exception.DadoInvalidoException;

/**
 *
 * @author Rafael
 */
public class ListaPrecoBuilder {

    private Long ID;
    private String nome;

    public ListaPrecoBuilder comID(Long ID) {
        this.ID = ID;
        return this;
    }

    public ListaPrecoBuilder comNome(String nombre) {
        this.nome = nombre;
        return this;
    }

  
    public ListaDePreco construir() throws DadoInvalidoException {
        return new ListaDePreco(ID, nome);
    }

}
