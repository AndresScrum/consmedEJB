package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the auth_rol database table.
 * 
 */
@Entity
@Table(name="auth_rol")
@NamedQuery(name="AuthRol.findAll", query="SELECT a FROM AuthRol a")
public class AuthRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUTH_ROL_IDROL_GENERATOR", sequenceName="SEQ_AUTH_ROL",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTH_ROL_IDROL_GENERATOR")
	@Column(name="id_rol")
	private Integer idRol;

	@Column(name="activo_rol")
	private Boolean activoRol;

	@Column(name="nombre_rol")
	private String nombreRol;

	//bi-directional many-to-one association to AuthUsuario
	@OneToMany(mappedBy="authRol",cascade=CascadeType.ALL)
	private List<AuthUsuario> authUsuarios;

	public AuthRol() {
	}

	public Integer getIdRol() {
		return this.idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public Boolean getActivoRol() {
		return this.activoRol;
	}

	public void setActivoRol(Boolean activoRol) {
		this.activoRol = activoRol;
	}

	public String getNombreRol() {
		return this.nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public List<AuthUsuario> getAuthUsuarios() {
		return this.authUsuarios;
	}

	public void setAuthUsuarios(List<AuthUsuario> authUsuarios) {
		this.authUsuarios = authUsuarios;
	}

	public AuthUsuario addAuthUsuario(AuthUsuario authUsuario) {
		getAuthUsuarios().add(authUsuario);
		authUsuario.setAuthRol(this);

		return authUsuario;
	}

	public AuthUsuario removeAuthUsuario(AuthUsuario authUsuario) {
		getAuthUsuarios().remove(authUsuario);
		authUsuario.setAuthRol(null);

		return authUsuario;
	}

}