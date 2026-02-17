import java.util.List;

public class PbjBusinessManager {

    private BreadDAO breadDAO = new BreadDAO();
    private PeanutButterDAO pbDAO = new PeanutButterDAO();
    private JellyDAO jellyDAO = new JellyDAO();
    private PbjSandwichDAO sandwichDAO = new PbjSandwichDAO();

    // ===============================
    // BREAD
    // ===============================
    public Bread saveBread(Bread bread) throws Exception {
        if (bread.getId() == 0) {
            bread = breadDAO.create(bread);
        } else {
            breadDAO.update(bread);
        }
        return bread;
    }

    public void deleteBread(int id) throws Exception {
        breadDAO.delete(id);
    }

    public Bread getBreadById(int id) throws Exception {
        return breadDAO.readById(id);
    }

    public List<Bread> getAllBread() throws Exception {
        return breadDAO.readAll();
    }

    // ===============================
    // PEANUT BUTTER
    // ===============================
    public PeanutButter savePeanutButter(PeanutButter pb) throws Exception {
        if (pb.getId() == 0) {
            pb = pbDAO.create(pb);
        } else {
            pbDAO.update(pb);
        }
        return pb;
    }

    public void deletePeanutButter(int id) throws Exception {
        pbDAO.delete(id);
    }

    public PeanutButter getPeanutButterById(int id) throws Exception {
        return pbDAO.readById(id);
    }

    public List<PeanutButter> getAllPeanutButter() throws Exception {
        return pbDAO.readAll();
    }

    // ===============================
    // JELLY
    // ===============================
    public Jelly saveJelly(Jelly jelly) throws Exception {
        if (jelly.getId() == 0) {
            jelly = jellyDAO.create(jelly);
        } else {
            jellyDAO.update(jelly);
        }
        return jelly;
    }

    public void deleteJelly(int id) throws Exception {
        jellyDAO.delete(id);
    }

    public Jelly getJellyById(int id) throws Exception {
        return jellyDAO.readById(id);
    }

    public List<Jelly> getAllJelly() throws Exception {
        return jellyDAO.readAll();
    }

    // ===============================
    // PBJ SANDWICH
    // ===============================
    public PbjSandwich saveSandwich(PbjSandwich sandwich) throws Exception {
        if (sandwich.getId() == 0) {
            sandwich = sandwichDAO.create(sandwich);
        } else {
            sandwichDAO.update(sandwich);
        }
        return sandwich;
    }

    public void deleteSandwich(int id) throws Exception {
        sandwichDAO.delete(id);
    }

    public PbjSandwich getSandwichById(int id) throws Exception {
        return sandwichDAO.readById(id);
    }

    public List<PbjSandwich> getAllSandwiches() throws Exception {
        return sandwichDAO.readAll();
    }
}
