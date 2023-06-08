package br.com.hiver.primebot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.hiver.primebot.model.Produto;
import br.com.hiver.primebot.model.Servico;

@Component
public class ServicoDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Servico> listar(){
		return manager.createQuery("SELECT P FROM Servico P", Servico.class).getResultList();
	}
	public List<Servico> listar(String filtro){
		List<Servico> retorno = null;
		Integer tamanho = filtro.length();
		if(tamanho == 0) {
			return listar();
		}
		if(StringUtils.isNumeric(filtro)) {
			TypedQuery<Servico> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Servico P "
	  			+ "WHERE P.id = :id "
	  			+ "OR lower(P.descricao) like :descricao", Servico.class);
			retorno =  tq.setParameter("id", Long.valueOf(filtro))
			.setParameter("descricao", "%"+filtro.toLowerCase().trim()+"%").getResultList();
		}else{
			TypedQuery<Servico> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Servico P "
	  			+ "WHERE lower(P.descricao) like :descricao", Servico.class);
			retorno = tq.setParameter("descricao", "%"+filtro.toLowerCase().trim()+"%").getResultList();

		}
		return retorno;
	}	
	@Transactional
	public Servico incluir(Servico entity) {
		manager.persist(entity);
		return entity;
	}
	@Transactional
	public Servico alterar(Servico entity) {
			return manager.merge(entity);
	}
	@Transactional
	public void  excluir(Servico entity) {
		entity = findById(entity.getId());
			 manager.remove(entity);
	}
	public Servico findById(Long id){
		return manager.find(Servico.class, id);
	}	
}
