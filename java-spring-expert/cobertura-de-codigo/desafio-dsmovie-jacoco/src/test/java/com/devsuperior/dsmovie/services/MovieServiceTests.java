package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.tests.MovieFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {

	@InjectMocks
	private MovieService movieService;

	@Mock
	private MovieRepository movieRepository;

	private MovieEntity movie;
	private MovieDTO movieDTO;
	Pageable pageable;
	private PageImpl<MovieEntity> page;

	@BeforeEach
	void setUp() throws Exception {
		movie = MovieFactory.createMovieEntity();
		movieDTO = MovieFactory.createMovieDTO();
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(movie));

		Mockito.when(movieRepository.searchByTitle(ArgumentMatchers.anyString(), (Pageable) ArgumentMatchers.any())).thenReturn(page);
	}

	@Test
	public void findAllShouldReturnPagedMovieDTO() {
		Page<MovieDTO> result = movieService.findAll(movie.getTitle(), pageable);
		Assertions.assertNotNull(result);
		Assertions.assertFalse(result.isEmpty());
		Assertions.assertEquals(1, result.getSize());
		Assertions.assertEquals(movie.getTitle(), result.getContent().getFirst().getTitle());
		Mockito.verify(movieRepository, Mockito.times(1)).searchByTitle(movie.getTitle(), pageable);
	}

	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}

	@Test
	public void insertShouldReturnMovieDTO() {
	}

	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
	}
}
