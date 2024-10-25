# Backend Challenge

Este desafio consiste em desenvolver um formulário que permite aos usuários enviar mensagens para a empresa. Além disso, uma cópia do email enviado será enviada também ao remetente, garantindo que ele tenha um registro da comunicação.

### Funcionalidades Principais:
- **Envio de Email:** O usuário pode preencher o formulário e enviar um email diretamente para a empresa.
- **Cópia ao Remetente:** O cliente receberá uma cópia do email enviado, proporcionando maior transparência e confirmação.
- **Segurança com RECAPTCHA:** O formulário inclui a verificação RECAPTCHA para garantir que as mensagens sejam enviadas por usuários reais, prevenindo spam e abusos.

## Tecnologias Utilizadas

- **Backend**: Java com Spring Boot
- **Frontend**: React com JavaScript, HTML e CSS
- **Outras Tecnologias**: Docker

## Passo a Passo para Rodar o Projeto

1. **Clonar o Repositório**

   Execute o seguinte comando para clonar o repositório e navegar até a pasta do projeto:

   ```bash
   git clone https://github.com/Vmp3/BackendChallenge
   cd BackendChallenge/challenge
   ```

2. **Rodar o Projeto**

   Utilize o Docker para construir e iniciar o projeto com o seguinte comando:

   ```bash
   docker-compose up --build
   ```
### Observação
O RECAPTCHA funciona somente em localhost, pois a chave do RECAPTCHA está configurada para operar apenas nesse domínio.

