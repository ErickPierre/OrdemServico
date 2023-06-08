package br.com.hiver.primebot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.hibernate.service.spi.InjectService;
import org.springframework.context.annotation.Scope;

import br.com.hiver.primebot.model.Cliente;
import br.com.hiver.primebot.model.Produto;
import br.com.hiver.primebot.model.Servico;
import br.com.hiver.primebot.model.Usuario;
import br.com.hiver.primebot.pesquisa.PesquisaFilter;

@Scope("view")
public class PesquisaProdutosBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Produto produto;
	private Cliente cliente;
	private Servico servico;
	private Usuario usuario;
	
	
	private PesquisaFilter filtro;
	private List<Produto> produtosFiltrados;
	
		public void PesquisaProdutosBean(){
			filtro = new PesquisaFilter();
				produtosFiltrados = new ArrayList<>();
		}
		
		public void pesquisar() {
			produtosFiltrados = produto(filtro);
		}
		private List<Produto> produto(PesquisaFilter filtro2) {
			// TODO Auto-generated method stub
			return null;
		}

		public List<Produto> getPedidosFiltrados(){
				return produtosFiltrados;
		}
		}

