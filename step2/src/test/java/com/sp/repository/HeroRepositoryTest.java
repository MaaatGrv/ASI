package com.sp.repository;

import java.util.ArrayList;
import java.util.List;

import com.sp.model.Hero;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class HeroRepositoryTest {

	@Autowired
	HeroRepository hrepo;

	@BeforeEach
	public void setUp() {
		hrepo.save(new Hero(1, "jdoe", "strong", 100, "https//url.com"));
	}

	@AfterEach
	public void cleanUp() {
		hrepo.deleteAll();
	}

	@Test
	public void saveHero() {
		hrepo.save(new Hero(1, "test", "testPower", 1, "https//test_url.com"));
		assertTrue(true);
	}

	@Test
	public void saveAndGetHero() {
		hrepo.deleteAll();
		hrepo.save(new Hero(2, "test1", "testPower1", 1, "https//test1_url.com"));
		List<Hero> heroList = new ArrayList<>();
		hrepo.findAll().forEach(heroList::add);
		assertTrue(heroList.size() == 1);
		assertTrue(heroList.get(0).getSuperPowerName().equals("testPower1"));
		assertTrue(heroList.get(0).getName().equals("test1"));
		assertTrue(heroList.get(0).getImgUrl().equals("https//test1_url.com"));
	}

	@Test
	public void getHero() {
		List<Hero> heroList = hrepo.findByName("jdoe");
		assertTrue(heroList.size() == 1);
		assertTrue(heroList.get(0).getName().equals("jdoe"));
		assertTrue(heroList.get(0).getSuperPowerName().equals("strong"));
		assertTrue(heroList.get(0).getImgUrl().equals("https//url.com"));
	}

	@Test
	public void findByName() {
		hrepo.save(new Hero(1, "test1", "testPower1", 1, "https//test1_url.com"));
		hrepo.save(new Hero(2, "test2", "testPower2", 2, "https//test2_url.com"));
		hrepo.save(new Hero(3, "test2", "testPower2", 2, "https//test2_url.com"));
		hrepo.save(new Hero(4, "test2", "testPower2", 2, "https//test2_url.com"));
		List<Hero> heroList = new ArrayList<>();
		hrepo.findByName("test2").forEach(heroList::add);
		assertTrue(heroList.size() == 3);
	}

}
