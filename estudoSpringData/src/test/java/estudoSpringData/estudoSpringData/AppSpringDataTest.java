package estudoSpringData.estudoSpringData;


import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import estudoSpringData.estudoSpringData.dao.InterfaceSpringDataUser;
import estudoSpringData.estudoSpringData.dao.InterfaceTelefone;
import estudoSpringData.estudoSpringData.model.Telefone;
import estudoSpringData.estudoSpringData.model.UsuarioSpringData;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring-config.xml" })
public class AppSpringDataTest {

	@Autowired
	private InterfaceSpringDataUser interfaceSpringDataUse;

	@Autowired
	private InterfaceTelefone interfaceTelefone;
	
	@Test
	public void testeInsert() {

		UsuarioSpringData usuarioSpringData = new UsuarioSpringData();
		usuarioSpringData.setNome("Jota");
		usuarioSpringData.setEmail("example4@gmail.com");
		usuarioSpringData.setIdade(18);
		usuarioSpringData.setLogin("lol");
		usuarioSpringData.setSenha("123");

		interfaceSpringDataUse.save(usuarioSpringData);
	}

	@Test
	public void testeConsulta() {
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(3L);

		System.out.println(usuarioSpringData.get().getId());
		System.out.println(usuarioSpringData.get().getEmail());
		System.out.println(usuarioSpringData.get().getNome());
		System.out.println(usuarioSpringData.get().getIdade());
		System.out.println(usuarioSpringData.get().getLogin());
		System.out.println(usuarioSpringData.get().getSenha());
		
		for(Telefone telefone :usuarioSpringData.get().getTelefones()) {
			System.out.println(telefone.getTipo());
			System.out.println(telefone.getNumero());
			System.out.println(telefone.getId());
			System.out.println(telefone.getUsuarioSpringData().getNome());
			System.out.println("---------------------------------------");
		}
	}

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

	@Test
	public void testeUpdate() {
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(3L);

		UsuarioSpringData data = usuarioSpringData.get();

		data.setEmail("example3@gmail.com");
		data.setNome("Blablu");

		interfaceSpringDataUse.save(data);

	}

	@Test
	public void testeDelete() {
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(1L);

		interfaceSpringDataUse.delete(usuarioSpringData.get());

	}

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

	@Test
	public void testeConsultaNomeParam() {
		UsuarioSpringData usuarioSpringData = interfaceSpringDataUse.buscarPorNomeParam("Blablu");
		
		
			System.out.println(usuarioSpringData.getId());
			System.out.println(usuarioSpringData.getEmail());
			System.out.println(usuarioSpringData.getNome());
			System.out.println(usuarioSpringData.getIdade());
			System.out.println(usuarioSpringData.getLogin());
			System.out.println(usuarioSpringData.getSenha());
			System.out.println("-----------------------------------------");
		}
	
	@Test
	public void testeDeletePorNome() {
		interfaceSpringDataUse.deletePorNome("joão");
	}

	@Test
	public void testeUpdatePorEmail() {
		interfaceSpringDataUse.updatePorEmail("example2UPDATE@gmail.com","joão");
	}

	@Test
	public void testeInsertTelefone() {
		
		Optional<UsuarioSpringData> usuarioSpringData = interfaceSpringDataUse.findById(3L);
		
		Telefone telefone = new Telefone();
		telefone.setTipo("Casa");
		telefone.setNumero("998661131");
		telefone.setUsuarioSpringData(usuarioSpringData.get());
		
		interfaceTelefone.save(telefone);
		
	}
}
