


# PQuince
[![Build Status](https://dev.azure.com/QuinceIT/PQuince/_apis/build/status/PQuince%20CI%20pipeline?branchName=develop)](https://dev.azure.com/QuinceIT/PQuince/_build/latest?definitionId=1&branchName=develop)

# DevOps services

Latest Docker image: https://hub.docker.com/repository/docker/dusancina98/pquince

SonarCloud: https://sonarcloud.io/dashboard?id=QuinceIT_PQuince

Azure: https://dev.azure.com/QuinceIT/PQuince

Web location: https://pquince.herokuapp.com/

# Developer Teams

Team| Student
--- | ---
Dušan Petrović | Student 1
Nikola Kolović | Student 2 
Stefan Zindović | Student 3
Jelena Petković | Student 4

## Installation

Prvo je neophodno da podesite bazu, udjite u PgAdmin i otkucajte podatke kao sa slike:

![alt text](https://user-images.githubusercontent.com/57506510/107808990-bd034080-6d6a-11eb-9bae-42db0bc76614.png)

Otvorite programski alat Eclipse i pratite sledece korake sa slike:

![alt text](https://user-images.githubusercontent.com/57506510/107729948-87bb0c00-6cf2-11eb-9dea-8a4b64f9a41e.jpeg)

Zatim udjite u fajl WebConfig.java i odkomentarisite delove koda sa gornje slike nalik kodu za donje.

![alt text](https://user-images.githubusercontent.com/57506510/107809908-20da3900-6d6c-11eb-8014-933497b67180.jpeg)

Zatim pokrenite projekat kao sto je prikazano na slici ispod:

![alt text](https://user-images.githubusercontent.com/57506510/107808721-52ea9b80-6d6a-11eb-84bb-3fc4690f2c6a.png)

Otvorite programski alat Visual Studio Code i pratite sledece korake sa slike:

![alt text](https://user-images.githubusercontent.com/57506510/107730757-825ec100-6cf4-11eb-8fcf-d729c494d0a7.jpeg)

Zatim u terminalu otkucajte sledeci kod kako bi instalirati neophodne module za frontend:

```bash
npm install
```

Zatim pokrenite front end aplikaciju komandom:

```bash
npm start
```

Na localhost portu 3000 se moze pristupiti klijentskoj aplikaciji dok je za server neophodno osloboditi port 8081
