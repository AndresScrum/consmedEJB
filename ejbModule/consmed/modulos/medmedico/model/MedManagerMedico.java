package consmed.modulos.medmedico.model;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import consmed.core.model.entities.AuthRol;
import consmed.core.model.entities.AuthUsuario;
import consmed.core.model.entities.MedEspecialidad;
import consmed.core.model.entities.MedMedico;

@Stateless
@LocalBean
public class MedManagerMedico {
	@PersistenceContext(unitName = "consmedDS")
	private EntityManager em;

	public MedManagerMedico() {
	}

	// Econtrar especialidad por id
	public MedEspecialidad findEspecialidadById(int id_especialidad) {
		MedEspecialidad med = em.find(MedEspecialidad.class, id_especialidad);
		return med;

	}

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

	// Encontrar medico por id
	public MedMedico findMedicoById(int id_medico) {
		MedMedico med = em.find(MedMedico.class, id_medico);
		return med;

	}

	// Método que me devuelve la Lista de Especialidades
	public List<MedEspecialidad> findAllMedEspecialidades() {
		Query q = em.createQuery("SELECT m FROM MedEspecialidad m", MedEspecialidad.class);
		@SuppressWarnings("unchecked")
		List<MedEspecialidad> listaEspecialidades = q.getResultList();
		return listaEspecialidades;
	}

	// Método que me devuelve la Lista de Pacientes
	public List<MedMedico> findAllMedMedicos() {
		Query q = em.createQuery("SELECT m FROM MedMedico m", MedMedico.class);
		@SuppressWarnings("unchecked")
		List<MedMedico> listaMedicos = q.getResultList();
		return listaMedicos;
	}

	// Método que me devuelve el medico por identificación

	// Método que me devuelve true/false si existe una especialidad con el mismo
	// nombre
	@SuppressWarnings("unchecked")
	public boolean findMedEspecialidaByNombre(String nombre_esp) {
		System.out.println("10");
		String JPQL = "SELECT m FROM MedEspecialidad m WHERE m.nombreEsp=?1";
		Query query = em.createQuery(JPQL, MedEspecialidad.class);
		query.setParameter(1, nombre_esp);
		List<MedEspecialidad> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;
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

	@SuppressWarnings("unchecked")
	public AuthUsuario findAthUsuarioByCorreo(String correo) {
		String JPQL = "SELECT a FROM AuthUsuario a WHERE a.correoUsua=?1";
		Query query = em.createQuery(JPQL, AuthUsuario.class);
		query.setParameter(1, correo);
		
		List<AuthUsuario> usuarios;
		AuthUsuario usuario = null;
		usuarios =  query.getResultList();
		for (AuthUsuario authUsuario : usuarios) {
			usuario=authUsuario;
		}
		return usuario;
	}

	// Método que me devuelve el medico por identificacion
	@SuppressWarnings("unchecked")
	public boolean findMedMedicoByIdentificacion(String identificacion) {
		String JPQL = "SELECT m FROM MedMedico m WHERE m.identificacionMed=?1";
		Query query = em.createQuery(JPQL, MedMedico.class);
		query.setParameter(1, identificacion);
		List<MedMedico> lista;
		lista = query.getResultList();
		int numero = lista.size();
		if (numero > 0)
			return true;
		else
			return false;
	}

	// Método que me devuelve el medico por correo
	@SuppressWarnings("unchecked")
	public boolean findMedMedicoCorreo(String correo) {
		String JPQL = "SELECT m FROM MedMedico m WHERE m.correoMed=?1";
		Query query = em.createQuery(JPQL, MedMedico.class);
		query.setParameter(1, correo);
		List<MedMedico> lista;
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

	// Método que ingresa una especialidad
	public void ingresarMedEspecialidad(String nombre_esp, boolean activo_especialidad) throws Exception {
		boolean existeEspecialidad = findMedEspecialidaByNombre(nombre_esp);
		if (existeEspecialidad) {
			throw new Exception("Ya existe una especialidad con el nombre " + nombre_esp + " !");

		}
		MedEspecialidad especialidad = new MedEspecialidad();
		especialidad.setNombreEsp(nombre_esp);
		especialidad.setActivoEsp(activo_especialidad);
		em.persist(especialidad);
	}

	// Método que ingresa un médico
	public void ingresarMedMedico(String nombres_med, String apellidos_med, String identificacion_med, String correomed,
			String contrasenia, String telefono_med, boolean activo_med, int id_especialidad_fk, String foto_med,
			int id_rol) throws Exception {

		ingresarAuthUsuario(correomed, contrasenia, id_rol);
		boolean repetidoCorreo, repetidoIdentificacion;

		repetidoCorreo = findMedMedicoCorreo(correomed);

		repetidoIdentificacion = findMedMedicoByIdentificacion(identificacion_med);
		AuthUsuario usuario = findAthUsuarioByCorreo(correomed);

		if (usuario == null) {
			throw new Exception("Error al seleccionar el usuario ");
		}
		if (repetidoCorreo) {
			em.remove(usuario);
			throw new Exception("Ya existe un medico registrado con  el correo: " + correomed);
		}
		if (repetidoIdentificacion) {
			em.remove(usuario);
			throw new Exception("Ya existe un medico registrado con  esta identificación: " + identificacion_med);
		}

		MedEspecialidad especialidad = findEspecialidadById(id_especialidad_fk);
		if (especialidad == null) {
			em.remove(usuario);
			throw new Exception("Error al selecionar la especialidad");
		}

		MedMedico medico = new MedMedico();
		medico.setNombresMed(nombres_med);
		medico.setApellidosMed(apellidos_med);
		medico.setIdentificacionMed(identificacion_med);
		medico.setCorreoMed(correomed);
		medico.setTelefonoMed(telefono_med);
		medico.setAuthUsuario(usuario);
		medico.setMedEspecialidad(especialidad);
		medico.setActivoMed(activo_med);
		;
		medico.setFotoMed(foto_med);

		em.persist(medico);
	}

	public void editarEspecialidad(MedEspecialidad especialidadCargada) throws Exception {
		MedEspecialidad especialidadActual = findEspecialidadById(especialidadCargada.getIdEspecialidad());
		if (especialidadActual == null) {
			throw new Exception("Error al editar la especialidad");
		}
		if (!especialidadActual.getNombreEsp().equals(especialidadCargada.getNombreEsp())) {
			System.out.println("especialidadActual.getNombreEsp() " + especialidadActual.getNombreEsp());
			System.out.println("especialidadCargada.getNombreEsp(): " + especialidadCargada.getNombreEsp());
			boolean existe = findMedEspecialidaByNombre(especialidadCargada.getNombreEsp());
			if (existe) {
				throw new Exception(
						"Ya existe la especialidad " + especialidadCargada.getNombreEsp() + " verifique el nombre!");
			}
		}

		especialidadActual.setActivoEsp(especialidadCargada.getActivoEsp());
		especialidadActual.setNombreEsp(especialidadCargada.getNombreEsp());
		em.merge(especialidadActual);
	}

//método que me edita un médico
	public void editarMedico(MedMedico medicoCargada,int id_especialidad_fk,int id_rol_fk) throws Exception {
	System.out.println(""+id_especialidad_fk);
	System.out.println(""+id_rol_fk);
	System.out.println(""+medicoCargada.getApellidosMed());
	System.out.println(""+medicoCargada.getCorreoMed());
	System.out.println(""+medicoCargada.getFotoMed());
	System.out.println(""+medicoCargada.getIdentificacionMed());

	System.out.println(""+medicoCargada.getTelefonoMed());

	System.out.println(""+medicoCargada.getIdMedico());

	System.out.println(""+medicoCargada.getActivoMed());

		if (id_rol_fk==0) {
		throw new Exception("Error al cargar el rol");
	}
	if (id_especialidad_fk==0) {
		throw new Exception("Error al cargar la especialidad");
	}
	System.out.println("a");		
		MedMedico medicoActual = findMedicoById(medicoCargada.getIdMedico());
		System.out.println("b");
		if (medicoActual == null) {
			throw new Exception("Error al editar el médico");
		}
		System.out.println("d");
		if (!medicoActual.getIdentificacionMed().equals(medicoCargada.getIdentificacionMed())) {
			boolean existe = findMedMedicoByIdentificacion(medicoCargada.getIdentificacionMed());
			if (existe) {
				throw new Exception("La identificación ya está registrada con otro médico");
			}
		}
		System.out.println("e");
		if (!medicoActual.getCorreoMed().equals(medicoCargada.getCorreoMed())) {
			boolean existe = findMedMedicoCorreo(medicoCargada.getCorreoMed());
			if (existe) {
				throw new Exception("El correo ya está registrado con otro médico");
			}
		}
		System.out.println("f");
		medicoActual.setActivoMed(medicoCargada.getActivoMed());
		medicoActual.setApellidosMed((medicoCargada.getApellidosMed()));
		medicoActual.setCorreoMed(medicoCargada.getCorreoMed());
		medicoActual.setNombresMed((medicoCargada.getNombresMed()));
		medicoActual.setFotoMed(medicoCargada.getFotoMed());
		medicoActual.setTelefonoMed(medicoCargada.getTelefonoMed());
		medicoActual.setIdMedico(medicoCargada.getIdMedico());
		medicoActual.setIdentificacionMed(medicoCargada.getIdentificacionMed());
		System.out.println("g");
		AuthRol rol=new AuthRol();
		rol=findRolById(id_rol_fk);
		System.out.println("h");
		AuthUsuario usuario=new AuthUsuario();
		System.out.println("h1");
		usuario=findUsuarioById(medicoCargada.getAuthUsuario().getIdUsuario());
		System.out.println("PASA----"+usuario.getCorreoUsua());
		System.out.println("h2");
		usuario.setCorreoUsua(medicoCargada.getCorreoMed());
		System.out.println("h3");
		usuario.setAuthRol(rol);
		System.out.println("i");
		MedEspecialidad especialidad=new MedEspecialidad();
		especialidad=findEspecialidadById(id_especialidad_fk);
		medicoActual.setAuthUsuario(usuario);
		medicoActual.setMedEspecialidad(especialidad);
		em.merge(medicoActual);

	}

	public void eliminarMedEspecialidad(int id_especialidad) throws Exception {
		MedEspecialidad esp;
		esp = findEspecialidadById(id_especialidad);
		if (esp == null)
			throw new Exception("No se puede Eliminar la especialidad revise si existe");
		boolean existeRelacion = findFkMedEspecialidad(id_especialidad);
		if (existeRelacion)
			throw new Exception("La especialidad no se puede eliminar está siendo utilizado en médicos");
		else
			em.remove(esp);
	}

	@SuppressWarnings("unchecked")
	public boolean findFkMedEspecialidad(int id_especialidad) throws Exception {
		if (id_especialidad == 0)
			throw new Exception("La especialidad no se ha podido eliminar");
		String JPQL = "SELECT m FROM MedMedico m  where m.medEspecialidad.idEspecialidad='" + id_especialidad + "'";
		Query query = em.createQuery(JPQL, MedMedico.class);
		List<MedMedico> listaMedicos = query.getResultList();
		if (listaMedicos.size() > 0)
			return true;
		else
			return false;
	}

}
