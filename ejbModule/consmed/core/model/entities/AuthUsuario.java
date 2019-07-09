package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the auth_usuario database table.
 * 
 */
@Entity
@Table(name="auth_usuario")
@NamedQuery(name="AuthUsuario.findAll", query="SELECT a FROM AuthUsuario a")
public class AuthUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUTH_USUARIO_IDUSUARIO_GENERATOR", sequenceName="SEQ_AUTH_USUARIO",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTH_USUARIO_IDUSUARIO_GENERATOR")
	@Column(name="id_usuario")
	private Integer idUsuario;

	@Column(name="contrasenia_usua")
	private String contraseniaUsua;

	@Column(name="correo_usua")
	private String correoUsua;

	//bi-directional many-to-one association to AuthRol
	@ManyToOne
	@JoinColumn(name="id_rol")
	private AuthRol authRol;

	//bi-directional many-to-one association to MedMedico
	@OneToMany(mappedBy="authUsuario",cascade=CascadeType.ALL)
	private List<MedMedico> medMedicos;

	//bi-directional many-to-one association to PacPaciente
	@OneToMany(mappedBy="authUsuario",cascade=CascadeType.ALL)
	private List<PacPaciente> pacPacientes;

	//bi-directional many-to-one association to SegBitacora
	@OneToMany(mappedBy="authUsuario",cascade=CascadeType.ALL)
	private List<SegBitacora> segBitacoras;

	public AuthUsuario() {
	}

	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getContraseniaUsua() {
		return this.contraseniaUsua;
	}

	public void setContraseniaUsua(String contraseniaUsua) {
		this.contraseniaUsua = contraseniaUsua;
	}

	public String getCorreoUsua() {
		return this.correoUsua;
	}

	public void setCorreoUsua(String correoUsua) {
		this.correoUsua = correoUsua;
	}

	public AuthRol getAuthRol() {
		return this.authRol;
	}

	public void setAuthRol(AuthRol authRol) {
		this.authRol = authRol;
	}

	public List<MedMedico> getMedMedicos() {
		return this.medMedicos;
	}

	public void setMedMedicos(List<MedMedico> medMedicos) {
		this.medMedicos = medMedicos;
	}

	public MedMedico addMedMedico(MedMedico medMedico) {
		getMedMedicos().add(medMedico);
		medMedico.setAuthUsuario(this);

		return medMedico;
	}

	public MedMedico removeMedMedico(MedMedico medMedico) {
		getMedMedicos().remove(medMedico);
		medMedico.setAuthUsuario(null);

		return medMedico;
	}

	public List<PacPaciente> getPacPacientes() {
		return this.pacPacientes;
	}

	public void setPacPacientes(List<PacPaciente> pacPacientes) {
		this.pacPacientes = pacPacientes;
	}

	public PacPaciente addPacPaciente(PacPaciente pacPaciente) {
		getPacPacientes().add(pacPaciente);
		pacPaciente.setAuthUsuario(this);

		return pacPaciente;
	}

	public PacPaciente removePacPaciente(PacPaciente pacPaciente) {
		getPacPacientes().remove(pacPaciente);
		pacPaciente.setAuthUsuario(null);

		return pacPaciente;
	}

	public List<SegBitacora> getSegBitacoras() {
		return this.segBitacoras;
	}

	public void setSegBitacoras(List<SegBitacora> segBitacoras) {
		this.segBitacoras = segBitacoras;
	}

	public SegBitacora addSegBitacora(SegBitacora segBitacora) {
		getSegBitacoras().add(segBitacora);
		segBitacora.setAuthUsuario(this);

		return segBitacora;
	}

	public SegBitacora removeSegBitacora(SegBitacora segBitacora) {
		getSegBitacoras().remove(segBitacora);
		segBitacora.setAuthUsuario(null);

		return segBitacora;
	}

}