package test.runtime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.protobuf.LazyField;
import ext.ServiceContainerBase;
import ext.exception.OperationFailedException;
import ext.exception.ServiceConstructException;
import ext.sql.DbContextBase;
import ext.sql.DbSettings;
import services.DbContext;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TestHelper {
    private final ServiceContainerBase serviceContainerBase;
    private Gson gson = null;

    public TestHelper(ServiceContainerBase serviceContainerBase) {
        this.serviceContainerBase = serviceContainerBase;
    }

    /**
     * 使用内存数据库进行测试
     */
    public void storeInMemory() {
        DbSettings dbSettings = new DbSettings(
                "org.h2.Driver",
                "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", // 使用了面向memory的数据库
                "sa",
                "123456 sa"
        );
        serviceContainerBase.setConfig("dbsettings", dbSettings);
        // 使用调试模式
        serviceContainerBase.setConfig("debug", "true");
    }

    /**
     * 使用文件数据库进行测试
     * @param fileName 文件名，推荐使用test
     */
    public void storeInFile(String fileName) {
        DbSettings dbSettings = new DbSettings(
            "org.h2.Driver",
            "jdbc:h2:~/" + fileName, // 使用了面向文件的h2数据库
            "sa",
            "123456 sa"
        );
        serviceContainerBase.setConfig("dbsettings", dbSettings);
        // 使用调试模式
        serviceContainerBase.setConfig("debug", "true");
    }

    public String readFileTxt(String fileName) throws IOException {
        String filePath = ClassLoader.getSystemResource(fileName).getPath();

        InputStreamReader inputStreamReader = new InputStreamReader(
                new FileInputStream(filePath), StandardCharsets.UTF_8
        );
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuilder builder = new StringBuilder();
        String lineText;
        while ((lineText = reader.readLine()) != null) {
            builder.append(lineText);
            builder.append("\n");
        }
        return builder.toString();
    }

    public FileReader loadResource(String fileName) throws FileNotFoundException {
        String filePath = ClassLoader.getSystemResource(fileName).getPath();
        return new FileReader(filePath);
    }

    /**
     * 根据token找到对应的jsonElement,仅供测试使用
     * @param token 格式为fileName,key
     */
    public JsonElement loadResultWithToken(String token) throws FileNotFoundException {
        String[] tokens = token.split(",");
        String fileName = tokens[0];
        String e = tokens[1];
        JsonObject object = gson.fromJson(loadResource("test/results/" + fileName), JsonObject.class);
        return object.get(e);
    }

    public Gson gson() {
        if (gson == null) {
            gson = new GsonBuilder().setPrettyPrinting().create();
        }
        return gson;
    }

    private DbContext context() {
        try {
            return (DbContext) serviceContainerBase.getService(DbContextBase.class);
        } catch (ServiceConstructException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void useFile(String fileName) throws IOException, OperationFailedException {
        DbContext context = context();
        assert context != null;
        context.executeNoQuery(readFileTxt(fileName));
    }
}
