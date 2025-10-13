package com.carrafafood.carrafafood.auth.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Permissao {

	@EqualsAndHashCode.Include
	@Id
	private Long Id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String descricao;

}
