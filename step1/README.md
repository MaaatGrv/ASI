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

## 2 Création d'un Contexte REST

### 2.1 Création d'un RestController Simple

#### 2.1.1 Création du RestController 
