import java.sql.Timestamp;

public class Denuncia {

    private int id;
    private String descricao;
    private String localizacao;
    private String tipo;
    private String status;
    private Timestamp dataRegistro;

    public Denuncia(String descricao, String localizacao, String tipo) {
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.tipo = tipo;
    }

    public Denuncia(int id, String descricao, String localizacao, String tipo, String status, Timestamp dataRegistro) {
        this.id = id;
        this.descricao = descricao;
        this.localizacao = localizacao;
        this.tipo = tipo;
        this.status = status;
        this.dataRegistro = dataRegistro;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getLocalizacao() { return localizacao; }
    public void setLocalizacao(String localizacao) { this.localizacao = localizacao; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(Timestamp dataRegistro) { this.dataRegistro = dataRegistro; }

    @Override
    public String toString() {
        return String.format("[ID: %d] (%s) %s - %s em %s (%s)",
                id, status, tipo, descricao, localizacao, dataRegistro);
    }
}