package com.learn.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.cursomc.domain.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {};