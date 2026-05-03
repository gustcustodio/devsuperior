package com.devsuperior.dsmovie.controllers;

import org.junit.jupiter.api.Test;

public class MovieControllerRA {
	
	@Test
	public void findAllShouldReturnOkWhenMovieNoArgumentsGiven() {
	}
	
	@Test
	public void findAllShouldReturnPagedMoviesWhenMovieTitleParamIsNotEmpty() {		
	}
	
	@Test
	public void findByIdShouldReturnMovieWhenIdExists() {		
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() {	
	}
	
	@Test
	public void insertShouldReturnUnprocessableEntityWhenAdminLoggedAndBlankTitle() {
	}
	
	@Test
	public void insertShouldReturnForbiddenWhenClientLogged() {
	}
	
	@Test
	public void insertShouldReturnUnauthorizedWhenInvalidToken() {
	}
	
}
