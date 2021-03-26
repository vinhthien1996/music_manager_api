package com.example.demo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.entity.Genre;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class GenreRepositoryTest {
	
	@Autowired
	private GenreRepository repository;
	
	@Test
	public void testSaveGenre() {
        Genre genre = new Genre("ABC");
        Genre genreTest = repository.save(genre);
        assertNotNull(genreTest);
        assertEquals(repository.save(genre).getGenre_name(), genre.getGenre_name());
    }
	
	@Test
	public void testFindGenre() {
        List<Genre> list = repository.findAll();
        assertNotNull(list);
        assertEquals(list.size(), 12);
    }
	
	@Test
	public void testFindGenreById() {
		// Test find by id
		assertNotNull(repository.findById(264));
		assertEquals(repository.findById(264).get().getGenre_name(), "Pop");
	}
	
	@Test
	public void testcountGenre() {
		// Test find by id
		assertEquals(repository.countGenre(), 12);
	}
}
