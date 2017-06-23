using System;
using System.Drawing;

namespace ccolor
{
    class Program
    {
        static void Main(string[] args)
        {

            Random rnd = new Random();
            int bcor = rnd.Next(15);
            int fcor=bcor;

            while (fcor == bcor) fcor = rnd.Next(15);


            Console.BackgroundColor = (ConsoleColor)bcor;
            Console.ForegroundColor = (ConsoleColor)fcor;

            Console.Clear();
            Console.Title = "F="+fcor+" B="+bcor;
            if (args.Length!=0) Console.ReadKey();

        }
    }
}
