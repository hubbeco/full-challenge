# API Teste:

## Requisitos para o uso da API:

  * É necessário possuir a API reCAPTCHA configurada
     * Possuir a secret key
  * Java 21
  * Gmail com segurança de dois fatores ativada, para criar a senha de app no seu gmail

### Variaveis de ambiente

  * É necessário configurar as váriaveis de ambiente

```yaml
server:
  port: 80

cors:
  originsPatterns: http://localhost:8080

spring:
  application:
    name: ApiTest
  mail:
    host: smtp.gmail.com
    port: 587
    username: email
    password: password
    properties:
      mail:
        smtp:
          auth: true
          starttls.enable: true

recaptcha:
  key: secret key
  url: https://www.google.com/recaptcha/api/siteverify

text:
  mail:
    title: Contact form
    body: "contact from {name}, using mail: {email}, about: {comment}"
    html: "contact from {name}, using mail: {email}, about: {comment}"
```

### Documentação

  * OpenAPI: http://localhost/swagger-ui/index.html


### endpoint

  * http://localhost:80/form

### Envio correto:

 * request

```JSON
{
    "g-recaptcha-response": "my correct captcha",
    "comment": "my comment",
    "name": "my name",
    "mail": "my.name@example.com"
}
```

 * response: **201**

#### Erro de captcha:

 * request

```JSON
{
    "g-recaptcha-response": "my wrong captcha",
    "comment": "my comment",
    "name": "my name",
    "mail": "my.name@example.com"
}
```

 * response: **401**

```json
{
    "type": "about:blank",
    "title": "UnauthorizedError",
    "detail": "The captcha is incorrect!",
    "instance": "/api-endpoint",
}
```

#### Erro de usuário:

 * request

```JSON
{
    "g-recaptcha-response": "my correct captcha",
    "comment": "my comment",
    "name": "my name",
    "mail": "my.name@"
}
```

 * response: **400**

```json
{
    "type": "about:blank",
    "title": "BadRequestError",
    "detail": "The email is invalid",
    "instance": "/api-endpoint",
}
```

--- 

 * request

```JSON
{
    "g-recaptcha-response": "my correct captcha",
    "comment": "my comment",
    "name": "",
    "mail": "my.name@example.com"
}
```

 * response: **400**

```json
{
    "type": "about:blank",
    "title": "BadRequestError",
    "detail": "The name is empty",
    "instance": "/api-endpoint",
}
```

#### Erro do servidor:

 * response: **500**

```json
{
    "type": "about:blank",
    "title": "InternalServerError",
    "detail": "Some generic error name.",
    "instance": "/api-endpoint",
}
```
