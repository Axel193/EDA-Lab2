import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Client {
    public static void main(String[] args)
            throws IOException
    {
        System.out.println("Cargando datos...");
        Platform platform = new Platform();
        String file = "YoutubeDTSV2.csv";
        platform.insertFromFile(file);
        Platform.Video actualVideo = platform.begin();
        actualVideo.play();

        boolean exit = false;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in))){
            System.out.println("Ingrese una opción");
            while (!exit){
                System.out.println("1: para siguiente video");
                System.out.println("2: para buscar una canción por el ID");
                System.out.println("3: para invertir el orden de la lista");
                System.out.println("4: para imprimir la lista restante");
                System.out.println("5: para salir");

                System.out.println();
                switch (br.readLine()){
                    case "1":
                        if(actualVideo.next != null){
                            System.out.println("Siguiente video...");
                            actualVideo = actualVideo.next;
                            actualVideo.play();
                        }
                        else{
                            System.out.println("No quedan más videos");
                        }
                        break;
                    case "2":
                        System.out.println("Ingrese id del vídeo");
                        Platform.Video targetVideo = platform.search(platform.begin(),br.readLine());
                        if(targetVideo != null){
                            actualVideo = targetVideo;
                            System.out.println("Vídeo encontrado");
                            actualVideo.play();
                        } else {
                            System.out.println("Vídeo no encontrado");
                        }
                        break;
                    case "3":
                        System.out.println("Invirtiendo lista");
                        platform.reverse(platform.begin());
                        actualVideo = platform.begin();
                        break;
                    case "4":
                        platform.iterativePrint(actualVideo);
                        break;
                    case "5":
                        exit = true;
                        break;
                    default:
                        System.out.println("Ingrese opción válida");
                }
            }
        } catch (IOException ex){
            System.out.println(ex);
        }

    }
}
