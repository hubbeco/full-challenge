# Backend challenge :clipboard:

> Candidato a contratação deve fazer um fork do repositório e fazer sua implementação de micro serviço.

## Requisitos da API :paperclip:

Implemente uma microserviço para um formulário de contato que replique o email para o usuário e a empresa que esteja utilizando o serviço:

 * Deve possuir um `Procfile` (heroku) ou `Dockerfile` para executar em produção.
 * Re-escreva a `README.md` com instruções de uso para o projeto.
 * Ofereça suporte para [ReCaptcha v2](https://developers.google.com/recaptcha/docs/display) e/ou [HCaptcha](https://www.hcaptcha.com/).

### Variaveis de ambiente

```env
PORT = <80>
ORIGINS = <https://example.com>
RECAPTCHA_KEY = <secret key api recaptcha>
RECAPTCHA_URL = <https://www.google.com/recaptcha/api/siteverify>
MAIL_HOST = <mail.example.com>
MAIL_PORT = <586>
MAIL_SECURE = <false>
MAIL_AUTH_USER = <staff@example.com>
MAIL_AUTH_PASS = <12435678>
TEXT_MAIL_TITLE = <Contact form>
TEXT_MAIL_BODY = <contact from {name}, using mail: {email}, about: {comment}>
TEXT_MAIL_HTML = <contact from {name}, using mail: {email}, about: {comment}>
```

### Funcionamento

#### Envio correto:

 * request

```JSON
{
    "g-recaptcha-response": "my correct captcha",
    "comment": "my comment",
    "name": "my name",
    "mail": "my.name@example.com"
}
```

 * response:

O código do status deve ser **201** para envio correto. Não é necessário responder qualquer coisa em body.

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

 * response

O código do status deve ser **401**, retornar um json conforme a [RFC7807](https://datatracker.ietf.org/doc/html/rfc7807).
<!-- diferencial rfc9457, mais atualizada, porém mais extensa a explicação. -->

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

 * response

O código do status deve ser **400**, retornar um json conforme a [RFC7807](https://datatracker.ietf.org/doc/html/rfc7807).

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

 * response

O código do status deve ser **400**, retornar um json conforme a [RFC7807](https://datatracker.ietf.org/doc/html/rfc7807).

```json
{
    "type": "about:blank",
    "title": "BadRequestError",
    "detail": "The name is empty",
    "instance": "/api-endpoint",
}
```

#### Erro do servidor:

 * response

O código do status deve ser **500**, retornar um json conforme a [RFC7807](https://datatracker.ietf.org/doc/html/rfc7807).

```json
{
    "type": "about:blank",
    "title": "InternalServerError",
    "detail": "Some generic error name.",
    "instance": "/api-endpoint",
}
```

## Diferenciais :pushpin:

 * Implementar testes automatizados.
 * Implementar em GoLang ou Lua com framework como openresty.
 * Documentar com OpenAPI, PostmanAPI, Swagger ou similar.
 * Utilizar commits semânticos e seguir algum git-flow.
 * Vtilizar versionamento semantico.
 * _Outros critérios que são segredos!_ :shushing_face:

-----------------------------

:raising_hand_man: **Boa sorte!**
