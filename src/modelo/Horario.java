package modelo;

public class Horario {
    private int codigoCurso;
    private int codigoDocente;
    private String hora;

    public Horario(int codigoCurso, int codigoDocente, String hora) {
        this.codigoCurso = codigoCurso;
        this.codigoDocente = codigoDocente;
        this.hora = hora;
    }

    public int getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public int getCodigoDocente() {
        return codigoDocente;
    }

    public void setCodigoDocente(int codigoDocente) {
        this.codigoDocente = codigoDocente;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    @Override
    public String toString(){
        return this.codigoCurso+", "+this.codigoDocente+", "+this.hora;
    }
}
