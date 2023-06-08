package br.com.hiver.primebot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
 

import br.com.hiver.primebot.model.Produto;
import br.com.hiver.primebot.pesquisa.PesquisaFilter;

@Component
public class ProdutoDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Produto> listar(){
		return manager.createQuery("SELECT P FROM Produto P", Produto.class).getResultList();
	}
	public List<Produto> listar(String filtro){
		List<Produto> retorno = null;
		Integer tamanho = filtro.length();
		if(tamanho == 0) {
			return listar();
		}
		if(StringUtils.isNumeric(filtro)) {
			TypedQuery<Produto> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Produto P "
	  			+ "WHERE P.codigo = :codigo "
	  			+ "OR lower(P.descricao) like :descricao", Produto.class);
			retorno =  tq.setParameter("codigo", Long.valueOf(filtro))
			.setParameter("descricao", "%"+filtro.toLowerCase().trim()+"%").getResultList();
		}else{
			TypedQuery<Produto> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Produto P "
	  			+ "WHERE lower(P.descricao) like :descricao", Produto.class);
			retorno = tq.setParameter("descricao", "%"+filtro.toLowerCase().trim()+"%").getResultList();

		}
		return retorno;
	}
	@Transactional
	public Produto incluir(Produto entity){
		manager.persist(entity);
		return entity;
	}
	@Transactional
	public Produto alterar(Produto entity){
		return manager.merge(entity);		
	}
	@Transactional
	public void excluir(Produto entity){
		entity = findById(entity.getCodigo());
		manager.remove(entity);		
	}	
	public Produto findById(Long codigo){
		return manager.find(Produto.class, codigo);
	}	

}
