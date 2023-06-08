package br.com.hiver.primebot.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import antlr.StringUtils;
import br.com.hiver.primebot.dao.ProdutoDAO;
import br.com.hiver.primebot.model.Produto;
import lombok.Data;
import lombok.Getter;

@Component
@Scope("view")
@Data
public class ProdutoView implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private List<Produto> listaProdutos;
	private Produto entity;
	private String pesquisa;
	boolean novo;
	
	@Autowired 
	private ProdutoDAO produtoDAO;
	
	@PostConstruct
	public void inicializar(){
		novo = true;
		entity = new Produto();
		
		String codigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
		if(org.apache.commons.lang3.StringUtils.isNumeric(codigo)){
           entity = produtoDAO.findById(Long.valueOf(codigo)); 
           novo =false;
		}   
		listaProdutos = produtoDAO.listar(); 
				//new ArrayList<>();
	}	
	public List<Produto> getListaProdutos(){
		return this.listaProdutos;
	}
	public void abrirCadastro(Produto entity){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(
				"/CadastroProduto.xhtml?codigo="+entity.getCodigo()
				);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void salvar(){
		//try {
		if (novo) {
	     	produtoDAO.incluir(entity);
		}else {
	     	produtoDAO.alterar(entity);
		}
     	FacesContext.getCurrentInstance() .addMessage("msgeral", new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", ""));
     	entity= new Produto();
     	//	return ("/PesquisaProduto.xhtml");
		//}catch(Throwable t) {
			
		//}
	}

	public Produto getEntity(){
		return entity;
	}

	public void setEntity(Produto entity) {
		this.entity = entity;
	}
	public void pesquisar() {
		listaProdutos = produtoDAO.listar(pesquisa);
	}
	public void excluir() {
		produtoDAO.excluir(entity);
		listaProdutos = produtoDAO.listar("");
	}
	

}
