using Microsoft.EntityFrameworkCore;
using MyWorld.Business;
using MyWorld.Data;

var builder = WebApplication.CreateBuilder(args);

//added
var connectionString = builder.Configuration.GetConnectionString("WorldDb");

// Add services to the container.
//added (4)
builder.Services.AddDbContext<MyWorld.Data.WorldDbContext>(options =>
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString)));
builder.Services.AddScoped<ICountryRepository, CountryRepository>();
builder.Services.AddScoped<ICountryService, CountryService>();
builder.Services.AddControllers();

// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();
//added
builder.Services.AddEndpointsApiExplorer();
//tried to add, but wouldn't build
//builder.Services.AddSwaggerGen();
//added
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowBlazorDev", policy =>
    {
        //policy.WithOrigins("https://localhost:5008", "http://localhost:7085", "http://localhost:7227")
        //policy.WithOrigins("http://localhost:7227", "http://localhost:7085")
        policy.AllowAnyOrigin()
              .AllowAnyHeader()
              .AllowAnyMethod();
    });
});


var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

//added (2)
app.UseRouting();
app.UseCors("AllowBlazorDev");

//removed
//app.UseHttpsRedirection();

app.UseAuthorization();

//added
app.MapControllers();

app.Run();
