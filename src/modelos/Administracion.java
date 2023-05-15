package modelos;

import archivos.IAccesoDatos;
import archivos.AccesoDatosImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Administracion {

    public final String ROOT = "ADMINISTRACION/";
    public final String ESTABLECIMIENTOSFILENAME = "establecimientos.txt";
    public final String ESPECIALIDADESFILENAME = "especialidades.txt";
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
        this.establecimientos = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESTABLECIMIENTOS, ROOT + ESTABLECIMIENTOSFILENAME);
    }

    public void actualizarEspecialidades() {
        this.especialidades = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESPECIALIDADES, ROOT + ESPECIALIDADESFILENAME);
    }

    public String agregarEstablecimiento(Establecimiento establecimiento) {
        if (this.datos.existe(ROOT + ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            if (this.establecimientos.containsKey(establecimiento.getCodigo())) {
                return "Ya existe un establecimiento con este codigo";
            } else {
                this.datos.crearDirectorio(establecimiento.getCodigo() + "-" + establecimiento.getDepartamento());
                this.datos.guardarRegistro(ROOT + ESTABLECIMIENTOSFILENAME, establecimiento, true);
                return "1";
            }
        } else {
            this.datos.crearDirectorio(ROOT);
            this.datos.guardarRegistro(ROOT + ESTABLECIMIENTOSFILENAME, establecimiento, false);
            this.datos.crearDirectorio(establecimiento.getCodigo() + "-" + establecimiento.getDepartamento());
            actualizarEstablecimientos();
            return "1";
        }
    }

    public List<Establecimiento> obtenerEstablecimientos() {
        List<Establecimiento> establecimientosList = new ArrayList();
        Map establecimientosMap = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESTABLECIMIENTOS, ROOT + ESTABLECIMIENTOSFILENAME);

        establecimientosMap.forEach((key, iteratorObject) -> {
            Establecimiento establecimientoAux = (Establecimiento) iteratorObject;
            establecimientosList.add(establecimientoAux);
        });
        return establecimientosList;
    }

    public Establecimiento buscarEstablecimiento(int codigoEstablecimiento) {
        if (this.datos.existe(ROOT + ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            if (this.establecimientos.containsKey(codigoEstablecimiento)) {
                return (Establecimiento) this.establecimientos.get(codigoEstablecimiento);
            }
        }
        return null;
    }

    public boolean actualizarEstablecimiento(Establecimiento establecimiento) {
        String nombreNuevo = establecimiento.getCodigo() + "-" + establecimiento.getDepartamento();

        if (this.datos.existe(ROOT + ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            Map establecimientosAux = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESTABLECIMIENTOS, ROOT + ESTABLECIMIENTOSFILENAME);

            if (establecimientosAux.containsKey(establecimiento.getCodigo())) {
                Establecimiento stb = (Establecimiento) establecimientosAux.get(establecimiento.getCodigo());
                String nombreActual = stb.getCodigo() + "-" + stb.getDepartamento();

                this.datos.renombrarArchivo(nombreActual, nombreNuevo);
                establecimientosAux.replace(stb.getCodigo(), establecimiento);

                if (this.datos.borrar(ROOT + ESTABLECIMIENTOSFILENAME)) {
                    establecimientosAux.forEach((key, establecimientoAux) -> {
                        this.datos.guardarRegistro(ROOT + ESTABLECIMIENTOSFILENAME, establecimientoAux, this.datos.existe(ROOT + ESTABLECIMIENTOSFILENAME));
                    });
                    return this.datos.existe(ROOT + ESTABLECIMIENTOSFILENAME);
                }
            }
        }
        return false;
    }

    public boolean eliminarEstablecimiento(Establecimiento establecimiento) {
        if (this.datos.existe(ROOT + ESTABLECIMIENTOSFILENAME)) {
            actualizarEstablecimientos();
            Map establecimientosAux = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESTABLECIMIENTOS, ROOT + ESTABLECIMIENTOSFILENAME);

            if (establecimientosAux.containsKey(establecimiento.getCodigo())) {
                String path = establecimiento.getCodigo() + "-" + establecimiento.getDepartamento();

                this.datos.borrar(path);
                establecimientosAux.remove(establecimiento.getCodigo());

                if (this.datos.borrar(ROOT + ESTABLECIMIENTOSFILENAME)) {
                    establecimientosAux.forEach((key, establecimientoAux) -> {
                        this.datos.guardarRegistro(ROOT + ESTABLECIMIENTOSFILENAME, establecimientoAux, true);
                    });
                    return true;
                }
            }
        }
        return false;
    }

    public String agregarEspecialidad(Especialidad especialidad) {
        if (this.datos.existe(ROOT + ESPECIALIDADESFILENAME)) {
            actualizarEspecialidades();
            if (this.especialidades.containsKey(especialidad.getCodigoEspecialidad())) {
                return "Ya existe una especialidad con este codigo";
            } else {
                this.datos.guardarRegistro(ROOT + ESPECIALIDADESFILENAME, especialidad, true);
                return "1";
            }
        } else {
            this.datos.guardarRegistro(ROOT + ESPECIALIDADESFILENAME, especialidad, false);
            return this.datos.existe(ROOT + ESPECIALIDADESFILENAME) ? "1" : "0";
        } 
    }
    
    public List<Especialidad> obtenerEspecialidades() {
        List<Especialidad> especialidadesList = new ArrayList();
        Map establecimientosMap = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESPECIALIDADES, ROOT + ESPECIALIDADESFILENAME);

        establecimientosMap.forEach((key, iteratorObject) -> {
            Especialidad especialidadAux = (Especialidad) iteratorObject;
            especialidadesList.add(especialidadAux);
        });
        return especialidadesList;
    }

    public Especialidad buscarEspecialidad(int codigoEspecialidad) {
        if (this.datos.existe(ROOT + ESPECIALIDADESFILENAME)) {
            actualizarEspecialidades();
            if (this.especialidades.containsKey(codigoEspecialidad)) {
                return (Especialidad) this.especialidades.get(codigoEspecialidad);
            }
        }
        return null;
    }

    public boolean actualizarEspecialidad(Especialidad especialidad) {
        if (this.datos.existe(ROOT + ESPECIALIDADESFILENAME)) {
            actualizarEspecialidades();
            Map especialidadesAux = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESPECIALIDADES, ROOT + ESPECIALIDADESFILENAME);
            if (especialidadesAux.containsKey(especialidad.getCodigoEspecialidad())) {
                especialidadesAux.replace(especialidad.getCodigoEspecialidad(), especialidad);

                if (this.datos.borrar(ROOT + ESPECIALIDADESFILENAME)) {
                    especialidadesAux.forEach((key, establecimientoAux) -> {
                        this.datos.guardarRegistro(ROOT + ESPECIALIDADESFILENAME, establecimientoAux, this.datos.existe(ROOT + ESPECIALIDADESFILENAME));
                    });
                    return this.datos.existe(ROOT + ESPECIALIDADESFILENAME);
                }
            }
        }
        return false;
    }

    public boolean eliminarEspecialidad(Especialidad especialidad) {
        if (this.datos.existe(ROOT + ESPECIALIDADESFILENAME)) {
            actualizarEspecialidades();
            Map especialidadesAux = this.datos.obtenerRegistros(IAccesoDatos.TypoRegistro.ESPECIALIDADES, ROOT + ESPECIALIDADESFILENAME);

            if (especialidadesAux.containsKey(especialidad.getCodigoEspecialidad())) {
                especialidadesAux.remove(especialidad.getCodigoEspecialidad());
                this.datos.borrar(ROOT + ESPECIALIDADESFILENAME);
                especialidadesAux.forEach((key, especialidadAux) -> {
                    this.datos.guardarRegistro(ROOT + ESPECIALIDADESFILENAME, especialidadAux, this.datos.existe(ROOT + ESPECIALIDADESFILENAME));
                });

                return this.datos.existe(ROOT + ESPECIALIDADESFILENAME);
            }
        }

        return false;
    }

    public String agregarCurso(Curso curso) {

        return "0";
    }
}
