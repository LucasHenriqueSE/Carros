package br.com.livro.teste;

import java.sql.SQLException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroDAO;

public class CarroTest {
	
	private static CarroDAO dao;
	
	private Carro carro;
	
	@BeforeClass
	public static void iniciar() {
		dao = new CarroDAO();
	}
	
	@Before
	public void recuperarCarroTeste() {
		try {
			this.carro = dao.buscarPorNome("Ferrari Enzo 3000 - Teste").get(0);
		}catch(NullPointerException e){
			System.out.println("O carro de teste não existe.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void salvar() {
		try {
			Carro carro = new Carro();
			carro.setNome("Ferrari Enzo 3000 - Teste");
			carro.setDescricao("Ferrari Enzo - Modelo 3000 - Massa de Teste");
			carro.setTipo("Luxo");
			carro.setLatitude("-23.564224");
			carro.setLongitude("-46.653156");
			carro.setUrlFoto("http://www.livroandroid.com.br/livro/carros/luxo/Ferrari_Enzo.png");
			carro.setUrlVideo("http://www.livroandroid.com.br/livro/carros/luxo/ferrari_enzo.mp4");
			
			dao.salvar(carro);
			Assert.assertNotNull(carro.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void buscarPorId() {
		try {
			carro = dao.buscarPorId(carro.getId());
			Assert.assertNotNull(carro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void buscarPorNome() {
		try {
			List<Carro> carros = dao.buscarPorNome(carro.getNome());
			Assert.assertNotNull(carros);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void buscarPorTipo() {
		try {
			List<Carro> carros = dao.buscarPorTipo(carro.getTipo());
			Assert.assertNotNull(carros);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void listar() {
		try {
			List<Carro> carros = dao.listar();
			Assert.assertNotNull(carros);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void excluir() {
		try {
			boolean excluiu = dao.excluir(carro.getId());
			Assert.assertTrue(excluiu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
