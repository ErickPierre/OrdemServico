package br.com.hiver.primebot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hiver.primebot.model.OrdemServico;
import br.com.hiver.primebot.model.OrdemServicoItemProduto;
import br.com.hiver.primebot.model.OrdemServicoItemServico;


public interface OrdemServicoItemServicoDAO extends JpaRepository<OrdemServicoItemServico, Long>{

	@Query("SELECT o FROM OrdemServicoItemServico o WHERE o.ordemServico.codigo = :os ")
	public List<OrdemServicoItemServico> listarServicos(@Param("os") Long os);
	
}
