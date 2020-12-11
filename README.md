<h1>E ai dev!</h1>

<p>Então, basicamente eu sugiro que faça o clone do repositório e faça os testes localmente.</p>


<h2>Documentação</h2>
<p>Estou utilizando a lib do Sweagger para mostrar mais facilmente os endpoints, o que espera etc.</p>

<h2>DTO's (Data Transfer Object)</h2>
<p>Utilizei os mesmos para proteção contra algum posível injection nos endpoints, ou seja,
por segurança, também pode ser interessante para não transferir grandes objetos o tempo inteiro, etc. 
Lembrando que a serialização e deserialização de objetos grandes e complexos consome recursos computacionais que muitas vezes poderia ser evitado.</p>

<h2>HATEOAS</h2>

<p>
Eu utilizei esse modelo de resposta para uma breve introdução do mesmo, é uma menira 
muito interessante para facilitar em integrações e também
diminuir a quantidade de regra de negócio no client.
</p>

<p>
Qualquer dúvida, pode me chamar a qualquer momento, estarei disponível a sanar las
e discutir sobre possíveis implementações.
</p>
