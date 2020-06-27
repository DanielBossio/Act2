/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.lipermi.exception.LipeRMIException;
import net.sf.lipermi.handler.CallHandler;
import net.sf.lipermi.net.Server;

/**
 *
 * @author Win 8.1
 */
public class ConexionServidor {
    private String ipServ, nombreServ;
    private int puertoServ;
    private Server servRMI;
    private CallHandler manejadorRMI;//Conoce los objetos locales exportados y las instancias remotas
    private Object objetoRemoto;//El objeto que se va a enviar

    public ConexionServidor() {
        try{
            //Obtener host local
            ipServ = InetAddress.getLocalHost().getHostAddress();
            nombreServ = InetAddress.getLocalHost().getHostName();
            this.puertoServ = 5003;
        } catch (UnknownHostException ex) {
            System.out.println("IP inaccesible");
        }
    }
    
    public ConexionServidor(int puertoServ) {
        try{
            ipServ = InetAddress.getLocalHost().getHostAddress();
            nombreServ = InetAddress.getLocalHost().getHostName();
            this.puertoServ = puertoServ;
        } catch (UnknownHostException ex) {
            System.out.println("IP inaccesible");
        }
    }
    
    public void conectar(){
        try{
            this.objetoRemoto = new Object();
            this.manejadorRMI = new CallHandler();
            this.servRMI = new Server();
           //Exportar el objeto. Se debe conocer la clase del objeto y el objeto en sí 
            manejadorRMI.registerGlobal(Object.class, objetoRemoto);
            //Conectar
            servRMI.bind(puertoServ, manejadorRMI);
        } catch (LipeRMIException ex) {
            //Error al exportar el objeto
            Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //Error en la conexión
            Logger.getLogger(ConexionServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void desconectar(){
        this.servRMI.close();
    }
}
