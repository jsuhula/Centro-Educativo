package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelos.Administracion;
import modelos.Establecimiento;
import archivos.AccesoDatosImpl;
import archivos.IAccesoDatos;
import java.util.List;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vistas.Establecimientos;
import vistas.Inicio;

public class EstablecimientoController implements ActionListener {

    protected IAccesoDatos datos;
    private Inicio padre;
    private final Establecimientos vistaEstablecimientos;
    private final Administracion admin;
    private Establecimiento establecimiento;
    private DefaultTableModel tablaModeloEstablecimientos;
    private DefaultTableCellRenderer align;

    public EstablecimientoController() {
        this.datos = new AccesoDatosImpl();
        this.admin = new Administracion();
        this.vistaEstablecimientos = new Establecimientos();
        this.vistaEstablecimientos.nuevoBtn.addActionListener(EstablecimientoController.this);
        this.vistaEstablecimientos.obtenerBtn.addActionListener(EstablecimientoController.this);
        this.vistaEstablecimientos.actualizarBtn.addActionListener(EstablecimientoController.this);
        this.vistaEstablecimientos.eliminarBtn.addActionListener(EstablecimientoController.this);
        this.vistaEstablecimientos.volverBtn.addActionListener(EstablecimientoController.this);
        this.vistaEstablecimientos.cargarListaBtn.addActionListener(EstablecimientoController.this);
    }

    public final void iniciar(Inicio padre) {
        this.vistaEstablecimientos.setTitle("Administracion");
        this.vistaEstablecimientos.setLocationRelativeTo(null);
        this.vistaEstablecimientos.setResizable(false);
        this.vistaEstablecimientos.setVisible(true);
        this.padre = padre;
        this.padre.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.vistaEstablecimientos.nuevoBtn) {
            guardarEstablecimiento();
        } else if (e.getSource() == this.vistaEstablecimientos.obtenerBtn) {
            obtenerEstablecimiento();
        } else if (e.getSource() == this.vistaEstablecimientos.actualizarBtn) {
            actualizarEstablecimiento();
        } else if (e.getSource() == this.vistaEstablecimientos.eliminarBtn) {
            eliminarEstablecimiento();
        } else if (e.getSource() == this.vistaEstablecimientos.volverBtn) {
            this.vistaEstablecimientos.dispose();
            this.padre.setVisible(true);
        } else if (e.getSource() == this.vistaEstablecimientos.cargarListaBtn) {
            cargarEstablecimientos();
        }
    }

    public void guardarEstablecimiento() {
        if (!estaVacio()) {
            obtenerEstablecimientoInput();
            String ejecutar = this.admin.agregarEstablecimiento(this.establecimiento);
            if (ejecutar.equalsIgnoreCase("1")) {
                JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Se agrego correctamente");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this.vistaEstablecimientos, ejecutar);
            }
        } else {
            JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Todos los campos marcados con (*) son obligatorios");
        }
    }

    public void obtenerEstablecimiento() {
        if (this.vistaEstablecimientos.codigoInput.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vistaEstablecimientos, "Debe de ingresar el codigo del establecimiento");
        } else {
            this.establecimiento = this.admin.buscarEstablecimiento(Integer.parseInt(this.vistaEstablecimientos.codigoInput.getText()));
            if (establecimiento == null) {
                JOptionPane.showMessageDialog(this.vistaEstablecimientos, "No hay establecimientos encontrados");
            } else {
                this.vistaEstablecimientos.nombreInput.setText(this.establecimiento.getNombre());
                this.vistaEstablecimientos.ubicacionInput.setText(this.establecimiento.getDepartamento());
                this.vistaEstablecimientos.direccionInput.setText(this.establecimiento.getDireccion());
                this.vistaEstablecimientos.directorInput.setText(this.establecimiento.getDirector());
            }
        }
    }

    public void actualizarEstablecimiento() {
        if (!estaVacio()) {
            String mensaje = "Esta seguro(a) que quiere realizar los cambios en el establecimiento";
            int option = JOptionPane.showConfirmDialog(vistaEstablecimientos, mensaje, "GUARDAR CAMBIOS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == 0) {
                obtenerEstablecimientoInput();
                if (this.admin.actualizarEstablecimiento(this.establecimiento)) {
                    JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Se realizaron los cambios correctamente");
                    limpiarCampos();
                } else {
                    JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Ocurrio un error al actualizar");
                }
            }

        } else {
            JOptionPane.showMessageDialog(this.vistaEstablecimientos, "No hay datos, intente buscando primero un registro");
        }
    }

    public void eliminarEstablecimiento() {
        if (!estaVacio()) {
            String codigo;
            String mensaje = "La accion es permanente, por seguridad digite el codigo del establecimiento: ";
            codigo = JOptionPane.showInputDialog(this.vistaEstablecimientos, mensaje, "ELIMINAR ESTABLECIMIENTO", JOptionPane.WARNING_MESSAGE);

            if (codigo != null) {
                codigo = codigo.equalsIgnoreCase("null") ? codigo : codigo;
            }

            if (codigo != null) {
                if (codigo.equalsIgnoreCase(this.vistaEstablecimientos.codigoInput.getText())) {
                    obtenerEstablecimientoInput();
                    if (this.admin.eliminarEstablecimiento(this.establecimiento)) {
                        JOptionPane.showMessageDialog(this.vistaEstablecimientos, "La accion se realizo correctamente");
                        limpiarCampos();
                    } else {
                        JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Ocurrio un error al eliminar el establecimiento");
                    }
                } else {
                    JOptionPane.showMessageDialog(this.vistaEstablecimientos, "Asegurece de escribir el codigo correctamente", "FALLIDO", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this.vistaEstablecimientos, "No hay datos, intente buscando primero un registro");
        }
    }

    public void cargarEstablecimientos() {
        cargarTablaEstablecimientos(this.admin.obtenerEstablecimientos(), 0);
    }

    private void cargarTablaEstablecimientos(List<Establecimiento> establecimientos, int totalAlumnos) {
        this.tablaModeloEstablecimientos = new DefaultTableModel();
        /**
         * align: variable con la que alineamos nuestros datos en la tabla y le
         * damos un ancho definido en un arreglo @widthColumns[]
         */
        this.align = new DefaultTableCellRenderer();
        int widthColumns[] = {50, 100, 250, 100};

        this.align.setHorizontalAlignment(SwingConstants.CENTER);
        this.tablaModeloEstablecimientos.addColumn("CODIGO");
        this.tablaModeloEstablecimientos.addColumn("NOMBRE");
        this.tablaModeloEstablecimientos.addColumn("UBICACION");
        this.tablaModeloEstablecimientos.addColumn("TOTAL ALUMNOS");

        for (Establecimiento establecimientoAux : establecimientos) {
            String row[] = {String.valueOf(establecimientoAux.getCodigo()),
                establecimientoAux.getNombre(),
                establecimientoAux.getDepartamento(),
                String.valueOf(totalAlumnos)};
            this.tablaModeloEstablecimientos.addRow(row);
        }
        this.vistaEstablecimientos.establecimientosTbl.setModel(this.tablaModeloEstablecimientos);

        for (int i = 0; i < widthColumns.length; i++) {
            this.vistaEstablecimientos.establecimientosTbl.getColumnModel().getColumn(i).setPreferredWidth(widthColumns[i]);
            this.vistaEstablecimientos.establecimientosTbl.getColumnModel().getColumn(i).setCellRenderer(align);
            this.vistaEstablecimientos.establecimientosTbl.getColumnModel().getColumn(i).setResizable(false);
        }
    }

    public void obtenerEstablecimientoInput() {
        this.establecimiento = new Establecimiento();
        this.establecimiento.setCodigo(Integer.parseInt(this.vistaEstablecimientos.codigoInput.getText()));
        this.establecimiento.setNombre(this.vistaEstablecimientos.nombreInput.getText());
        this.establecimiento.setDepartamento(this.vistaEstablecimientos.ubicacionInput.getText());
        this.establecimiento.setDireccion(this.vistaEstablecimientos.direccionInput.getText());
        this.establecimiento.setDirector(this.vistaEstablecimientos.directorInput.getText());
    }

    public void limpiarCampos() {
        this.vistaEstablecimientos.codigoInput.setText("");
        this.vistaEstablecimientos.ubicacionInput.setText("");
        this.vistaEstablecimientos.nombreInput.setText("");
        this.vistaEstablecimientos.direccionInput.setText("");
        this.vistaEstablecimientos.directorInput.setText("");
    }

    public boolean estaVacio() {
        return this.vistaEstablecimientos.codigoInput.getText().isEmpty()
                || this.vistaEstablecimientos.nombreInput.getText().isEmpty()
                || this.vistaEstablecimientos.ubicacionInput.getText().isEmpty()
                || this.vistaEstablecimientos.direccionInput.getText().isEmpty()
                || this.vistaEstablecimientos.directorInput.getText().isEmpty();
    }
}
