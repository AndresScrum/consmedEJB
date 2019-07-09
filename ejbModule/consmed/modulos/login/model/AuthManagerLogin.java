package consmed.modulos.login.model;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import consmed.core.model.entities.AuthUsuario;
import consmed.core.model.entities.MedMedico;
import consmed.core.model.entities.PacPaciente;
import consmed.modulos.authautorizacion.model.AuthManagerAutorizacion;

@Stateless
@LocalBean
public class AuthManagerLogin {
	@PersistenceContext(unitName = "consmedDS")
	private EntityManager em;
	@EJB
	private AuthManagerAutorizacion authManagerAutorizacion;

	public AuthManagerLogin() {
	}

	public Login comprobarCredenciales(String correo, String contrasenia) throws Exception {
		Login login = new Login();
		List<AuthUsuario> lista = findUsuarioByCorreo(correo);

		if (lista.isEmpty()) {
				throw new Exception("Credenciales incorrectas intente otra vez");
		} else {
			AuthUsuario usuario = lista.get(0);
			System.out.println("{");
			boolean existeUsuario = credencialesValidas(usuario, correo, contrasenia);
			System.out.println("}");
			if (existeUsuario) {
				System.out.println("SI EXISTE USUARIO");
				login.setId_usuario(usuario.getIdUsuario());
				login.setCorreo(usuario.getCorreoUsua());
				// concatenar nombres y apellidos
				login.setNombre_rol(usuario.getAuthRol().getNombreRol());
				//
				if (usuario.getAuthRol().getNombreRol().equals("Médico")) {
					System.out.println("SI EXISTE USUARIO");
					login.setNombres(concatenarNombresAndApellidosMedico(usuario, usuario.getCorreoUsua()));
				} else {
					if (usuario.getAuthRol().getNombreRol().equals("Paciente")) {
						System.out.println("ENTRA:PACIENTE ");
						login.setNombres(concatenarNombresAndApellidosPaciente(usuario, usuario.getCorreoUsua()));
						System.out.println("LOGIN ROL " + login.getNombre_rol());
					} else {
						login.setNombres("Administrador " + usuario.getCorreoUsua());
					}
				}
				if (login.getNombres().equals("Usuario no identificado")) {

					AuthUsuario user = authManagerAutorizacion.findUsuarioById(login.getId_usuario());
					if (user.getCorreoUsua() != null) {
						em.remove(user);
						login.setNombres(null);
					}
				}
			} else {
				return null;
			}

			return login;
		}
	}

	public String concatenarNombresAndApellidosPaciente(AuthUsuario usuario, String correoUsuario) {
		List<PacPaciente> listaPacientes = usuario.getPacPacientes();
		for (PacPaciente pacPaciente : listaPacientes) {
			if (pacPaciente.getCorreoPac().equals(correoUsuario)) {
				String nombres = pacPaciente.getApellidosPac() + " " + pacPaciente.getNombresPac();
				return nombres;
			}

		}
		return "Usuario no identificado";

	}

	public String concatenarNombresAndApellidosMedico(AuthUsuario usuario, String correoUsuario) {
		List<MedMedico> listaMedicos = usuario.getMedMedicos();
		for (MedMedico medico : listaMedicos) {
			if (medico.getCorreoMed().equals(correoUsuario)) {
				String nombres = medico.getApellidosMed() + " " + medico.getNombresMed();
				return nombres;
			}

		}
		return "Usuario no identificado";

	}

	public boolean credencialesValidas(AuthUsuario usuario, String correo, String contrasenia) {
		// Falta encriptar la base de datos
		// Desencriptar la base y comparar o encriptar la clve de afuera y comparar
		System.out.println("Contrasenia de BDD: "+usuario.getContraseniaUsua());
		System.out.println("Contrasenia que ingresa: "+contrasenia);
		if (usuario.getCorreoUsua().equals(correo) && usuario.getContraseniaUsua().equals(contrasenia)) {
			return true;
		} else {
			System.out.println("ENTRA A FALSO");
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	public List<AuthUsuario> findUsuarioByCorreo(String correo) {
		String JPQL = "SELECT a FROM AuthUsuario a WHERE a.correoUsua=?1";
		Query query = em.createQuery(JPQL, AuthUsuario.class);
		query.setParameter(1, correo);
		List<AuthUsuario> lista;
		lista = query.getResultList();
		System.out.println("USUARIO: " + lista.size());
		return lista;
	}

}
