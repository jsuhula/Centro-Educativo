package modelo;

public class Establecimiento {

    private int codigoEstablecimiento;
    private String departamentoUbicacion;
    private String direccionEstablecimiento;
    private String director;
    
    public Establecimiento(){
        
    }

    public Establecimiento(int codigoEstablecimiento, String departamentoUbicacion, String direccionEstablecimiento, String director) {
        this.codigoEstablecimiento = codigoEstablecimiento;
        this.departamentoUbicacion = departamentoUbicacion;
        this.direccionEstablecimiento = direccionEstablecimiento;
        this.director = director;
    }

    public int getCodigoEstablecimiento() {
        return codigoEstablecimiento;
    }

    public void setCodigoEstablecimiento(int codigoEstablecimiento) {
        this.codigoEstablecimiento = codigoEstablecimiento;
    }

    public String getDepartamentoUbicacion() {
        return departamentoUbicacion;
    }

    public void setDepartamentoUbicacion(String departamentoUbicacion) {
        this.departamentoUbicacion = departamentoUbicacion;
    }

    public String getDireccionEstablecimiento() {
        return direccionEstablecimiento;
    }

    public void setDireccionEstablecimiento(String direccionEstablecimiento) {
        this.direccionEstablecimiento = direccionEstablecimiento;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    @Override
    public String toString() {
        return this.codigoEstablecimiento + ","+this.departamentoUbicacion +"," + this.direccionEstablecimiento+","+this.director;
    }
}
