package br.souza.ifinancas.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.souza.ifinancas.application.JPAUtil;
import br.souza.ifinancas.application.RepositoryException;

public class Repository<T> {
	private EntityManager em = null;
	
	public Repository() {
		em = JPAUtil.getEntityManager();
	}
	
	public T create(T entity) throws RepositoryException {
		try { 
			getEntityManager().getTransaction().begin();
			T e = getEntityManager().merge(entity);
			getEntityManager().getTransaction().commit();
			return e;
		} catch (Exception e) {
			System.out.println("Erro ao executar o create");
			e.printStackTrace();
			throw new RepositoryException("Erro ao criar");
		}
	}
	
	public void remove(T entity) throws RepositoryException {
		try { 
			getEntityManager().getTransaction().begin();
			T o = getEntityManager().merge(entity);
			getEntityManager().remove(o);
			getEntityManager().getTransaction().commit();
		} catch (Exception e) {
			System.out.println("Erro ao executar o remove");
			e.printStackTrace();
			throw new RepositoryException("Erro ao Remover");
		}

	}
	
	public List<T> getAll(Class clazz) throws RepositoryException {
		try { 
			String entityName = clazz.getSimpleName();
			// jpql
			Query query = em.createQuery("SELECT u FROM "+entityName+" u");
			return query.getResultList();
		} catch (Exception e) {
			System.out.println("Erro ao executar o getAll");
			e.printStackTrace();
			throw new RepositoryException("Erro ao Consultar no banco.");
		}

	}
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
}
