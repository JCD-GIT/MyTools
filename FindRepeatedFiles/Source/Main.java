

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.*;

import com.sun.media.jfxmedia.MediaPlayer;
import javafx.scene.media.Media;
import  sun.audio.*;    //import the sun.audio package
import  java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.nio.file.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

import static java.nio.file.Files.delete;
import static java.nio.file.StandardCopyOption.*;
import java.nio.channels.FileChannel;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class Main {


    public static boolean show = false;
    public static boolean number_files = false;


    public static void main(String[] args) throws IOException, InterruptedException {


        Arquivo m;
        File f;
        int opt=0;

        /* With a unique parameter just get a list of all file files */
        if (args.length == 1) {
            m = new Arquivo(args[0]);


        }

        if (args.length == 2) {
            if (args[1].toLowerCase().equals("-n")) opt=1;
            if (args[1].toLowerCase().equals("-c")) opt=2;
            if (args[1].toLowerCase().equals("-cn")) opt=3;
            if (args[1].toLowerCase().equals("-nc")) opt=3;

            if (opt>0) m = new Arquivo(args[0],opt);


        }


    }
}

class Arquivo {
    String diretorio;
    static int max = 1500;
    static int file_count;
    static int file_total;
    static File[] AllFiles = new File[max];
    static File[] files;
    static int increase = 500;
    int maxpos = 300;
    int ids_quantity;
    int increasepos = 100;
    int[] AllPos = new int[maxpos];
    int index = 0;
    int flag_file_copy = 0;
    String filesExt = ".*";
    boolean filter_extension = false;


    public Arquivo(String dir) {    /* Initialization, getting files and number of files in Source folder*/
        file_total = 0;
        System.out.println("Searching all file files in folder and subfolders ...\n");
        getdir(dir);
        file_count = file_total;
        System.out.println("\nDone. " + file_count + " files found!\n\n");



    }
    public Arquivo(String dir, int ops) throws FileNotFoundException {
        Arquivo m = new Arquivo(dir);

        comparefiles(m, file_count, ops);
    }


    void attrib(int i, File v) {  /* Allocate more memory to File vector if needed, if not just set value to array */
        if (i < max) {
            AllFiles[i] = v;
        } else {
            File[] temp = new File[max];
            int j;
            for (j = 0; j < max; j++) temp[j] = AllFiles[j];
            AllFiles = new File[max + increase];
            for (j = 0; j < max; j++) AllFiles[j] = temp[j];
            max = max + increase;
            AllFiles[i] = v;
        }
        return;
    }


    void getdir(String dir) {     /* Go through folder and subfolder and get all file files */
        diretorio = dir;
        String fname, ext;
        int i;
        File folder = new File(dir);
        files = folder.listFiles();
        for (File file : files) {    /* Go to each file file in each folder */
            if (!file.isDirectory()) {
                fname = file.getName();
                ext = fname.substring(fname.length() - 3);
                if ((!filter_extension) | (filesExt.contains("." + ext.toLowerCase()))) {
                    file_total++;
                    attrib(index, file);/* AllFiles[index]=file; */
                    index++;
                }
            }
            if (file.isDirectory()) getdir(dir + "\\" + file.getName());
        }
        file_count = file_total;
        return;
    }
    boolean AreTheSame(File f1, File f2) throws FileNotFoundException {
        int i1,i2;
        long n,l;
        i1=0;
        i2=0;
        n=0;
        if (f1.length()!=f2.length()) {
            return false;
        }
        else
          {
              l=f1.length();
              BufferedReader bfr1 = new BufferedReader(new FileReader(f1.getAbsolutePath()));
              BufferedReader bfr2 = new BufferedReader(new FileReader(f2.getAbsolutePath()));
              try {
                  while ((i1==i2) && (n<l))
                  {
                      i1 = bfr1.read();
                      i2 = bfr2.read();
                      n++;
                   /*   System.out.println(i1+"-"+i2+"-"+n);*/
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              }
              if (i1==i2)  return true;
              return false;
          }
    }

    public void comparefiles_old(Arquivo mus, int n, int op) throws FileNotFoundException {
        int i, j;
        File f1, f2;
        boolean flag1sttime;

        for (i = 0; i < n; i++) {
            /*System.out.print(".");*/
            f1 = AllFiles[i];
            if (f1!=null)
             {
              flag1sttime=false;
              for (j = i + 1; j < n; j++)
              {
                  f2 = AllFiles[j];
                  if (f2 != null)
                  {
                      if ((op==1) && (f1.getName().equals(f2.getName())))
                      {
                          if (!flag1sttime) {
                              System.out.println("\n-----------------------------------------------------------------");
                              System.out.println(f1.getAbsolutePath());
                              flag1sttime = true;
                          }
                          System.out.println(f2.getAbsolutePath());
                          AllFiles[j] = null; /* Already compared and matched */
                      }
                      if ((op==2) && (AreTheSame(f1,f2)))
                      {
                       if (!flag1sttime)
                       {
                              System.out.println("\n-----------------------------------------------------------------");
                              System.out.println(f1.getAbsolutePath());
                              flag1sttime = true;
                       }
                          System.out.println(f2.getAbsolutePath());
                          AllFiles[j] = null; /* Already compared and matched */
                      }
                      if ((op==3) && (f1.getName().equals(f2.getName())) && (AreTheSame(f1,f2)))
                      {
                          if (!flag1sttime)
                          {
                              System.out.println("\n-----------------------------------------------------------------");
                              System.out.println(f1.getAbsolutePath());
                              flag1sttime = true;
                          }
                          System.out.println(f2.getAbsolutePath());
                          AllFiles[j] = null; /* Already compared and matched */
                      }

                  }
              }
                 if (flag1sttime) {
                     System.out.println("-----------------------------------------------------------------");
                     flag1sttime = false;
                     AllFiles[i] = null;
                 }

            }

        }
        return;
    }

    public void comparefiles(Arquivo mus, int n, int op) throws FileNotFoundException {
        int i, j,k;
        File f1, f2;
        boolean flag1sttime=false;



        Map<String, List<Integer>> findR = new HashMap<>();

        for (i = 0; i < n; i++)
        {
            /*System.out.print(".");*/
            f1 = AllFiles[i];
            String strk = "";
            if (op == 1) strk = f1.getName();
            if (op == 2) strk = strk + f1.length();
            if (op == 3) strk = f1.getName() + f1.length();

            if (findR.containsKey(strk))
            {
                List<Integer> tmp = findR.get(strk);
                tmp.add(i);
                findR.put(strk, tmp);
            }
            else
            {
                List<Integer> tmp = new ArrayList<>();
                tmp.add(i);
                findR.put(strk, tmp);
            }
        }


        Set<String> keys = findR.keySet();
        for(String strk: keys)
        {
                List<Integer> tmp = findR.get(strk);
                if (tmp.size() > 1)
                {
                    if (op==1)
                    {
                        System.out.println("\n-----------------------------------------------------------------");
                        for (j = 0; j < tmp.size(); j++)
                        {
                            f1 = AllFiles[tmp.get(j)];
                            System.out.println(f1.getAbsolutePath());
                        }
                    }
                    else
                    {
                        for (j=0; j < tmp.size(); j++)
                        {
                            flag1sttime=true;
                            if (tmp.get(j)>=0)
                            {
                                for (k = j+1; k < tmp.size(); k++)
                                {
                                    if (tmp.get(k)>=0)
                                    {
                                        if (AreTheSame(AllFiles[tmp.get(j)], AllFiles[tmp.get(k)]))
                                        {
                                            if (flag1sttime)
                                            {
                                                System.out.println("\n-----------------------------------------------------------------");
                                                f1 = AllFiles[tmp.get(j)];
                                                System.out.println(f1.getAbsolutePath());
                                            }
                                            flag1sttime=false;
                                            f1 = AllFiles[tmp.get(k)];
                                            System.out.println(f1.getAbsolutePath());
                                            tmp.set(k, -1);
                                        }
                                    }
                                }
                            }
                        }


                    }
                }

       }
       return;
    }


}
