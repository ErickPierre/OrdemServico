package br.com.hiver.primebot.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class OrdemServico implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	@Column
	private String placa;
	@Column(length = 1)
	private String categoria;
	@Column
	private String statusOs;
	@Column
	private String observacao;
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy = "ordemServico", cascade = {CascadeType.ALL})
	@Fetch(FetchMode.SELECT)
	private List<OrdemServicoItemProduto> listaProdutos;
	
	@OneToMany(mappedBy = "ordemServico", cascade = {CascadeType.ALL})
	@Fetch(FetchMode.SELECT)
	private List<OrdemServicoItemServico> listaServicos;
	
	public List<OrdemServicoItemProduto> getListaProdutos() {
		return listaProdutos;
	}
	public void setListaProdutos(List<OrdemServicoItemProduto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}
	public List<OrdemServicoItemServico> getListaServicos() {
		return listaServicos;
	}
	public void setListaServicos(List<OrdemServicoItemServico> listaServicos) {
		this.listaServicos = listaServicos;
	}
	public OrdemServico() {
		this.listaProdutos = new ArrayList<OrdemServicoItemProduto>();
		this.listaServicos = new ArrayList<OrdemServicoItemServico>();
		
	}
	public OrdemServico(Long codigo, String placa, String categoria, String statusOs, String observacao) {
		
		this.codigo = codigo;
		this.placa = placa;
		this.categoria = categoria;
		this.statusOs = statusOs;
		this.observacao = observacao;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getStatusOs() {
		return statusOs;
	}
	public void setStatusOs(String statusOs) {
		this.statusOs = statusOs;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServico other = (OrdemServico) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	
	
}
