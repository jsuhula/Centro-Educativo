package operacion_archivos;

import modelo.Establecimiento;
import modelo.Docente;
import modelo.Alumno;
import modelo.Curso;
import modelo.Especialidad;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class AccesoDatosImpl implements IAccesoDatos {

    @Override
    public void crearArchivo(String fileName) {
        File file = new File(fileName.toUpperCase());
        if (!file.exists()) {
            try {
                PrintWriter open = new PrintWriter(file);
                open.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "No se pudo crear el archivo: " + fileName + ", ERROR: " + ex.getMessage());
            }
        }
    }

    @Override
    public boolean borrarArchivo(String fileName) {
        File file = new File(fileName.toUpperCase());
        return file.delete();
    }

    @Override
    public boolean renombrarArchivo(String nombreArchivoActual, String renombre) {
        File file = new File(nombreArchivoActual);
        File fileAux = new File(renombre.toUpperCase());
        return file.renameTo(fileAux);
    }

    @Override
    public boolean existeArchivo(String fileName) {
        File file = new File(fileName.toUpperCase());
        return file.exists();
    }

    @Override
    public void guardarRegistro(String fileName, Object object, boolean anexar) {
        File file = new File(fileName);
        try {
            PrintWriter openFile = new PrintWriter(new FileWriter(fileName, anexar));
            openFile.println(object.toString().toUpperCase());
            openFile.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showConfirmDialog(null, "No se ha podido encontrar en el archivo, error: " + ex.getMessage());
        } catch (IOException ex) {
            JOptionPane.showConfirmDialog(null, "Ocurrio un error: " + ex.getMessage());
        }
    }

    @Override
    public Map obtenerRegistros(TypoRegistro list, String path) {
        List<String> dataFromFile;
        Map registros = new HashMap();
        switch (list) {
            case ALUMNOS:
                Alumno alumno;
                dataFromFile = leerDatos(path);
                for (int i = 0; i < dataFromFile.size(); i++) {
                    String[] line = dataFromFile.get(i).split(",");
                    alumno = new Alumno(Integer.parseInt(line[0]), line[1], line[2], line[3], line[4]);
                    registros.put(alumno.getCodigoAlumno(), alumno);
                }
                break;
            case DOCENTES:
                Docente docente;
                dataFromFile = leerDatos(path);
                for (int i = 0; i < dataFromFile.size(); i++) {
                    String[] line = dataFromFile.get(i).split(",");
                    docente = new Docente(Integer.parseInt(line[0]), line[1], line[2], line[3], line[4]);
                    registros.put(docente.getCodigoDocente(), docente);
                }
                break;
            case ESPECIALIDADES:
                Especialidad especialidad;
                dataFromFile = leerDatos(path);
                for (int i = 0; i < dataFromFile.size(); i++) {
                    String[] line = dataFromFile.get(i).split(",");
                    especialidad = new Especialidad(Integer.parseInt(line[0]), line[1]);
                    registros.put(especialidad.getCodigoEspecialidad(), especialidad);
                }
                break;
            case ESTABLECIMIENTOS:
                Establecimiento establecimiento;
                dataFromFile = leerDatos(path);
                for (int i = 0; i < dataFromFile.size(); i++) {
                    String[] line = dataFromFile.get(i).split(",");
                    establecimiento = new Establecimiento(Integer.parseInt(line[0]), line[1], line[2], line[3]);
                    registros.put(establecimiento.getCodigoEstablecimiento(), establecimiento);
                }
                break;
            case CURSOS:
                Curso curso;
                dataFromFile = leerDatos(path);
                for (int i = 0; i < dataFromFile.size(); i++) {
                    String[] line = dataFromFile.get(i).split(",");
                    curso = new Curso(Integer.parseInt(line[0]), line[1]);
                    registros.put(curso.getCodigoCurso(), curso);
                }
                break;
        }

        return registros;
    }

    private List<String> leerDatos(String fileName) {
        List<String> data = new ArrayList();
        File file = new File(fileName);

        try {
            String line;
            BufferedReader open = new BufferedReader(new FileReader(file));
            line = open.readLine();
            while (line != null) {
                data.add(line);
                line = open.readLine();
            }
            open.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no encontrado, error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("No se a podido leer el archivo, error: " + ex.getMessage());
        }
        return data;
    }
}
