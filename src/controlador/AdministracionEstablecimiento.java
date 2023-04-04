package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import modelo.Administracion;
import modelo.Establecimiento;
import operacion_archivos.AccesoDatosImpl;
import operacion_archivos.IAccesoDatos;
import vistas.ControlEstablecimientos;
import vistas.RoundedBorder;

public class AdministracionEstablecimiento implements ActionListener {

    protected final IAccesoDatos datos;
    private final ControlEstablecimientos vistaEstablecimientos;
    private final Administracion admin;

    public AdministracionEstablecimiento() {
        this.datos = new AccesoDatosImpl();
        this.vistaEstablecimientos = new ControlEstablecimientos();
        this.admin = new Administracion();
        this.vistaEstablecimientos.guardarBtn.addActionListener(AdministracionEstablecimiento.this);
        this.vistaEstablecimientos.consultarBtn.addActionListener(AdministracionEstablecimiento.this);
        this.vistaEstablecimientos.actualizarBtn.addActionListener(AdministracionEstablecimiento.this);
        this.vistaEstablecimientos.eliminarBtn.addActionListener(AdministracionEstablecimiento.this);
        start();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vistaEstablecimientos.guardarBtn) {
            guardarEstablecimiento();
        } else if (e.getSource() == this.vistaEstablecimientos.consultarBtn) {
            consultarEstablecimiento();
        } else if (e.getSource() == this.vistaEstablecimientos.actualizarBtn) {
            actualizarEstablecimiento();
        } else if (e.getSource() == this.vistaEstablecimientos.eliminarBtn) {
            eliminarEstablecimiento();
        }
    }

    public final void start() {
        this.vistaEstablecimientos.setTitle("Administracion");
        this.vistaEstablecimientos.setLocationRelativeTo(null);
        this.vistaEstablecimientos.setResizable(false);
        this.vistaEstablecimientos.setVisible(true);
    }

    public void guardarEstablecimiento() {
        if (!isEmpty()) {
            Establecimiento establecimiento = new Establecimiento(Integer.parseInt(this.vistaEstablecimientos.codigoTf.getText()),
                    this.vistaEstablecimientos.ubicacionTf.getText(),
                    this.vistaEstablecimientos.direccionTf.getText(),
                    this.vistaEstablecimientos.directorTf.getText());
            String ejecutar = this.admin.agregarEstablecimiento(establecimiento);
            if (ejecutar.equalsIgnoreCase("1")) {
                JOptionPane.showMessageDialog(vistaEstablecimientos, "Se agrego correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaEstablecimientos, ejecutar);
            }
        } else {
            JOptionPane.showMessageDialog(vistaEstablecimientos, "Todos los campos son obligatorios");
        }
    }

    public void consultarEstablecimiento() {
        if (this.vistaEstablecimientos.codigoTf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaEstablecimientos, "Debe de ingresar el codigo del establecimiento");
        } else {
            Establecimiento establecimiento = this.admin.buscarEstablecimiento(Integer.parseInt(this.vistaEstablecimientos.codigoTf.getText()));
            if (establecimiento == null) {
                JOptionPane.showMessageDialog(vistaEstablecimientos, "No hay establecimientos encontrados");
            } else {
                this.vistaEstablecimientos.ubicacionTf.setText(establecimiento.getDepartamentoUbicacion());
                this.vistaEstablecimientos.direccionTf.setText(establecimiento.getDireccionEstablecimiento());
                this.vistaEstablecimientos.directorTf.setText(establecimiento.getDirector());
            }
        }
    }

    public void actualizarEstablecimiento() {
        if (!isEmpty()) {
            String mensaje = "Esta seguro(a) que quiere realizar los cambios en el establecimiento";
            int option = JOptionPane.showConfirmDialog(vistaEstablecimientos, mensaje, "GUARDAR CAMBIOS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                Establecimiento establecimiento = new Establecimiento(Integer.parseInt(this.vistaEstablecimientos.codigoTf.getText()),
                        this.vistaEstablecimientos.ubicacionTf.getText(),
                        this.vistaEstablecimientos.direccionTf.getText(),
                        this.vistaEstablecimientos.directorTf.getText());

                if (this.admin.actualizarEstablecimiento(establecimiento)) {
                    JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Se realizaron los cambios correctamente");
                } else {
                    JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Ocurrio un error al actualizar");
                }
            }

        } else {
            JOptionPane.showMessageDialog(this.vistaEstablecimientos, "No hay datos, intente buscando primero un registro");
        }
    }

    public void eliminarEstablecimiento() {

        if (!isEmpty()) {
            String codigo;
            String mensaje = "La accion es permanente, por seguridad digite el codigo del establecimiento: ";
            codigo = JOptionPane.showInputDialog(this.vistaEstablecimientos, mensaje, "ELIMINAR ESTABLECIMIENTO", JOptionPane.WARNING_MESSAGE);
            codigo = codigo == null ? "" : codigo; 
            
            if (!codigo.isEmpty()) {
                if (codigo.equalsIgnoreCase(this.vistaEstablecimientos.codigoTf.getText())) {
                    Establecimiento establecimiento = new Establecimiento(Integer.parseInt(this.vistaEstablecimientos.codigoTf.getText()),
                            this.vistaEstablecimientos.ubicacionTf.getText(),
                            this.vistaEstablecimientos.direccionTf.getText(),
                            this.vistaEstablecimientos.directorTf.getText());

                    if (this.admin.eliminarEstablecimiento(establecimiento)) {
                        JOptionPane.showMessageDialog(this.vistaEstablecimientos, "La accion se realizo correctamente");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Ocurrio un error al eliminar el establecimiento");
                    }
                }else{
                    JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Asegurece de escribir el codigo correctamente", "FALLIDO", JOptionPane.ERROR_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Asegurece de escribir el codigo correctamente", "FALLIDO", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this.vistaEstablecimientos, "No hay datos, intente buscando primero un registro");
        }
    }

    public void limpiarCampos() {
        this.vistaEstablecimientos.codigoTf.setText("");
        this.vistaEstablecimientos.ubicacionTf.setText("");
        this.vistaEstablecimientos.direccionTf.setText("");
        this.vistaEstablecimientos.directorTf.setText("");
    }

    public boolean isEmpty() {
        return this.vistaEstablecimientos.codigoTf.getText().isEmpty()
                || this.vistaEstablecimientos.ubicacionTf.getText().isEmpty()
                || this.vistaEstablecimientos.direccionTf.getText().isEmpty()
                || this.vistaEstablecimientos.directorTf.getText().isEmpty();
    }
}
