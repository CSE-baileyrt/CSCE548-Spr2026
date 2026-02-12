using MySqlConnector;
using System;
using System.Collections.Generic;

namespace PbjProject
{
    public class DataProvider
    {
        private readonly string _connStr;
        public DataProvider(string connStr)
        {
            _connStr = connStr;
        }


        private MySqlConnection GetConnection() => new MySqlConnection(_connStr);

        // ---------- Bread ----------
        public void CreateBread(Bread b)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("INSERT INTO Bread (brand, wheatLevel, price) VALUES (@b,@w,@p)", con);
                cmd.Parameters.AddWithValue("@b", b.Brand);
                cmd.Parameters.AddWithValue("@w", b.WheatLevel);
                cmd.Parameters.AddWithValue("@p", b.Price);
                cmd.ExecuteNonQuery();
            }
        }


        public Bread ReadBreadById(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM Bread WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                using (var r = cmd.ExecuteReader())
                {
                    if (!r.Read()) return null;
                    return new Bread
                    {
                        ID = r.GetInt32("ID"),
                        Brand = r.GetString("brand"),
                        WheatLevel = r.GetInt32("wheatLevel"),
                        Price = r.GetDecimal("price")
                    };
                }
            }
        }


        public List<Bread> ReadAllBread()
        {
            var list = new List<Bread>();
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM Bread", con);
                using (var r = cmd.ExecuteReader())
                {
                    while (r.Read())
                    {
                        list.Add(new Bread
                        {
                            ID = r.GetInt32("ID"),
                            Brand = r.GetString("brand"),
                            WheatLevel = r.GetInt32("wheatLevel"),
                            Price = r.GetDecimal("price")
                        });
                    }
                    return list;
                }
            }
        }


        public void UpdateBread(Bread b)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("UPDATE Bread SET brand=@b, wheatLevel=@w, price=@p WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", b.ID);
                cmd.Parameters.AddWithValue("@b", b.Brand);
                cmd.Parameters.AddWithValue("@w", b.WheatLevel);
                cmd.Parameters.AddWithValue("@p", b.Price);
                cmd.ExecuteNonQuery();
            }
        }


        public void DeleteBread(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("DELETE FROM Bread WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.ExecuteNonQuery();
            }
        }


        // ---------- PeanutButter ----------
        public void CreatePeanutButter(PeanutButter p)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("INSERT INTO PeanutButter (brand,isCrunchy,price) VALUES (@b,@c,@p)", con);
                cmd.Parameters.AddWithValue("@b", p.Brand);
                cmd.Parameters.AddWithValue("@c", p.IsCrunchy);
                cmd.Parameters.AddWithValue("@p", p.Price);
                cmd.ExecuteNonQuery();
            }
        }

        public PeanutButter ReadPeanutButterById(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM PeanutButter WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                using (var r = cmd.ExecuteReader())
                {
                    if (!r.Read()) return null;
                    return new PeanutButter
                    {
                        ID = r.GetInt32("ID"),
                        Brand = r.GetString("brand"),
                        IsCrunchy = r.GetBoolean("isCrunchy"),
                        Price = r.GetDecimal("price")
                    };
                }
            }
        }

        public List<PeanutButter> ReadAllPeanutButter()
        {
            var list = new List<PeanutButter>();
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM PeanutButter", con);
                using (var r = cmd.ExecuteReader())
                {
                    while (r.Read())
                    {
                        list.Add(new PeanutButter
                        {
                            ID = r.GetInt32("ID"),
                            Brand = r.GetString("brand"),
                            IsCrunchy = r.GetBoolean("isCrunchy"),
                            Price = r.GetDecimal("price")
                        });
                    }
                    return list;
                }
            }
        }


        public void UpdatePeanutButter(PeanutButter p)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("UPDATE PeanutButter SET brand=@b,isCrunchy=@c,price=@p WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", p.ID);
                cmd.Parameters.AddWithValue("@b", p.Brand);
                cmd.Parameters.AddWithValue("@c", p.IsCrunchy);
                cmd.Parameters.AddWithValue("@p", p.Price);
                cmd.ExecuteNonQuery();
            }
        }


        public void DeletePeanutButter(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("DELETE FROM PeanutButter WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.ExecuteNonQuery();
            }
        }



        // ---------- Jelly ----------
        public void CreateJelly(Jelly j)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("INSERT INTO Jelly (brand,flavor,price) VALUES (@b,@f,@p)", con);
                cmd.Parameters.AddWithValue("@b", j.Brand);
                cmd.Parameters.AddWithValue("@f", j.Flavor);
                cmd.Parameters.AddWithValue("@p", j.Price);
                cmd.ExecuteNonQuery();
            }
        }


        public Jelly ReadJellyById(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM Jelly WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                using (var r = cmd.ExecuteReader())
                {
                    if (!r.Read()) return null;
                    return new Jelly
                    {
                        ID = r.GetInt32("ID"),
                        Brand = r.GetString("brand"),
                        Flavor = r.GetString("flavor"),
                        Price = r.GetDecimal("price")
                    };
                }
            }
        }


        public List<Jelly> ReadAllJelly()
        {
            var list = new List<Jelly>();
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM Jelly", con);
                using (var r = cmd.ExecuteReader())
                {
                    while (r.Read())
                    {
                        list.Add(new Jelly
                        {
                            ID = r.GetInt32("ID"),
                            Brand = r.GetString("brand"),
                            Flavor = r.GetString("flavor"),
                            Price = r.GetDecimal("price")
                        });
                    }
                    return list;
                }
            }
        }


        public void UpdateJelly(Jelly j)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("UPDATE Jelly SET brand=@b,flavor=@f,price=@p WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", j.ID);
                cmd.Parameters.AddWithValue("@b", j.Brand);
                cmd.Parameters.AddWithValue("@f", j.Flavor);
                cmd.Parameters.AddWithValue("@p", j.Price);
                cmd.ExecuteNonQuery();
            }
        }


        public void DeleteJelly(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("DELETE FROM Jelly WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.ExecuteNonQuery();
            }
        }

        // ---------- PBJ Sandwich ----------
        public PbjSandwich CreatePbj(PbjSandwich s)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand(@"INSERT INTO PbjSandwich
(customer,bread1_id,pb_id,jelly_id,bread2_id,totalCost)
VALUES (@c,@b1,@pb,@j,@b2,@t); SELECT LAST_INSERT_ID();", con);
                cmd.Parameters.AddWithValue("@c", s.Customer);
                cmd.Parameters.AddWithValue("@b1", s.Bread1.ID);
                cmd.Parameters.AddWithValue("@pb", s.Pb.ID);
                cmd.Parameters.AddWithValue("@j", s.Jelly.ID);
                cmd.Parameters.AddWithValue("@b2", s.Bread2.ID);
                cmd.Parameters.AddWithValue("@t", s.TotalCost);
                s.ID = Convert.ToInt32(cmd.ExecuteScalar());
                return s;
            }
        }

        public PbjSandwich ReadPbjById(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM PbjSandwich WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                using (var r = cmd.ExecuteReader())
                {
                    if (!r.Read()) return null;
                    return new PbjSandwich
                    {
                        ID = r.GetInt32("ID"),
                        Customer = r.GetString("customer"),
                        Bread1 = new Bread { ID = r.GetInt32("bread1_id") },
                        Pb = new PeanutButter { ID = r.GetInt32("pb_id") },
                        Jelly = new Jelly { ID = r.GetInt32("jelly_id") },
                        Bread2 = new Bread { ID = r.GetInt32("bread2_id") },
                        TotalCost = r.GetDecimal("totalCost")
                    };
                }
            }
        }


        public List<PbjSandwich> ReadAllPbj()
        {
            var list = new List<PbjSandwich>();
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("SELECT * FROM PbjSandwich", con);
                using (var r = cmd.ExecuteReader())
                {
                    while (r.Read())
                    {
                        list.Add(new PbjSandwich
                        {
                            ID = r.GetInt32("ID"),
                            Customer = r.GetString("customer"),
                            Bread1 = new Bread { ID = r.GetInt32("bread1_id") },
                            Pb = new PeanutButter { ID = r.GetInt32("pb_id") },
                            Jelly = new Jelly { ID = r.GetInt32("jelly_id") },
                            Bread2 = new Bread { ID = r.GetInt32("bread2_id") },
                            TotalCost = r.GetDecimal("totalCost")
                        });
                    }
                    return list;
                }
            }
        }


        public void UpdatePbj(PbjSandwich s)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand(@"UPDATE PbjSandwich SET
customer=@c,bread1_id=@b1,pb_id=@pb,jelly_id=@j,bread2_id=@b2,totalCost=@t
WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", s.ID);
                cmd.Parameters.AddWithValue("@c", s.Customer);
                cmd.Parameters.AddWithValue("@b1", s.Bread1.ID);
                cmd.Parameters.AddWithValue("@pb", s.Pb.ID);
                cmd.Parameters.AddWithValue("@j", s.Jelly.ID);
                cmd.Parameters.AddWithValue("@b2", s.Bread2.ID);
                cmd.Parameters.AddWithValue("@t", s.TotalCost);
                cmd.ExecuteNonQuery();
            }
        }


        public void DeletePbj(int id)
        {
            using (var con = GetConnection())
            {
                con.Open();
                var cmd = new MySqlCommand("DELETE FROM PbjSandwich WHERE ID=@id", con);
                cmd.Parameters.AddWithValue("@id", id);
                cmd.ExecuteNonQuery();
            }
        }
    }
}
