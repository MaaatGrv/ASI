package com.sp.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sp.model.Hero;
import com.sp.repository.HeroRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = HeroService.class)
public class HeroServiceTest {
		
	@MockBean
	private HeroRepository hRepo;
	
	@Autowired
	private HeroService hService;
	
	Hero tmpHero=new Hero(1, "jdoe", "strong", 100, "https//url.com");
	
	@Test
	public void getHero() {
		Mockito.when(
				hRepo.findById(Mockito.any())
				).thenReturn(Optional.ofNullable(tmpHero));
		Hero heroInfo=hService.getHero(45);
		assertTrue(heroInfo.toString().equals(tmpHero.toString()));
	}
}
