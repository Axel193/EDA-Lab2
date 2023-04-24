import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class Platform {
    Video head;
    Platform(){
        head = null;
    }
    Platform(Video v){
        head = v;
    }

    static class Video {
        String videoID;
        String videoTitle;
        String channelID;
        String channelTitle;
        String publishedAt;
        int viewCount;
        int likeCount;
        int commentCount;

        Video next;
        Video(String videoID, String videoTitle, String channelID, String channelTitle, String publishedAt, int viewCount, int likeCount, int commentCount){
            this.videoID = videoID;
            this.videoTitle = videoTitle;
            this.channelID = channelID;
            this.channelTitle = channelTitle;
            this.publishedAt = publishedAt;
            this.viewCount = viewCount;
            this.likeCount = likeCount;
            this.commentCount = commentCount;
            next = null;
        }

        void play(){
            System.out.println(videoID +" "+videoTitle);
        }
    }

    Video begin(){
        return head;
    }

    long isNumericLong(String s){
        long d = 0;
        try {
            d = Long.parseLong(s);
        }
        catch (NumberFormatException ignored){}
        return d;
    }
    int isNumericInt(String s){
        int i = 0;
        try {
            i = Integer.parseInt(s);
        }
        catch (NumberFormatException ignored){}
        return i;
    }

    Video arrayToVideo(ArrayList<String> array){
        Video v = new Video(array.get(0),
                array.get(1),
                array.get(2),
                array.get(3),
                array.get(4),
                array.get(5),
                array.get(6),
                array.get(7)
        );
        return v;
    }
    void insertFromFile(String file){
        String string;
        boolean first = false;
        try (BufferedReader br = new BufferedReader(new FileReader(file));){
            //Skip first
            br.readLine();
            while((string = br.readLine()) != null){
                boolean inQuotes = false;
                int start = 0;
                ArrayList<String> newLines = new ArrayList<>();
                for (int i = 0; i < string.length(); i++) {
                    if (string.charAt(i) == '\"') {
                        inQuotes = !inQuotes;
                    } else if (string.charAt(i) == ',' && !inQuotes) {
                        newLines.add(string.substring(start, i));
                        start = i +1;
                    }
                }
                newLines.add(string.substring(start));
                Video newVideo = arrayToVideo(newLines);
                insertAtEnd(newVideo);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    Video recursiveLast(Video v){
        if ( v.next != null){
            return recursiveLast(v.next);
        }
        else{
            return v;
        }
    }
    Video iterativeLast(Video v){
        while(v.next != null){
            v = v.next;
        }
        return v;
    }

    void insertAtEnd(Video v){
        Video tail = recursiveLast(v);
        if ( tail == null){
            head = v;
            return;
        }
        tail.next = v;
    }
    void recursivePrint(Video v){
        recursivePrint(v.next);
        if(v == null){
            return;
        }
        v.play();
    }

    void iterativePrint(Video v){
        Video actual = v;
        while (v.next != null){
            while(!(Objects.equals(actual.videoID,v.videoID))){
                actual = actual.next;
            }
            v.play();
            actual = head;
            v = v.next;
        }
    }

    Video search(Video v, String videoID){
        //implemente esta función, puede ser iterativa o recursiva.
        return null;
    }

    void reverse(Video v){
        //implemente esta función, puede ser iterativa o recursiva.
    }



    public static void main(String[] args) {
        //pruebas para la API
        Platform platform = new Platform();
        String file = "YoutubeDTSV2.csv";
        platform.insertFromFile(file);
        platform.reverse(platform.begin());
        platform.iterativePrint(platform.begin());
        System.out.println("search: " + platform.search(platform.begin(),"y83x7MgzWOA"));
    }
}
