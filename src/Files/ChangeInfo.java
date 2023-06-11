package Files;

/**
 * @author M.Advand
 * @version 0.1.2
 * An interface which able us to search,edit and delete data from a file
 */

public interface  ChangeInfo {

    Object search(String id);
    Object edit();
    String delete(String id);

}
