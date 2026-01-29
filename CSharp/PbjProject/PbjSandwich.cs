using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PbjProject
{
    public class PbjSandwich
    {
        public int ID { get; set; }
        public string Customer { get; set; }
        public Bread Bread1 { get; set; }
        public PeanutButter Pb { get; set; }
        public Jelly Jelly { get; set; }
        public Bread Bread2 { get; set; }
        public decimal TotalCost { get; set; }
    }
}
