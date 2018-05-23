package br.com.livro.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

public class CarroDAO extends BaseDAO {
	public Carro buscarPorId(Long id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where id = ?");
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Carro c = recuperarCarro(rs);
				rs.close();
				return c;
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public List<Carro> buscarPorNome(String name) throws SQLException {
		List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where lower(nome) like ?");
			stmt.setString(1, "%" + name.toLowerCase() + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Carro c = recuperarCarro(rs);
				carros.add(c);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return carros;
	}

	public List<Carro> buscarPorTipo(String tipo) throws SQLException {
		List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro where tipo = ?");
			stmt.setString(1, tipo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Carro carro = recuperarCarro(rs);
				carros.add(carro);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return carros;
	}

	public List<Carro> listar() throws SQLException {
		List<Carro> carros = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();
			stmt = conn.prepareStatement("select * from carro");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Carro carro = recuperarCarro(rs);
				carros.add(carro);
			}
			rs.close();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return carros;
	}

	private Carro recuperarCarro(ResultSet rs) throws SQLException {
		Carro carro = new Carro();

		carro.setId(rs.getLong("id"));
		carro.setNome(rs.getString("nome"));
		carro.setDescricao(rs.getString("descricao"));
		carro.setTipo(rs.getString("tipo"));
		carro.setUrlFoto(rs.getString("url_foto"));
		carro.setUrlVideo(rs.getString("url_video"));
		carro.setLatitude(rs.getString("latitude"));
		carro.setLongitude(rs.getString("longitude"));

		return carro;
	}

	public void salvar(Carro carro) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = getConnection();
			if (carro.getId() == null) {
				stmt = conn.prepareStatement(
						"insert into carro (nome, descricao, tipo, url_foto, url_video, latitude, longitude) values (?,?,?,?,?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
			} else {
				stmt = conn.prepareStatement(
						"update carro set nome = ?, descricao = ?, tipo = ?, url_foto = ?, url_video = ?, latitude = ?, longitude = ? where id = ?");
			}

			stmt.setString(1, carro.getNome());
			stmt.setString(2, carro.getDescricao());
			stmt.setString(3, carro.getTipo());
			stmt.setString(4, carro.getUrlFoto());
			stmt.setString(5, carro.getUrlVideo());
			stmt.setString(6, carro.getLatitude());
			stmt.setString(7, carro.getLongitude());

			if (carro.getId() != null) {
				stmt.setLong(8, carro.getId());
			}

			int count = stmt.executeUpdate();
			if (count == 0) {
				throw new SQLException("Erro ao inserir o carro");
			}

			if (carro.getId() == null) {
				Long id = recuperarIdGerado(stmt);
				carro.setId(id);
			}
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	private Long recuperarIdGerado(PreparedStatement stmt) throws SQLException {
		ResultSet rs = stmt.getGeneratedKeys();
		if (rs.next()) {
			Long id = rs.getLong(1);
			rs.close();
			return id;
		} else {
			return 0L;
		}
	}

	public boolean excluir(Long id) throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement("delete from carro where id = ?");
			stmt.setLong(1, id);
			int count = stmt.executeUpdate();
			boolean excluiu = count > 0;
			return excluiu;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}
}
