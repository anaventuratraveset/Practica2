package dataio;

import gestion.GestionLab;
import laboratorio.Bacteria;
import laboratorio.Experimento;
import laboratorio.Poblacion;
import medio.*;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Gestion de archivos
 * @author Ana Ventura-Traveset
 */

public class FileManager {

    /**
     * Abre archivo y lo carga en memoria
     * Para ello, se lee el archivo y se crea un experimento con la información del archivo
     * @param nombreExperimento
     * @return experimento
     * @throws FileNotFoundException
     */
    public static Experimento abrirArchivo(String nombreExperimento) throws FileNotFoundException {
        File file = new File("./" + nombreExperimento + ".txt");
        Experimento experimento =null;

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String stringInfoTotal="";

        try {
            // lo leo
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String [] todosArgs = bufferedReader.readLine().split("\n");
            String nombreExpFromFile = todosArgs[0];
            experimento = new Experimento(nombreExpFromFile);
            stringInfoTotal+=experimento.toStringInfoExperimentoToFile()+"\n";

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String [] infoPoblacion = line.split(";");

                Poblacion poblacion = new Poblacion();
                //Empiezo con la info de poblaciones
                String nombrePoblacionFromFile = infoPoblacion[0];
                poblacion.setNombrePoblacion(nombrePoblacionFromFile);

                int numBacteriasFromFile = Integer.parseInt(infoPoblacion[1]);
                poblacion.setNumInicialBacterias(numBacteriasFromFile);
                for (int i = 0; i < numBacteriasFromFile; i++) {
                    Bacteria bacteria = new Bacteria();
                    poblacion.setBacteriaNueva(bacteria);
                }

                float temperaturaFromFile = Float.parseFloat(infoPoblacion[2]);
                poblacion.setTemperatura(temperaturaFromFile);

                Luminosidad.luminosidad luminosidadFromFile = Luminosidad.luminosidad.valueOf(infoPoblacion[3]);
                poblacion.setLuminosidad(luminosidadFromFile);

                int numPatronComida = Integer.parseInt(infoPoblacion[4]);
                poblacion.setNumeroPatronComida(numPatronComida);

                // Empiezo con la info de comida
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate fechaInicioFromFile = LocalDate.parse(infoPoblacion[5], dtf);
                poblacion.setFechaInicio(fechaInicioFromFile);
                int cantidadInicialFromFile = Integer.parseInt(infoPoblacion[6]);
                poblacion.setCantidadInicial(cantidadInicialFromFile);
                //Tengo que ver lo de arriba
                LocalDate fechaFinFromFile;

                switch (numPatronComida){
                    case 1:
                        LocalDate fechaPicoFromFile = LocalDate.parse(infoPoblacion[7], dtf);
                        poblacion.setFechaPico(fechaPicoFromFile);
                        int cantidadPicoFromFile = Integer.parseInt(infoPoblacion[8]);
                        poblacion.setCantidadPico(cantidadPicoFromFile);
                        fechaFinFromFile = LocalDate.parse(infoPoblacion[9], dtf);
                        poblacion.setFechaFin(fechaFinFromFile);
                        int cantidadFinalFromFile = Integer.parseInt(infoPoblacion[10]);
                        poblacion.setCantidadFinal(cantidadFinalFromFile);
                        ComidaPico comidaPico = new ComidaPico(cantidadInicialFromFile, fechaInicioFromFile, cantidadPicoFromFile, fechaPicoFromFile, cantidadFinalFromFile, fechaFinFromFile);
                        poblacion.setDosisComidaDiaria(comidaPico.calcularComida());
                        poblacion.setComida(comidaPico);
                        break;

                    case 2:
                        fechaFinFromFile = LocalDate.parse(infoPoblacion[7], dtf);
                        poblacion.setFechaFin(fechaFinFromFile);
                        ComidaCte comidaCte = new ComidaCte(cantidadInicialFromFile, fechaInicioFromFile, fechaFinFromFile);
                        poblacion.setDosisComidaDiaria(comidaCte.calcularComida());
                        poblacion.setComida(comidaCte);
                        break;

                    case 3:
                        fechaFinFromFile = LocalDate.parse(infoPoblacion[7], dtf);
                        poblacion.setFechaFin(fechaFinFromFile);
                        cantidadFinalFromFile = Integer.parseInt(infoPoblacion[8]);
                        poblacion.setCantidadFinal(cantidadFinalFromFile);
                        ComidaIncremento comidaIncremento = new ComidaIncremento(cantidadInicialFromFile, fechaInicioFromFile, fechaFinFromFile, cantidadFinalFromFile);
                        poblacion.setDosisComidaDiaria(comidaIncremento.calcularComida());
                        poblacion.setComida(comidaIncremento);
                        break;

                    case 4:
                        fechaFinFromFile = LocalDate.parse(infoPoblacion[7], dtf);
                        poblacion.setFechaFin(fechaFinFromFile);
                        ComidaIntermitente comidaIntermitente = new ComidaIntermitente(cantidadInicialFromFile, fechaInicioFromFile, fechaFinFromFile);
                        poblacion.setDosisComidaDiaria(comidaIntermitente.calcularComida());
                        poblacion.setComida(comidaIntermitente);
                        break;
                }
                GestionLab.addPoblacion(poblacion,experimento);
                stringInfoTotal+=poblacion.toStringInfoPobFile()+"\n";
            }
            JOptionPane.showMessageDialog(null, "\nFICHERO CARGADO EN MEMORIA\n");
            bufferedReader.close();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"\nERROR FileManager leyendo archivo. \nPuede que no exista ningún archivo con ese nombre.");
        }
        finally {
            if (bufferedReader != null){
                try{
                    bufferedReader.close(); // cuando deja de leer
                }catch(IOException ioe){
                    System.out.println(ioe.getMessage());
                }
            }
            if(inputStreamReader != null){
                try{
                    inputStreamReader.close(); // cuando dejan de entrar datos
                }
                catch(IOException ioException){
                    System.out.println(ioException.getMessage());
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        return experimento;
    }

    /**
     * Guarda/guarda como experimento en archivo
     * Para ello, se crea un archivo con el nombre nombreExperimento.txt y se escribe en él la información del experimento
     * mediante el método toStringInfoExperimentoToFile() de Experimento
     * @param nombreExperimento
     * @param experimento
     * @return comprobacion
     */
    public static boolean guardarArchivo(String nombreExperimento, Experimento experimento) {
        File file1 = new File( "./"+nombreExperimento + ".txt");
        PrintWriter printWriter = null;
        boolean comprobacion=false;
        try {
            printWriter = new PrintWriter(file1);
            String experimentoInfoFile = experimento.getNombreExperimento() ;
            printWriter.println(experimentoInfoFile);//escribe en el fichero primero el nombre del experimento
            for (int i = 0; i < experimento.getPoblacionesList().size(); i++) {
                String infoPoblacionesFile = "";
                infoPoblacionesFile += experimento.getPoblacionesList().get(i).toStringInfoPobFile();
                infoPoblacionesFile += ";"+experimento.getPoblacionesList().get(i).getComida().toStringToFile();
                printWriter.print(infoPoblacionesFile); //escribe en el fichero ahora la info de cada población
                printWriter.println();
            }
            printWriter.close();
            comprobacion=true;
        } catch (IOException e) {
            // Cacheo esta excepción porque el constructor de PrintWriter lanza esa excepción
            e.printStackTrace();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    return comprobacion;
    }
}