package br.com.hiver.primebot.view;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.hiver.primebot.dao.OrdemServicoDAO;
import br.com.hiver.primebot.dao.ServicoDAO;
import br.com.hiver.primebot.model.Cliente;
import br.com.hiver.primebot.model.OrdemServico;
import br.com.hiver.primebot.model.OrdemServicoItemProduto;
import br.com.hiver.primebot.model.OrdemServicoItemServico;
import br.com.hiver.primebot.model.Produto;
import br.com.hiver.primebot.model.Servico;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component 
@Scope("view")
@Data
public class OrdemServicoView implements Serializable{

	

	private static final long serialVersionUID = 1L;
	
	private List<OrdemServico> listaOrdemServico;
	private OrdemServico entity;
	
	private Servico servico;
	private Produto produto;
	private Integer quantidadeServico;
	private Integer quantidadeProduto;
	private String pesquisa;
	boolean novo;
	
	@Autowired
	private OrdemServicoDAO ordemServicoDAO;
		
	@PostConstruct
	public void inicializar() {
		novo = true;
		entity= new OrdemServico();
		servico = new Servico();
		produto = new Produto();
		quantidadeServico = Integer.valueOf(0);
		quantidadeProduto = Integer.valueOf(0);
		entity.setCliente(new Cliente());
		listaOrdemServico= ordemServicoDAO.listar();
				//new ArrayList<>();
		String codigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
		if(org.apache.commons.lang3.StringUtils.isNumeric(codigo)){
           entity = ordemServicoDAO.findById(Long.valueOf(codigo)); 
           novo =false;
		}
	}
	public List<OrdemServico> getListaOrdemServico(){
		return this.listaOrdemServico;
		
	}
	public void abrirCadastro(OrdemServico entity){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(
				"/CadastroOS.xhtml?codigo="+entity.getCodigo()
				);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	public void salvar() {
		if (novo) {
	     	ordemServicoDAO.incluir(entity);
		}else {
	     	ordemServicoDAO.alterar(entity);
		}
		FacesContext.getCurrentInstance() .addMessage("msgeral", new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", " "));
		entity = new OrdemServico();
	}
	public OrdemServico getEntity() {
			return entity;
	}
	public void setEntity(OrdemServico entity) {
		this.entity = entity;
	}
	public void incluirServico(){
		OrdemServicoItemServico aux = new OrdemServicoItemServico();
		aux.setOrdemServico(this.entity);
		aux.setServico(this.servico);
		aux.setQuantidade(this.quantidadeServico);
		aux.setValorUnt( servico.getValor().doubleValue() );
		aux.setValorTotal(servico.getValor().doubleValue() * this.quantidadeServico );
		
		entity.getListaServicos().add(aux);
	}
	public void excluirServico(OrdemServicoItemServico itemSel){
		
		entity.getListaServicos().remove(itemSel);
		/*
		for (int i=0;i<entity.getListaServicos().size()-1;i++) {
			OrdemServicoItemServico aux = entity.getListaServicos().get(i);
			if(aux.equals(itemSel)) {
				entity.getListaServicos().remove(i);
				return;
			}
			
		}
		
		*/
	}	 
	public void incluirProduto(){
		OrdemServicoItemProduto aux = new OrdemServicoItemProduto();
		aux.setOrdemServico(this.entity);
		aux.setProduto(this.produto);
		aux.setQuantidade(this.quantidadeProduto);
		aux.setValorUnt( produto.getValor().doubleValue() );
		aux.setValorTotal(produto.getValor().doubleValue() * this.quantidadeProduto );
		
		entity.getListaProdutos().add(aux);
	}
	public void excluirProduto(OrdemServicoItemProduto itemSel){
		
		entity.getListaProdutos().remove(itemSel);
	}
	public void pesquisar() {
		listaOrdemServico = ordemServicoDAO.listar(pesquisa);
	}
	public void excluir() {
		ordemServicoDAO.excluir(entity);
		listaOrdemServico = ordemServicoDAO.listar("");
	}
	
}
