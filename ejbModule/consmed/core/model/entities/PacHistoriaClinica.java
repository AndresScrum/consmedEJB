package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the pac_historia_clinica database table.
 * 
 */
@Entity
@Table(name="pac_historia_clinica")
@NamedQuery(name="PacHistoriaClinica.findAll", query="SELECT p FROM PacHistoriaClinica p")
public class PacHistoriaClinica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PAC_HISTORIA_CLINICA_IDHISTORIACLINICA_GENERATOR", sequenceName="SEQ_PAC_HISTORIA_CLINICA",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAC_HISTORIA_CLINICA_IDHISTORIACLINICA_GENERATOR")
	@Column(name="id_historia_clinica")
	private Integer idHistoriaClinica;

	private String cama;

	private String cuidados;

	private String diagnostico;

	@Column(name="enfermedad_actual")
	private String enfermedadActual;

	private String estudios;

	@Column(name="evolucion_medica")
	private String evolucionMedica;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_atencion")
	private Date fechaAtencion;

	@Column(name="hora_atencion")
	private Time horaAtencion;

	@Column(name="motivo_consulta")
	private String motivoConsulta;

	private String plan;

	private String tratamiento;

	//bi-directional many-to-one association to MedMedico
	@ManyToOne
	@JoinColumn(name="id_medico")
	private MedMedico medMedico;

	//bi-directional many-to-one association to PacCabeceraHc
	@ManyToOne
	@JoinColumn(name="id_cabecera")
	private PacCabeceraHc pacCabeceraHc;

	public PacHistoriaClinica() {
	}

	public Integer getIdHistoriaClinica() {
		return this.idHistoriaClinica;
	}

	public void setIdHistoriaClinica(Integer idHistoriaClinica) {
		this.idHistoriaClinica = idHistoriaClinica;
	}

	public String getCama() {
		return this.cama;
	}

	public void setCama(String cama) {
		this.cama = cama;
	}

	public String getCuidados() {
		return this.cuidados;
	}

	public void setCuidados(String cuidados) {
		this.cuidados = cuidados;
	}

	public String getDiagnostico() {
		return this.diagnostico;
	}

	public void setDiagnostico(String diagnostico) {
		this.diagnostico = diagnostico;
	}

	public String getEnfermedadActual() {
		return this.enfermedadActual;
	}

	public void setEnfermedadActual(String enfermedadActual) {
		this.enfermedadActual = enfermedadActual;
	}

	public String getEstudios() {
		return this.estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getEvolucionMedica() {
		return this.evolucionMedica;
	}

	public void setEvolucionMedica(String evolucionMedica) {
		this.evolucionMedica = evolucionMedica;
	}

	public Date getFechaAtencion() {
		return this.fechaAtencion;
	}

	public void setFechaAtencion(Date fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public Time getHoraAtencion() {
		return this.horaAtencion;
	}

	public void setHoraAtencion(Time horaAtencion) {
		this.horaAtencion = horaAtencion;
	}

	public String getMotivoConsulta() {
		return this.motivoConsulta;
	}

	public void setMotivoConsulta(String motivoConsulta) {
		this.motivoConsulta = motivoConsulta;
	}

	public String getPlan() {
		return this.plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getTratamiento() {
		return this.tratamiento;
	}

	public void setTratamiento(String tratamiento) {
		this.tratamiento = tratamiento;
	}

	public MedMedico getMedMedico() {
		return this.medMedico;
	}

	public void setMedMedico(MedMedico medMedico) {
		this.medMedico = medMedico;
	}

	public PacCabeceraHc getPacCabeceraHc() {
		return this.pacCabeceraHc;
	}

	public void setPacCabeceraHc(PacCabeceraHc pacCabeceraHc) {
		this.pacCabeceraHc = pacCabeceraHc;
	}

}