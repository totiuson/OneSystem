/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.domain;

import br.com.onesystem.exception.DadoInvalidoException;
import br.com.onesystem.valueobjects.EstadoDeNota;
import br.com.onesystem.valueobjects.EstadoDePedido;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Rafael Cordeiro
 */
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "SEQ_PEDIDOAFORNECEDORES",
        sequenceName = "SEQ_PEDIDOAFORNECEDORES")
public class PedidoAFornecedores extends Pedido implements Serializable {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "pedidoAFornecedores")
    private List<NotaRecebida> notasrecebidas;

    public PedidoAFornecedores() {
    }

    public PedidoAFornecedores(Long id, Pessoa pessoa, Operacao operacao, List<ItemDePedido> itens, String contato, EstadoDePedido estado,
            FormaDeRecebimento formaDeRecebimento, List<ParcelaDePedido> parcelaDePedido, Moeda moedaPadrao, BigDecimal desconto,
            BigDecimal acrescimo, BigDecimal despesaCobranca, BigDecimal frete,
            BigDecimal totalEmDinheiro, Date emissao, Date previsaoDeEntrega, String observacao) throws DadoInvalidoException {
        super(id, pessoa, operacao, itens, contato, estado, formaDeRecebimento, parcelaDePedido, moedaPadrao, desconto, acrescimo, despesaCobranca, frete, totalEmDinheiro, emissao, previsaoDeEntrega, observacao);
    }

       
    public void adiciona(ItemDePedido item) {
        if (itens == null) {
            itens = new ArrayList<>();
        }
        item.setPedido(this);
        itens.add(item);
    }

    public void atualiza(ItemDePedido item) {
        if (itens.contains(item)) {
            itens.set(itens.indexOf(item), item);
        } else {
            item.setPedido(this);
            itens.add(item);
        }
    }

    public void remove(ItemDePedido item) {
        itens.remove(item);
    }

    public void adiciona(ParcelaDePedido parcela) {
        if (parcelaDePedido == null) {
            parcelaDePedido = new ArrayList<>();
        }
        parcela.setPedido(this);
        parcelaDePedido.add(parcela);
    }

    public void atualiza(ParcelaDePedido parcela) {
        if (parcelaDePedido.contains(parcela)) {
            parcelaDePedido.set(parcelaDePedido.indexOf(parcela), parcela);
        } else {
            parcela.setPedido(this);
            parcelaDePedido.add(parcela);
        }
    }

    public void remove(ParcelaDePedido parcela) {
        parcelaDePedido.remove(parcela);
    }
    
     public void adiciona(NotaRecebida nota) {
        if(this.notasrecebidas == null){
            this.notasrecebidas = new ArrayList<>();
        }
        this.notasrecebidas.add(nota);
        BigDecimal total = this.notasrecebidas.stream().filter(n -> !n.getEstado().equals(EstadoDeNota.CANCELADO)).map(n -> n.getItens().stream().map(ItemDeNota::getQuantidade).reduce(BigDecimal.ZERO, BigDecimal::add)).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (total.equals(getItensDePedido().stream().map(i -> i.getQuantidade()).reduce(BigDecimal.ZERO, BigDecimal::add))) {
            efetiva();
        }
    }

    public List<NotaRecebida> getNotasrecebidas() {
        return notasrecebidas;
    }

}
