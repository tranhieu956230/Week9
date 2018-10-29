/**
 * @author Trần Đức Hiếu
 * @version 1.0.0
 * @date 29/10/2018
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static ArrayList<String> result = new ArrayList<>();

    public static void main(String[] args) {
        try {
            final String data = readContentFromFile("in.txt");
            writeContentToFile("out.txt", data);
            findFileByName("./", "\\.txt");

            System.out.println();
            System.out.println("Search result: ");
            for(String file : result) {
                System.out.println(file);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * @param path Đường dẫn đến file
     * @return Nội dung file
     * @throws FileNotFoundException
     */
    public static String readContentFromFile(String path) throws FileNotFoundException {
        String result = "";
        File file = new File(path);
        Scanner input = new Scanner(file);
        while (input.hasNext()) {
            result += input.nextLine() + "\n";
        }
        return result;
    }

    /**
     * Ghi data vào cuối file
     * @param path Đưuòng dẫn đến file
     * @param data Nội dung cần ghi vào file
     */
    public static void writeContentToFile(String path, String data) {
        File file = new File(path);
        FileWriter fr = null;
        try {
            fr = new FileWriter(file, true);
            fr.write("\n");
            fr.write("--------New Content-------\n");
            fr.write(data);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Tìm file recursive trong thư mục
     * @param folderPath thư mục cần tìm kiếm
     * @param pattern mẫu regex cần tìm kiếm
     */
    public static void findFileByName(String folderPath, String pattern) {
        File file = new File(folderPath);
        if (file.isDirectory()) {
            System.out.println("Search directory ... " + file.getAbsoluteFile());

            if (file.canRead()) {
                for (File temp : file.listFiles()) {
                    if (temp.isDirectory()) {
                        findFileByName(temp.toString(), pattern);
                    } else {
                        Pattern r = Pattern.compile(pattern);
                        Matcher m = r.matcher(temp.getName());
                        if (m.find()) {
                            result.add(temp.getAbsoluteFile().toString());
                        }
                    }
                }
            } else {
                System.out.println(file.getAbsoluteFile() + "Permission denied");
            }
        }
    }
}
