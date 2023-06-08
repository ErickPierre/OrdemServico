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

import br.com.hiver.primebot.dao.UsuarioDAO;
import br.com.hiver.primebot.model.Cliente;
import br.com.hiver.primebot.model.Usuario;
import lombok.Data;
import lombok.Getter;

@Component
@Scope("view")
@Data
public class UsuarioView implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private List<Usuario> listaUsuarios;
	private Usuario entity;
	private String pesquisa;
	boolean novo;
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@PostConstruct
	public void inicializar() {
		novo= true;
		entity = new Usuario();
		
		String codigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
		if(org.apache.commons.lang3.StringUtils.isNumeric(codigo)){
           entity = usuarioDAO.findById(Long.valueOf(codigo)); 
           novo =false;
		}
		listaUsuarios= usuarioDAO.listar();
		
	}
	public List<Usuario> getListaUsuarios(){
		return this.listaUsuarios;
	}
		public void abrirCadastro(Usuario entity){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(
					"/CadastroUsuario.xhtml?codigo="+entity.getCodigo()
					);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			public void salvar(){
				//try {
				if (novo) {
			     	usuarioDAO.incluir(entity);
				}else {
			     	usuarioDAO.alterar(entity);
				}
		     	FacesContext.getCurrentInstance() .addMessage("msgeral", new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com sucesso", ""));
		     	entity= new Usuario();
		     	//	return ("/PesquisaCliente.xhtml");
				//}catch(Throwable t) {
					
				//}
			
	}
			
	public Usuario getEntity() {
		return entity;
	}
	public void getEntity (Usuario entity) {
		this.entity = entity;
	}
	public void setEntity(Usuario entity) {
		this.entity = entity;
	}
	public void pesquisar() {
		listaUsuarios = usuarioDAO.listar(pesquisa);
	}
	public void excluir() {
		usuarioDAO.excluir(entity);
		listaUsuarios= usuarioDAO.listar("");
	}
}
