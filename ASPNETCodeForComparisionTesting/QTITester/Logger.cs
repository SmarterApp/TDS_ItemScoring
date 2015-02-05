using System;
using System.IO;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace QTITester
{
    public class Logger : IDisposable
    {
        StreamWriter streamWriter;
        public Logger(String fileName)
        {
            streamWriter = new StreamWriter(fileName);
        }

        public void Info(String message)
        {
            Write("Info: " + message);
        }

        public void Error(String message)
        {
            Write("Error: " + message);
        }

        public void Error(String message, Exception exp)
        {
            Write("Error: " + message, exp);
        }

        private void Write(String message)
        {
            streamWriter.WriteLine(message);
        }

        private void Write(String message, Exception exp)
        {
            streamWriter.WriteLine(message);
            streamWriter.WriteLine("=================Start Exception Details================");
            streamWriter.WriteLine(exp.Message);
            streamWriter.WriteLine(exp.StackTrace);
            streamWriter.WriteLine("=================End Exception Details================");
        }

        public void Dispose()
        {
            streamWriter.Close();
        }
    }
}
