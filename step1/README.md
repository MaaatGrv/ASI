**Author**: Jacques Saraydaryan, All rights reserved
# Step 1: Création d'une application Springboot: Web Dynamique

## 1 Création de l'application
- Suivre les étapes de la [Step0](../step0/README.md) et créer un projet SpringBoot avec les propriétés suivantes:
  - ```GroupeId```: com.tuto.springboot
  - ```ArtefactId```: SPWebAppStep1
  - ```Packaging```: jar

- Ajouter la dépendance suivante :
```
<dependencies>
...
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
  </dependencies>
```
- Cette dépendance va permettre d'utiliser le moteur de templating (template de page html complété coté server) `Thymeleaf` par Springboot
- `Thymeleaf` est un moteur de templating java orienté serveur. L'idée principale est d'insérer dans les balises HTML des éléments propres au moteur de template afin que le DOM puisse être modifié dynamiquement.
- Plus de détails sont disponibles sur le site officiel de `Thymeleaf` [https://www.thymeleaf.org](https://www.thymeleaf.org)
- Dans le répertoire `src/main/resources` créer les répertoires suivants:
  - `templates`: contiendra les templates HTML compléter par le server Web
  - `static`: contiendra les fichiers statiques envoyés directement au web browser (e.g, css, js, img, html static...)

## 2 Création d'un premier template Thymeleaf
### 2.1 Création d'un controller Http
- Dans le répertoire `src/main/java`, créer le package `com.sp.controller`
- Dans le package `com.sp.controller`, créer le fichier `RequestCrt.java` comme suit:

```java
package com.sp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller 
public class RequestCrt {
	
	private static String messageLocal="Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
	
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("messageLocal", messageLocal);
		return "index";
	}

}
```
- Explications:

  ```java
  ...
  @Controller 
  public class RequestCrt {
  ...
  ```
  - `@Controller` : annotation indiquant à Springboot que la classe courante sera un controller http (pourra intercepter les requètes http) et que SpringBoot gérera son cycle de vie. Cette classe sera détectée automatiquement par le scanning auto du classpath.
  
  ```java
  ...
	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("messageLocal", messageLocal);
		return "index";
	}
  ...
  ```
  - `@RequestMapping` : annotation indiquant de la fonction courante sera déclenchée lors d'un appel HTTP spécifique. Ici la fonction `index(...)` sera déclenchée d'une requète HTTP: GET URL: "/" ou URL: "/index"
    - `value = { "/", "/index" }`: URL attendue pour déclencher la fonction
    - `method = RequestMethod.GET` : Méthode HTTP attendue pour déclencher la fonction
  - `public String index(Model model) {...}` : Fonction déclenchée lors de l'appel HTTP spécifique. `Model model` contient tous les attributs associés à la requête HTTP, servira également à en ajouter de nouveau si nécessaire (e.g `model.addAttribute("messageLocal", messageLocal);` )
  - `return "index";` si aucune autre indication est apportée (e.g une autre annotation précisant la nature du retour), Sprinboot cherchera a retourner un fichier `index.html` contenu dans le répertoire `templates`

### 2.2 Création du template index.html
- Dans le répertoire `src/main/resources/templates` ajouter le fichier `index.html` comme suite:
  <details open>
    <summary>Simple <b>index.html</b></summary>

    ```html
    <!DOCTYPE HTML>
      <html xmlns:th="http://www.thymeleaf.org">
      <head>
      <meta charset="UTF-8" />
      <title>Welcome</title>
      <link rel="stylesheet" type="text/css" th:href="@{/static/style.css}" />

      </head>
      <body>
	        <h1>Welcome</h1>
	        <h2 th:utext="${message}">..!..</h2>
      </body>
    </html>
    ```
  </details>

  <details >
    <summary>Beautifull <b>index.html</b></summary>

      ```html
        <!DOCTYPE HTML>
        <html xmlns:th="http://www.thymeleaf.org">
        <head>
            <meta charset="UTF-8" />
            <title>Welcome</title>
            <link rel="stylesheet" type="text/css" th:href="@{/css/style.css}" />
            <!-- UIkit CSS -->
            <link rel="stylesheet"
            	href="https://cdn.jsdelivr.net/npm/uikit@3.8.1/dist/css/uikit.min.css" />

            <!-- UIkit JS -->
            <script
            	src="https://cdn.jsdelivr.net/npm/uikit@3.8.1/dist/js/uikit.min.js"></script>
            <script
            	src="https://cdn.jsdelivr.net/npm/uikit@3.8.1/dist/js/uikit-icons.min.js"></script>
        </head>
        <body>
          <nav class="uk-navbar-container" uk-navbar >
		        <div class="uk-navbar-left">
		        	<ul class="uk-navbar-nav">
		        		<li class="uk-active"><a href="/uk"><span
		        				uk-icon="icon: home; ratio: 1"></span> Hello</a></li>
		        		<li><a href="#">Actions</a>
		        			<div class="uk-navbar-dropdown">
		        				<ul class="uk-nav uk-navbar-dropdown-nav">
		        					<li><a href="/uk/view">Display</a></li>
		        					<li><a href="/uk/addPoney">Add</a></li>
		        					<li><a href="/uk/list">List</a></li>
		        				</ul>
		        			</div></li>
		        	</ul>
		        </div>
	        </nav>

        	<div class="uk-margin uk-padding">         
        		<h1 class="uk-heading-large">Welcome</h1>
        		<h2 th:utext="${message}">..!..</h2>
        		<article class="uk-comment">
        			<header class="uk-comment-header">
        				<div class="uk-grid-medium uk-flex-middle" uk-grid>
        					<div class="uk-width-auto">
        						<img class="uk-comment-avatar" src="/img/lego-builder.png"
        							width="80" height="80" alt="">
        					</div>
        					<div class="uk-width-expand">
        						<h4 class="uk-comment-title uk-margin-remove">
        						
        						</h4>
        						<ul
        							class="uk-comment-meta uk-subnav uk-subnav-divider uk-margin-remove-top">
        							<li><a href="#">First Introduction</a></li>
        							<li><a href="#">More Info</a></li>
        						</ul>
        					</div>
        				</div>
        			</header>
        			<div class="uk-comment-body">
        				<p th:utext="${messageLocal}">...</p>
        			</div>
        		</article>

        	</div>
        </body>
        </html>
      ```

  </details>

### 2.1 Création d'un RestController Simple

#### 2.1.1 Création du RestController 
