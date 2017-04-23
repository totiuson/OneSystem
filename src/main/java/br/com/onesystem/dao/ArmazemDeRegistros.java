package br.com.onesystem.dao;

import br.com.onesystem.util.JPAUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class ArmazemDeRegistros<T> implements Serializable {

    private EntityManager em = JPAUtil.getEntityManager();

    private final Class<T> classe;

    public ArmazemDeRegistros(Class<T> classe) {
        this.classe = classe;
    }

    public List<T> listaTodosOsRegistros() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));
        List<T> lista = em.createQuery(query).getResultList();

        return lista;
    }

    public List<T> listaRegistrosDaConsulta(String consulta, Map<String, Object> parametros) {
        TypedQuery<T> query = em.createQuery(consulta, classe);
        adicionarParametrosNaConsulta(query, parametros);
        return query.getResultList();
    }

    public T resultadoUnicoDaConsulta(String consulta, Map<String, Object> parametros) {
        try {
            TypedQuery<T> query = em.createQuery(consulta, classe);
            adicionarParametrosNaConsulta(query, parametros);
            return query.getSingleResult();
        } catch (NoResultException nre) {
            nre.getMessage();
        }
        return null;
    }

    private void adicionarParametrosNaConsulta(TypedQuery<T> query, Map<String, Object> parametros) {
        for (Map.Entry<String, Object> map : parametros.entrySet()) {
            query.setParameter(map.getKey(), map.getValue());
        }
    }

    public T find(T objeto) {
        return em.find(classe, objeto);
    }

}
