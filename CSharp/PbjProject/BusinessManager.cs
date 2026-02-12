using PbjProject;
using System;
using System.Collections.Generic;
using System.Linq;


// ================= BUSINESS LAYER =================
public class BusinessManager
{
    private readonly DataProvider _dp;


    public BusinessManager(DataProvider dp)
    {
        _dp = dp;
    }


    // ----------- Bread -----------
    public void SaveBread(Bread b)
    {
        if (b.ID == 0) _dp.CreateBread(b);
        else _dp.UpdateBread(b);
    }


    public void DeleteBread(int id) => _dp.DeleteBread(id);
    public Bread GetBreadById(int id) => _dp.ReadBreadById(id);
    public List<Bread> GetAllBread() => _dp.ReadAllBread();


    // ----------- PeanutButter -----------
    public void SavePeanutButter(PeanutButter p)
    {
        if (p.ID == 0) _dp.CreatePeanutButter(p);
        else _dp.UpdatePeanutButter(p);
    }


    public void DeletePeanutButter(int id) => _dp.DeletePeanutButter(id);
    public PeanutButter GetPeanutButterById(int id) => _dp.ReadPeanutButterById(id);
    public List<PeanutButter> GetAllPeanutButter() => _dp.ReadAllPeanutButter();


    // ----------- Jelly -----------
    public void SaveJelly(Jelly j)
    {
        if (j.Flavor.Equals("ketchup"))
            throw new ArgumentException("You are a sick person! Get out of my store.");
        if (j.ID == 0) _dp.CreateJelly(j);
        else _dp.UpdateJelly(j);
    }


    public void DeleteJelly(int id) => _dp.DeleteJelly(id);
    public Jelly GetJellyById(int id) => _dp.ReadJellyById(id);
    public List<Jelly> GetAllJelly() => _dp.ReadAllJelly();


    // ----------- PBJ Sandwich -----------
    public PbjSandwich SavePbj(PbjSandwich s)
    {
        if (s.ID == 0) return _dp.CreatePbj(s);
        else
        {
            _dp.UpdatePbj(s);
            return s;
        }
    }


    public void DeletePbj(int id) => _dp.DeletePbj(id);
    public PbjSandwich GetPbjById(int id) => _dp.ReadPbjById(id);
    public List<PbjSandwich> GetAllPbj() => _dp.ReadAllPbj();
}