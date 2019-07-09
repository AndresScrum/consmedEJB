package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the med_medico database table.
 * 
 */
@Entity
@Table(name="med_medico")
@NamedQuery(name="MedMedico.findAll", query="SELECT m FROM MedMedico m")
public class MedMedico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MED_MEDICO_IDMEDICO_GENERATOR", sequenceName="SEQ_MED_MEDICO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MED_MEDICO_IDMEDICO_GENERATOR")
	@Column(name="id_medico")
	private Integer idMedico;

	@Column(name="activo_med")
	private Boolean activoMed;

	@Column(name="apellidos_med")
	private String apellidosMed;

	@Column(name="correo_med")
	private String correoMed;

	@Column(name="foto_med")
	private String fotoMed;

	@Column(name="identificacion_med")
	private String identificacionMed;

	@Column(name="nombres_med")
	private String nombresMed;

	@Column(name="telefono_med")
	private String telefonoMed;

	//bi-directional many-to-one association to AuthUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AuthUsuario authUsuario;

	//bi-directional many-to-one association to MedEspecialidad
	@ManyToOne
	@JoinColumn(name="id_especialidad")
	private MedEspecialidad medEspecialidad;

	//bi-directional many-to-one association to PacHistoriaClinica
	@OneToMany(mappedBy="medMedico",cascade=CascadeType.ALL)
	private List<PacHistoriaClinica> pacHistoriaClinicas;

	//bi-directional many-to-one association to ReserCita
	@OneToMany(mappedBy="medMedico",cascade=CascadeType.ALL)
	private List<ReserCita> reserCitas;

	public MedMedico() {
	}

	public Integer getIdMedico() {
		return this.idMedico;
	}

	public void setIdMedico(Integer idMedico) {
		this.idMedico = idMedico;
	}

	public Boolean getActivoMed() {
		return this.activoMed;
	}

	public void setActivoMed(Boolean activoMed) {
		this.activoMed = activoMed;
	}

	public String getApellidosMed() {
		return this.apellidosMed;
	}

	public void setApellidosMed(String apellidosMed) {
		this.apellidosMed = apellidosMed;
	}

	public String getCorreoMed() {
		return this.correoMed;
	}

	public void setCorreoMed(String correoMed) {
		this.correoMed = correoMed;
	}

	public String getFotoMed() {
		return this.fotoMed;
	}

	public void setFotoMed(String fotoMed) {
		this.fotoMed = fotoMed;
	}

	public String getIdentificacionMed() {
		return this.identificacionMed;
	}

	public void setIdentificacionMed(String identificacionMed) {
		this.identificacionMed = identificacionMed;
	}

	public String getNombresMed() {
		return this.nombresMed;
	}

	public void setNombresMed(String nombresMed) {
		this.nombresMed = nombresMed;
	}

	public String getTelefonoMed() {
		return this.telefonoMed;
	}

	public void setTelefonoMed(String telefonoMed) {
		this.telefonoMed = telefonoMed;
	}

	public AuthUsuario getAuthUsuario() {
		return this.authUsuario;
	}

	public void setAuthUsuario(AuthUsuario authUsuario) {
		this.authUsuario = authUsuario;
	}

	public MedEspecialidad getMedEspecialidad() {
		return this.medEspecialidad;
	}

	public void setMedEspecialidad(MedEspecialidad medEspecialidad) {
		this.medEspecialidad = medEspecialidad;
	}

	public List<PacHistoriaClinica> getPacHistoriaClinicas() {
		return this.pacHistoriaClinicas;
	}

	public void setPacHistoriaClinicas(List<PacHistoriaClinica> pacHistoriaClinicas) {
		this.pacHistoriaClinicas = pacHistoriaClinicas;
	}

	public PacHistoriaClinica addPacHistoriaClinica(PacHistoriaClinica pacHistoriaClinica) {
		getPacHistoriaClinicas().add(pacHistoriaClinica);
		pacHistoriaClinica.setMedMedico(this);

		return pacHistoriaClinica;
	}

	public PacHistoriaClinica removePacHistoriaClinica(PacHistoriaClinica pacHistoriaClinica) {
		getPacHistoriaClinicas().remove(pacHistoriaClinica);
		pacHistoriaClinica.setMedMedico(null);

		return pacHistoriaClinica;
	}

	public List<ReserCita> getReserCitas() {
		return this.reserCitas;
	}

	public void setReserCitas(List<ReserCita> reserCitas) {
		this.reserCitas = reserCitas;
	}

	public ReserCita addReserCita(ReserCita reserCita) {
		getReserCitas().add(reserCita);
		reserCita.setMedMedico(this);

		return reserCita;
	}

	public ReserCita removeReserCita(ReserCita reserCita) {
		getReserCitas().remove(reserCita);
		reserCita.setMedMedico(null);

		return reserCita;
	}

}