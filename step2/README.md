# Step 2: Mise en place de Tests sur une application Springboot

## 1 Contexte
Afin de mettre en place de la qualité logicielle, il est indispensable de pouvoir tester son application. Il existe plusieurs types de tests:
- Tests Systèmes : testent l'ensemble de l'application sur des scénarios donnés
- Tests d'Intégration: testent un sous-ensemble de fonctionnalité de l'application (e.g authentification d'un utilisateur)
- Tests unitaires: testent une fonction d'une classe

<img alt="img Test" src="https://blog.octo.com/wp-content/uploads/2018/06/pyramide_globale.png
" width="600">

Les tests unitaires testent de petites portions de fonctionnalités de façon indépendante. Ces tests nombreux permettent de vérifier que nos modifications n'ont pas altéré le comportement d'autres fonctionnalités dans l'application. Ils vont permettre de tester la non-régression de notre application.

Dans ce tuto., nous allons mettre en oeuvre des tests unitaires et d'intégration dans une application SpringBoot.

## 2 Configuration du projet
- Vérifier que vous avez bien réalisé la [step1](../step1/README.md), nous allons repartir sur la base du projet créé lors de cette étape.
- Dans le fichier ```pom.xml``` vérifier que les éléments suivant sont présents:

```xml
...
    <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
	</dependency>
...

```
- Cette dépendance va permettre de récupérer l'ensemble des outils pour les tests (e.g JUnit, Hamcrest and Mockito )

- Maven nous propose une structure permettant d'isoler les tests dans des packages séparés. En effet, lors de la production de notre archive finale, il sera plus facile de ne pas intégrer les tests à la version finales de notre application


<img alt="img Maven Test" src="./images/MavenProjectStructure.jpg
" width="300">

## 3 Création de tests unitaires simples
Dans cette section nous allons créer des tests unitaires sur ```Hero.java```. Nous allons tester respectivement la création d'un object avec des paramètres ainsi que l'affichage de cet objet.

### 3.1 Création de HeroTest
  - Créer le package ```com.sp.model``` dans ```src/test/java```
  - Créer le fichier ```HeroTest.java``` comme suit:

```java
package com.sp.model;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HeroTest {
	private List<String> stringList;
	private List<Integer> intList;

	@Before
	public void setUp() {
		System.out.println("[BEFORE TEST] -- Add Hero to test");
		stringList = new ArrayList<String>();
		intList = new ArrayList<Integer>();
		stringList.add("normalString1");
		stringList.add("normalString2");
		stringList.add(";:!;!::!;;<>");
		intList.add(5);
		intList.add(500);
		intList.add(-1);
	}

	@After
	public void tearDown() {
		System.out.println("[AFTER TEST] -- CLEAN hero list");
		stringList = null;
		intList = null;
	}

	@Test
	public void createHero() {
		for(String msg:stringList) {
			for(String msg2:stringList) {
				for(String msg3:stringList) {
					for(Integer msg4:intList) {
						Hero h=new Hero(msg4, msg, msg2, msg4, msg3);
						System.out.println("msg:"+msg+", msg2:"+msg2+", msg3:"+msg3+", msg4:"+msg4);
						assertTrue(h.getId().intValue() == msg4.intValue());
						assertTrue(h.getName() == msg);
						assertTrue(h.getSuperPowerName() == msg2);
						assertTrue(h.getSuperPowerValue() == msg4);
						assertTrue(h.getImgUrl() == msg3);
					}	
				}	
			}
		}
	}
	
	@Test
	public void displayHero() {
		Hero h=new Hero(1,"jdoe", "strong", 100, "https//url.com");
		String expectedResult="HERO [1]: name:jdoe, superPowerName:strong, superPowerValue:100 imgUrl:https//url.com";
		assertTrue(h.toString().equals(expectedResult));
		
	}
}
```
- Explications:
  ```java
    ...
    @Before
	public void setUp() {
        ...
  ```
  - L'annotation ```@Before``` permet d'exécuter une fonction ```AVANT``` chaque test
  ```java
    ...
	@After
	public void tearDown() {
        ...
  ```
  - L'annotation ```@After``` permet d'exécuter une fonction ```APRES``` chaque test
  ```java
    ...
	@Test
	public void createHero() {
        ...
    }
    @Test
	public void displayHero() {
        ...
    }
  ```
    - L'annotation ```@Test``` permet d'indiquer au Framework de tests (JUNIT) que la méthode est une méthode de test

### 3.2 Exécution de notre fonction de Test
- Exécuter votre test en utilisant le Framework Junit sous Eclipse directement
  - Clic droit sur le projet ```RUN AS```-> ``` JUNIT TEST```
  - Le résultat suivant doit apparaite:


<img alt="img Maven Test" src="./images/JunitExecution1.jpg
" width="400">

- Exécuter votre test en utilisant Maven
  - Clic droit sur le projet ```RUN AS```-> ``` MAVEN TEST```

   - Le résultat suivant doit apparaite à la console:

```
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------< com.tuto.springboot:SPWebAppStep2 >------------------
[INFO] Building SPWebApp2 0.0.1-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
...
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.sp.model.HeroTest
[BEFORE TEST] -- Add Hero to test
msg:normalString1, msg2:normalString1, msg3:normalString1, msg4:5
msg:normalString1, msg2:normalString1, msg3:normalString1, msg4:500
...
[AFTER TEST] -- CLEAN hero list
[BEFORE TEST] -- Add Hero to test
[AFTER TEST] -- CLEAN hero list
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.257 s - in com.sp.model.HeroTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.087 s
[INFO] Finished at: 2020-04-22T11:28:20+02:00
[INFO] ------------------------------------------------------------------------
```

## 4 Création de tests sur le Repository
Dans cette Section nous allons créer des tests pour ```HeroRepository.java```. Pour vérifier le fonctionnement du contrôleur nous aurons besoin de simuler une base de données embarquée

### 4.1 Création de HeroRepositoryTest
  - Créer le package ```com.sp.repository``` dans ```src/test/java```
  - Créer le fichier ```HeroRepositoryTest``` comme suit:

```java
    package com.sp.repository;

    import static org.junit.Assert.assertTrue;

    import java.util.ArrayList;
    import java.util.List;
    import org.junit.After;
    import org.junit.Before;
    import org.junit.Test;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.test.context.junit4.SpringRunner;
    import com.sp.model.Hero;

    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class HeroRepositoryTest {

        @Autowired
        HeroRepository hrepo;

        @Before
        public void setUp() {
            hrepo.save(new Hero(1, "jdoe", "strong", 100, "https//url.com"));
        }

        @After
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
```
- Explications:
  -    ``` @RunWith(SpringRunner.class)``` permet de lancer un contexte Springboot et de créer des objets spécifiquement pour le test. Ici nous permet d'accéder à ```HeroRepository hrepo;```
  -   ``` @DataJpaTest ``` permet de créer une base de données temporaire pour l'exécution du test
    ```java
        @Autowired
        HeroRepository hrepo;
    ```
  - Il est possible d'injecter un ```HeroRepository``` grâce à ``` @RunWith(SpringRunner.class)``` et au contexte Springboot créé pour le test.

### 4.2 Exécution de notre fonction de Test
- Exécuter votre test en utilisant le Framework Junit sous Eclipse directement
  - Clic droit sur le projet ```RUN AS```-> ``` JUNIT TEST```
  - Le résultat suivant doit apparaite:


<img alt="img Maven Test" src="./images/JunitExecution2.jpg
" width="400">

- Exécuter votre test en utilisant Maven
  - Clic droit sur le projet ```RUN AS```-> ``` MAVEN TEST```


## 5 Création de tests sur le RestController
Dans cette Section nous allons créer des tests pour ```HeroService.java```. Pour vérifier le fonctionnement du contrôleur nous aurons besoin de simuler le comportement d'autres controleurs (e.g ```HeroRepository```).

### 5.1 Création de HeroServiceTest
  - Créer le package ```com.sp.service``` dans ```src/test/java```
  - Créer le fichier ```HeroService``` comme suit:
  
```java
package com.sp.service;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sp.model.Hero;
import com.sp.repository.HeroRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HeroService.class)
public class HeroServiceTest {

	@Autowired
	private HeroService hService;

	@MockBean
	private HeroRepository hRepo;
	
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

```
  - Explications:
    - ```@RunWith(SpringRunner.class)``` : permet de lancer un contexte Springboot et des créer des objets spécifiquement pour le test. 
    - ```@WebMvcTest(value = HeroService.class)``` : indique à Springboot de limiter le contexte de l'application pour ce test à l'objet ```HeroService```. 
    ```java
    ...
    	@Autowired
	    private HeroService hService;
    ...
    ```
    - Injection du service ```HeroService```. Il s'agit du seul object, ```Bean```, accessible dans ce contexte de test (défini dans ```@WebMvcTest(value = HeroService.class)``` ).
    ```java
    ...
      @MockBean
	    private HeroRepository hRepo;
    ...
    ```
    - ``` @MockBean ``` permet de remplacer la ressource cible par une version "simulée" par Mockito mock. Nous pourrons ainsi définir le comportement attendu de cette ressource ciblée.

    ```java 
    ...
    Mockito.when(
				hRepo.findById(Mockito.any())
				).thenReturn(Optional.ofNullable(tmpHero));
    ...
    ```
    - Redéfini le comportement attendu par ```hRepo```. Dans notre cas, lors de l'appel de ```hRepo.findById``` avec comme argument n'importe quel objet (```Mockito.any()```), cette méthode va retourner toujours le même objet ```Optional.ofNullable(tmpHero)```.

    ```java
    ...
    assertTrue(heroInfo.toString().equals(tmpHero.toString()));
    ...
    ```
    - Test si le retour de ```hService.getHero``` correspond à la valeur attendue.

### 5.2 Exécution de notre fonction de Test
- Exécuter votre test en utilisant le Framework Junit sous Eclipse directement
  - Clic droit sur le projet ```RUN AS```-> ``` JUNIT TEST```
  - Le résultat suivant doit apparaite:


<img alt="img Maven Test" src="./images/JunitExecution4.jpg
" width="400">

- Exécuter votre test en utilisant Maven
  - Clic droit sur le projet ```RUN AS```-> ``` MAVEN TEST```



## 6 Création de tests sur le RestController
Dans cette Section nous allons créer des tests pour  ```HeroRestCrt.java```. Pour vérifier le fonctionnement du contrôleur nous aurons besoin de simuler l'envoi de requête HTTP.

### 6.1 Création de HeroRestCrt
  - Créer le package ```com.sp.rest``` dans ```src/test/java```
  - Créer le fichier ```HeroRestCrt``` comme suit:

```java
package com.sp.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sp.model.Hero;
import com.sp.service.HeroService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HeroRestCrt.class)
public class HeroRestCrtTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private HeroService hService;

	Hero mockHero=new Hero(1, "jdoe", "strong", 100, "https//url.com");
	
	@Test
	public void retrieveHero() throws Exception {
		Mockito.when(
				hService.getHero(Mockito.anyInt())
				).thenReturn(mockHero);
				

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hero/50").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getContentAsString());
		String expectedResult="{\"id\":1,\"name\":\"jdoe\",\"superPowerName\":\"strong\",\"superPowerValue\":100,\"imgUrl\":\"https//url.com\"}";


		JSONAssert.assertEquals(expectedResult, result.getResponse()
				.getContentAsString(), false);
	}

}
```
- Explications:
    - ```@RunWith(SpringRunner.class)``` : permet de lancer un contexte Springboot et de créer des objets spécifiquement pour le test. 
    - ```@WebMvcTest(value = HeroRestCrt.class)``` : indique à Springboot de limiter le contexte de l'application pour ce test à l'objet ```HeroRestCrt```. Nous allons par la suite simuler le comportement des autres controlleurs utilisés (e.g ```HeroService```). Le contexte de l'application à simuler se trouvera dans l'objet ```MockMvc mockMvc```  (https://reflectoring.io/spring-boot-web-controller-test/) 

  ```java
  ...
  	@Autowired
	private MockMvc mockMvc;
  ```
    - Récupère le contexte de l'application à tester qui a été défini par ```@WebMvcTest```

  ```java
  ...
  @MockBean
	private HeroService hService;
  ...
  ```
  - ``` @MockBean ``` permet de remplacer la ressource cible par une version "simulée" par Mockito mock. Nous pourrons ainsi définir le comportement attendu de cette ressource ciblée.

  ```java
  Mockito.when(
				hService.getHero(Mockito.anyInt())
				).thenReturn(mockHero);
  ```
   - Redéfinit le comportement attendu par hService. Dans notre cas lors de l'appel de ```hService.getHero``` avec comme argument n'importe quel entier (```Mockito.anyInt()```), cette méthode va retourner toujours le même objet ```mockHero```.

  ```java
  ...
      RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/hero/50").accept(MediaType.APPLICATION_JSON);
  ...
  ```
  - Prépare une requète Http de type ```get``` avec comme Url ```/hero/50``` et comme médiat type ```MediaType.APPLICATION_JSON``` à simuler.

  ```java
  ...
  MvcResult result = mockMvc.perform(requestBuilder).andReturn();
  ...
  ```
  - Exécute la requète dans notre contexte d'application simulée (ici uniquement l'objet ```HeroRestCrt```) et récupère le résultat à cette requête.

  ```java
  	JSONAssert.assertEquals(expectedResult, result.getResponse()
				.getContentAsString(), false);
  ```
  - Compare le contenu de la requête au résultat attendu (conversion automatique des string au format JSON)


### 6.2 Exécution de notre fonction de Test
- Exécuter votre test en utilisant le Framework Junit sous Eclipse directement
  - Clic droit sur le projet ```RUN AS```-> ``` JUNIT TEST```
  - Le résultat suivant doit apparaite:


<img alt="img Maven Test" src="./images/JunitExecution5.jpg
" width="400">

- Exécuter votre test en utilisant Maven
  - Clic droit sur le projet ```RUN AS```-> ``` MAVEN TEST```