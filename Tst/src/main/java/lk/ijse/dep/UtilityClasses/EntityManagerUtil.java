package lk.ijse.dep.UtilityClasses;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EntityManagerUtil {

    private static EntityManagerUtil entityManagerUtil;

    private EntityManagerUtil(){

    }

    public static EntityManagerUtil getInstance(){
        entityManagerUtil = new EntityManagerUtil();
        return entityManagerUtil;
    }

    private EntityManagerFactory getFactory (){
        File file = new File("Tst/resources/application.properties");
        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader(file);
            properties.load(fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit1",properties);

        return entityManagerFactory;

    }
    public EntityManager getManager(){
        EntityManagerFactory factory = entityManagerUtil.getFactory();
        EntityManager entityManager = factory.createEntityManager();
        return entityManager;
    }

}
