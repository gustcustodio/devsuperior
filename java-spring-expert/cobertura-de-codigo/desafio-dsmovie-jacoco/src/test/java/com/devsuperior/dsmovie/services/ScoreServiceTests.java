package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.dto.ScoreDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.entities.ScoreEntity;
import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.repositories.ScoreRepository;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import com.devsuperior.dsmovie.tests.ScoreFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class ScoreServiceTests {

	@InjectMocks
	private ScoreService scoreService;

	@Mock
	private UserService userService;

	@Mock
	private ScoreRepository scoreRepository;

	@Mock
	private MovieRepository movieRepository;

	private Long existingId, nonExistingId;
	private UserEntity user;
	private MovieEntity movie;
	private ScoreEntity score;
	private ScoreDTO scoreDTO;

	@BeforeEach
	void setUp() throws Exception{
		existingId = 1L;
		nonExistingId = 2L;
		user = UserFactory.createUserEntity();
		movie = MovieFactory.createMovieEntity();
		score = ScoreFactory.createScoreEntity();
		scoreDTO = ScoreFactory.createScoreDTO();

		Mockito.when(userService.authenticated()).thenReturn(user);
		Mockito.when(movieRepository.findById(existingId)).thenReturn(Optional.of(movie));
		Mockito.when(movieRepository.findById(nonExistingId)).thenReturn(Optional.empty());
		Mockito.when(scoreRepository.saveAndFlush(score)).thenReturn(score);
		Mockito.when(movieRepository.save(movie)).thenReturn(movie);
	}

	@Test
	public void saveScoreShouldReturnMovieDTO() {
		MovieDTO result = scoreService.saveScore(scoreDTO);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(existingId, result.getId());
		Mockito.verify(userService).authenticated();
		Mockito.verify(movieRepository, Mockito.times(1)).findById(existingId);
		Mockito.verify(scoreRepository, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any());
		Mockito.verify(movieRepository, Mockito.times(1)).save(ArgumentMatchers.any());
	}

	@Test
	public void saveScoreShouldThrowResourceNotFoundExceptionWhenNonExistingMovieId() {
		ScoreDTO scoreDTOWithNonExistingId = new ScoreDTO(nonExistingId, 5.0);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> scoreService.saveScore(scoreDTOWithNonExistingId));
		Mockito.verify(movieRepository, Mockito.times(1)).findById(nonExistingId);
	}

}
