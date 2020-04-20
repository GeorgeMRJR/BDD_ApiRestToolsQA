# language: pt
@e2e
Funcionalidade: Teste End to End da API Livraria ToolsQA
  o objetivo deste teste é testar de ponta a ponta a API REST
  Swagger da API: http://bookstore.toolsqa.com/swagger/index.html

  Contexto: Gerar o token de autorização
    Dado Que sou um usuario autorizado

  @tag1
  Cenario: Usuario autorizado esta apito a adicionar e remover um livro
    Dado Que a lista de livros esta disponivel
    Quando Eu adiciono um livro na minha lista de leitura
    Entao O livro sera adicionado
    Quando Eu removo um livro na minha lista de leitura
    Entao O livro sera removido
