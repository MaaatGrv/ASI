**Author**: Jacques Saraydaryan, All rights reserved
# Step 3: CI/CD pour une application SpringBoot

## 1 Contexte
Afin d'automatiser la mise en oeuvre de test et la compilation de l'application, nous allons mettre en place sur chaîne d'intégration continue et de déploiement continue pour notre application Springboot.
Git lab propose un gamme d'outils permettant la mise en place de processus automatique pour l'intégration continue (e.g vérification de la syntaxe, exécution de test unitaires, création de livrable...).

Une complète présentation de la CI/CD de gitlab est disponible ici [https://docs.gitlab.com/ee/ci/](https://docs.gitlab.com/ee/ci/). 
![GitlabCi-CD image]( https://docs.gitlab.com/ee/ci/introduction/img/gitlab_workflow_example_11_9.png)
*Workflow Gitlab CI-CD*

Pour mettre en place l'intégration continue dans un repository git lab, il suffit d'ajouter un fichier ```.gitlab-ci.yml``` à la racine du répository.
Ce fichier va permettre de configurer tout le process automatique d'intégration continue.

### 5.2 Présentation de  ```.gitlab-ci.yml```

```.gitlab-ci.yml``` contient l'ensemble des instructions permettant la mise en oeuvre de pipeline d'intégration continue. Un ```pipeline``` est une suite d'opérations (```jobs```) qui va être réalisée automatiquement dès qu'un déclencheur est activé (e.g push sur la branch DEV, MASTER). Ces ```jobs``` peuvent être exécutés en parallèle et regroupés en ```stage``` (étape regroupant plusieurs ```jobs```). Une complète présentation des ```pipelines``` et des ```stages``` est disponible ici [https://docs.gitlab.com/ee/ci/pipelines.html](https://docs.gitlab.com/ee/ci/pipelines.html).   

- Créer un fichier ```.gitlab-ci.yml``` à la racine de votre projet comme suit:

```yaml
# image docker (container virtuel) pour executer les jobs (e.g effectuer le build de l'application)
image: "maven:3-jdk-8"

# commandes à executer sur le container virtuel (e.g ajout d'outils non dispo sur l'image de base)
before_script:
  - echo "I am a script executed before"

# definition de l'ordre d'execution des jobs 
# (e.g tous les jobs qui ont 'state:build' seront executés en premiers, puis tous les jobs de 'state:test' etc..)
stages:
  - build
  - test
  - package

# definition d'un job, à quelle étape il sera executé (stage), le script a executé (e.g mvn compile)
job_build:
  stage: build
  script:
    - mvn compile
    
# definition un nouveau job
job_test:
  stage: test
  script:
    - mvn test

# definition un nouveau job
job_package:
  stage: package
  script:
    - mvn package
  # definition d'un livrable issue de la compilation (et des autres opérations demandées), disponible au téléchargement
  artifacts:
    paths:
    - target/*.jar
    expire_in: 1 week

```
