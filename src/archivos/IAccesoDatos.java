package archivos;

import java.util.*;

public interface IAccesoDatos {

    public enum TypoRegistro {
        ALUMNOS,
        DOCENTES,
        CURSOS,
        ESPECIALIDADES,
        ESTABLECIMIENTOS
    }
    
    public abstract void crearDirectorio(String dirName);
    
    public abstract void crearArchivo(String fileName);
    
    public abstract boolean renombrarArchivo(String nombreArchivoActual, String renombre);

    public abstract boolean existe(String fileName);

    public abstract boolean borrar(String fileName);
    
    public abstract void guardarRegistro(String fileName, Object object, boolean anexar);

    public abstract Map obtenerRegistros(TypoRegistro list, String path);
}
