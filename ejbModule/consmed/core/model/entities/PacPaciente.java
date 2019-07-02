package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pac_paciente database table.
 * 
 */
@Entity
@Table(name="pac_paciente")
@NamedQuery(name="PacPaciente.findAll", query="SELECT p FROM PacPaciente p")
public class PacPaciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PAC_PACIENTE_IDPACIENTE_GENERATOR", sequenceName="SEQ_PAC_PACIENTE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PAC_PACIENTE_IDPACIENTE_GENERATOR")
	@Column(name="id_paciente")
	private Integer idPaciente;

	@Column(name="activo_pac")
	private Boolean activoPac;

	@Column(name="apellidos_pac")
	private String apellidosPac;

	@Column(name="correo_pac")
	private String correoPac;

	@Column(name="direccion_pac")
	private String direccionPac;

	@Column(name="foto_pac")
	private String fotoPac;

	private String identificacion;

	@Column(name="nombres_pac")
	private String nombresPac;

	@Column(name="telefono_pac")
	private String telefonoPac;

	//bi-directional many-to-one association to FactFactura
	@OneToMany(mappedBy="pacPaciente")
	private List<FactFactura> factFacturas;

	//bi-directional many-to-one association to PacCabeceraHc
	@OneToMany(mappedBy="pacPaciente")
	private List<PacCabeceraHc> pacCabeceraHcs;

	//bi-directional many-to-one association to AuthUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AuthUsuario authUsuario;

	//bi-directional many-to-one association to ReserCita
	@OneToMany(mappedBy="pacPaciente")
	private List<ReserCita> reserCitas;

	public PacPaciente() {
	}

	public Integer getIdPaciente() {
		return this.idPaciente;
	}

	public void setIdPaciente(Integer idPaciente) {
		this.idPaciente = idPaciente;
	}

	public Boolean getActivoPac() {
		return this.activoPac;
	}

	public void setActivoPac(Boolean activoPac) {
		this.activoPac = activoPac;
	}

	public String getApellidosPac() {
		return this.apellidosPac;
	}

	public void setApellidosPac(String apellidosPac) {
		this.apellidosPac = apellidosPac;
	}

	public String getCorreoPac() {
		return this.correoPac;
	}

	public void setCorreoPac(String correoPac) {
		this.correoPac = correoPac;
	}

	public String getDireccionPac() {
		return this.direccionPac;
	}

	public void setDireccionPac(String direccionPac) {
		this.direccionPac = direccionPac;
	}

	public String getFotoPac() {
		return this.fotoPac;
	}

	public void setFotoPac(String fotoPac) {
		this.fotoPac = fotoPac;
	}

	public String getIdentificacion() {
		return this.identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getNombresPac() {
		return this.nombresPac;
	}

	public void setNombresPac(String nombresPac) {
		this.nombresPac = nombresPac;
	}

	public String getTelefonoPac() {
		return this.telefonoPac;
	}

	public void setTelefonoPac(String telefonoPac) {
		this.telefonoPac = telefonoPac;
	}

	public List<FactFactura> getFactFacturas() {
		return this.factFacturas;
	}

	public void setFactFacturas(List<FactFactura> factFacturas) {
		this.factFacturas = factFacturas;
	}

	public FactFactura addFactFactura(FactFactura factFactura) {
		getFactFacturas().add(factFactura);
		factFactura.setPacPaciente(this);

		return factFactura;
	}

	public FactFactura removeFactFactura(FactFactura factFactura) {
		getFactFacturas().remove(factFactura);
		factFactura.setPacPaciente(null);

		return factFactura;
	}

	public List<PacCabeceraHc> getPacCabeceraHcs() {
		return this.pacCabeceraHcs;
	}

	public void setPacCabeceraHcs(List<PacCabeceraHc> pacCabeceraHcs) {
		this.pacCabeceraHcs = pacCabeceraHcs;
	}

	public PacCabeceraHc addPacCabeceraHc(PacCabeceraHc pacCabeceraHc) {
		getPacCabeceraHcs().add(pacCabeceraHc);
		pacCabeceraHc.setPacPaciente(this);

		return pacCabeceraHc;
	}

	public PacCabeceraHc removePacCabeceraHc(PacCabeceraHc pacCabeceraHc) {
		getPacCabeceraHcs().remove(pacCabeceraHc);
		pacCabeceraHc.setPacPaciente(null);

		return pacCabeceraHc;
	}

	public AuthUsuario getAuthUsuario() {
		return this.authUsuario;
	}

	public void setAuthUsuario(AuthUsuario authUsuario) {
		this.authUsuario = authUsuario;
	}

	public List<ReserCita> getReserCitas() {
		return this.reserCitas;
	}

	public void setReserCitas(List<ReserCita> reserCitas) {
		this.reserCitas = reserCitas;
	}

	public ReserCita addReserCita(ReserCita reserCita) {
		getReserCitas().add(reserCita);
		reserCita.setPacPaciente(this);

		return reserCita;
	}

	public ReserCita removeReserCita(ReserCita reserCita) {
		getReserCitas().remove(reserCita);
		reserCita.setPacPaciente(null);

		return reserCita;
	}

}