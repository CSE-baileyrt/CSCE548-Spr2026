import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class PbjConsoleTest {

	private static final String BASE = "http://localhost:8080/api";

	public static void main(String[] args) {
		String layer = "svc";
		if (layer.equals("data"))
			TestDataLayer();
		else if (layer.equals("bus"))
			TestBusinessLayer();
		else if (layer.equals("svc"))
			TestServiceLayer();
	}

	static void TestServiceLayer() {
		HttpClient client = HttpClient.newHttpClient();

		System.out.println("=== PBJ API TEST HARNESS START ===");

		// ======================
		// CREATE BREAD
		// ======================
		String breadJson = "{ \"id\":0, \"brand\":\"Wonder\", \"wheatLevel\":5, \"price\":2.50 }";

		String breadResponse = "";
		try {
			breadResponse = post(client, BASE + "/bread", breadJson);
		} catch(Exception ex) {
			handleException("TestServiceLayer.bread", ex);
		}

		int breadId = extractInt(breadResponse, "id");
		System.out.println("Created Bread ID: " + breadId);
		
		// ======================
		// CREATE PB
		// ======================
		String pbJson = "{ \"id\":0, \"brand\":\"Jif\", \"isCrunchy\":true, \"price\":3.25 }";

		String pbResponse = "";
		try {
			pbResponse = post(client, BASE + "/pb", pbJson);
		} catch(Exception ex) {
			handleException("TestServiceLayer.peanutbutter", ex);
		}

		int pbId = extractInt(pbResponse, "id");
		System.out.println("Created PB ID: " + pbId);

		// ======================
		// CREATE JELLY
		// ======================
		String jellyJson = "{ \"id\":0, \"brand\":\"Smuckers\", \"flavor\":\"Grape\", \"price\":2.75 }";

		String jellyResponse = "";
		try {
			jellyResponse = post(client, BASE + "/jelly", jellyJson);
		} catch(Exception ex) {
			handleException("TestServiceLayer.jelly", ex);
		}

		int jellyId = extractInt(jellyResponse, "id");
		System.out.println("Created Jelly ID: " + jellyId);

		// ======================
		// CREATE SANDWICH
		// ======================
		
		double totalCost = 2.50 * 2 + 3.25 + 2.75;
		String sandwichJson = "{ " + "\"id\":0," + "\"customer\":\"Robert\"," + "\"bread1\":{\"id\":" + breadId + "},"
				+ "\"pb\":{\"id\":" + pbId + "}," + "\"jelly\":{\"id\":" + jellyId + "}," + "\"bread2\":{\"id\":"
				+ breadId + "}," + "\"totalCost\":" + totalCost + "}";

		String sandwichResponse = "";
		try {
			sandwichResponse = post(client, BASE + "/sandwich", sandwichJson);
		} catch(Exception ex) {
			handleException("TestServiceLayer.sandwich.create", ex);
		}

		int sandwichId = extractInt(sandwichResponse, "id");
		System.out.println("Created Sandwich ID: " + sandwichId);

		// ======================
		// UPDATE SANDWICH
		// ======================
		String updateJson = "{ " + "\"id\":" + sandwichId + "," + "\"customer\":\"Robert UPDATED\","
				+ "\"bread1\":{\"id\":" + breadId + "}," + "\"pb\":{\"id\":" + pbId + "}," + "\"jelly\":{\"id\":"
				+ jellyId + "}," + "\"bread2\":{\"id\":" + breadId + "}," + "\"totalCost\":" + totalCost + "}";

		String updateResponse = "";
		try {
			updateResponse = put(client, BASE + "/sandwich", updateJson);
		} catch(Exception ex) {
			handleException("TestServiceLayer.sandwich.update", ex);
		}

		System.out.println("Updated Sandwich Response: " + updateResponse);

		// ======================
		// DELETE SANDWICH
		// ======================
		try {
			delete(client, BASE + "/sandwich/" + sandwichId);
			System.out.println("Deleted Sandwich ID: " + sandwichId);
		} catch(Exception ex) {
			handleException("TestServiceLayer.sandwich.delete", ex);
		}

		System.out.println("=== TEST COMPLETE ===");
	}

	static void TestBusinessLayer() {
		try {
			PbjBusinessManager mgr = new PbjBusinessManager();

			System.out.println("===== BUSINESS LAYER TEST =====");

			// ===================================
			// STEP 1 — CREATE RELATED OBJECTS
			// ===================================
			Bread bread = new Bread(0, "Business Bread", 70, 3.10);
			bread = mgr.saveBread(bread);

			PeanutButter pb = new PeanutButter(0, "Business PB", false, 4.00);
			pb = mgr.savePeanutButter(pb);

			Jelly jelly = new Jelly(0, "Business Jelly", "Raspberry", 3.60);
			jelly = mgr.saveJelly(jelly);

			// ===================================
			// STEP 2 — CREATE SANDWICH
			// ===================================
			PbjSandwich sandwich = new PbjSandwich(0, "Business Tester", bread, pb, jelly, bread,
					bread.getPrice() + bread.getPrice() + pb.getPrice() + jelly.getPrice());

			mgr.saveSandwich(sandwich);

			sandwich = mgr.getAllSandwiches().get(mgr.getAllSandwiches().size() - 1);

			System.out.println("\nCREATED SANDWICH:");
			System.out.println(sandwich);

			// ===================================
			// STEP 3 — UPDATE
			// ===================================
			sandwich.setCustomer("UPDATED Customer Name");
			sandwich.setTotalCost(sandwich.getTotalCost() + 1.00);

			mgr.saveSandwich(sandwich);

			PbjSandwich updated = mgr.getSandwichById(sandwich.getId());

			System.out.println("\nUPDATED SANDWICH:");
			System.out.println(updated);

			// ===================================
			// STEP 4 — DELETE
			// ===================================
			mgr.deleteSandwich(updated.getId());

			PbjSandwich deletedCheck = mgr.getSandwichById(updated.getId());

			System.out.println("\nDELETED SANDWICH CHECK:");
			System.out.println(deletedCheck == null ? "Delete Successful" : "Delete Failed");

			System.out.println("\n===== TEST COMPLETE =====");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static void TestDataLayer() {

		try {
			BreadDAO breadDAO = new BreadDAO();
			PeanutButterDAO pbDAO = new PeanutButterDAO();
			JellyDAO jellyDAO = new JellyDAO();
			PbjSandwichDAO sandwichDAO = new PbjSandwichDAO();

			System.out.println("===== PBJ DAO TEST HARNESS =====");

			// -----------------------------
			// CREATE TEST DATA
			// -----------------------------
			System.out.println("\n--- Creating Records ---");

			Bread bread = new Bread(0, "Test Bread Brand", 60, 3.25);
			bread = breadDAO.create(bread);
			System.out.println("Created bread " + bread.getId());

			PeanutButter pb = new PeanutButter(0, "Test PB Brand", true, 4.10);
			pb = pbDAO.create(pb);
			System.out.println("Created PB " + pb.getId());

			Jelly jelly = new Jelly(0, "Test Jelly Brand", "Blueberry", 3.50);
			jelly = jellyDAO.create(jelly);
			System.out.println("Created jelly " + jelly.getId());

			System.out.println("Created Bread / PB / Jelly");

			// Pull latest inserted records (simple approach: readAll and get last)
			Bread breadFromDb = getLast(breadDAO.readAll());
			PeanutButter pbFromDb = getLast(pbDAO.readAll());
			Jelly jellyFromDb = getLast(jellyDAO.readAll());

			// Create Sandwich
			PbjSandwich sandwich = new PbjSandwich(0, "Console Tester", breadFromDb, pbFromDb, jellyFromDb, breadFromDb,
					breadFromDb.getPrice() + pbFromDb.getPrice() + jellyFromDb.getPrice() + breadFromDb.getPrice());

			sandwich = sandwichDAO.create(sandwich);
			System.out.println("Created Sandwich " + sandwich.getId());

			// -----------------------------
			// READ ALL
			// -----------------------------
			System.out.println("\n--- Reading All Tables ---");

			printList("Bread", breadDAO.readAll());
			printList("Peanut Butter", pbDAO.readAll());
			printList("Jelly", jellyDAO.readAll());
			printList("Sandwich", sandwichDAO.readAll());

			// -----------------------------
			// READ BY ID
			// -----------------------------
			System.out.println("\n--- Read By ID ---");

			Bread breadOne = breadDAO.readById(breadFromDb.getId());
			System.out.println("Bread By ID: " + breadOne.getBrand());

			// -----------------------------
			// UPDATE
			// -----------------------------
			System.out.println("\n--- Updating Records ---");

			breadOne.setBrand("UPDATED Bread Brand");
			breadDAO.update(breadOne);

			Bread updatedBread = breadDAO.readById(breadOne.getId());
			System.out.println("Updated Bread Brand: " + updatedBread.getBrand());

			// -----------------------------
			// DELETE TEST (Optional - Comment Out If Needed)
			// -----------------------------
			System.out.println("\n--- Delete Test ---");

			// Delete last sandwich
			PbjSandwich lastSandwich = getLast(sandwichDAO.readAll());
			sandwichDAO.delete(lastSandwich.getId());
			System.out.println("Deleted Sandwich ID: " + lastSandwich.getId());

			System.out.println("\n===== TEST COMPLETE =====");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// -----------------------------
	// Helper Methods
	// -----------------------------
	private static <T> T getLast(List<T> list) {
		if (list == null || list.isEmpty())
			return null;
		return list.get(list.size() - 1);
	}

	private static <T> void printList(String label, List<T> list) {
		System.out.println(label + " Count = " + list.size());
		for (T item : list) {
			System.out.println(item.toString());
		}
		System.out.println();
	}

	static String post(HttpClient client, String url, String json) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	static String put(HttpClient client, String url, String json) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.PUT(HttpRequest.BodyPublishers.ofString(json)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	static String get(HttpClient client, String url) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() >= 400)
			throw new RuntimeException("GET failed: " + response.statusCode());

		return response.body();
	}

	static void delete(HttpClient client, String url) throws Exception {

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).DELETE().build();

		client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	static int extractInt(String json, String field) {

        String search = "\"" + field + "\":";
        int start = json.indexOf(search) + search.length();

        int end = json.indexOf(",", start);
        if (end == -1)
            end = json.indexOf("}", start);

        return Integer.parseInt(json.substring(start, end).trim());
    }
	
	static void handleException(String codeLoc, Exception ex) {
		System.out.println("");
		System.out.println("======================================");
		System.out.println("Exception in PbjConsoleTest");
		System.out.println("\tMethod/function: " + codeLoc);
		System.out.println(ex.getMessage());
		System.out.println(ex.getStackTrace());
		System.out.println("======================================");
		System.out.println("");
	}
}
