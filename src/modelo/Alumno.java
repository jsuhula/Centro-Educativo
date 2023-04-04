package modelo;

public class Alumno {

    private int codigoAlumno;
    private String nombres;
    private String apellidos;
    private String correo;
    private String sexo;

    public Alumno() {
    }

    public Alumno(int codigoAlumno, String nombres, String apellidos, String correo, String sexo) {
        this.codigoAlumno = codigoAlumno;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.sexo = sexo;
        this.correo = correo;
    }

    public int getCodigoAlumno() {
        return codigoAlumno;
    }

    public void setCodigoAlumno(int codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
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

    public String getSexo() {
        return this.sexo;
    }

    public void setGenero(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return this.codigoAlumno + "," + this.nombres + "," + this.apellidos + "," + this.correo + "," + this.sexo;
    }
    
    
    
}
