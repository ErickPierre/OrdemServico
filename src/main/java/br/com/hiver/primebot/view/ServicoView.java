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

import br.com.hiver.primebot.dao.ServicoDAO;
import br.com.hiver.primebot.model.Servico;
import lombok.Data;
import lombok.Getter;

@Component
@Scope("view")
@Data
public class ServicoView implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private List<Servico> listaServico;
	private Servico entity;
	private String pesquisa;
	boolean novo;
	
	@Autowired 
	private ServicoDAO servicoDAO;
	
	@PostConstruct
	public void inicializar() {
		novo = true;
		entity = new Servico();
		
		String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
		if(org.apache.commons.lang3.StringUtils.isNumeric(id)){
           entity = servicoDAO.findById(Long.valueOf(id)); 
           novo =false;
		}   
		listaServico= servicoDAO.listar();
				//new ArrayList<>();
	}
	public List<Servico> getListaServico(){
		return this.listaServico;
	}
		public void abrirCadastro(Servico entity){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
					"/CadastroServico.xhtml?id="+entity.getId()
					);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		public void salvar(){
			//try {
			if (novo) {
		     	servicoDAO.incluir(entity);
			}else {
		     	servicoDAO.alterar(entity);
			}
		FacesContext.getCurrentInstance() .addMessage("msgeral", new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", " "));
		entity = new Servico();
		//	return ("/PesquisaProduto.xhtml");
		//}catch(Throwable t) {
			
		//}
	}
	public Servico getEntity() {
			return entity;
	}
	public void setEntity(Servico entity) {
		this.entity = entity;
	}
	public void pesquisar() {
		listaServico = servicoDAO.listar(pesquisa);
	}
	public void excluir() {
		servicoDAO.excluir(entity);
		listaServico = servicoDAO.listar("");
	}
}
