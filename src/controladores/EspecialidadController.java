package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Administracion;
import modelos.Especialidad;
import vistas.Especialidades;
import vistas.Inicio;

/**
 *
 * @author danie
 */
public class EspecialidadController implements ActionListener {

    private final Especialidades vistaEspecialidades;
    private Inicio padre;
    private Especialidad especialidad;
    private final Administracion admin;
    private DefaultTableModel tablaModeloEspecialdiades;
    private DefaultTableCellRenderer align;

    public EspecialidadController() {
        this.admin = new Administracion();
        this.vistaEspecialidades = new Especialidades();
        this.vistaEspecialidades.nuevoBtn.addActionListener(EspecialidadController.this);
        this.vistaEspecialidades.obtenerDatosBtn.addActionListener(EspecialidadController.this);
        this.vistaEspecialidades.actualizarBtn.addActionListener(EspecialidadController.this);
        this.vistaEspecialidades.eliminarBtn.addActionListener(EspecialidadController.this);
        this.vistaEspecialidades.volverBtn.addActionListener(EspecialidadController.this);
        this.vistaEspecialidades.asignarCoordinadorBtn.addActionListener(EspecialidadController.this);
    }

    public final void iniciar(Inicio padre) {
        this.vistaEspecialidades.setTitle("CONTROL ESPECIALIDADES");
        this.vistaEspecialidades.setLocationRelativeTo(null);
        this.vistaEspecialidades.setResizable(false);
        this.vistaEspecialidades.setVisible(true);
        this.padre = padre;
        this.padre.setVisible(false);
        cargarEspecialidades();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vistaEspecialidades.volverBtn) {
            this.vistaEspecialidades.dispose();
            this.padre.setVisible(true);
        } else if (e.getSource() == this.vistaEspecialidades.nuevoBtn) {
            guardarEspecialidad();
        } else if (e.getSource() == this.vistaEspecialidades.obtenerDatosBtn) {
            buscarEspecialidad();
        } else if (e.getSource() == this.vistaEspecialidades.actualizarBtn) {
            actualizarEspecialidad();
        } else if (e.getSource() == this.vistaEspecialidades.eliminarBtn) {
            eliminarEspecialidad();
        } else if(e.getSource() == this.vistaEspecialidades.asignarCoordinadorBtn){
            asignacionCoordinador();
        }
    }
    
    public void asignacionCoordinador(){
        if(estaVacio()){
            JOptionPane.showMessageDialog(this.vistaEspecialidades, "Es necesario tener el detalle de la especialidad, intente de nuevo");
        }else{
            JOptionPane.showInputDialog(this.vistaEspecialidades, "Ingrese el codigo del maestro para asignarlo");
        }
    }
    
    public void guardarEspecialidad() {
        if (!estaVacio()) {
            obtenerEspecialidad();
            String hecho = this.admin.agregarEspecialidad(this.especialidad);
            if (hecho.equalsIgnoreCase("1")) {
                JOptionPane.showMessageDialog(this.vistaEspecialidades, "Se agrego con exito");
            } else if (hecho.equalsIgnoreCase("0")) {
                JOptionPane.showMessageDialog(this.vistaEspecialidades, "No se pudo realizar la operacion comuniquese con soporte");
            } else {
                JOptionPane.showMessageDialog(this.vistaEspecialidades, hecho);
            }
        }else{
            JOptionPane.showMessageDialog(this.vistaEspecialidades, "Por favor llene todos los campos necesarios");
        }
    }

    public void buscarEspecialidad() {
        if (this.vistaEspecialidades.codigoInput.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this.vistaEspecialidades, "Debe de ingresar el codigo del establecimiento");
        } else {
            this.especialidad = this.admin.buscarEspecialidad(Integer.parseInt(this.vistaEspecialidades.codigoInput.getText()));
            if (this.especialidad == null) {
                JOptionPane.showMessageDialog(this.vistaEspecialidades, "No se encontraron especialidades que coincidan");
            } else {
                this.vistaEspecialidades.nombreInput.setText(this.especialidad.getNombreEspecialidad());
            }
        }
    }

    public void actualizarEspecialidad() {
        if (!estaVacio()) {
            int ok = JOptionPane.showConfirmDialog(this.vistaEspecialidades, "Confirma que quiere realizar los cambios?", "REALIZAR CAMBIOS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (ok == 0) {
                obtenerEspecialidad();
                if (this.admin.actualizarEspecialidad(this.especialidad)) {
                    JOptionPane.showMessageDialog(this.vistaEspecialidades, "Se guardaron los cambios");
                } else {
                    JOptionPane.showMessageDialog(this.vistaEspecialidades, "Ocurrio un error, Comuniquese con su administrador");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this.vistaEspecialidades, "Asegurese de llenar los campos necesarios para esta operacion");
        }
    }

    public void eliminarEspecialidad() {
        if (estaVacio()) {
            JOptionPane.showMessageDialog(this.vistaEspecialidades, "No hay datos, intente buscando primero un registro");
        } else {
            obtenerEspecialidad();
            String codigo;
            String mensaje = "La accion es permanente, por seguridad digite el codigo de la especialidad";
            codigo = JOptionPane.showInputDialog(this.vistaEspecialidades, mensaje, "ELIMINAR ESPECIALIDAD", JOptionPane.WARNING_MESSAGE);
            if (codigo != null) {
                codigo = codigo.equalsIgnoreCase("null") ? codigo : codigo;
            }

            if (codigo != null) {
                if (codigo.equalsIgnoreCase(String.valueOf(this.especialidad.getCodigoEspecialidad()))) {
                    if (this.admin.eliminarEspecialidad(this.especialidad)) {
                        JOptionPane.showMessageDialog(this.vistaEspecialidades, "Se elimino exitosamente");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this.vistaEspecialidades, "No se pudo eliminar el establecimiento, verifique nuevamente");
                    }
                }else{
                    JOptionPane.showMessageDialog(this.vistaEspecialidades, "El codigo introducido no coincide, verifique");
                }
            }
        }
    }
    
    public void cargarEspecialidades() {
        cargarTablaEspecialidades(this.admin.obtenerEspecialidades(), 0);
    }

    private void cargarTablaEspecialidades(List<Especialidad> especialidades, int totalAlumnos) {
        this.tablaModeloEspecialdiades = new DefaultTableModel();
        /**
         * align: variable con la que alineamos nuestros datos en la tabla y le
         * damos un ancho definido en un arreglo @widthColumns[]
         */
        this.align = new DefaultTableCellRenderer();
        int widthColumns[] = {100, 400, 150};

        this.align.setHorizontalAlignment(SwingConstants.CENTER);
        this.tablaModeloEspecialdiades.addColumn("CODIGO");
        this.tablaModeloEspecialdiades.addColumn("NOMBRE");
        this.tablaModeloEspecialdiades.addColumn("TOTAL ALUMNOS");

        for (Especialidad especialidadAux : especialidades) {
            String row[] = {String.valueOf(especialidadAux.getCodigoEspecialidad()),
                especialidadAux.getNombreEspecialidad(),
                String.valueOf(totalAlumnos)};
            this.tablaModeloEspecialdiades.addRow(row);
        }
        this.vistaEspecialidades.especialidadesTbl.setModel(this.tablaModeloEspecialdiades);

        for (int i = 0; i < widthColumns.length; i++) {
            this.vistaEspecialidades.especialidadesTbl.getColumnModel().getColumn(i).setPreferredWidth(widthColumns[i]);
            this.vistaEspecialidades.especialidadesTbl.getColumnModel().getColumn(i).setCellRenderer(align);
            this.vistaEspecialidades.especialidadesTbl.getColumnModel().getColumn(i).setResizable(false);
        }
    }

    public Especialidad obtenerEspecialidad() {
        this.especialidad = new Especialidad();
        this.especialidad.setCodigoEspecialidad(Integer.parseInt(this.vistaEspecialidades.codigoInput.getText()));
        this.especialidad.setNombreEspecialidad(this.vistaEspecialidades.nombreInput.getText());
        return this.especialidad;
    }

    public void limpiarCampos() {
        this.vistaEspecialidades.codigoInput.setText("");
        this.vistaEspecialidades.nombreInput.setText("");
        this.vistaEspecialidades.coordinadorInput.setText("");
    }

    public boolean estaVacio() {
        return this.vistaEspecialidades.codigoInput.getText().isEmpty()
                || this.vistaEspecialidades.nombreInput.getText().isEmpty();
    }
}
