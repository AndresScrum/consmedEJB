package consmed.modulos.pacpaciente.model;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import consmed.core.model.entities.AuthRol;
import consmed.core.model.entities.AuthUsuario;
import consmed.core.model.entities.FactFactura;
import consmed.core.model.entities.MedMedico;
import consmed.core.model.entities.PacCabeceraHc;
import consmed.core.model.entities.PacHistoriaClinica;
import consmed.core.model.entities.PacPaciente;
import consmed.core.model.entities.ReserCita;

@Stateless
@LocalBean
public class PacManagerPaciente {
	@PersistenceContext(unitName = "consmedDS")
	private EntityManager em;

	public PacManagerPaciente() {
	}

	/*
	 * Rol
	 */

	// Econtrar rol por id
	public AuthRol findRolById(int id_rol) {
		AuthRol rol = em.find(AuthRol.class, id_rol);
		return rol;

	}

	// Encontrar usuario por id
	public AuthUsuario findUsuarioById(int id_usuario) {
		AuthUsuario usuar = em.find(AuthUsuario.class, id_usuario);
		return usuar;

	}

	// Encontrar paciente por id
	public PacPaciente findPacienteById(int id_paciente) {
		PacPaciente pac = em.find(PacPaciente.class, id_paciente);
		return pac;

	}

	// Método que me devuelve la Lista de Pacientes
	public List<PacPaciente> findAllPacPacientes() {
		Query q = em.createQuery("SELECT p FROM PacPaciente p", PacPaciente.class);
		@SuppressWarnings("unchecked")
		List<PacPaciente> listaPacientes = q.getResultList();
		return listaPacientes;
	}

	// Método que me devuelve el paciente por identificación
	@SuppressWarnings("unchecked")
	public boolean findPacPacienteIdentificacion(String identificacion) {
		String JPQL = "SELECT p FROM PacPaciente p WHERE p.identificacion=?1";
		Query query = em.createQuery(JPQL, PacPaciente.class);
		query.setParameter(1, identificacion);
		List<PacPaciente> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;
	}

	// Encontrar paciente por id_usuario
	public PacPaciente findPacienteByIdUsuario(int id_usuario) {
		String JPQL = "SELECT p FROM PacPaciente p WHERE p.authUsuario.idUsuario=?1";
		Query query = em.createQuery(JPQL, PacPaciente.class);
		query.setParameter(1, id_usuario);
		List<PacPaciente> lista;
		lista = query.getResultList();
		PacPaciente pac = new PacPaciente();
		for (PacPaciente pacPaciente : lista) {
			pac = pacPaciente;
		}
		return pac;

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

	public AuthUsuario findAthUsuarioByCorreo(String correo) {
		String JPQL = "SELECT a FROM AuthUsuario a WHERE a.correoUsua=?1";
		Query query = em.createQuery(JPQL, AuthUsuario.class);
		query.setParameter(1, correo);
		AuthUsuario usuario;
		usuario = (AuthUsuario) query.getSingleResult();
		return usuario;
	}

	// Método que me devuelve paciente por id usuario
	public PacPaciente findPacPacienteByUsuario(int id_usuario) {
		String JPQL = "SELECT p FROM PacPaciente p WHERE p.authUsuario.idUsuario=?1";
		Query query = em.createQuery(JPQL, PacPaciente.class);
		query.setParameter(1, id_usuario);
		PacPaciente paciente;
		paciente = (PacPaciente) query.getSingleResult();
		return paciente;
	}

	// Método que me devuelve el paciente por correo
	@SuppressWarnings("unchecked")
	public boolean findPacPacienteCorreo(String correo) {
		String JPQL = "SELECT p FROM PacPaciente p WHERE p.correoPac=?1";
		Query query = em.createQuery(JPQL, PacPaciente.class);
		query.setParameter(1, correo);
		List<PacPaciente> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;
	}

	public void ingresarAuthUsuarioPaciente(String correoUsua, String contrasenia_usua, int id_rol,
			String identificacion) throws Exception {
		if (id_rol == 0) {
			throw new Exception("Error al al seleccionar el rol! ");
		}
		boolean repetidoCorreo, repetidoIdentificacion;

		repetidoCorreo = findPacPacienteCorreo(correoUsua);

		repetidoIdentificacion = findPacPacienteIdentificacion(identificacion);
		if (repetidoCorreo) {
			throw new Exception("Ya existe un paciente registrado con  el correo: " + correoUsua);
		}
		if (repetidoIdentificacion) {
			throw new Exception("Ya existe un paciente registrado con  esta identificación: " + identificacion);
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

	public void ingresarAuthUsuarioMedico(String correoUsua, String contrasenia_usua, int id_rol) throws Exception {
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

	// Método que ingresa una Paciente
	public void ingresarPacPaciente(String nombres_pac, String apellidos_pac, String identificacion, String correoPac,
			String contrasenia, int id_rol, String telefono_pac, String direccion_pac, boolean activo_pac,
			String foto_pac) throws Exception {
		ingresarAuthUsuarioPaciente(correoPac, contrasenia, id_rol, identificacion);
		AuthUsuario usuario = findAthUsuarioByCorreo(correoPac);
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
		paciente.setFotoPac(foto_pac);
		em.persist(paciente);
	}

	// Método que ingresa una Paciente
	public void editarPacPaciente(PacPaciente pacienteCargado) throws Exception {
		PacPaciente pacienteActual = findPacienteById(pacienteCargado.getIdPaciente());
		boolean existecorreo = false, existecorreoPaciente = false, existeIdentificacion = false;

		if (!pacienteActual.getCorreoPac().equals(pacienteCargado.getCorreoPac())) {
			existecorreo = findAthUsuarioCorreo(pacienteCargado.getCorreoPac());
			existecorreoPaciente = findPacPacienteCorreo(pacienteCargado.getCorreoPac());
			if (existecorreo || existecorreoPaciente) {
				throw new Exception("Ya existe un paciente con el correo " + pacienteCargado.getCorreoPac());
			}

		}
		if (!pacienteActual.getIdentificacion().equals(pacienteCargado.getIdentificacion())) {
			existeIdentificacion = findPacPacienteIdentificacion(pacienteCargado.getIdentificacion());
			if (existeIdentificacion) {
				throw new Exception(
						"La identificación " + pacienteCargado.getIdentificacion() + " ya se encuentra registrada");
			}
		}
		AuthUsuario usuario = findAthUsuarioByCorreo(pacienteActual.getCorreoPac());
		pacienteActual.setActivoPac(pacienteCargado.getActivoPac());
		pacienteActual.setApellidosPac(pacienteCargado.getApellidosPac());
		pacienteActual.setAuthUsuario(usuario);
		pacienteActual.setCorreoPac(pacienteCargado.getCorreoPac());
		pacienteActual.setDireccionPac(pacienteCargado.getDireccionPac());
		pacienteActual.setFotoPac(pacienteCargado.getFotoPac());
		pacienteActual.setIdentificacion(pacienteCargado.getIdentificacion());
		pacienteActual.setNombresPac(pacienteCargado.getNombresPac());
		pacienteActual.setTelefonoPac(pacienteCargado.getTelefonoPac());
		pacienteActual.setIdPaciente(pacienteCargado.getIdPaciente());
		em.merge(pacienteActual);
	}

//Método que ingresa una Paciente
	public void editarPacPacientePerfil(PacPaciente pacienteCargado) throws Exception {
		if (pacienteCargado == null) {
			throw new Exception("Error en el id ");

		}
		PacPaciente pacienteActual = findPacienteById(pacienteCargado.getIdPaciente());

		boolean existecorreo = false, existecorreoPaciente = false, existeIdentificacion = false;

		if (!pacienteActual.getCorreoPac().equals(pacienteCargado.getCorreoPac())) {
			existecorreo = findAthUsuarioCorreo(pacienteCargado.getCorreoPac());
			existecorreoPaciente = findPacPacienteCorreo(pacienteCargado.getCorreoPac());
			if (existecorreo || existecorreoPaciente) {
				throw new Exception("Ya existe un paciente con el correo " + pacienteCargado.getCorreoPac());
			}

		}
		if (!pacienteActual.getIdentificacion().equals(pacienteCargado.getIdentificacion())) {
			existeIdentificacion = findPacPacienteIdentificacion(pacienteCargado.getIdentificacion());
			if (existeIdentificacion) {
				throw new Exception(
						"La identificación " + pacienteCargado.getIdentificacion() + " ya se encuentra registrada");
			}
		}
		AuthUsuario usuario = findAthUsuarioByCorreo(pacienteActual.getCorreoPac());
		pacienteActual.setApellidosPac(pacienteCargado.getApellidosPac());
		pacienteActual.setAuthUsuario(usuario);
		pacienteActual.setCorreoPac(pacienteCargado.getCorreoPac());
		pacienteActual.setDireccionPac(pacienteCargado.getDireccionPac());
		pacienteActual.setFotoPac(pacienteCargado.getFotoPac());
		pacienteActual.setIdentificacion(pacienteCargado.getIdentificacion());
		pacienteActual.setNombresPac(pacienteCargado.getNombresPac());
		pacienteActual.setTelefonoPac(pacienteCargado.getTelefonoPac());
		em.merge(pacienteActual);

	}

//Eliminar paciente
	public void eliminarPaciente(int id_paciente) throws Exception {
		System.out.println("1");
		boolean existePacienteReservas = existePacienteCitas(id_paciente);
		System.out.println("2");
		boolean existePacienteCabeceraHc = existePacienteCabeceraHc(id_paciente);

		System.out.println("3");
		boolean existePacienteFactura = existePacienteFactura(id_paciente);
		System.out.println("4");
		if (existePacienteReservas) {
			throw new Exception("No se puede eliminar el paciente está siendo utilizado en reserva de citas");
		}
		if (existePacienteCabeceraHc) {
			throw new Exception(
					"No se puede eliminar el paciente está siendo utilizado en la cabecera de la historia clínica");
		}
		if (existePacienteFactura) {
			throw new Exception("No se puede eliminar el paciente está siendo utilizado en las facturas");
		}
		PacPaciente paciente = findPacienteById(id_paciente);
		AuthUsuario usuario = findAthUsuarioByCorreo(paciente.getCorreoPac());
		em.remove(paciente);
		em.remove(usuario);

	}

	@SuppressWarnings("unchecked")
	public boolean existePacienteCitas(int id_paciente)  {
		// Método que me devuelve el paciente por identificación
		String JPQL = "SELECT r FROM ReserCita r WHERE r.pacPaciente.idPaciente=?1";
		Query query = em.createQuery(JPQL, ReserCita.class);
		query.setParameter(1, id_paciente);
		List<ReserCita> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;
		

	}

	/********************************************
	 * Historia clinica
	 * 
	 ****************************************/
	
	/**
	 * Encontrar cabecera por ID
	 * @param idCabecera
	 * @return
	 */
		public PacCabeceraHc findCabeceraHcById(int idCabecera) {
			PacCabeceraHc cab = em.find(PacCabeceraHc.class, idCabecera);
			return cab;

		}
	/**
	 * Crear cabecera historia cl�nica
	 */
	public void ingresarPacCabeceraHc(PacPaciente paciente, String fechaNacimiento, double peso, double altura,
			String genero, String ocupacion) throws Exception {
System.out.println("ingresarCabe idPaci: "+paciente.getIdPaciente());
		if(!existePacienteCabeceraHc(paciente.getIdPaciente())) {
			PacCabeceraHc cabera = new PacCabeceraHc();
			cabera.setPacPaciente(paciente);
			cabera.setFechaNacimiento(fechaNacimiento);
			cabera.setPeso(new BigDecimal(peso));
			cabera.setAltura(new BigDecimal(altura));
			cabera.setGenero(genero);
			cabera.setOcupacion(ocupacion);
			em.persist(cabera);
		}else {
			throw new Exception("Ya existe cabecera");
		}
		
	}
	
	/**
	 * Editar cabecera historia cl�nica
	 */
	public void editarPacCabeceraHc(PacCabeceraHc cabeceraCargado) throws Exception {
		
		PacCabeceraHc cabecera=findCabeceraHcById(cabeceraCargado.getIdCabecera());
			cabecera.setPacPaciente(cabeceraCargado.getPacPaciente());
			cabecera.setFechaNacimiento(cabeceraCargado.getFechaNacimiento());
			cabecera.setPeso(cabeceraCargado.getPeso());
			cabecera.setAltura(cabeceraCargado.getAltura());
			cabecera.setGenero(cabeceraCargado.getGenero());
			cabecera.setOcupacion(cabeceraCargado.getOcupacion());
			em.merge(cabecera);		
	}

	public void ingresarPacHistoriaC(PacCabeceraHc cabecera, Date fechaAtencion, Time horaAtencion, MedMedico medico,
			String cama, String motivoConsulta, String enfermedadActual, String diagnostico, String evolucionMedica,
			String plan, String tratamiento, String estudios, String cuidados) {
		PacHistoriaClinica hc = new PacHistoriaClinica();
		hc.setPacCabeceraHc(cabecera);
		hc.setFechaAtencion(fechaAtencion);
		hc.setHoraAtencion(horaAtencion);
		hc.setMedMedico(medico);
		hc.setCama(cama);
		hc.setMotivoConsulta(motivoConsulta);
		hc.setEnfermedadActual(enfermedadActual);
		hc.setDiagnostico(diagnostico);
		hc.setEvolucionMedica(evolucionMedica);
		hc.setPlan(plan);
		hc.setTratamiento(tratamiento);
		hc.setEstudios(estudios);
		hc.setCuidados(cuidados);
		em.persist(hc);
	}
	/**
	 * Obtener cabecera paciente
	 * @param id_paciente
	 * @return
	 */
	public PacCabeceraHc getPacienteCabeceraHc(int id_paciente) {
		String JPQL = "SELECT p FROM PacCabeceraHc p WHERE p.pacPaciente.idPaciente=?1";
		Query query = em.createQuery(JPQL, PacCabeceraHc.class);
		query.setParameter(1, id_paciente);
		List<PacCabeceraHc> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if(numero<1) {
			return null;
		}else {
			return lista.get(0);
		}

	}

	@SuppressWarnings("unchecked")
	public boolean existePacienteCabeceraHc(int id_paciente) {
		// Método que me devuelve el paciente por identificación
		String JPQL = "SELECT p FROM PacCabeceraHc p WHERE p.pacPaciente.idPaciente=?1";
		Query query = em.createQuery(JPQL, PacCabeceraHc.class);
		query.setParameter(1, id_paciente);
		List<PacCabeceraHc> lista;
		lista = query.getResultList();
		int numero = lista.size();
		System.out.println("size lsitaCab: "+numero);
		if (numero > 0)
			return true;
		else
			return false;

	}

	@SuppressWarnings("unchecked")
	public boolean existePacienteFactura(int id_paciente) {
		// Método que me devuelve el paciente por identificación
		String JPQL = "SELECT f FROM FactFactura f WHERE f.pacPaciente.idPaciente=?1";
		Query query = em.createQuery(JPQL, FactFactura.class);
		query.setParameter(1, id_paciente);
		List<FactFactura> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;

	}

}
