package br.com.hiver.primebot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hiver.primebot.model.OrdemServico;
import br.com.hiver.primebot.model.OrdemServicoItemProduto;


public interface OrdemServicoItemProdutoDAO extends JpaRepository<OrdemServicoItemProduto, Long>{

	@Query("SELECT o FROM OrdemServicoItemProduto o WHERE o.ordemServico.codigo = :os ")
	public List<OrdemServicoItemProduto> listarProdutos(@Param("os") Long os);
	
}
