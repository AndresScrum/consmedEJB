package consmed.modulos.segauditoria.model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import consmed.core.model.entities.AuthUsuario;
import consmed.core.model.entities.SegBitacora;

/**
 * Session Bean implementation class SegManagerAuditoria
 */
@Stateless
@LocalBean
public class SegManagerAuditoria {

    @PersistenceContext(unitName="consmedDS")
    private EntityManager em;
    public SegManagerAuditoria() {
        
    }

    public AuthUsuario findUsuarioBy(int idUsuario) {
    	AuthUsuario usuario=em.find(AuthUsuario.class, idUsuario);
    	return usuario;
    }
    public void ingresarBitacora(int idUsuario, String nombre, String descripcion) {
    	AuthUsuario usuario=findUsuarioBy(idUsuario);
    	String ip="";
    	 InetAddress address=null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(address==null) {
			ip="Error get ip";
		}else {
			ip=address.getHostAddress();
         System.out.println("IP Local :"+address.getHostAddress());
		}
         Date fecha=new Date();
         Timestamp time=new Timestamp(fecha.getTime());
         SegBitacora bitacora=new SegBitacora();
         bitacora.setAuthUsuario(usuario);
         bitacora.setNombre(nombre);
         bitacora.setIp(ip);
         bitacora.setFecha(time);
         bitacora.setDescripcion(descripcion);
         em.persist(bitacora);
    	
    }
}
