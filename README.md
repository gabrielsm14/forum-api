# forum-api
Aplicação Java
Uma API REST baseada em um fórum de dúvidas, onde é possível cadastrar tópicos, listar, buscar, atualizar e deletar, com validação usando o Bean validation. A parte de acesso ao banco de dados é feita com JPA, porém utilizando um modelo do spring boot que é o spring data jpa. Usei o spring security para proteger a aplicação, fazendo a autenticação com web token de maneira stateless. Utilizei cache para melhorar a performance. Teste e profiles. Por fim fornece uma documentação da api usando o swagger e springfox.

