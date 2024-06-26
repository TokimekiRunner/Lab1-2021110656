
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main.
 *
 * @author TOKEMEKI Runner
 */

public class Main {
  private static String fileName = "test.txt";
  //    private static String fileName = "test.txt";
  private static String resultsFileName = "testResults.txt";

  //    private static String resultsFileName = "testResults.txt";
  public static void main(String[] args) { //主函数入口
    readFile();
    Cgraph graph = readWordsToGraph();
    testFunc6And2Generate(graph);

    //          TestFunc3_brige(graph);
    // TestFunc4_generate(graph);
    testFunc5Path(graph);

  }


  public static void readFile() {
    File file = new File(fileName);
    BufferedReader reader = null;
    try { //清空之前的文件testResults文件并重新建
      File fileres = new File(fileName.substring(0, fileName.length() - 4) + "Results.txt");
      fileres.delete();
      fileres.createNewFile();
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      while ((tempString = reader.readLine()) != null) {
        //                String s = tempString.replaceAll("[\\p{Punct}]+", " ");  //标点变成空格
        String s = tempString.replaceAll("[\\p{Punct}\\u201C\\u201D]+", " ");  //标点变空格，注意左右引号
        //    System.out.println(s);
        String[] words = s.trim().split("\\s+");   //按空格分割

        for (int i = 0; i < words.length; i++)  { //正则表达式匹配字母并变成小写
          Pattern p = Pattern.compile("a-z||A-Z");
          Matcher m = p.matcher(words[i]);
          words[i] = m.replaceAll("").trim().toLowerCase();
        }

        for (String str : words) {
          write(fileName.substring(0, fileName.length() - 4) + "Results.txt", str + "\r\n");
        }
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  static void write(String fileName, String str) {
    try {
      //new File(fileName);
      FileWriter writer = new FileWriter(fileName, true);
      writer.write(str);
      ;
      //     System.out.println("write successfully");//一个单词打印一次
      writer.close();
    } catch (IOException e) { //抛出异常，必须的
      e.printStackTrace();
    }
  }

  static Cgraph readWordsToGraph() {
    Cgraph graph = new Cgraph();
    File file = new File(resultsFileName);
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      String word1 = null;
      String word2 = null;
      while ((tempString = reader.readLine()) != null) {
        word2 = tempString;
        if (word1 != null) {
          graph.selfaddEdge(word1, word2);
          //     System.out.println(word1+word2);
        }
        word1 = tempString;
        //         System.out.println(tempString);
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //         graph.selfprintmatrix();//打印邻接矩阵
    return graph;
  }

  static void testFunc3Brige(Cgraph graph) {
    //   ReadFile();
    //    CGraph graph = readWordsToGraph();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("please input two word or (0,0) to exit:");
      String word1 = scanner.next();
      String word2 = scanner.next();
      if (word1.equals("0") && word2.equals("0")) {
        break;
      }
      String res = graph.queryBridgeWords(word1, word2);
      System.out.print(res);
    }
    scanner.close();
    return;
  }

  static void testFunc4Generate(Cgraph graph) {
    //     ReadFile();
    //    CGraph graph = readWordsToGraph();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("please input your text or -1 to exit:");
      String inputText = "";
      inputText = scanner.nextLine();
      if (inputText.equals("-1")) {
        break;
      }
      String res = graph.generateNewText(inputText);
      System.out.print(res + "\n");

    }
    scanner.close();
  }

  static void testFunc5Path(Cgraph graph) {
    //   ReadFile();
    //    CGraph graph = readWordsToGraph();
    Scanner scanner = new Scanner(System.in);
    while (true) {
      System.out.println("please input two word or (0,0) to exit(NW:not a word):");
      String word1 = scanner.next();
      String word2 = scanner.next();
      if (word1.equals("0") && word2.equals("0")) {
        break;
      }
      String res = graph.calcShortestPath(word1, word2);
      System.out.print(res);
    }
    scanner.close();
    return;
  }

  static void testFunc6And2Generate(Cgraph graph) {
    //   ReadFile();
    //  CGraph graph = readWordsToGraph();
    graph.randomwalk();
    graph.showDirectedGraph(graph.matrix);
  }

}
