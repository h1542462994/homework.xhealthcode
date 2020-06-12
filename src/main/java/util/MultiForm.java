package util;

import ext.Tuple;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.http.HttpServletRequest;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class MultiForm implements Closeable {
    private HashMap<String,Tuple<String, InputStream>> streams = new HashMap<>();

    public HashMap<String, Tuple<String, InputStream>> getStreams() {
        return streams;
    }

    public void setStreams(HashMap<String, Tuple<String, InputStream>> streams) {
        this.streams = streams;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }

    private HashMap<String, String> params = new HashMap<>();
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload fileUpload = new ServletFileUpload(factory);

    public MultiForm(HttpServletRequest request){
        try {
            List<FileItem> fileItems = fileUpload.parseRequest(new ServletRequestContext(request));
            for(FileItem fileItem: fileItems){
                if(fileItem.isFormField()){
                    params.put(fileItem.getFieldName(), fileItem.getString());
                } else {
                    streams.put(fileItem.getFieldName(), new Tuple<>(fileItem.getName(), fileItem.getInputStream()));
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void close() throws IOException {
        for (Tuple<String, InputStream> item: streams.values()){
            item.second.close();
        }
    }
}
