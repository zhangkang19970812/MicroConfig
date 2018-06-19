package com.nju.tutorialtool.controller;

import com.nju.tutorialtool.service.DownLoadProjectService;
import com.nju.tutorialtool.util.enums.BaseDirConstant;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class DownloadController {
    @Autowired
    private DownLoadProjectService downLoadProjectService;

//    @RequestMapping(value = "/download", method = RequestMethod.GET)
//    ResponseEntity<InputStreamResource> downloadFile() throws Exception {
//        InputStream inputStream = downLoadProjectService.downloadProject(new ServerInfo("114.115.137.102", "root", "stk0123STK0123", "", ""), "account_service", "H:/programs/spark");
////        FileSystemResource file = new FileSystemResource(filePath);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", "account_service.zip"));
//        headers.add("Pragma", "no-cache");
//        headers.add("Expires", "0");
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        InputStreamResource inputStreamResource = objectMapper.readValue(inputStream, InputStreamResource.class);
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .contentLength(inputStream.available())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(inputStreamResource);
//    }

    @RequestMapping(value="/download", method=RequestMethod.GET)
    public void getDownload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        InputStream inputStream = downLoadProjectService.downloadProjects();
        ServletContext context = request.getServletContext();

        // get MIME type of the file
        String mimeType = "application/octet-stream";
        System.out.println("context getMimeType is null");

        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
//        response.setContentLength(inputStream.available());


        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", BaseDirConstant.zipName);
        response.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try {
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
