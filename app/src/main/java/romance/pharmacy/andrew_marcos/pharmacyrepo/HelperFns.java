package romance.pharmacy.andrew_marcos.pharmacyrepo;

/**
 * Created by andre on 14-Mar-17.
 */

public class HelperFns {


    public Boolean isOnline() {
        try {
            Process p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }


}
