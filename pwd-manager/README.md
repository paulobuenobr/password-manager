Documentação do projeto pwd-manager
----------------------------------------

Sobre o projeto e suas funcionalidades
--------------------------------------
Projeto Maven Web que realiza o gerenciamento e divulgação de senhas de atendimento, dos tipos normal e preferencial.

A página da gerência oferece as opções de chamar a próxima senha, que é exibida para os clientes em uma página de exibição pública. Através dessa página também é possível reiniciar todas as senhas. A prioridade de chamada é para senhas preferenciais.
Endereço: http://localhost:8080/pwd-manager/manager.xhtml

A página pública exibe a senha chamada pelo gerente..
Endereço: http://localhost:8080/pwd-manager/display.xhtml

As senhas chamadas são aquelas geradas por uma página exclusiva do cliente, que representa o terminal de solicitação de senha.
Endereço: http://localhost:8080/pwd-manager/customer.xhtml

Há também uma página que lista todas as senhas geradas, com status "Chamada" ou "Na fila".
Endereço: http://localhost:8080/pwd-manager/list.xhtml

O sistema também realiza uma recuperação automática da lista de senhas geradas, divulgadas e chamadas, caso haja interrupção dos serviços.

Sobre as tecnologias
---------------------
O projeto segue um modelo de arquétipo Maven para projetos Web, e permite o gerenciamento, solicitação e acompanhamento das senhas através da utilização de um Managed Bean JSF (PasswordMB), que concentra a interação com as páginas.

Há também um serviço de persistência das senhas, que é representada por um serviço (PasswordService) que se utiliza de serialização de arquivos para realizar essa persistência.

Na camada web é utilizado JSF 2 e Primefaces 5, com requisições Ajax na atualização das informações das páginas (Ajax Poll).

Sobre a geração e instalação do projeto
---------------------------------------
É pré-requisito ter o Maven instalado no computador que irá abrigar o servidor de aplicações (https://maven.apache.org/).

Também é preciso ter o servidor de aplicações Tomcat, versão 8.x.xx https://tomcat.apache.org/download-80.cgi).

Para gerar o arquivo. war da aplicação, basta executar o comando mvn clean install na raiz do projeto.

Para realizar o deploy da aplicação, basta copiar o arquivo pwd-manager.war para o diretório <TOMCAT_HOME>/webapps.

Cenário para testes
-------------------

1) Abra a página de geração de senhas (http://localhost:8080/pwd-manager/customer.xhtml), que simula um terminal com acesso por parte do cliente e solicite uma senha do tipo normal.

2) Solicite mais duas senhas do tipo normal e uma preferencial.

3) Acompanhe as senhas geradas e seus status na página http://localhost:8080/pwd-manager/list.xhtml.

4) Abra o painel de acompanhamento público através da página http://localhost:8080/pwd-manager/display.xhtml.

5) Utilizando a página do gerente (http://localhost:8080/pwd-manager/manager.xhtml), clique em Próxima senha. A senha chamada deve ser a P00001. Ao pedir uma próxima, N00001. Repetindo a operação, N00002 E N00003.

6) Reinicie o servidor. Solicite uma senha normal e outra preferencial. Os códigos gerados deverão ser N00004 e P00002.