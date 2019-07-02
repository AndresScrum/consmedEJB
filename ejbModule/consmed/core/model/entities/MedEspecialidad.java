package consmed.core.model.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the med_especialidad database table.
 * 
 */
@Entity
@Table(name="med_especialidad")
@NamedQuery(name="MedEspecialidad.findAll", query="SELECT m FROM MedEspecialidad m")
public class MedEspecialidad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MED_ESPECIALIDAD_IDESPECIALIDAD_GENERATOR", sequenceName="SEQ_MED_ESPECIALIDAD")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MED_ESPECIALIDAD_IDESPECIALIDAD_GENERATOR")
	@Column(name="id_especialidad")
	private Integer idEspecialidad;

	@Column(name="activo_esp")
	private Boolean activoEsp;

	@Column(name="nombre_esp")
	private String nombreEsp;

	//bi-directional many-to-one association to MedMedico
	@OneToMany(mappedBy="medEspecialidad")
	private List<MedMedico> medMedicos;

	public MedEspecialidad() {
	}

	public Integer getIdEspecialidad() {
		return this.idEspecialidad;
	}

	public void setIdEspecialidad(Integer idEspecialidad) {
		this.idEspecialidad = idEspecialidad;
	}

	public Boolean getActivoEsp() {
		return this.activoEsp;
	}

	public void setActivoEsp(Boolean activoEsp) {
		this.activoEsp = activoEsp;
	}

	public String getNombreEsp() {
		return this.nombreEsp;
	}

	public void setNombreEsp(String nombreEsp) {
		this.nombreEsp = nombreEsp;
	}

	public List<MedMedico> getMedMedicos() {
		return this.medMedicos;
	}

	public void setMedMedicos(List<MedMedico> medMedicos) {
		this.medMedicos = medMedicos;
	}

	public MedMedico addMedMedico(MedMedico medMedico) {
		getMedMedicos().add(medMedico);
		medMedico.setMedEspecialidad(this);

		return medMedico;
	}

	public MedMedico removeMedMedico(MedMedico medMedico) {
		getMedMedicos().remove(medMedico);
		medMedico.setMedEspecialidad(null);

		return medMedico;
	}

}