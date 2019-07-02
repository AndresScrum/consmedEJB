package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the pac_cabecera_hc database table.
 * 
 */
@Entity
@Table(name="pac_cabecera_hc")
@NamedQuery(name="PacCabeceraHc.findAll", query="SELECT p FROM PacCabeceraHc p")
public class PacCabeceraHc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PAC_CABECERA_HC_IDCABECERA_GENERATOR", sequenceName="SEQ_PAC_CABECERA_HC")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAC_CABECERA_HC_IDCABECERA_GENERATOR")
	@Column(name="id_cabecera")
	private Integer idCabecera;

	private BigDecimal altura;

	@Column(name="fecha_nacimiento")
	private String fechaNacimiento;

	private String genero;

	private String ocupacion;

	private BigDecimal peso;

	//bi-directional many-to-one association to PacPaciente
	@ManyToOne
	@JoinColumn(name="id_paciente")
	private PacPaciente pacPaciente;

	//bi-directional many-to-one association to PacHistoriaClinica
	@OneToMany(mappedBy="pacCabeceraHc")
	private List<PacHistoriaClinica> pacHistoriaClinicas;

	public PacCabeceraHc() {
	}

	public Integer getIdCabecera() {
		return this.idCabecera;
	}

	public void setIdCabecera(Integer idCabecera) {
		this.idCabecera = idCabecera;
	}

	public BigDecimal getAltura() {
		return this.altura;
	}

	public void setAltura(BigDecimal altura) {
		this.altura = altura;
	}

	public String getFechaNacimiento() {
		return this.fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return this.genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getOcupacion() {
		return this.ocupacion;
	}

	public void setOcupacion(String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public BigDecimal getPeso() {
		return this.peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public PacPaciente getPacPaciente() {
		return this.pacPaciente;
	}

	public void setPacPaciente(PacPaciente pacPaciente) {
		this.pacPaciente = pacPaciente;
	}

	public List<PacHistoriaClinica> getPacHistoriaClinicas() {
		return this.pacHistoriaClinicas;
	}

	public void setPacHistoriaClinicas(List<PacHistoriaClinica> pacHistoriaClinicas) {
		this.pacHistoriaClinicas = pacHistoriaClinicas;
	}

	public PacHistoriaClinica addPacHistoriaClinica(PacHistoriaClinica pacHistoriaClinica) {
		getPacHistoriaClinicas().add(pacHistoriaClinica);
		pacHistoriaClinica.setPacCabeceraHc(this);

		return pacHistoriaClinica;
	}

	public PacHistoriaClinica removePacHistoriaClinica(PacHistoriaClinica pacHistoriaClinica) {
		getPacHistoriaClinicas().remove(pacHistoriaClinica);
		pacHistoriaClinica.setPacCabeceraHc(null);

		return pacHistoriaClinica;
	}

}