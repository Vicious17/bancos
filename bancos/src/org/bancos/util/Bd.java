/*
 *  Copyright (C) 2011 - 2016  DVCONSULTORES

    Este programa es software libre: usted puede redistribuirlo y/o modificarlo 
    bajo los terminos de la Licencia Pública General GNU publicada 
    por la Fundacion para el Software Libre, ya sea la version 3 
    de la Licencia, o (a su eleccion) cualquier version posterior.

    Este programa se distribuye con la esperanza de que sea útil, pero 
    SIN GARANTiA ALGUNA; ni siquiera la garantia implicita 
    MERCANTIL o de APTITUD PARA UN PROPoSITO DETERMINADO. 
    Consulte los detalles de la Licencia Pública General GNU para obtener 
    una informacion mas detallada. 

    Deberia haber recibido una copia de la Licencia Pública General GNU 
    junto a este programa. 
    En caso contrario, consulte <http://www.gnu.org/licenses/>.
 */

package org.bancos.util;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;



/**
 * Crea Usuarios y clave de Base de Datos para todos los programas
 */
@ManagedBean
@RequestScoped
public class Bd  {



	// Constructor	
	public Bd()  {
    }
	

	
    //Declaracion de variables para manejo de mensajes multi idioma y pais
    private String lenguaje = "es";
    private String pais = "VEN";
    private Locale  localidad = new Locale(lenguaje, pais);
    ResourceBundle recursos =  ResourceBundle.getBundle("org.bancos.util.MessagesBundle",localidad);
    private String Message = "";
    String productName; //Manejador de base de datos
    @SuppressWarnings("unused")
	private Locale OsLang = Locale.getDefault();

    
      /**
     * Recursos de lenguaje. Archivos Properties
     **/
    public String getMessage(String mensaje) {
        Message = recursos.getString(mensaje);
        return Message;
    }
    
 
	//Declaracion de variables y manejo de mensajes
    String userLang = System.getProperty("user.language");//Identifica el lenguaje el OS
    String userCountry = System.getProperty("user.country");//Identifica el pais el OS
    Locale locale = new Locale(userLang, userCountry);//Identifica idioma y pais, por defecto le colocamos ven
    java.util.Date fecact = new java.util.Date();
    //Para trabajar con quartz properties, por alguna razón no funciona con external context
    static FacesContext ctx = FacesContext.getCurrentInstance();
    protected static final String JNDI = ctx.getExternalContext().getInitParameter("JNDI_BD"); //"jdbc/orabiz"; //Nombre del JNDI
    protected static final String JNDIDESA = ctx.getExternalContext().getInitParameter("JNDI_DESA"); //"jdbc/orabiz"; //Nombre del JNDI
    protected static final String JNDISB = ctx.getExternalContext().getInitParameter("JNDI_BDSB"); //"jdbc/sybase"; //Nombre del JNDI
    protected static final String JNDI_EXTERNAL = ctx.getExternalContext().getInitParameter("JNDI_BD_EXTERNAL"); //"jdbc/orabiz"; //Nombre del JNDI
	static final String JNDIMAIL = ctx.getExternalContext().getInitParameter("JNDI_MAIL"); //"jdbc/orabiz"; //Nombre del JNDI
    static final String THREADNUMBER = ctx.getExternalContext().getInitParameter("THREAD_NUMBER");
    static final String LOGOUT_URL = ctx.getExternalContext().getInitParameter("LOGOUT_URL");//Url logout
    static final String BIRT_VIEWER_WORKING_FOLDER = ctx.getExternalContext().getInitParameter("BIRT_VIEWER_WORKING_FOLDER");//Url logout
    static final String BIRT_VIEWER_LOG_DIR = ctx.getExternalContext().getInitParameter("BIRT_VIEWER_LOG_DIR");//Url logout
    static final String BANCOS_BD_LANG = ctx.getExternalContext().getInitParameter("BANCOS_BD_LANG");//Localización
    static final String CHECK_UPDATE = ctx.getExternalContext().getInitParameter("CHECK_UPDATE");//Chequea actualizaciones
    java.text.SimpleDateFormat sdfecha = new java.text.SimpleDateFormat("dd/MM/yyyy", locale );
    java.text.SimpleDateFormat sdfecha2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
    java.text.SimpleDateFormat sdfecha3 = new java.text.SimpleDateFormat("dd/MM/yyyy");
    java.text.SimpleDateFormat sdfecha4 = new java.text.SimpleDateFormat("YYYYMMDD");
    String fecha = sdfecha.format(fecact); //Fecha formateada para insertar en tablas
    
    //Variables para uso internos de servlet
    private static final String PARAMINFOA = "dirUploadFiles"; //Uploads
    private static final String PARAMINFOB = "dirUploadRep"; //Uploads
    
   
    
	
    /**
	 * @return the openbizviewBdLang
	 */
	public static String getBancosBdLang() {
		return BANCOS_BD_LANG;
	}
    /**
	 * @return the paraminfob
	 */
	public static String getPARAMINFOB() {
		return PARAMINFOB;
	}
/**
	 * @return the paraminfoa
	 */
	public static String getPARAMINFOA() {
		return PARAMINFOA;
	}
	
	/**
	 * @return the openbizviewLocale
	 */
	public static String getBancosLocale() {
		return BANCOS_BD_LANG;
	}


    /**
     * Obtiene la fecha del dia formateada, ya que se va a utilizar en todas la tablas
     * se crea el metodo.
     * @throws IOException 
     */
    public String getFecha(){
    	java.text.SimpleDateFormat sdfecha_es = new java.text.SimpleDateFormat("dd/MM/yyyy", locale );
    	java.text.SimpleDateFormat sdfecha_en = new java.text.SimpleDateFormat("dd/MMM/yyyy", locale );
    	if(BANCOS_BD_LANG=="es"){
    		fecha = sdfecha_es.format(fecact);
    	} else {
    		fecha = sdfecha_en.format(fecact);
    	}
        return fecha;
    }
    
    /**
     * Obtiene la fecha del dia formateada con hora y minutos, ya que se va a utilizar en todas la tablas
     * se crea el metodo.
     * @throws IOException 
     */
    public String getFechaHora(){
    	java.text.SimpleDateFormat sdfecha_es = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm", locale );
    	java.text.SimpleDateFormat sdfecha_en = new java.text.SimpleDateFormat("dd/MMM/yyyy HH:mm", locale );
    	if(BANCOS_BD_LANG.equals("es")){
    		fecha = sdfecha_es.format(fecact);
    	} else {
    		fecha = sdfecha_en.format(fecact);
    	}
        return fecha;
    }
      

	
	/**
     * Formatea la fecha leyendo el formato desde xml de configuración
     * @param La fecha a cargar 
     * @throws IOException 
     */
    public String getFechaFormat(Date pfecha) throws IOException {
    	FacesContext ctx = FacesContext.getCurrentInstance();
    	String ff =
                ctx.getExternalContext().getInitParameter("FORMAT_DATE");
    	java.text.SimpleDateFormat sdfecha = new java.text.SimpleDateFormat(ff, locale );    	 
        return sdfecha.format(pfecha);
    }
    
    
  	/*
     * Java method to sort Map in Java by value e.g. HashMap or Hashtable
     * throw NullPointerException if Map contains null values
     * It also sort values even if they are duplicates
     */
    @SuppressWarnings("rawtypes")
	public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValues(Map<K,V> map){
        List<Map.Entry<K,V>> entries = new LinkedList<Map.Entry<K,V>>(map.entrySet());
     
        Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {

            @SuppressWarnings("unchecked")
			@Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
     
        //LinkedHashMap will keep the keys in the order they are inserted
        //which is currently sorted on natural ordering
        Map<K,V> sortedMap = new LinkedHashMap<K,V>();
     
        for(Map.Entry<K,V> entry: entries){
            sortedMap.put(entry.getKey(), entry.getValue());
        }
     
        return sortedMap;
    }

 


	 /**
		 * Setea categoria1
		 * @param next
		 */
	public void setAccCat1(String cat1){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cat1", cat1);
	}
	
	 /**
	 * Setea categoria1
	 * @param next
	 */
	public void setAccCat2(String cat2){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cat2", cat2);
	}
	
	 /**
		 * Setea categoria1
		 * @param next
		 */
	public void setAccCat3(String cat3){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cat3", cat3);
	}
	
	/**
	 * Setea Rol de usuario
	 * @param next
	 */
	public void setRol(String segrol){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("segrol", segrol);
	}
	
	/**
	 * Setea codgrup
	 * @param next
	 */
	public void setCodgrup(String codgrup){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codgrup", codgrup);
	}
	

	
	/**
	 * Setea list
	 * @param next
	 */
	public void setIdgrupo(String idgrupo){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("idgrupo", idgrupo);
	}
		
	/**
	 * Setea usuario lista
	 * @param next
	 */
	public void setBcoduser(String bcoduser){
		//System.out.println("bcoduser: " + bcoduser);
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bcoduser", bcoduser);
	}
	
	/**
	 * Setea una fecha para busqueda
	 * @param next
	 */
	public void setFecha(String fecha){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fecha", fecha);
	}
	
	/**
	 * Setea las opciones de envio, solo para usar en mailprogramación
	 * @param next
	 */
	public void setOpcTareas(String opc){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("opc", opc);
	   if(opc.equals("2")){
		   RequestContext.getCurrentInstance().execute("PF('dlg3').show()");
	   }
	}
	
	//Para opciones de mailtarea
	/**
	 * Setea una reporte para busqueda
	 * @param next
	 */
	public void setMailReporte(String mailreporte){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mailreporte", mailreporte);
	}
	
	/**
	 * Setea una reporte para busqueda
	 * @param next
	 */
	public void setMailFrecuencia(String mailfrecuencia){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mailfrecuencia", mailfrecuencia);
	}
	
	
	/**
	 * Setea una reporte para busqueda
	 * @param next
	 */
	public void setMailgrupo(String mailgrupo){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mailgrupo", mailgrupo);
	}
	
	/**
	 * Setea usuario para sgc lista
	 * @param next
	 */
	public void setCoduser(String coduser){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("coduser", coduser);
	}
	
	
	/**
	 * Setea codven lista
	 * @param next
	 */
	public void setCodven(String codven){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codven", codven);
	}

	
	/**
	 * Setea area lista
	 * @param next
	 */
	public void setCodigo(String codigo){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codigo", codigo);
	}
		
	/**
	 * Setea anocal para busqueda
	 * @param next
	 */
	public void setAnocal(String anocal){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("anocal", anocal);
	}
	
	/**
	 * Setea tipo de valor de la tolerancia sup para busqueda
	 * @param next
	 */
	public void setMescal(String mescal){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mescal", mescal);
	}
	
	/**
	 * Setea tipo de valor de la tolerancia sup para busqueda
	 * @param next
	 */
	public void setSemcal(String semcal){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("semcal", semcal);
	}
	
	/**
	 * Setea tipo de valor de la tolerancia sup para busqueda
	 * @param next
	 */
	public void setAccion(String accion){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accion", accion);
	}

	//Para opciones de mailtarea
	/**
	 * Setea una reporte para busqueda
	 * @param next
	 */
	public void setReporte(String reporte){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("reporte", reporte);
	}
	
	/**
	 * Setea tipo de valor de la meta para busqueda
	 * @param next
	 */
	public void setCodcia(String codcia){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codcia", codcia);
	}
		
	/**
	 * Setea tipo de valor de la meta para busqueda
	 * @param next
	 */
	public void setCodban(String codban){
	   	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("codban", codban);
	}
		
}