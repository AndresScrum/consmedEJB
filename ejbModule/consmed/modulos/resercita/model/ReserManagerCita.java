package consmed.modulos.resercita.model;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import consmed.core.model.entities.MedMedico;
import consmed.core.model.entities.PacPaciente;
import consmed.core.model.entities.ReserCita;

/**
 * Session Bean implementation class ReserManagerCita
 */
@Stateless
@LocalBean
public class ReserManagerCita {
	@PersistenceContext(unitName = "consmedDS")
	private EntityManager em;

	public ReserManagerCita() {

	}

	// Obtiene cita por id
	public ReserCita findCitaById(int id_cita) {
		ReserCita cit = em.find(ReserCita.class, id_cita);
		return cit;
	}

	// Obtiene cita por id de m�dico y fecha
	public List<ReserCita> findCitaByDocFecha(int id_medico, Date fecha) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaString = dateFormat.format(fecha);
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
		System.out.println("date1: " + date1);
		String JPQL = "SELECT c FROM ReserCita c " + "WHERE c.medMedico.idMedico=?1 " + 
		"AND c.fechaReser=?2 AND c.activoReser=true";
		Query q = em.createQuery(JPQL, ReserCita.class);
		q.setParameter(1, id_medico);
		q.setParameter(2, date1);
		@SuppressWarnings("unchecked")
		List<ReserCita> list = q.getResultList();
		return list;
	}

	// Obtiene cita por id de paciente
	public List<ReserCita> findCitaByPaciente(int idPaciente) throws ParseException {
		String JPQL = "SELECT c FROM ReserCita c " + "WHERE c.pacPaciente.idPaciente=?1 AND c.activoReser=true";
		Query q = em.createQuery(JPQL, ReserCita.class);
		q.setParameter(1, idPaciente);
		@SuppressWarnings("unchecked")
		List<ReserCita> list = q.getResultList();
		return list;
	}

	// Obtiene citas no pagadas por id de paciente
	public List<ReserCita> findCitaNoPagadoByPaciente(int idPaciente) throws ParseException {
		Date fecha=new Date();
		System.out.println("finf cita no pagado paciente: "+idPaciente);
		String JPQL = "SELECT c FROM ReserCita c " + "WHERE c.pagoReser=false AND c.pacPaciente.idPaciente=?1"
				+ " AND fechaReser>=?2 AND c.activoReser=true "
				+ "order by fechaReser,horaReser asc";
		Query q = em.createQuery(JPQL, ReserCita.class);
		q.setParameter(1, idPaciente);
		q.setParameter(2, fecha);
		@SuppressWarnings("unchecked")
		List<ReserCita> list = q.getResultList();
		return list;
	}

	// Obtiene cita pagadas por id de paciente
	public List<ReserCita> findCitaPagadoByPaciente(int idPaciente) throws ParseException {
		String JPQL = "SELECT c FROM ReserCita c " + "WHERE c.pagoReser=true AND c.pacPaciente.idPaciente=?1 ";
		Query q = em.createQuery(JPQL, ReserCita.class);
		q.setParameter(1, idPaciente);
		@SuppressWarnings("unchecked")
		List<ReserCita> list = q.getResultList();
		return list;
	}

	public void ingresarReserCita(PacPaciente pacPaciente, MedMedico medMedico, Date fechaReser, Time horaReser,
			String asuntoReser, String sintomaReser) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fechaString = dateFormat.format(fechaReser);
		try {
			fechaReser = new SimpleDateFormat("yyyy-MM-dd").parse(fechaString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("Ingresando cita...");
		ReserCita cita = new ReserCita();
		cita.setPacPaciente(pacPaciente);
		cita.setMedMedico(medMedico);
		cita.setFechaReser(fechaReser);
		cita.setHoraReser(horaReser);
		cita.setAsuntoReser(asuntoReser);
		cita.setSintomaReser(sintomaReser);
		cita.setActivoReser(true);
		cita.setPagoReser(false);
		em.persist(cita);
	}
	
	//Cambia el estado
	public void actualizarEstadoCita(ReserCita cita, boolean estado) {
		cita.setActivoReser(estado);
		System.out.println("Actualiz�");
		em.merge(cita);
	}
	
	public void actualizarCita(ReserCita citaCargado) throws Exception{
		System.out.println("Actualiz�");
		em.merge(citaCargado);
	}

}
