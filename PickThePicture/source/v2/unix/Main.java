
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

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

import java.time.LocalDate;
import java.util.Date;
import java.time.Month;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;	
import java.util.List;
import java.util.ArrayList;
	


public class Main {


    public static boolean show = false;
    public static boolean number_files = false;
	public static boolean compare_folder = false;
	public static String folder2compare = "";
	public static String input_dir = "";
	


    public static void main(String[] args) throws IOException, InterruptedException {


        Picture m;
        File f;

        /* With a unique parameter just get a list of all picture files */
        if (args.length == 1) {
            show = true;
            m = new Picture(args[0]);
        }
        /* With more than 2 parameters */
        if (args.length > 2) {
            if (args[0].equals("show"))
            {
                if ((args.length == 4) && (args[2].equals("ALL")))
                {
                    m = new Picture(args[1], "ALL", Integer.parseInt(args[3]));
                }
                else {
                    f = new File(args[3]);
                    if (f.exists() && f.isFile())
                        m = new Picture(args[1], Integer.parseInt(args[2]), args[3], "");  /* String, input text files with picture ids to play */
                    else {
                        if (args.length == 5) {
                            if (args[4].equals("ALL"))
                                m = new Picture(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), "ALL");
                        } else {
                            m = new Picture(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), "");  /* integer, number the pictures to shuffle */
                        }
                    }
                }
            }
            if (args[0].equals("copy"))
            {
             /* With four parameters, do the same as two, but copy instead play */
                if (args.length == 4) {
                    f = new File(args[2]);
                    if (f.exists() && f.isFile())
                        m = new Picture(args[1], args[2], args[3]);
                    else
                        m = new Picture(args[1], Integer.parseInt(args[2]), args[3]);
                }
              /* With five parameters, do the same as three, but number the files if it's the case */
                if (args.length == 5) {
					input_dir = args[1];
					if (input_dir.substring(input_dir.length()-1).equals("/"))
						input_dir = input_dir.substring(0,input_dir.length()-1);
                    if (args[4].equals("-n")) 
						Main.number_files=true;
					else
					   {
						   f = new File(args[4]);
						   if (f.exists() && f.isDirectory()) 
						     {
							   folder2compare = args[4];
						       compare_folder = true;
							 }
					   }
                    f = new File(args[2]);
                    if (f.exists() && f.isFile())
                        m = new Picture(args[1], args[2], args[3]);
                    else
                        m = new Picture(args[1], Integer.parseInt(args[2]), args[3]);
                }

            }

        }

    }

}

class Picture {
    String diretorio;
    static int max = 1500;
    static int picture_count;
    static int picture_total;
    static float interval_time;
    static File[] AllFiles = new File[max];
    File[] files;
    int increase = 500;
    int maxpos = 300;
    int ids_quantity;
    int increasepos = 100;
    int[] AllPos = new int[maxpos];
    int index = 0;
    int flag_picture_copy = 0;
    String picturesExt=".jpg.bmp.png.gif.tif";


    public Picture(String dir) {    /* Initialization, getting files and number of pictures in Source folder*/
        picture_total = 0;
        System.out.println("Searching all picture files in folder and subfolders ...\n");
        getdir(dir);
        picture_count = picture_total;
        System.out.println("\nDone. "+ picture_count+ " pictures found!\n\n");

    }


    public Picture(String dir, int number, String target) throws InterruptedException {    /* Setting number of pictures, target folder, random */
        Picture m = new Picture(dir);
        try {
            select_pictures(m, number, target, AllPos);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Picture(String dir, String status, int totaltime) throws IOException, InterruptedException {    /* Setting number of pictures, total time */
        Picture m = new Picture(dir);
        interval_time = (60*totaltime);
        interval_time=interval_time/picture_total;
        try {
            select_pictures(m, picture_total, "", AllPos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Picture(String dir, int breaktime, int number, String status) throws IOException, InterruptedException {    /* Setting number of pictures, number of repetitions */
        Picture m = new Picture(dir);
        interval_time = breaktime;
        if (status.equals("ALL")) number = number * picture_total;
        try {
            select_pictures(m, number, "", AllPos);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Picture(String dir, int breaktime, String ids, String target) throws IOException, InterruptedException {    /* Setting number of pictures, target folder, id list */
            Picture m = new Picture(dir);
            interval_time=breaktime;
            try {
               getIDS(ids);   /* maxpos get number of ids */
             } catch (FileNotFoundException e) {
                e.printStackTrace();
            }  catch (IOException e) {
                e.printStackTrace();
            }
            try {
                select_pictures(m, -1, "", AllPos);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }





    public Picture(String dir, String ids, String target) throws IOException, InterruptedException {    /* Setting number of pictures, target folder, id list */
        Picture m = new Picture(dir);
        try {
            getIDS(ids);   /* maxpos get number of ids */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        select_pictures(m, -1, target, AllPos);        /* number = -1 when not random */


    }

    int attrib_int(int i, int v) {  /* Allocate more memory to ids vector if needed, if not just set value to array */
        if (i < maxpos) {
            AllPos[i] = v;
        } else {
            int[] temp = new int[maxpos];
            int j;
            for (j = 0; j < maxpos; j++) temp[j] = AllPos[j];
            AllPos = new int[maxpos + increasepos];
            for (j = 0; j < maxpos; j++) AllPos[j] = temp[j];
            maxpos = maxpos + increase;
            AllPos[i] = v;
        }
        return (0);
    }

    int attrib(int i, File v) {  /* Allocate more memory to File vector if needed, if not just set value to array */
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
        return (0);
    }


    int getIDS(String ids) throws IOException {      /* Get IDs from Ids input file */
        String lines="";
        String aux;
        int i = 0, j = 0, k = 0;

        BufferedReader br = new BufferedReader(new FileReader(ids));  /* Read all file adding to string */
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            lines = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            br.close();
        }

                /* Add each each id to array and get the number of ids */
        i = lines.indexOf("id#:[");
        k = lines.indexOf("]");
        while (i >= 0) {
                    /* AllPos[j] = Integer.parseInt(lines.substring(i+5,k));*/
            attrib_int(j, Integer.parseInt(lines.substring(i+5,k)));
            lines = lines.substring(k+1);
            i = lines.indexOf("id#:[");
            if (i>=0)
            {
                aux = lines.substring(i);
                k = aux.indexOf("]") + i;
            }
            j++;

        }
        ids_quantity=j;
        return (0);
    }

    int getdir(String dir) {     /* Go through folder and subfolder and get all picture files */
        diretorio = dir;
        String fname, ext;
        int i;
        File folder = new File(dir);
        files = folder.listFiles();
        for (File file : files) {    /* Go to each picture file in each folder */
            if (!file.isDirectory())
                {
                fname = file.getName();
                ext = fname.substring(fname.length() - 3);
                if (picturesExt.contains("."+ext.toLowerCase()))
                {
                    if (Main.show) {
                        System.out.println("id#:["+picture_total+"]\t"+ dir + "/" + file.getName());
                    }
                    picture_total++;
                    attrib(index, file);/* AllFiles[index]=file; */
                    index++;
                }
            }
            if (file.isDirectory()) getdir(dir + "/" + file.getName());
        }
        picture_count = picture_total;
        return (0);
    }

    String zerofill (int i, int j)  /* Adds zero to the left to all numbers be with same number of digits */
    {
        String r,r2;

        r=Integer.toString(i);
        r2=Integer.toString(j);

        while (r.length()<r2.length())
        {
            r="0"+r;
        }
        return (r);
    }
    /* Play or copy pictures according */
    int select_pictures(Picture mus, int n, String target_Folder, int ids_file[]) throws IOException, InterruptedException {
        File temp;
        File target;
        File mpos;
        int pos, i;
        String picfile="";
        boolean copy_files = false;
        boolean aleatory = true;
        boolean first = true;

        JFrame frame = new JFrame();
        ImageIcon icon = new ImageIcon(picfile);
        JLabel label = new JLabel(icon);

        target = new File(target_Folder);
        if (target.exists()) copy_files = true;

        if (n == -1)  /* n=-1 when selection is not random. */ {
            n = ids_quantity;
            aleatory = false;
        }
		
		List<String> lista = new ArrayList<>();
		
		if (Main.compare_folder)
			{
             String fname;
             File folder = new File(Main.folder2compare);
             files = folder.listFiles();
             for (File file : files) {     
               if (!file.isDirectory())
                {
                  fname = file.getName();
				  if (fname.substring(0,7).compareTo("Picked(") == 0)
				     {
                       pos = fname.indexOf(")");
				       lista.add(fname.substring(pos+8));
                     }
                }
			  }	
		    }

        for (i = 0; i < n; i++) {    /* For each picture from list or to be selected randomly */
            System.out.println("\n" + (i + 1) + "/" + n);
            if (picture_count == 1)    /* if only one picture remaining, play this one and select all again */
            {
                pos = 0;
                temp = AllFiles[pos];
                System.out.println(temp.getName());
                System.out.println(temp.getAbsolutePath());
                picture_count = picture_total;
            }
            else
            {
                if (aleatory) {
                    Random rand = new Random();    /* Select one picture randomly */
                    pos = rand.nextInt(picture_count);
                    System.out.println("id#: " + pos);
                    temp = AllFiles[pos];
                    picture_count--;             /* Exchange selected picture with last one and reduce list size to not repeat */
                    AllFiles[pos] = AllFiles[picture_count];
                    AllFiles[picture_count] = temp;
                    System.out.println(temp.getName());
                    System.out.println(temp.getAbsolutePath());
                } else {
                    pos = ids_file[i];           /* not random, just take file from array */
                    System.out.println("id#: " + pos);
                    temp = AllFiles[pos];
                    System.out.println(temp.getName());
                    System.out.println(temp.getAbsolutePath());
                }
            }
            Path FROM = Paths.get(temp.getAbsolutePath());
			
            String ext = temp.getName().substring(temp.getName().length() - 3);  /* Set extension according */

            if (picturesExt.contains("."+ext.toLowerCase()))
             ext=ext.toLowerCase();

            if (copy_files) {     /* when copying if same filename exist, copy to a different folder */

			String change_name = "";
			String path_text="";
			
			if (Main.compare_folder)
			{	
			
			    change_name = temp.getAbsolutePath();
				change_name = change_name.substring(Main.input_dir.length());
				
				
			    pos = change_name.indexOf("/");
				while (pos>=0)
					 {
					   change_name = change_name.substring(0,pos)+"_"+change_name.substring(pos+1);	
					   pos = change_name.indexOf("/");
					 }

				pos = change_name.indexOf(" ");
				while (pos>=0)
					 {
					   change_name = change_name.substring(0,pos)+"_"+change_name.substring(pos+1);	
					   pos = change_name.indexOf(" ");
					 }
			    
			    /* change_name = change_name.substring(2); */
				if (lista.contains(change_name))
				{
                   System.out.println("File not added because is already there ==>" + change_name);					
				   i--;
				}
				else  				/* If file does not exist in compare folder */
				  {					
                   LocalDate currentDate = LocalDate.now(); 				;
                   /* int day = currentDate.getDayOfMonth(); */
                   Month month = currentDate.getMonth();
		           String m = month.getDisplayName(TextStyle.SHORT, Locale.getDefault());
                   int year = currentDate.getYear();
				   change_name = "Picked(" + i + ")" + m + year + change_name;
			       path_text = target_Folder + "/" + change_name;
				
   				   Path TO = Paths.get(path_text);
                   CopyOption[] options = new CopyOption[]{
                      StandardCopyOption.REPLACE_EXISTING,
                      StandardCopyOption.COPY_ATTRIBUTES};
                   try {
                      Files.copy(FROM, TO, options);
                      }  catch (IOException e) {
                      e.printStackTrace();
                      }
				  }  
			  }
			else
		      {
                change_name = temp.getName();
			    path_text = target_Folder + "/" + change_name;
			
                if (Main.number_files) path_text = target_Folder + "/(" + zerofill(i+1,n) + ")"+ temp.getName();
                target = new File(path_text);
                int j = 0;
                while (target.exists()) {
                    j++;
                    target = new File(target_Folder + "/" + j);
                    if (!target.exists()) target.mkdir();

    	            path_text = target_Folder + "/" + j + "/" + temp.getName();
                    target = new File(path_text);
                }
              
                Path TO = Paths.get(path_text);
                CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES};
                try {
                    Files.copy(FROM, TO, options);
                    }  catch (IOException e) {
                    e.printStackTrace();
                    }
			   }	 
			 	
            } else {
                    icon = new ImageIcon(picfile);
                    icon =  new ImageIcon(FROM.toString());
                    label = new JLabel(icon);
                    frame.add(label);
                    frame.setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setVisible(true);
                try {

                       Thread.sleep((int)(interval_time*1000));

                     } catch (InterruptedException e) {
                     e.printStackTrace();
                     }
               /* try {*/
                    if (i!=n-1) {
                        frame.setVisible(false);
                        frame.remove(label);
                        icon = null;
                        label = null;
                    }
            }
        }
        return (0);
    }
}
