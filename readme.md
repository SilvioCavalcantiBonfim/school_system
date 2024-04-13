
# School System - Projeto Final do Curso "Vai na Web"

Bem-vindo ao repositório do projeto final do curso de Desenvolvimento Backend Java "Vai na Web". Este projeto consiste em uma API REST chamada "School System" que gerencia dados de colaboradores e estudantes em uma instituição de ensino. O projeto inclui uma série de rotas para manipular dados relacionados a colaboradores e estudantes, bem como uma implementação opcional de validação de dados utilizando o algoritmo CRC32 para checksum.

## Rotas da API
### Colaboradores
- **GET /colaboradores**: Lista todos os colaboradores.
- **POST /colaboradores**: Cadastra um novo colaborador.
- **DELETE /colaboradores/:id**: Apaga um colaborador específico.
- **PUT /colaboradores/:id**: Atualiza os dados de um colaborador específico.
### Estudantes
- **GET /estudantes**: Lista todos os estudantes.
- **POST /estudantes**: Cadastra um novo estudante.
- **DELETE /estudantes/:id**: Apaga um estudante específico.
- **PUT /estudantes/:id**: Atualiza os dados de um estudante específico.

## Funcionalidades Adicionais

### Checksum com CRC32

A API implementa opcionalmente o recurso de `if-match` com o algoritmo `CRC32` para verificação de checksum, garantindo assim a integridade dos dados durante as operações de atualização.

## Testes de Integração

Foram realizados testes de integração para garantir que a API está funcionando corretamente e atende às expectativas do projeto. Os testes incluem cenários para verificar o funcionamento das rotas da API e sua interação com o banco de dados.

## Contribuições

Sinta-se à vontade para contribuir com este projeto enviando pull requests, relatando problemas ou sugerindo melhorias.