package br.com.livro.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.livro.domain.Carro;
import br.com.livro.domain.CarroDAO;

public class CarroService {

	private CarroDAO dao = new CarroDAO();

	public Carro buscarPorId(Long id) {
		if (id != null) {
			try {
				return dao.buscarPorId(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public List<Carro> buscarPorNome(String nome) {
		List<Carro> carros = new ArrayList<>();
		try {
			carros = dao.buscarPorNome(nome);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carros;
	}

	public List<Carro> buscarPorTipo(String tipo) {
		List<Carro> carros = new ArrayList<>();
		try {
			carros = dao.buscarPorTipo(tipo);
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return carros;
	}

	public List<Carro> listar() {
		List<Carro> carros = new ArrayList<>();
		try {
			carros = dao.listar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return carros;
	}

	public void salvar(Carro carro) {
		try {
			dao.salvar(carro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletar(Long id) {
		try {
			dao.excluir(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
