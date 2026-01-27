using Common;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
//removed per GPT; put back after errors
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();
//added per GPT: errors
//builder.Services.AddEndpointsApiExplorer();
//builder.Services.AddSwaggerGen();

// Connection string
string connectionString = builder.Configuration.GetConnectionString("MySql");
// Dependency Injection
builder.Services.AddScoped<DataProvider>(_ =>
    new DataProvider(connectionString));

builder.Services.AddScoped<BusinessManager>();

// enable CORS
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowLocalFiles", policy =>
    {
        policy
            .AllowAnyOrigin()
            .AllowAnyMethod()
            .AllowAnyHeader();
    });
});


var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

app.UseHttpsRedirection();

app.UseAuthorization();

// CORS policy
app.UseCors("AllowLocalFiles");

app.MapControllers();

app.Run();
