package modelos;

public class Establecimiento {

    private int codigo;
    private String nombre;
    private String departamento;
    private String direccion;
    private String director;

    public Establecimiento(){

    }

    public Establecimiento(int codigo, String nombre, String departamento, String direccion, String director) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.departamento = departamento;
        this.direccion = direccion;
        this.director = director;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigoEstablecimiento) {
        this.codigo = codigoEstablecimiento;
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamentoUbicacion) {
        this.departamento = departamentoUbicacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccionEstablecimiento) {
        this.direccion = direccionEstablecimiento;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return this.codigo + ","+this.nombre+","+this.departamento +"," + this.direccion+","+this.director;
    }
}
