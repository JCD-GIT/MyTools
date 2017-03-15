
using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace NewUpdatedDef
{
    class Program
    {
        static void Main(string[] args)
        {
            Program p = new Program();


            p.Run(args[0], args[1], args[2]);




        }

        public void copia (string path_s, string file_s, string path_t)
        {

            int i;
            string filename="";

            for (i = path_s.Length; i<file_s.Length; i++)
            {
                filename = filename + file_s[i];
            }

            if (File.Exists(path_t + filename))
             {
               Console.WriteLine("==> "+path_s + filename + " already exist in target and was not copied.");
 
             }
            else
             {
              Console.WriteLine("Copying " + path_s + filename + " to " + path_t + filename);
              Directory.CreateDirectory(path_t + filename+"\\..");
              File.Copy(path_s + filename, path_t + filename);
             }

           
        }

        public void Run(string folderPath, string target, string mask)
        {
            int i;
            string tgt;
            foreach (string file in Directory.EnumerateFiles(folderPath, mask))
            {
                Console.WriteLine("Processing "+file);
                copia(folderPath, file, target);

                
            }

            

            foreach (string fold in Directory.EnumerateDirectories(folderPath))
            {

                /* foreach (string file in Directory.EnumerateFiles(fold, mask))
                {

                    Console.WriteLine("Processing " + file);
                    copia(folderPath, file, target);
                }*/

                tgt = target;
               
                for (i = folderPath.Length; i < fold.Length; i++)
                {
                    tgt = tgt + fold[i];
                }
                Run(fold, tgt , mask);




            }


        }
    }


}
