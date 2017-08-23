/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.GrupoFiscal;
import br.com.onesystem.util.StringUtils;
import br.com.onesystem.war.service.GrupoFiscalService;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "grupoFiscalConverter", forClass = GrupoFiscal.class)
public class GrupoFiscalConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof GrupoFiscal) {
                return (GrupoFiscal) object;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof GrupoFiscal) {
                String id = String.valueOf(((GrupoFiscal) object).getId());
                uic.getAttributes().put(id, (GrupoFiscal) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
