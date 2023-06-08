package br.com.hiver.primebot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.hiver.primebot.model.OrdemServico;
import br.com.hiver.primebot.model.Produto;

@Component
public class OrdemServicoDAO {
	
	@Autowired
	OrdemServicoItemProdutoDAO osipd;
	@Autowired
	OrdemServicoItemServicoDAO osisd;
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<OrdemServico> listar(){
		return manager.createQuery("SELECT P FROM OrdemServico P", OrdemServico.class).getResultList();
	}
	public List<OrdemServico> listar(String filtro){
		List<OrdemServico> retorno = null;
		Integer tamanho = filtro.length();
		if(tamanho == 0) {
			return listar();
		}
		if(StringUtils.isNumeric(filtro)) {
			TypedQuery<OrdemServico> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM OrdemServico P "
	  			+ "WHERE P.codigo = :codigo "
	  			+ "OR lower(P.placa) like :placa", OrdemServico.class);
			retorno =  tq.setParameter("codigo", Long.valueOf(filtro))
			.setParameter("placa", "%"+filtro.toLowerCase().trim()+"%").getResultList();
		}else{
			TypedQuery<OrdemServico> tq = manager.createQuery(
					"SELECT P "
	  			+ "FROM OrdemServico P "
	  			+ "WHERE lower(P.placa) like :placa", OrdemServico.class);
			retorno = tq.setParameter("placa", "%"+filtro.toLowerCase().trim()+"%").getResultList();

		}
		return retorno;
	}
	@Transactional
	public OrdemServico incluir(OrdemServico entity) {
			manager.persist(entity);
			return entity;
	}
	@Transactional
	public OrdemServico alterar(OrdemServico entity) {
			return manager.merge(entity);
		
	}
	@Transactional
	public void  excluir(OrdemServico entity) {
		entity = findById(entity.getCodigo());
		
			 manager.remove(entity);
	}
	public OrdemServico findById(Long codigo){
		OrdemServico os = manager.find(OrdemServico.class, codigo);
		
		os.setListaProdutos(osipd.listarProdutos(os.getCodigo()));
		os.setListaServicos(osisd.listarServicos(os.getCodigo()));
		
		
		//if(os.getListaProdutos().size()>0)
		//	os.getListaProdutos().get(0);
		
		return os;
	}	
	
}
