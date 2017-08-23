/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.converter;

import br.com.onesystem.domain.Estado;
import br.com.onesystem.war.builder.EstadoBV;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Rafael
 */
@FacesConverter(value = "estadoBVConverter", forClass = EstadoBV.class)
public class EstadoBVConverter implements Converter, Serializable {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object instanceof Estado) {
                return new EstadoBV((Estado) object);
            } else if (object instanceof EstadoBV) {
                return (EstadoBV) object;
            }
        }
        return new EstadoBV();
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if (object != null) {
            if (object instanceof EstadoBV) {
                String id = String.valueOf(((EstadoBV) object).getId());
                uic.getAttributes().put(id, (EstadoBV) object);
                return id;
            } else if (object instanceof Estado) {
                String id = String.valueOf(((Estado) object).getId());
                uic.getAttributes().put(id, (Estado) object);
                return id;
            } else {
                return object.toString();
            }
        } else {
            return "";
        }
    }
}
