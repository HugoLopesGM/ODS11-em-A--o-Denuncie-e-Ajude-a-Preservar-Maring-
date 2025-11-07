import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DenunciaDAO {

    public void criarTabela() {
        String sql = "CREATE TABLE IF NOT EXISTS denuncia (" +
                " id INT AUTO_INCREMENT PRIMARY KEY," +
                " descricao TEXT NOT NULL," +
                " localizacao VARCHAR(255)," +
                " tipo VARCHAR(20)," +
                " status VARCHAR(20) DEFAULT 'REGISTRADA'," +
                " data_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ") ENGINE=InnoDB;";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println("Tabela 'denuncia' verificada/criada com sucesso no MySQL.");

        } catch (SQLException e) {
            System.err.println("Erro ao criar tabela 'denuncia': " + e.getMessage());
        }
    }

    public void registrarDenuncia(Denuncia denuncia) {
        String sql = "INSERT INTO denuncia (descricao, localizacao, tipo) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, denuncia.getDescricao());
            pstmt.setString(2, denuncia.getLocalizacao());
            pstmt.setString(3, denuncia.getTipo());

            pstmt.executeUpdate();
            System.out.println("Denúncia registrada com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao registrar denúncia: " + e.getMessage());
        }
    }

    public List<Denuncia> listarTodas() {
        List<Denuncia> denuncias = new ArrayList<>();
        String sql = "SELECT * FROM denuncia ORDER BY data_registro DESC";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                denuncias.add(mapResultSetToDenuncia(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar denúncias: " + e.getMessage());
        }
        return denuncias;
    }

    public Denuncia buscarPorId(int id) {
        String sql = "SELECT * FROM denuncia WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToDenuncia(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar denúncia por ID: " + e.getMessage());
        }

        return null;
    }

    public void atualizarStatus(int id, String novoStatus) {
        String sql = "UPDATE denuncia SET status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, novoStatus);
            pstmt.setInt(2, id);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Status da denúncia ID " + id + " atualizado para " + novoStatus);
            } else {
                System.out.println("Denúncia com ID " + id + " não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status: " + e.getMessage());
        }
    }

    public void excluirDenuncia(int id) {
        String sql = "DELETE FROM denuncia WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int linhasAfetadas = pstmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Denúncia ID " + id + " excluída com sucesso.");
            } else {
                System.out.println("Denúncia com ID " + id + " não encontrada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao excluir denúncia: " + e.getMessage());
        }
    }

    private Denuncia mapResultSetToDenuncia(ResultSet rs) throws SQLException {
        return new Denuncia(
                rs.getInt("id"),
                rs.getString("descricao"),
                rs.getString("localizacao"),
                rs.getString("tipo"),
                rs.getString("status"),
                rs.getTimestamp("data_registro")
        );
    }
}