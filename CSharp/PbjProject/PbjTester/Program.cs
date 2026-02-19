using PbjProject;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Media;
using System.Net.Http.Json;
using System.Threading;

class Program
{
    static void Main(string[] args)
    {
        var layer = "svc";
        if (layer.Equals("data"))
            DataTestHarness();
        else if (layer.Equals("bus"))
            BusinessTestHarness();
        else if (layer.Equals("svc"))
            ServiceTestHarness().Wait();
    }

    static async Task ServiceTestHarness()
    {
        var client = new HttpClient();
        client.BaseAddress = new Uri("https://localhost:44387/api/");


        var breads = await client.GetFromJsonAsync<Bread[]>("bread");
        var pb = (await client.GetFromJsonAsync<PeanutButter[]>("peanutbutter"))[0];
        var jelly = (await client.GetFromJsonAsync<Jelly[]>("jelly"))[0];


        var pbj = new PbjSandwich
        {
            Customer = "ApiClient",
            Bread1 = breads[0],
            Bread2 = breads[0],
            Pb = pb,
            Jelly = jelly,
            TotalCost = breads[0].Price * 2 + pb.Price + jelly.Price
        };


        var resp = await client.PostAsJsonAsync("pbjsandwich", pbj);
        var created = await resp.Content.ReadFromJsonAsync<PbjSandwich>();


        Console.WriteLine($"CREATED PBJ ID: {created.ID}");


        created.Customer = "UpdatedApiClient";
        await client.PostAsJsonAsync("pbjsandwich", created);


        var fetched = await client.GetFromJsonAsync<PbjSandwich>($"pbjsandwich/{created.ID}");
        Console.WriteLine($"UPDATED CUSTOMER: {fetched.Customer}");
    }

    static void BusinessTestHarness()
    {
        string connStr = "Server=localhost;Port=3306;Database=pbjproject;Uid=user;Pwd=password;";
        var dp = new DataProvider(connStr);
        var bm = new BusinessManager(dp);


        Console.WriteLine("=== BUSINESS MANAGER TEST ===\n");


        // Grab existing FK data
        var bread1 = bm.GetAllBread().First();
        var bread2 = bm.GetAllBread().Last();
        var pb = bm.GetAllPeanutButter().First();
        var jelly = bm.GetAllJelly().First();


        // -------- CREATE --------
        var sandwich = new PbjSandwich
        {
            Customer = "BusinessLayerUser",
            Bread1 = bread1,
            Bread2 = bread2,
            Pb = pb,
            Jelly = jelly,
            TotalCost = bread1.Price + bread2.Price + pb.Price + jelly.Price
        };


        bm.SavePbj(sandwich);
        var created = bm.GetAllPbj().Last();
        PrintPbj(created, "CREATED");


        // -------- UPDATE --------
        created.Customer = "UpdatedBusinessUser";
        created.TotalCost += 1.00m;
        bm.SavePbj(created);
        var updated = bm.GetPbjById(created.ID);
        PrintPbj(updated, "UPDATED");


        // -------- DELETE --------
        bm.DeletePbj(updated.ID);
        var deleted = bm.GetPbjById(updated.ID);
        Console.WriteLine(deleted == null ? "DELETED SUCCESSFULLY" : "DELETE FAILED");


        Console.WriteLine("\n=== TEST COMPLETE ===");
        Console.ReadLine();
    }


    static void DataTestHarness()
    {

        string connStr = "Server=localhost;Port=3306;Database=pbjproject;Uid=user;Pwd=password;";
        var dp = new DataProvider(connStr);

        Console.WriteLine("===== PBJ DATA PROVIDER TEST HARNESS =====\n");

        // ----------- BREAD -----------
        Console.WriteLine("--- BREAD TEST ---");
        var bread = new Bread { Brand = "TestBrand", WheatLevel = 2, Price = 1.50m };
        dp.CreateBread(bread);
        var breads = dp.ReadAllBread();
        var insertedBread = breads.Last();
        PrintBread(insertedBread, "Inserted");

        insertedBread.Price = 2.00m;
        dp.UpdateBread(insertedBread);
        var updatedBread = dp.ReadBreadById(insertedBread.ID);
        PrintBread(updatedBread, "Updated");

        dp.DeleteBread(updatedBread.ID);
        var deletedBread = dp.ReadBreadById(updatedBread.ID);
        Console.WriteLine(deletedBread == null ? "Deleted successfully\n" : "Delete failed\n");

        // ----------- PEANUT BUTTER -----------
        Console.WriteLine("--- PEANUT BUTTER TEST ---");
        var pb = new PeanutButter { Brand = "TestPB", IsCrunchy = true, Price = 3.25m };
        dp.CreatePeanutButter(pb);
        var pbs = dp.ReadAllPeanutButter();
        var insertedPb = pbs.Last();
        PrintPB(insertedPb, "Inserted");

        insertedPb.Price = 3.75m;
        dp.UpdatePeanutButter(insertedPb);
        var updatedPb = dp.ReadPeanutButterById(insertedPb.ID);
        PrintPB(updatedPb, "Updated");

        dp.DeletePeanutButter(updatedPb.ID);
        var deletedPb = dp.ReadPeanutButterById(updatedPb.ID);
        Console.WriteLine(deletedPb == null ? "Deleted successfully\n" : "Delete failed\n");

        // ----------- JELLY -----------
        Console.WriteLine("--- JELLY TEST ---");
        var jelly = new Jelly { Brand = "TestJelly", Flavor = "Blueberry", Price = 2.10m };
        dp.CreateJelly(jelly);
        var jellies = dp.ReadAllJelly();
        var insertedJelly = jellies.Last();
        PrintJelly(insertedJelly, "Inserted");

        insertedJelly.Price = 2.50m;
        dp.UpdateJelly(insertedJelly);
        var updatedJelly = dp.ReadJellyById(insertedJelly.ID);
        PrintJelly(updatedJelly, "Updated");

        dp.DeleteJelly(updatedJelly.ID);
        var deletedJelly = dp.ReadJellyById(updatedJelly.ID);
        Console.WriteLine(deletedJelly == null ? "Deleted successfully\n" : "Delete failed\n");

        // ----------- PBJ SANDWICH -----------
        Console.WriteLine("--- PBJ SANDWICH TEST ---");

        // Need valid FK objects
        var b1 = dp.ReadAllBread().First();
        var b2 = dp.ReadAllBread().Last();
        var pbBase = dp.ReadAllPeanutButter().First();
        var jBase = dp.ReadAllJelly().First();

        var pbj = new PbjSandwich
        {
            Customer = "TestCustomer",
            Bread1 = b1,
            Bread2 = b2,
            Pb = pbBase,
            Jelly = jBase,
            TotalCost = b1.Price + b2.Price + pbBase.Price + jBase.Price
        };

        dp.CreatePbj(pbj);
        var pbjs = dp.ReadAllPbj();
        var insertedPbj = pbjs.Last();
        PrintPbj(insertedPbj, "Inserted");

        insertedPbj.Customer = "UpdatedCustomer";
        dp.UpdatePbj(insertedPbj);
        var updatedPbj = dp.ReadPbjById(insertedPbj.ID);
        PrintPbj(updatedPbj, "Updated");

        dp.DeletePbj(updatedPbj.ID);
        var deletedPbj = dp.ReadPbjById(updatedPbj.ID);
        Console.WriteLine(deletedPbj == null ? "Deleted successfully\n" : "Delete failed\n");

        Console.WriteLine("===== TEST HARNESS COMPLETE =====");
        Console.ReadLine();
    }

    // ----------- PRINT HELPERS -----------
    static void PrintBread(Bread b, string label)
    {
        Console.WriteLine($"{label} Bread -> ID:{b.ID}, Brand:{b.Brand}, Wheat:{b.WheatLevel}, Price:{b.Price}");
    }

    static void PrintPB(PeanutButter p, string label)
    {
        Console.WriteLine($"{label} PeanutButter -> ID:{p.ID}, Brand:{p.Brand}, Crunchy:{p.IsCrunchy}, Price:{p.Price}");
    }

    static void PrintJelly(Jelly j, string label)
    {
        Console.WriteLine($"{label} Jelly -> ID:{j.ID}, Brand:{j.Brand}, Flavor:{j.Flavor}, Price:{j.Price}");
    }

    static void PrintPbj(PbjSandwich s, string label)
    {
        Console.WriteLine($"{label} PBJ -> ID:{s.ID}, Customer:{s.Customer}, Bread1:{s.Bread1.ID}, PB:{s.Pb.ID}, Jelly:{s.Jelly.ID}, Bread2:{s.Bread2.ID}, Total:{s.TotalCost}");
    }
}
