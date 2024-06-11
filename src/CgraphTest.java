import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
class CgraphTest {
  private static String fileName = "test.txt";
  //    private static String fileName = "test.txt";
  private static String resultsFileName = "testResults.txt";
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
  @Test
  void calcShortestPath1() {
    readFile();
    Cgraph graph = readWordsToGraph();
    String res= graph.calcShortestPath("","");
    System.out.println(res);
    org.junit.jupiter.api.Assertions.assertEquals(res," cannot get to ");
    System.out.println("PASS");
  }
  @Test
  void calcShortestPath2() {
    readFile();
    Cgraph graph = readWordsToGraph();
    String res= graph.calcShortestPath("i","NW");
    System.out.println(res);
  }
  @Test
  void calcShortestPath3() {
    readFile();
    Cgraph graph = readWordsToGraph();
    String res= graph.calcShortestPath("i","an");
    System.out.println(res);
    org.junit.jupiter.api.Assertions.assertEquals(res,"i->am->an[length:2]\n");
    System.out.println("PASS");
  }
  @Test
  void calcShortestPath4() {
    readFile();
    Cgraph graph = readWordsToGraph();
    String res= graph.calcShortestPath("hit","man");
    System.out.println(res);
    org.junit.jupiter.api.Assertions.assertEquals(res,"hit cannot get to man");
    System.out.println("PASS");
  }
}