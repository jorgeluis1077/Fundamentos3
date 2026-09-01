
package nombrearraylist;

import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class NombreArrayList {

    public static void main(String[] args) {
        Path filePath = Paths.get("C:\\Users\\jorge\\Documents\\Fundamentos3\\Fundamentos3\\nombreArrayList\\listado.txt");
        ArrayList<String> nameList = readNamesFromFile(filePath.toString());
        printNames (nameList);
    }
    public static ArrayList<String>readNamesFromFile (String filePath){
        ArrayList<String> names = new ArrayList<>();
        try{
            Stream<String> lines = Files.lines(Paths.get(filePath));
            for(String line :(Iterable<String>) lines::iterator){
            names.add(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return names;
    }

    public static void printNames(ArrayList<String> nameList) {
        for(int i = 0; i < nameList.size(); i++){
            System.out.println(nameList.get(i));
        }
    }
    
}
