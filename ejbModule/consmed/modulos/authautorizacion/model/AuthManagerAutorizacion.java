package consmed.modulos.authautorizacion.model;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import consmed.core.model.entities.AuthRol;
import consmed.core.model.entities.AuthUsuario;
import consmed.core.model.entities.PacPaciente;
import consmed.modulos.pacpaciente.model.PacManagerPaciente;

@Stateless
@LocalBean
public class AuthManagerAutorizacion {
	@PersistenceContext(unitName="consmedDS")
	private EntityManager em;
@EJB
private PacManagerPaciente pacManagerPaciente;

	public AuthManagerAutorizacion() {
	}

	// Econtrar rol por id 
	public AuthRol findRolById(int id_rol) {
		AuthRol rol= em.find(AuthRol.class, id_rol);
		return rol;

	}	


	// Encontrar usuario por id
	public AuthUsuario findUsuarioById(int id_usuario) {
		AuthUsuario usuar = em.find(AuthUsuario.class, id_usuario);
		return usuar;
	}
	public List<AuthRol> findAllRoles() {
		Query q = em.createQuery("SELECT a FROM AuthRol a", AuthRol.class);
		@SuppressWarnings("unchecked")
		List<AuthRol> listaRoles= q.getResultList();
		return listaRoles;
	}
	@SuppressWarnings("unchecked")
	public int  findRolByNombre(String nombreRol) {
		String JPQL= "SELECT a FROM AuthRol a WHERE a.nombreRol=?1";
				Query query = em.createQuery(JPQL, AuthRol.class);
				query.setParameter(1, nombreRol);
				List<AuthRol> lista;
				int id_rol=0;
				lista = query.getResultList();
				for (AuthRol authRol : lista) {
					if (authRol.getNombreRol().equals("Paciente")) {
					id_rol=authRol.getIdRol();
					return id_rol;
					}
				}
				return id_rol;
			}

//ingresar un rol	
	public void ingresarAuthRol(String nombre, boolean activo) throws Exception {
		
		boolean existeRol= findAthRolByNOmbre(nombre);
		if (existeRol) {
			throw new Exception("Error ya existe un rol con el nombre: " + nombre);
		}
		AuthRol rol = new AuthRol();
		rol.setActivoRol(activo);
		rol.setNombreRol(nombre);
		em.persist(rol);

	}

	// Validación si ya existe un rol con el mismo nombre
	@SuppressWarnings("unchecked")
	public boolean findAthRolByNOmbre(String nombre) {
		
		String JPQL = "SELECT a FROM AuthRol a WHERE a.nombreRol=?1";
		Query query = em.createQuery(JPQL, AuthRol.class);
		query.setParameter(1, nombre);
		List<AuthRol> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;
	}

	public void ingresarAuthUsuario(String correoUsua, String contrasenia_usua, int id_rol) throws Exception {
		if (id_rol == 0) {
			throw new Exception("Error al al seleccionar el rol! ");
		}
		boolean existeCorreo = findAthUsuarioCorreo(correoUsua);
		if (existeCorreo) {
			throw new Exception("Error ya existe un usuario registrado con ese correo: " + correoUsua);
		}
		AuthRol authRol = findRolById(id_rol);
		AuthUsuario usuario = new AuthUsuario();
		usuario.setAuthRol(authRol);
		usuario.setContraseniaUsua(contrasenia_usua);
		usuario.setCorreoUsua(correoUsua);
		em.persist(usuario);

	}
	// Método que me devuelve el usuario por correo
	@SuppressWarnings("unchecked")
	public boolean findAthUsuarioCorreo(String correo) {
		System.out.println("10");
		String JPQL = "SELECT a FROM AuthUsuario a WHERE a.correoUsua=?1";
		Query query = em.createQuery(JPQL, AuthUsuario.class);
		query.setParameter(1, correo);
		List<AuthUsuario> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;
	}
	public void ingresarPacPaciente(String nombres_pac, String apellidos_pac, String identificacion, String correoPac,
			String contrasenia, String telefono_pac, String direccion_pac,
			boolean activo_pac) throws Exception {
		int id_rol=findRolByNombre("Paciente");
		System.out.println("id_rol: "+id_rol);
		if (id_rol==0) {
			ingresarAuthRol("Paciente", true);
			id_rol=findRolByNombre("Paciente");
		}
		ingresarAuthUsuario(correoPac, contrasenia, id_rol);
		boolean repetidoCorreo, repetidoIdentificacion;

		repetidoCorreo = pacManagerPaciente.findPacPacienteCorreo(correoPac);

		repetidoIdentificacion = pacManagerPaciente.findPacPacienteIdentificacion(identificacion);

		if (repetidoCorreo) {
			//se debe eliminar el usuarioAuth
			throw new Exception("Ya existe un paciente registrado con  el correo: " + correoPac);
		}
		if (repetidoIdentificacion) {
			//se debe eliminar el usuarioAuth
			throw new Exception("Ya existe un paciente registrado con  esta identificación: " + identificacion);
		}

		AuthUsuario usuario = pacManagerPaciente.findAthUsuarioByCorreo(correoPac);
		if (usuario == null) {
			throw new Exception("Error al seleccionar el usuario ");
		}
		PacPaciente paciente = new PacPaciente();
		paciente.setNombresPac(nombres_pac);
		paciente.setApellidosPac(apellidos_pac);
		paciente.setIdentificacion(identificacion);
		paciente.setCorreoPac(correoPac);
		paciente.setTelefonoPac(telefono_pac);
		paciente.setAuthUsuario(usuario);
		paciente.setDireccionPac(direccion_pac);
		paciente.setActivoPac(activo_pac);
		paciente.setFotoPac("INGRESE UNA IMAGEN");
		em.persist(paciente);
	}
	
	public void ingresarRol(String nombre_rol, boolean activo_rol) throws Exception {
	boolean existe =findAthRolByNOmbre(nombre_rol);
	if (existe) {
		throw new Exception("Ya existe un rol con el nombre: "+nombre_rol);
	}else {
	AuthRol rol=new AuthRol();
	rol.setNombreRol(nombre_rol);
	rol.setActivoRol(activo_rol);
em.persist(rol);	
	}
		
	}
	
	
	

}
