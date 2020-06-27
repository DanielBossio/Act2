/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Client;

/**
 *
 * @author Win 8.1
 */
public class ConexionCliente {
    private String ipServ;
    private int puertoServ;
    private Client cliRMI;
    private CallHandler manejadorRMI;//Conoce los objetos locales exportados y las instancias remotas
    private Object objetoRemoto;//El objeto que se va a recibir y a ser usado

    public ConexionCliente() {
        this.ipServ = "127.0.0.2";
        this.puertoServ = 5003;
    }
    
    public ConexionCliente(String ipServ, int puertoServ) {
        this.ipServ = ipServ;
        this.puertoServ = puertoServ;
    }
    
    public void conectar(){
        try{
            this.manejadorRMI = new CallHandler();
            //Establecer la conexión entre el cliente y el servidor
            this.cliRMI = new Client(ipServ, puertoServ, manejadorRMI);
            //Obtener el objeto. Se debe conocer la clase del objeto y el objeto en sí
            this.objetoRemoto = cliRMI.getGlobal(Object.class);
        } catch (IOException ex) {
            //No se pudo acceder al servidor
            Logger.getLogger(ConexionCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desconectar(){
        try {
            cliRMI.close();
        } catch (IOException ex) {
            //No se pudo desconectar
            Logger.getLogger(ConexionCliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            cliRMI = null;
        }
        
    }
    
    public Object getObjeto(){
        if (this.objetoRemoto != null) return objetoRemoto;
        //Si no hay objeto no se estableció conexión con el servidor
        return null;
    }
}
