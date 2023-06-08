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

import br.com.hiver.primebot.dao.ClienteDAO;
import br.com.hiver.primebot.model.Cliente;
import lombok.Data;
import lombok.Getter;
@Component
@Scope("view")
@Data
public class ClienteView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Cliente> listaClientes;
	private Cliente entity;
	private String pesquisa;
	boolean novo;
	
	@Autowired
	private ClienteDAO clienteDAO;
	
	@PostConstruct
	public void inicializar(){
		novo = true;
		entity = new Cliente();
		
		String codigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
		if(org.apache.commons.lang3.StringUtils.isNumeric(codigo)){
           entity = clienteDAO.findById(Long.valueOf(codigo)); 
           novo =false;
		}
		listaClientes = clienteDAO.listar();
		
	} 
	public List<Cliente> getListaClientes(){
		return this.listaClientes;
	}
		public void abrirCadastro(Cliente entity){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
					"/CadastroCliente.xhtml?codigo="+entity.getCodigo()
					);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void salvar(){
			//try {
			if (novo) {
		     	clienteDAO.incluir(entity);
			}else {
		     	clienteDAO.alterar(entity);
			}
	     	FacesContext.getCurrentInstance() .addMessage("msgeral", new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", ""));
	     	entity= new Cliente();
	     	//	return ("/PesquisaCliente.xhtml");
			//}catch(Throwable t) {
				
			//}
		}
	
	public Cliente getEntity(){
		return entity;
		
	}
	public void setEntity(Cliente entity) {
		this.entity = entity;
	}
	public void pesquisar() {
		listaClientes = clienteDAO.listar(pesquisa);
	}
	public void excluir() {
		clienteDAO.excluir(entity);
		listaClientes = clienteDAO.listar("");
	}
}


