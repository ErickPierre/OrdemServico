package br.com.hiver.primebot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.hiver.primebot.model.Cliente;

@Component
public class ClienteDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Cliente> listar(){
		return manager.createQuery("SELECT P FROM Cliente P", Cliente.class).getResultList();
	}
	public List<Cliente> listar(String filtro){
		List<Cliente> retorno = null;
		Integer tamanho = filtro.length();
		if(tamanho == 0) {
			return listar();
		}
		if(StringUtils.isNumeric(filtro)) {
			TypedQuery<Cliente> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Cliente P "
	  			+ "WHERE P.codigo = :codigo "
	  			+ "OR lower(P.nome) like :nome", Cliente.class);
			retorno =  tq.setParameter("codigo", Long.valueOf(filtro))
			.setParameter("nome", "%"+filtro.toLowerCase().trim()+"%").getResultList();
		}else{
			TypedQuery<Cliente> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Cliente P "
	  			+ "WHERE lower(P.nome) like :nome", Cliente.class);
			retorno = tq.setParameter("nome", "%"+filtro.toLowerCase().trim()+"%").getResultList();
		}
		return retorno;
	}
	@Transactional
	public Cliente incluir(Cliente entity) {
		manager.persist(entity);
		return entity;
	}
	@Transactional
	public Cliente alterar(Cliente entity) {
		return manager.merge(entity);	
	}
	@Transactional
	public void excluir(Cliente entity) {
		entity = findById(entity.getCodigo());
		manager.remove(entity);
	}	
		public Cliente findById(Long codigo){
			return manager.find(Cliente.class, codigo);
		}	
	}
	

