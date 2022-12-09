# Spring Data e JPA

#### Objetivo do repositório

Entender a parte de persistência de forma simples com
CRUD utilizando Spring DATA e JPA, banco de dados relacional PostgreSQL e teste unitários com Junit.

### Criando uma Entidade
~~~JAVA
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UsuarioSpringData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;


	private String login;

	private String senha;

	private String nome;

	private String email;

	private int idade;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

}
~~~
#### Testando a criação de Tabela no Banco

~~~ JAVA
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:META-INF/spring-config.xml"})
public class AppSpringDataTest {

    @Test
    public void testeCriacao() {
        System.out.println("iniciou Spring com Sucesso");
    }
}
~~~
![Criando o Banco](https://user-images.githubusercontent.com/101072311/198855857-f00146ec-93a4-4ea2-a594-34a92ee05057.png)
#### Criando o Repository
~~~JAVA
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import estudoSpringData.estudoSpringData.model.UsuarioSpringData;

@Repository
public interface InterfaceSpringDataUser extends CrudRepository<UsuarioSpringData, Long> {

}
~~~

#### Instanciando a dependência
 Fazendo a injeção de dependência da classe InterfaceSpringDataUser na classe de teste.

 ~~~JAVA
@Autowired
private InterfaceSpringDataUser interfaceSpringDataUser;
 ~~~
## CRUD

#### INSERT
Realizando cadastro no banco de dados.
~~~JAVA
@Test
	public void testeInsert() {

		UsuarioSpringData usuarioSpringData = new UsuarioSpringData();
		usuarioSpringData.setNome("José");
		usuarioSpringData.setEmail("example@gmail.com");
		usuarioSpringData.setIdade(18);
		usuarioSpringData.setLogin("@123");
		usuarioSpringData.setSenha("123");

		interfaceSpringDataUse.save(usuarioSpringData);
	}
~~~
![Insert na tabela](https://user-images.githubusercontent.com/101072311/198856153-37f97fce-fc3e-4ac6-be7d-509479675aea.png)
#### Consultando registro por Id

~~~JAVA
@Test
	public void testeConsulta() {
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(2L);

		System.out.println(usuarioSpringData.get().getId());
		System.out.println(usuarioSpringData.get().getEmail());
		System.out.println(usuarioSpringData.get().getNome());
		System.out.println(usuarioSpringData.get().getIdade());
		System.out.println(usuarioSpringData.get().getLogin());
		System.out.println(usuarioSpringData.get().getSenha());
	}
~~~

#### Consultando todos os registro no banco

~~~JAVA
@Test
	public void testeConsultaTodos() {
			Iterable<UsuarioSpringData> lista = interfaceSpringDataUse.findAll();

			for (UsuarioSpringData usuarioSpringData : lista) {
				System.out.println(usuarioSpringData.getId());
				System.out.println(usuarioSpringData.getEmail());
				System.out.println(usuarioSpringData.getNome());
				System.out.println(usuarioSpringData.getIdade());
				System.out.println(usuarioSpringData.getLogin());
				System.out.println(usuarioSpringData.getSenha());
				System.out.println("-----------------------------------------");
			}
	}
~~~

#### UPDATE de cadastro
Realizando Update de cadastro do Id (3).

![Tabela com todos os usuários](https://user-images.githubusercontent.com/101072311/198857545-475e744a-0a7f-4d68-b674-14b6119aad12.png)

~~~JAVA
@Test
	public void testeUpdate() {
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(3L);

		UsuarioSpringData data = usuarioSpringData.get();

		data.setEmail("example3@gmail.com");
		data.setNome("Blablu");

		interfaceSpringDataUse.save(data);

	}
~~~
![Tabela com Alteração](https://user-images.githubusercontent.com/101072311/198857761-affaa87c-61f1-4c96-ade9-e8aa2a727d84.png)

#### DELETE de registros
Deletando o Id(1) da tabela registro.

![Tabela com todos os usuários](https://user-images.githubusercontent.com/101072311/198857545-475e744a-0a7f-4d68-b674-14b6119aad12.png)

~~~JAVA
@Test
	public void testeDelete() {
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(1L);

		interfaceSpringDataUse.delete(usuarioSpringData.get());

	}
~~~
![Tabela com Usuário Deletado](https://user-images.githubusercontent.com/101072311/198857916-d1b41b0e-b68a-448a-b63f-620f699e50ee.png)

#### Consulta no banco
Realizando consultas customizadas com @Query.
~~~JAVA
@Repository
public interface InterfaceSpringDataUser extends CrudRepository<UsuarioSpringData, Long> {

	@Query(value = "select p from UsuarioSpringData p where p.nome like %?1%")
	public List<UsuarioSpringData> buscarPorNome(String nome);
}
~~~

~~~JAVA
@Test
	public void testeConsultaNome() {
		List<UsuarioSpringData> list = interfaceSpringDataUse.buscarPorNome("joão");

		for (UsuarioSpringData usuarioSpringData : list) {
			System.out.println(usuarioSpringData.getId());
			System.out.println(usuarioSpringData.getEmail());
			System.out.println(usuarioSpringData.getNome());
			System.out.println(usuarioSpringData.getIdade());
			System.out.println(usuarioSpringData.getLogin());
			System.out.println(usuarioSpringData.getSenha());
			System.out.println("-----------------------------------------");
		}

	}
~~~

#### DELETE por nome
Realizando Delete condicional.

![Tabela com Usuário Deletado](https://user-images.githubusercontent.com/101072311/198857916-d1b41b0e-b68a-448a-b63f-620f699e50ee.png)

~~~JAVA
@Modifying
	@Transactional
	@Query("delete from UsuarioSpringData u where u.nome = ?1")
	public void deletePorNome(String nome);
~~~

~~~JAVA
@Test
	public void testeDeletePorNome() {
		interfaceSpringDataUse.deletePorNome("joão");
	}
~~~
![delete por nome](https://user-images.githubusercontent.com/101072311/198883488-154cb129-03ad-4233-bd9d-9a1bb8fc1e12.png)

#### UPDATE condicional
![joão email](https://user-images.githubusercontent.com/101072311/198885005-74eea2f5-26e1-4530-810e-80822e1e5e18.png)
~~~JAVA
@Modifying
	@Transactional
	@Query("update UsuarioSpringData u set u.email = ?1 where u.nome =?2")
	public void updatePorEmail(String email, String nome);
~~~

~~~JAVA
@Test
	public void testeUpdatePorEmail() {
		interfaceSpringDataUse.updatePorEmail("example2UPDATE@gmail.com","joão");
	}
~~~
![joão Update](https://user-images.githubusercontent.com/101072311/198885019-8ca15efd-f8c7-4e24-8d10-e661b75e1520.png)

#### Criando e salvando objetos relacionados

Criando a tabela de telefone e aplicando o conceito de muitos para um e relacionando.

~~~JAVA
@Entity
public class Telefone {

	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private Long id;

	private String tipo;

	private String numero;

	@ManyToOne
	private UsuarioSpringData usuarioSpringData;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public UsuarioSpringData getUsuarioSpringData() {
		return usuarioSpringData;
	}

	public void setUsuarioSpringData(UsuarioSpringData usuarioSpringData) {
		this.usuarioSpringData = usuarioSpringData;
	}

}
~~~
![telefone](https://user-images.githubusercontent.com/101072311/198886498-ff811cfe-a0c4-41e4-ab2e-894cb25a60c2.png)

#### Criando o Repository telefone
~~~JAVA
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import estudoSpringData.estudoSpringData.model.UsuarioSpringData;

@Repository
public interface Telefone extends CrudRepository<Telefone, Long>{

}
~~~

#### Instanciando a dependência
 Fazendo a injeção de dependência da classe InterfaceTelefone na classe de teste.

~~~JAVA
@Autowired
	private InterfaceTelefone interfaceTelefone;
~~~

#### INSERT na tabela Telefone
Realizando cadastro no banco de dados.

![telefone vazio](https://user-images.githubusercontent.com/101072311/198890109-15576f7f-d381-4eb2-8dd1-c53c594370a4.png)

~~~JAVA
@Test
	public void testeInsertTelefone() {

		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(3L);

		Telefone telefone = new Telefone();
		telefone.setTipo("Celular");
		telefone.setNumero("1998454729");
		telefone.setUsuarioSpringData(usuarioSpringData.get());

		interfaceTelefone.save(telefone);

	}
~~~
![telefone setado](https://user-images.githubusercontent.com/101072311/198890114-cbb9d36a-d1f1-425d-8263-809d3f402481.png)

#### Relacionado a tabela de usuários com os telefones

Criando um relacionamento e usando conceito de muitos para um na classe UsuarioSpringData.

~~~JAVA
@OneToMany(mappedBy = "usuarioSpringData", orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Telefone> telefones;

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}
~~~

## Ferramentas e Tecnologias usadas nesse repositório 🌐
<div style="display: inline_block"><br>

<img align="center" alt="Augusto-Java" height="60" width="60" src=https://github.com/devicons/devicon/blob/master/icons/java/java-original.svg >
<img align="center" alt="Augusto-SpringBoot" height="60" width="60" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/spring/spring-original-wordmark.svg">
<img align="center" alt="Augusto-POSTGRESQL" height="60" width="60" src="https://raw.githubusercontent.com/devicons/devicon/1119b9f84c0290e0f0b38982099a2bd027a48bf1/icons/postgresql/postgresql-original-wordmark.svg">
<img align="center" alt="Augusto-Junit" height="60" width="60" src="https://github.com/junit-team/junit5/blob/86465f4f491219ad0c0cf9c64eddca7b0edeb86f/assets/img/junit5-logo.svg">

</div>    

## Teste o projeto 👁‍🗨

Download do projeto para testar em sua máquina: https://github.com/AugustoMello09/Spring-Date-e-JPA/archive/refs/heads/main.zip

## Entre em contato comigo através dos canais abaixo e desde já, agradeço a atenção. 🤝

<div>

 
  <a href="https://www.linkedin.com/in/jos%C3%A9-augusto-794a94234/" target="_blank"><img src="https://img.shields.io/badge/-LinkedIn-%230077B5?style=for-the-badge&logo=linkedin&logoColor=white" target="_blank"></a>
  <a href="mailto:joseaugusto.Mello01@gmail.com" target="_blank"><img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" target="_blank"></a>

  </div>

