package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the fact_hospital database table.
 * 
 */
@Entity
@Table(name="fact_hospital")
@NamedQuery(name="FactHospital.findAll", query="SELECT f FROM FactHospital f")
public class FactHospital implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FACT_HOSPITAL_IDHOSPITAL_GENERATOR", sequenceName="SEQ_FACT_HOSPITAL",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FACT_HOSPITAL_IDHOSPITAL_GENERATOR")
	@Column(name="id_hospital")
	private Integer idHospital;

	@Column(name="direccion_hosp")
	private String direccionHosp;

	@Column(name="nombre_hosp")
	private String nombreHosp;

	@Column(name="ruc_hosp")
	private String rucHosp;

	@Column(name="telefono_hosp")
	private String telefonoHosp;

	//bi-directional many-to-one association to FactFactura
	@OneToMany(mappedBy="factHospital",cascade=CascadeType.ALL)
	private List<FactFactura> factFacturas;

	public FactHospital() {
	}

	public Integer getIdHospital() {
		return this.idHospital;
	}

	public void setIdHospital(Integer idHospital) {
		this.idHospital = idHospital;
	}

	public String getDireccionHosp() {
		return this.direccionHosp;
	}

	public void setDireccionHosp(String direccionHosp) {
		this.direccionHosp = direccionHosp;
	}

	public String getNombreHosp() {
		return this.nombreHosp;
	}

	public void setNombreHosp(String nombreHosp) {
		this.nombreHosp = nombreHosp;
	}

	public String getRucHosp() {
		return this.rucHosp;
	}

	public void setRucHosp(String rucHosp) {
		this.rucHosp = rucHosp;
	}

	public String getTelefonoHosp() {
		return this.telefonoHosp;
	}

	public void setTelefonoHosp(String telefonoHosp) {
		this.telefonoHosp = telefonoHosp;
	}

	public List<FactFactura> getFactFacturas() {
		return this.factFacturas;
	}

	public void setFactFacturas(List<FactFactura> factFacturas) {
		this.factFacturas = factFacturas;
	}

	public FactFactura addFactFactura(FactFactura factFactura) {
		getFactFacturas().add(factFactura);
		factFactura.setFactHospital(this);

		return factFactura;
	}

	public FactFactura removeFactFactura(FactFactura factFactura) {
		getFactFacturas().remove(factFactura);
		factFactura.setFactHospital(null);

		return factFactura;
	}

}