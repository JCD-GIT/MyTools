
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


            p.Run(args[0], args[1], args[2], args[3]);




        }

        public void copia(string path_s, string file_s, string path_t, string path_fullbk)
        {

            int i;
            string filename = "";

            for (i = path_s.Length; i < file_s.Length; i++)
            {
                filename = filename + file_s[i];
            }

            bool tocopy = true;

            

            if (File.Exists(path_fullbk + filename) )
            {

                FileStream f1 = new FileStream(path_fullbk + filename, FileMode.Open, FileAccess.Read);
                FileStream f2 = new FileStream(path_s + filename, FileMode.Open, FileAccess.Read);

              
                try
                {
                    long l1 = new System.IO.FileInfo(path_fullbk + filename).Length;
                    long l2 = new System.IO.FileInfo(path_s + filename).Length;

                    


                    if (l1 == l2)
                    {

                        
                        int c1=0, c2=0;


                        long j;
                        for (j=0; (j < l1) && (c1==c2); j++)
                        {
                            c1 = f1.ReadByte();
                            c2 = f2.ReadByte();

                        }

                        if (c1 == c2) tocopy = false;
                    }
                }
                catch (FileNotFoundException)
                {
                    tocopy = false;
                }
                finally
                {
                    if (f1 != null)
                    {
                        f1.Close();
                    }
                    if (f2 != null)
                    {
                        f2.Close();
                    }

                }
            }

            if (!tocopy)
                Console.WriteLine("==> " + path_s + filename + " has not changed and do not need to be copied.");

            else
            {
                Console.WriteLine("Copying " + path_s + filename + " to " + path_t + filename);
                Directory.CreateDirectory(path_t + filename + "\\..");
                File.Copy(path_s + filename, path_t + filename);
            }


        }

        public void Run(string folderPath, string target, string mask, string path_fullbk)
        {
            int i;
            string tgt,pbk;
            foreach (string file in Directory.EnumerateFiles(folderPath, mask))
            {
                Console.WriteLine("Processing " + file);
                copia(folderPath, file, target, path_fullbk);


            }



            foreach (string fold in Directory.EnumerateDirectories(folderPath))
            {

                /* foreach (string file in Directory.EnumerateFiles(fold, mask))
                {

                    Console.WriteLine("Processing " + file);
                    copia(folderPath, file, target);
                }*/

                tgt = target;
                pbk = path_fullbk;
                

                for (i = folderPath.Length; i < fold.Length; i++)
                {
                    tgt = tgt + fold[i];
                    pbk = pbk + fold[i];

                }
                Run(fold, tgt, mask, pbk);




            }


        }
    }


}

