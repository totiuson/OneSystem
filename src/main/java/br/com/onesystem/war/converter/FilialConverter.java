/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Filial;
import br.com.onesystem.war.service.impl.BasicConverter;
import java.io.Serializable;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "filialConverter", forClass = Filial.class)
public class FilialConverter extends BasicConverter<Filial> implements Converter, Serializable {

    public FilialConverter() {
        super(Filial.class);
    }
}
