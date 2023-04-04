package modelo;

import java.io.File;
import java.util.Map;
import javax.swing.JOptionPane;
import operacion_archivos.*;

public class Administracion {

    public final String ESTABLECIMIENTOSFILENAME = "establecimiento.txt";
    public final String ESPECIALIDADESFILENAME = "especialidadestxt";
    public final String ALUMNOSFILENAME = "alumnos.txt";
    public final String DOCENTESFILENAME = "docentes.txt";
    public final String CURSOSFILENAME = "cursos.txt";
    public final String ASIGNACIONFILE = "cursosimpartidos.txt";

    private final IAccesoDatos datos;
    private Map establecimientos;
    private Map especialidades;

    public Administracion() {
        this.datos = new AccesoDatosImpl();
    }

    public void actualizarEstablecimientos() {
        this.establecimientos = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESTABLECIMIENTOS, ESTABLECIMIENTOSFILENAME);
    }

    public void actualizarEspecialidades() {
        this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESPECIALIDADES, ESPECIALIDADESFILENAME);
    }

    public String agregarEstablecimiento(Establecimiento establecimiento) {
        File path = new File(establecimiento.getCodigoEstablecimiento() + "-" + establecimiento.getDepartamentoUbicacion().toUpperCase());
        if (this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            if (this.establecimientos.containsKey(establecimiento.getCodigoEstablecimiento())) {
                return "Ya existe un establecimiento con este codigo";
            } else {
                path.mkdir();
                this.datos.guardarRegistro(ESTABLECIMIENTOSFILENAME, establecimiento.toString(), this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME));
                return "1";
            }
        } else {
            path.mkdir();
            this.datos.guardarRegistro(ESTABLECIMIENTOSFILENAME, establecimiento.toString(), this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME));
            return "1";
        }
    }

    public Establecimiento buscarEstablecimiento(int codigoEstablecimiento) {
        if (this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            if (this.establecimientos.containsKey(codigoEstablecimiento)) {
                return (Establecimiento) this.establecimientos.get(codigoEstablecimiento);
            }
        }
        return null;
    }

    public boolean actualizarEstablecimiento(Establecimiento establecimiento) {
        String nombreNuevo = establecimiento.getCodigoEstablecimiento() + "-" + establecimiento.getDepartamentoUbicacion();

        if (this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            Map establecimientosAux = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESTABLECIMIENTOS, ESTABLECIMIENTOSFILENAME);

            if (establecimientosAux.containsKey(establecimiento.getCodigoEstablecimiento())) {
                Establecimiento stb = (Establecimiento) establecimientosAux.get(establecimiento.getCodigoEstablecimiento());
                String nombreActual = stb.getCodigoEstablecimiento() + "-" + stb.getDepartamentoUbicacion();
                
                this.datos.renombrarArchivo(nombreActual, nombreNuevo);
                establecimientosAux.replace(stb.getCodigoEstablecimiento(), establecimiento);

                if (this.datos.borrarArchivo(ESTABLECIMIENTOSFILENAME)) {
                    establecimientosAux.forEach((key, establecimientoAux) -> {
                        this.datos.guardarRegistro(ESTABLECIMIENTOSFILENAME, establecimientoAux, this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME));
                    });
                    return this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME);
                }
            }
        }
        return false;
    }

    public boolean eliminarEstablecimiento(Establecimiento establecimiento) {
        if (this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            Map establecimientosAux = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESTABLECIMIENTOS, ESTABLECIMIENTOSFILENAME);

            if (establecimientosAux.containsKey(establecimiento.getCodigoEstablecimiento())) {
                String path = establecimiento.getCodigoEstablecimiento() + "-" + establecimiento.getDepartamentoUbicacion();
                this.datos.borrarArchivo(path);
                establecimientosAux.remove(establecimiento.getCodigoEstablecimiento());

                if (this.datos.borrarArchivo(ESTABLECIMIENTOSFILENAME)) {
                    establecimientosAux.forEach((key, establecimientoAux) -> {
                        this.datos.guardarRegistro(ESTABLECIMIENTOSFILENAME, establecimientoAux, this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME));
                    });
                    return this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME);
                }
            }
        }
        return false;
    }

    public void agregarEspecialidad(Establecimiento establecimiento, Especialidad especialidad) {
        if (this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME)) {
            if (this.establecimientos.containsKey(establecimiento.getCodigoEstablecimiento())) {
                Establecimiento sede = (Establecimiento) establecimientos.get(establecimiento.getCodigoEstablecimiento());
                File file = new File(establecimiento.getCodigoEstablecimiento() + "-" + sede.getDepartamentoUbicacion() + "/" + especialidad.getCodigoEspecialidad() + "-" + especialidad.getNombreEspecialidad());
                if (this.datos.existeArchivo(ESPECIALIDADESFILENAME)) {
                    if (file.exists()) {
                        JOptionPane.showMessageDialog(null, "Esta especialidad ya existe");
                    } else {
                        if (!this.especialidades.containsKey(especialidad.getCodigoEspecialidad())) {
                            this.datos.guardarRegistro(ESPECIALIDADESFILENAME, especialidad.toString(), this.datos.existeArchivo(ESPECIALIDADESFILENAME));
                            file.mkdir();
                        } else {
                            file.mkdir();
                        }
                    }
                } else {
                    this.datos.guardarRegistro(ESPECIALIDADESFILENAME, especialidad.toString(), this.datos.existeArchivo(ESPECIALIDADESFILENAME));
                    file.mkdir();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Empieze por registrar Establecimientos");
        }
    }

    public void inscribirAlumno(int codigoEstablecimiento, int codigoEspecialidad, Alumno alumno) {
        if (this.datos.existeArchivo(ESTABLECIMIENTOSFILENAME) || this.datos.existeArchivo(ESPECIALIDADESFILENAME)) {
            if (this.establecimientos.containsKey(codigoEstablecimiento)) {
                Establecimiento sede = (Establecimiento) establecimientos.get(codigoEstablecimiento);
                String path = sede.getCodigoEstablecimiento() + "-" + sede.getDepartamentoUbicacion() + "/";
                if (this.especialidades.containsKey(codigoEspecialidad)) {
                    Especialidad facultad = (Especialidad) this.especialidades.get(codigoEspecialidad);
                    path += facultad.getCodigoEspecialidad() + "-" + facultad.getNombreEspecialidad() + "/" + ALUMNOSFILENAME;

                    File file = new File(path);
                    if (file.exists()) {
                        Map alumnos = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ALUMNOS, path);
                        if (alumnos.containsKey(alumno.getCodigoAlumno())) {
                            JOptionPane.showMessageDialog(null, "El alumno con codigo: " + alumno.getCodigoAlumno() + " ya esta registrado");
                        } else {
                            this.datos.guardarRegistro(path, alumno.toString(), true);
                        }
                    } else {
                        this.datos.crearArchivo(path);
                        this.datos.guardarRegistro(path, alumno.toString(), false);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No se encuentra registrada la especialidad especificada");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encuentra registrado el establecimiento especificado");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Empieze por registrar Establecimientos y Especialidades");
        }
    }

    public void inscribirDocente(Establecimiento establecimiento, Especialidad especialidad, Docente docente) {
        String path = establecimiento.getCodigoEstablecimiento() + "-" + establecimiento.getDepartamentoUbicacion() + "/";
        path += especialidad.getCodigoEspecialidad() + "-" + especialidad.getNombreEspecialidad() + "/" + DOCENTESFILENAME;

        if (this.datos.existeArchivo(path)) {
            this.datos.guardarRegistro(path, docente, true);
        } else {
            this.datos.crearArchivo(path);
            this.datos.guardarRegistro(path, docente, false);
        }
    }

//    public void registrarCurso(Establecimiento establecimiento, Curso curso) {
//        if (this.establecimientos.containsKey(codigoEstablecimiento)) {
//            Establecimiento sede = (Establecimiento) establecimientos.get(codigoEstablecimiento);
//            String path = sede.getCodigoEstablecimiento() + "-" + sede.getDepartamentoUbicacion() + "/Cursos/";
//            File file = new File(path);
//            if (file.exists()) {
//                Map cursos = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.CURSOS, path + CURSOSFILENAME);
//
//                if (cursos.containsKey(curso.getCodigoCurso())) {
//                    JOptionPane.showMessageDialog(null, "El curso con codigo: " + curso.getCodigoCurso() + " ya esta registrado");
//                } else {
//                    this.datos.guardarRegistro(path + CURSOSFILENAME, curso.toString(), true);
//                }
//            } else {
//                file.mkdir();
//                this.datos.crearArchivo(path + CURSOSFILENAME);
//                this.datos.guardarRegistro(path + CURSOSFILENAME, curso.toString(), false);
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "No se encuentra registrado el establecimiento especificado");
//        }
//    }
//
//    
//        else {
//            JOptionPane.showMessageDialog(null, "Primero registre un establecimiento");
//    }
//
//    public void asignarCurso(Establecimiento establecimiento, Especialidad especialidad, Curso curso, Docente docente, String hora) {
//        String path = establecimiento.getCodigoEstablecimiento() + "-" + establecimiento.getDepartamentoUbicacion() + "/";
//        path += especialidad.getCodigoEspecialidad() + "-" + especialidad.getNombreEspecialidad() + "/" + ASIGNACIONFILE;
//        Horario horario = new Horario(curso.getCodigoCurso(), docente.getCodigoDocente(), hora);
//
//        if (this.datos.existeArchivo(path)) {
//            this.datos.guardarRegistro(path, horario, true);
//        } else {
//            this.datos.crearArchivo(path);
//            this.datos.guardarRegistro(path, horario, false);
//        }
//    }
}
