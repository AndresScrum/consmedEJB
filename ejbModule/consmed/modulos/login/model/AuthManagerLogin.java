package consmed.modulos.login.model;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import consmed.core.model.entities.AuthUsuario;
import consmed.core.model.entities.MedMedico;
import consmed.core.model.entities.PacPaciente;

@Stateless
@LocalBean
public class AuthManagerLogin {
	@PersistenceContext(unitName = "consmedDS")
	private EntityManager em;

	public AuthManagerLogin() {
	}

	public Login comprobarCredenciales(String correo, String contrasenia) throws Exception {
		System.out.println("CORREO:"+correo);
		System.out.println("CORREO:"+contrasenia);
		
		Login login = new Login();
		System.out.println("1");
		List<AuthUsuario>lista=findUsuarioByCorreo(correo);
		System.out.println("2");
		if (lista.isEmpty()) {
			System.out.println("3");
			throw new Exception("Credenciales incorrectas intente otra vez");
		}else {
			AuthUsuario usuario=lista.get(0);
			System.out.println("ROL: "+usuario.getCorreoUsua());
			System.out.println("ROL: "+usuario.getAuthRol().getNombreRol());
			boolean existeUsuario=credencialesValidas(usuario, correo, contrasenia);
			if (existeUsuario) {
				login.setId_usuario(usuario.getIdUsuario());
				login.setCorreo(usuario.getCorreoUsua());
				//concatenar nombres y apellidos
				login.setNombre_rol(usuario.getAuthRol().getNombreRol());
				//
				if (usuario.getAuthRol().getNombreRol().equals("Médico")) {
					login.setNombres(concatenarNombresAndApellidosMedico(usuario, usuario.getCorreoUsua()));
				}else {
					if (usuario.getAuthRol().getNombreRol().equals("Paciente")) {
						System.out.println("ENTRA:PACIENTE ");
						login.setNombres(concatenarNombresAndApellidosPaciente(usuario, usuario.getCorreoUsua()));
						System.out.println("LOGIN ROL "+login.getNombre_rol());
					}else {
						login.setNombres("Administrador "+usuario.getCorreoUsua());
					}
				}
		
			}
			return login;
		}
	}
	public String concatenarNombresAndApellidosPaciente(AuthUsuario usuario,String correoUsuario) {
	List<PacPaciente>listaPacientes=usuario.getPacPacientes();
		for (PacPaciente pacPaciente : listaPacientes) {
			if (pacPaciente.getCorreoPac().equals(correoUsuario)) {
				String nombres=pacPaciente.getApellidosPac()+" "+pacPaciente.getNombresPac();
				return nombres;
			}
		
			
		}
		return "Usuario no identificado";
		
	}
	public String concatenarNombresAndApellidosMedico(AuthUsuario usuario,String correoUsuario) {
	List<MedMedico>listaMedicos=usuario.getMedMedicos();
		for (MedMedico medico: listaMedicos) {
			if (medico.getCorreoMed().equals(correoUsuario)) {
				String nombres=medico.getApellidosMed()+" "+medico.getNombresMed();
				return nombres;
			}
		
			
		}
		return "Usuario no identificado";
		
	}
	

	
	public boolean credencialesValidas(AuthUsuario usuario,String correo, String contrasenia) {
		//Falta encriptar la base de datos
		//Desencriptar la base y comparar o encriptar la clve de afuera y comparar
		if (usuario.getCorreoUsua().equals(correo)&&usuario.getContraseniaUsua().equals(contrasenia)) {
			return true;
		}else {
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
		System.out.println("USUARIO: "+lista.size());
		return lista;
	}
	

}
