package com.caixaeletronico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caixaeletronico.models.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	List<Transacao> findByContaId(Long contaId);
}
