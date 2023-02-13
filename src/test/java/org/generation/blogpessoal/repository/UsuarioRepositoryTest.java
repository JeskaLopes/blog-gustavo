package org.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

// essa notação define que o teste executado vai rodar em uma porta aleatória local no computador
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

// essa notação define que todos os testes rodados serão executados por classe, não será a aplicação completa de uma vez
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {
    
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	// para conseguirmos executar os testes com consulta no banco de dados, precisamos primeiro inserir valores a ser pesquisados, por isso precisamos cadastrar os usuários previamente antes de rodar as funções de teste
	@BeforeAll
	void start(){

		usuarioRepository.deleteAll();
		
		// Para cadastrar um usuário no banco de dados h2, precisamos passar um id ficticio, pois como é um banco de dados em memória ele não cria automaticamente.
		usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs2O.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", "https://i.imgur.com/NtyGneo.jpg"));
		
		usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg"));

        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));

	}

	// a primeira função testa o metodo que deve retornar um usuário especifico do banco de dados pelo email dele
	@Test
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		
		// aqui estou indicando o email que precisa ser pesquisado
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
		
		// aqui eu preparo o que eu espero receber do teste, passando exatamente o mesmo email pesquisado. O teste vai comparar os dois valores, e caso os dois sejam iguais, o teste será positivo
		assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
	}

	// a segunda função testa se a aplicação consegue trazer um array de usuarios que possuam o nome silva no nome
	@Test
	@DisplayName("Retorna 3 usuarios")
	public void deveRetornarTresUsuarios() {

		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");

		assertEquals(3, listaDeUsuarios.size());
		
		assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
		assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));
		
	}

	// por ultimo limpamos o banco de dados h2
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
	}
	
}
