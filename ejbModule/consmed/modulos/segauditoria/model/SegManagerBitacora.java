package consmed.modulos.segauditoria.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import consmed.core.model.entities.SegBitacora;


@Stateless
@LocalBean
public class SegManagerBitacora {
	@PersistenceContext(unitName="consmedDS")
	private EntityManager em;
    public SegManagerBitacora() {
    }
	// Econtrar rol por id 
	public SegBitacora findBitacoraById(int id_rol) {
		SegBitacora bitacora= em.find(SegBitacora.class, id_rol);
		return bitacora;

	}
}
