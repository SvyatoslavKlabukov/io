import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.Writer;
import java.io.Reader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //String path = "C:\\Users\\svyatoslav\\IdeaProjects\\io\\test.txt";

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите команду: ");
        String commandLine = scanner.nextLine();
        while (!commandLine.equals("q")) {
            String[] arr = parseCommandLine(commandLine);

            String path = arr[0];
            String command = arr[1];
            String text = arr[2];
            switch (command) {
                case ("-create") -> createFile(path);
                case ("-delete") -> deleteFile(path);
                case ("-read") -> readFile(path);
                case ("-write") -> writeToFile(path, text);
                default -> System.out.println("Command not found");
            }
            System.out.println("Введите команду: ");
            commandLine = scanner.nextLine();
        }
    }

    public static void createFile(String path){
        try {
            File file = new File(path);
            boolean flag = file.createNewFile();
            if (flag){
                System.out.println("Файл "+file.toString()+" создан");
            }else {
                System.out.println("Файл "+file.toString()+" не создан");
            }
        } catch (IOException e) {
            System.out.println("Некорректный путь");
        }
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        boolean flag = file.delete();
        if (flag) {
            System.out.println("Файл " + file.toString() + " удален");
        } else {
            System.out.println("Файл " + file.toString() + " не удален");
        }
    }

    public static void readFile(String path){
        try (
                InputStream in = new FileInputStream(path);
                Reader readerFile = new InputStreamReader(in);
        ) {
            int c;
            while ((c = readerFile.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден при попытке чтения");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static void writeToFile(String path, String text){
        try (
                OutputStream out = new FileOutputStream(path);
                Writer writerFile = new OutputStreamWriter(out);
        ) {
            writerFile.write(text);
            System.out.println("Запись в файл " + path + " проведена");
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден при попытке записи");
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public static String [] completeArray(String commandLine, String command, int index){
        String [] arr = new String[3];
        arr[0]= commandLine.substring(0, index).trim();//путь к файлу
        arr[1]= command.trim();//команда
        try {
            arr[2] = commandLine.substring(index + " -write".length());//параметры
        }catch (IndexOutOfBoundsException e){
            arr[2]="";
        }
        return arr;
    }

    public static String [] parseCommandLine(String commandLine){
        String [] arr = new String[3];
        arr[0] = "";
        arr[1] = "";
        arr[2] = "";

        String create = " -create";
        int index = commandLine.indexOf(create);
        if (index != -1){
            arr = completeArray(commandLine, create, index);
            return arr;
        }

        String delete = " -delete";
        index = commandLine.indexOf(delete);
        if (index != -1){
            arr = completeArray(commandLine, delete, index);
            return arr;
        }

        String read = " -read";
        index = commandLine.indexOf(read);
        if (index != -1){
            arr = completeArray(commandLine, read, index);
            return arr;
        }

        String write = " -write";
        index = commandLine.indexOf(write);
        if (index != -1){
            arr = completeArray(commandLine, write, index);
            return arr;
        }

        return arr;
    }
}