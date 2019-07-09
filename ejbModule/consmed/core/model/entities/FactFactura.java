package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the fact_factura database table.
 * 
 */
@Entity
@Table(name="fact_factura")
@NamedQuery(name="FactFactura.findAll", query="SELECT f FROM FactFactura f")
public class FactFactura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FACT_FACTURA_IDFACTURA_GENERATOR", sequenceName="SEQ_FACT_FACTURA",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FACT_FACTURA_IDFACTURA_GENERATOR")
	@Column(name="id_factura")
	private Integer idFactura;

	private Boolean activo;

	private String detalle;

	private BigDecimal iva;

	private BigDecimal subtotal;

	private BigDecimal total;

	//bi-directional many-to-one association to FactHospital
	@ManyToOne
	@JoinColumn(name="id_hospital")
	private FactHospital factHospital;

	//bi-directional many-to-one association to PacPaciente
	@ManyToOne
	@JoinColumn(name="id_paciente")
	private PacPaciente pacPaciente;

	public FactFactura() {
	}

	public Integer getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getDetalle() {
		return this.detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getIva() {
		return this.iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public FactHospital getFactHospital() {
		return this.factHospital;
	}

	public void setFactHospital(FactHospital factHospital) {
		this.factHospital = factHospital;
	}

	public PacPaciente getPacPaciente() {
		return this.pacPaciente;
	}

	public void setPacPaciente(PacPaciente pacPaciente) {
		this.pacPaciente = pacPaciente;
	}

}