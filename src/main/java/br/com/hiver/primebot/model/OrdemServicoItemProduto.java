package br.com.hiver.primebot.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
@Entity
public class OrdemServicoItemProduto {
	
	@ManyToOne
	private OrdemServico ordemServico;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Produto produto;
	@Column
	private Integer quantidade;
	@Column
	private Double valorUnt;
	@Column
	private Double valorTotal;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (this != obj)
			return false;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdemServicoItemProduto other = (OrdemServicoItemProduto) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
}

 	
