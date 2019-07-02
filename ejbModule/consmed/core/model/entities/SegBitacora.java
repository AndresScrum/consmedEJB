package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the seg_bitacora database table.
 * 
 */
@Entity
@Table(name="seg_bitacora")
@NamedQuery(name="SegBitacora.findAll", query="SELECT s FROM SegBitacora s")
public class SegBitacora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SEG_BITACORA_IDBITACORA_GENERATOR", sequenceName="SEQ_SEG_BITACORA")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEG_BITACORA_IDBITACORA_GENERATOR")
	@Column(name="id_bitacora")
	private Integer idBitacora;

	private String descripcion;

	private Timestamp fecha;

	private String ip;

	private String nombre;

	//bi-directional many-to-one association to AuthUsuario
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private AuthUsuario authUsuario;

	public SegBitacora() {
	}

	public Integer getIdBitacora() {
		return this.idBitacora;
	}

	public void setIdBitacora(Integer idBitacora) {
		this.idBitacora = idBitacora;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public AuthUsuario getAuthUsuario() {
		return this.authUsuario;
	}

	public void setAuthUsuario(AuthUsuario authUsuario) {
		this.authUsuario = authUsuario;
	}

}