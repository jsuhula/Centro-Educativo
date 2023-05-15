package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vistas.Inicio;

/**
 *
 * @author danie
 */
public class InicioController implements ActionListener {

    private Inicio vistaInicio;
    private EstablecimientoController vistaEstablecimiento;
    private EspecialidadController vistaEspecialidad;

    public InicioController() {
        this.vistaInicio = new Inicio();
        this.vistaInicio.establecimientoBtn.addActionListener(InicioController.this);
        this.vistaInicio.especialidadesBtn.addActionListener(InicioController.this);
        this.vistaInicio.profesoresBtn.addActionListener(InicioController.this);
    }

    public final void start() {
        this.vistaInicio.setTitle("COLEGIO CXI");
        this.vistaInicio.setLocationRelativeTo(null);
        this.vistaInicio.setResizable(false);
        this.vistaInicio.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vistaInicio.establecimientoBtn) {
            establecimiento();
        } else if (e.getSource() == this.vistaInicio.especialidadesBtn) {
            especialidad();
        }
    }

    public void establecimiento() {
        limpiarMemoria();
        this.vistaEstablecimiento = new EstablecimientoController();
        this.vistaEstablecimiento.iniciar(this.vistaInicio);

    }

    public void especialidad() {
        limpiarMemoria();
        this.vistaEspecialidad = new EspecialidadController();
        this.vistaEspecialidad.iniciar(this.vistaInicio);
    }

    public void limpiarMemoria() {
        this.vistaEspecialidad = null;
        this.vistaEstablecimiento = null;
        Runtime garbage = Runtime.getRuntime();
        garbage.gc();
    }

}
