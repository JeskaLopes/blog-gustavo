package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@BeforeAll
	void start() {

		usuarioRepository.deleteAll();

		usuarioRepository.save(new Usuario(0L, "joão Silva", "Joao@email.com.br", "13465278", "htto://i.igmur.com/FETvs20.jpg"));

		usuarioRepository.save(new Usuario(0L, "joão Guilherme", "Joaoguilherme@email.com.br", "12465278","htto://i.igmur.com/FETvs20.jpg"));

		usuarioRepository.save(new Usuario(0L, "joão Gomes", "Joaogomes@email.com.br", "11465278", "htto://i.igmur.com/FETvs20.jpg"));

		usuarioRepository.save(new Usuario(0L, "joão Gustavo", "Joaogustavo@email.com.br", "10465278","htto://i.igmur.com/FETvs20.jpg"));

	}

	@Test
	@DisplayName("Retornar 1 usuario")
	public void deveRetornarUmUsuario() {
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("Joao@email.com.br");
		assertTrue(usuario.get().getUsuario().equals("Joao@email.com.br"));
	}

	@Test
	@DisplayName("Retornar 3 usuario")
	public void deveRetornarTresUsuario() {

		List<Usuario> ListaDeUsuario = usuarioRepository.findAllByNomeContainingIgnoreCase("João");
		assertEquals(3, ListaDeUsuario.size());
		assertTrue( ListaDeUsuario.get(0).getNome().equals("joão Silva"));
		assertTrue( ListaDeUsuario.get(1).getNome().equals("joão Gomes"));
		assertTrue( ListaDeUsuario.get(2).getNome().equals("joão Gustavo"));
	}

	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
}
