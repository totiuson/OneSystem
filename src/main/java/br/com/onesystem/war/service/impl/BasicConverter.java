/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.onesystem.war.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author Rafael
 */
public abstract class BasicConverter<Bean> implements Serializable {

    private Class clazz;

    public BasicConverter(Class clazz) {
        this.clazz = clazz;
    }

    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if (value != null && !value.isEmpty()) {
            Object object = uic.getAttributes().get(value);
            if (object != null && object.getClass().equals(clazz) || object.getClass().getSuperclass().equals(clazz)) {
                return (Bean) object;
            }
        }
        return null;
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        try {
            if (object != null) {
                if (object.getClass().equals(clazz) || object.getClass().getSuperclass().equals(clazz)) {
                    Bean bean = (Bean) object;

                    //Pega o id do objeto
                    Method m = bean.getClass().getMethod("getId", null);
                    m.setAccessible(true);
                    Long idObject = (Long) m.invoke(bean, null);

                    //Grava o objeto no componente e devolve o id
                    String id = String.valueOf(idObject);
                    uic.getAttributes().put(id, bean);
                    return id;
                } else {
                    return object.toString();
                }
            } else {
                return "";
            }
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Erro de acesso ao método - BasicConverter.");
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Erro parametros inválidos ao acessar o método - BasicConverter.");
        } catch (InvocationTargetException ex) {
            throw new RuntimeException("Erro na invocação do método - BasicConverter.");
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException("Erro o método não existe - BasicConverter.");
        } catch (SecurityException ex) {
            throw new RuntimeException("Erro de segurança ao realizar o acesso - BasicConverter.");
        }
    }

}
