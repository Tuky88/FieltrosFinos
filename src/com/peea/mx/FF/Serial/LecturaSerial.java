import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.util.Enumeration;
  public class LecturaSerial  {
	  //inicializamos y decalramos variables
	CommPortIdentifier portId;
	Enumeration puertos;
	SerialPort serialport;
	static InputStream entrada = null;
	Thread t;
	//creamos un constructor para realizar la conexion del puerto
  	public LecturaSerial(String puerto,int baudrate) {
        super();
        puertos=CommPortIdentifier.getPortIdentifiers();
        t = new Thread(new LeerSerial());
        while (puertos.hasMoreElements()) { //para recorrer el numero de los puertos, y especificar con cual quiero trabajar 
        	//hasmorelements mientras tenga mas eleementos
        	portId = (CommPortIdentifier) puertos.nextElement(); //next elemento recorre uno por uno
        	System.out.println(portId.getName()); //puertos disponbibles
        	if (portId.getName().equalsIgnoreCase(puerto)) {
        		try {
        		serialport= (SerialPort)portId.open("LecturaSerial", 100);//tiempo en ms
                        serialport.setSerialPortParams(baudrate, 8, 1, 0);
                        serialport.setDTR(true);
                            System.out.println(serialport.getBaudRate()+"//" +serialport.getDataBits()+"//");
    				entrada = serialport.getInputStream();//esta variable del tipo InputStream obtiene el dato serial
   				t.start();
                                System.out.println("dfsdfsdf");// inciamos el hilo para realizar nuestra accion de imprimir el dato serial
     		 
			} catch (Exception e) {
 			} } }
  }
    //con este metodo del tipo thread relaizamos 

  	public static class LeerSerial implements Runnable {
	   int aux=1;
       public void run () {
           String valor="",valorsito="/";
           int bandera=0;
           System.out.println("Lectura:");
    	   while(true){
    		  try {
                      valorsito=valor;
				aux = entrada.read(); // aqui estamos obteniendo nuestro dato serial
				Thread.sleep(5);
                                //System.out.println(aux);
 				if (aux>0) {
                                    
                                    //System.out.print();//imprimimos el dato serial
                                    //System.out.print(Integer.decode(Integer.toHexString(aux)));
                                    //System.out.print((char)(aux));
                                    valor+=(char)(aux);
                                    //System.out.println(valor +"//"+valorsito);
                                    
				}else
                                {
                                    System.out.println("termino la lectura.");
                                }
			} catch (Exception e) {
 			} 
           
           System.out.println(valor+" "+valorsito);
           }
       }
  }public static void main(String[] args) {
	   new LecturaSerial("COM4",4800);
 		 }}