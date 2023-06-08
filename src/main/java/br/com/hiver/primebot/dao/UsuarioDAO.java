package br.com.hiver.primebot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.hiver.primebot.model.Cliente;
import br.com.hiver.primebot.model.Usuario;

@Component
public class UsuarioDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Usuario> listar() {
		return manager.createQuery("SELECT P FROM Usuario P", Usuario.class) .getResultList();
		
	}
	public List<Usuario> listar(String filtro){
		List<Usuario> retorno = null;
		Integer tamanho = filtro.length();
		if(tamanho == 0) {
			return listar();
		}
		if(StringUtils.isNumeric(filtro)) {
			TypedQuery<Usuario> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Usuario P "
	  			+ "WHERE P.codigo = :codigo "
	  			+ "OR lower(P.nome) like :nome", Usuario.class);
			retorno =  tq.setParameter("codigo", Long.valueOf(filtro))
			.setParameter("nome", "%"+filtro.toLowerCase().trim()+"%").getResultList();
		}else{
			TypedQuery<Usuario> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM Usuario P "
	  			+ "WHERE lower(P.nome) like :nome", Usuario.class);
			retorno = tq.setParameter("nome", "%"+filtro.toLowerCase().trim()+"%").getResultList();
		}
		return retorno;
	}
	@Transactional
	public Usuario incluir(Usuario entity) {
		manager.persist(entity);
			return entity;
	}
	@Transactional
	public Usuario alterar (Usuario entity) {
			return manager.merge(entity);
	}
	@Transactional
	public void excluir(Usuario entity) {
		entity = findById(entity.getCodigo());
		manager.remove(entity);
	}	
		public Usuario findById(Long codigo){
			return manager.find(Usuario.class, codigo);
		}	
}
