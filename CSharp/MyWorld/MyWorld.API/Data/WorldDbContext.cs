using Microsoft.EntityFrameworkCore;
using MyWorld.Data.Entities;

namespace MyWorld.Data
{
    public class WorldDbContext : DbContext
    {
        public WorldDbContext(DbContextOptions<WorldDbContext> options) : base(options) { }

        public DbSet<Country> Countries { get; set; }
        public DbSet<City> Cities { get; set; }
        public DbSet<CountryLanguage> CountryLanguages { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Country>()
                .ToTable("country")
                .HasKey(c => c.Code);
            modelBuilder.Entity<Country>().Property(c => c.Code).HasColumnName("Code");

            modelBuilder.Entity<City>()
                .ToTable("city")
                .HasKey(c => c.Id);
            modelBuilder.Entity<City>().Property(c => c.Id).HasColumnName("ID");
            modelBuilder.Entity<City>()
                .HasOne(c => c.Country)
                .WithMany(cn => cn.Cities)
                .HasForeignKey(c => c.CountryCode)
                .HasPrincipalKey(cn => cn.Code);

            modelBuilder.Entity<CountryLanguage>()
                .ToTable("countrylanguage")
                .HasKey(cl => new { cl.CountryCode, cl.Language });
            modelBuilder.Entity<CountryLanguage>()
                .HasOne(cl => cl.Country)
                .WithMany(c => c.Languages)
                .HasForeignKey(cl => cl.CountryCode)
                .HasPrincipalKey(c => c.Code);

            // map column names if necessary (example):
            modelBuilder.Entity<Country>().Property(c => c.Name).HasColumnName("Name");
            // ... repeat mapping if your DB uses different case/columns
        }
    }
}
