package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the reser_cita database table.
 * 
 */
@Entity
@Table(name="reser_cita")
@NamedQuery(name="ReserCita.findAll", query="SELECT r FROM ReserCita r")
public class ReserCita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RESER_CITA_IDCITA_GENERATOR", sequenceName="SEQ_RESER_CITA",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RESER_CITA_IDCITA_GENERATOR")
	@Column(name="id_cita")
	private Integer idCita;

	@Column(name="activo_reser")
	private Boolean activoReser;

	@Column(name="asunto_reser")
	private String asuntoReser;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_reser")
	private Date fechaReser;

	@Column(name="hora_reser")
	private Time horaReser;

	@Column(name="pago_reser")
	private Boolean pagoReser;

	@Column(name="sintoma_reser")
	private String sintomaReser;

	//bi-directional many-to-one association to MedMedico
	@ManyToOne
	@JoinColumn(name="id_medico")
	private MedMedico medMedico;

	//bi-directional many-to-one association to PacPaciente
	@ManyToOne
	@JoinColumn(name="id_paciente")
	private PacPaciente pacPaciente;

	public ReserCita() {
	}

	public Integer getIdCita() {
		return this.idCita;
	}

	public void setIdCita(Integer idCita) {
		this.idCita = idCita;
	}

	public Boolean getActivoReser() {
		return this.activoReser;
	}

	public void setActivoReser(Boolean activoReser) {
		this.activoReser = activoReser;
	}

	public String getAsuntoReser() {
		return this.asuntoReser;
	}

	public void setAsuntoReser(String asuntoReser) {
		this.asuntoReser = asuntoReser;
	}

	public Date getFechaReser() {
		return this.fechaReser;
	}

	public void setFechaReser(Date fechaReser) {
		this.fechaReser = fechaReser;
	}

	public Time getHoraReser() {
		return this.horaReser;
	}

	public void setHoraReser(Time horaReser) {
		this.horaReser = horaReser;
	}

	public Boolean getPagoReser() {
		return this.pagoReser;
	}

	public void setPagoReser(Boolean pagoReser) {
		this.pagoReser = pagoReser;
	}

	public String getSintomaReser() {
		return this.sintomaReser;
	}

	public void setSintomaReser(String sintomaReser) {
		this.sintomaReser = sintomaReser;
	}

	public MedMedico getMedMedico() {
		return this.medMedico;
	}

	public void setMedMedico(MedMedico medMedico) {
		this.medMedico = medMedico;
	}

	public PacPaciente getPacPaciente() {
		return this.pacPaciente;
	}

	public void setPacPaciente(PacPaciente pacPaciente) {
		this.pacPaciente = pacPaciente;
	}

}