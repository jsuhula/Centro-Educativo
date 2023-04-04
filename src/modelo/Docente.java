package modelo;

public class Docente {
    
    private int codigoDocente;
    private String nombres;
    private String apellidos;
    private String email;
    private String sexo;

    public Docente() {
    }

    public Docente(int codigoDocente, String nombres, String apellidos, String email, String sexo) {
        this.codigoDocente = codigoDocente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.sexo = sexo;
    }

    public int getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(int codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    } 

    @Override
    public String toString() {
        return this.codigoDocente+"," +this.nombres+","+this.apellidos+","+this.email+","+this.sexo;
    }
}
