package consmed.modulos.factfacturacion.model;

import java.math.BigDecimal;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import consmed.core.model.entities.FactFactura;
import consmed.core.model.entities.FactHospital;
import consmed.core.model.entities.PacPaciente;

/**
 * Session Bean implementation class FactManagerFacturacion
 */
@Stateless
@LocalBean
public class FactManagerFacturacion {

	@PersistenceContext(unitName="consmedDS")
    private EntityManager em;
    public FactManagerFacturacion() {
        
    }
    
    public void ingresarFactFactura(Boolean activo, String detalle, BigDecimal iva, BigDecimal subtotal, BigDecimal total,
    		FactHospital factHospital,PacPaciente pacPaciente) {
    	FactFactura factura=new FactFactura();
    	factura.setActivo(activo);
    	factura.setDetalle(detalle);
    	factura.setIva(iva);
    	factura.setSubtotal(subtotal);
    	factura.setTotal(total);
    	factura.setFactHospital(factHospital);
    	factura.setPacPaciente(pacPaciente);
    	em.persist(factura);
    }
    
    public FactHospital findFacHospitalById(int idHospital) {
    	FactHospital hospital=em.find(FactHospital.class, idHospital);
    	return hospital;
    }

}
