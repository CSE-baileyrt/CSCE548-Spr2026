import java.util.List;

public class CityApp {

    public static void main(String[] args) {

        CityDao dao = new CityDao();

        // GET ALL
        List<City> cities = dao.getAllCities();
        System.out.println("All Cities: " + cities.size());
        for (City city : cities) {
            System.out.println("\t" + city);
        }
        System.out.println("Done");
        

//        // INSERT
//        City newCity = new City("Testopolis", "USA", "Test State", 123456);
//        dao.insertCity(newCity);
//        System.out.println("Inserted city.");
//
//        // GET BY ID
//        City city = dao.getCityById(1);
//        System.out.println("City with ID 1:");
//        System.out.println(city);
//
//        // UPDATE
//        if (city != null) {
//            city = new City(
//                city.getId(),
//                "Updated City",
//                city.getCountryCode(),
//                city.getDistrict(),
//                city.getPopulation() + 1000
//            );
//            dao.updateCity(city);
//            System.out.println("Updated city with ID 1.");
//        }

        // DELETE ALL (⚠ dangerous, for demo only)
        // dao.deleteAllCities();
        // System.out.println("All cities deleted.");
    }
}
