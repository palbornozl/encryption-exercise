# encryption-exercise

- Java jdk-11.0.7.10 (AdoptOpenJDK)
- Apache Maven 3.6.3
- Docker Engine v19.03.13


### Base de Datos
El resultado es almacenado en una base de datos relacional embebida (MySQL), cuya tabla debe ser:

*Schema*: exercise
*Tabla*: tbl_user

|nombre campo|observaciones
|---|---|
|email     |varchar(100) not null primary key
|id        |bigint auto_increment unique
|name      |varchar(250)
|username  |varchar(25)  not null
|phone     |integer
|created_at|timestamp    not null default current_timestamp()
|updated_at|timestamp    not null default current_timestamp()

#### Acceso a BD (Run)
Vía BD Mysql Client (workbench)
- JDBC URL: `jdbc:mysql://localhost:3306/exercise`
- User Name: `developer`
- Password: `password`

### Ejecución

#### Docker
Ejecutar comando: 

`docker-compose -f docker-compose.yml up -d --build`

#### Gradle
```shell script
source .env
gradle clean build bootRun
```

#### Rest Client
- Importar archivo restClientEncryption.json a un cliente Rest (Insomnia / Postman)
- Ejecutar save
- Ejecutar getAll
- Ejecutar findByEmail
- Ejecutar search 

#### Contratos
##### IN
###### Endpoint POST /save
```url
http://localhost:8097/encryption/user/save
```
- Obligatorios: email, username (formato: aA-zZ 0-9 _)
- Opcional: name, phone (numero largo 8)
```json
{
  "name": "user test",
  "email": "user@test1.com",
  "username": "myUserName_1",
  "phone": 12345678
}
```

###### Endpoint GET /all
```url
http://localhost:8097/encryption/user/all
```

###### Endpoint POST /email
```url
http://localhost:8097/encryption/user/email
```

- Obligatorios: email
```json
{
  "email": "user@test1.com"
}
```
###### Endpoint GET /search
```url
http://localhost:8097/encryption/user/search?rut=1-9
```
- Obligatorios: rut=1-9 (numero + - + dv)

##### OUT
###### Endpoint /save
```text
user@test.com
```
###### Endpoint /all
```json
[
  {
    "name": "myUserName",
    "username": "user test",
    "email": "user@test.com",
    "phone": 12345678
  }
]
```
###### Endpoint /email
```json
{
  "name": "myUserName",
  "username": "user test",
  "email": "user@test.com",
  "phone": 12345678
}
```
###### Endpoint search
```json
{
  "responseCode": 0,
  "description": "OK",
  "elapsedTime": 1378,
  "result": {
    "registerCount": 3
  }
}
```

###### Error
```json
{
  "errors": [
    {
      "timestamp": "2020-10-04 22:45:05",
      "status": 500,
      "title": "Internal Server Error",
      "detail": "[Error] El correo ya está registrado",
      "source": "uri=/user/save"
    }
  ]
}
```