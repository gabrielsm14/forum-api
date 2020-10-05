package br.com.forum.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.forum.modelo.Curso;
import br.com.forum.repository.CursoRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY.NONE)
@ActiveProfiles("test") // quando rodar a class de test força esse profile como sendo ativo 
public class CursoRepositoryTest {

	@Autowired
	private CursoRepository repository;

	@Autowired	
	private TestEntityManager em;
	
	@Test
	public void deveriaCarregarUmCursoAoBuscarPeloSeuNome() {
		String nomeCurso = "HTML 5";
		
		Curso html5 = new Curso();
		html5.setNome(nomeCurso);
		html5.setCategoria("programação");
		em.persist(html5);
		
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNotNull(curso);
		Assert.assertEquals(nomeCurso, 	curso.getNome());
	}
	
	@Test
	public void NaoDeveriaCarregarUmCursoNaoCadastrado() {
		String nomeCurso = "JPA";
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNull(curso);
	}

}
