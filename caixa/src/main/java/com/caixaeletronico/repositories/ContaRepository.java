package com.caixaeletronico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.caixaeletronico.models.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

}
