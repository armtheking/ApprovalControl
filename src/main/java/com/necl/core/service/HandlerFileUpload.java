/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.necl.core.service;

import com.necl.core.model.ConfigSystem;
import com.necl.core.service.ConfigSystemService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 *
 * @author C13.207
 */
@Service
public class HandlerFileUpload {

    @Autowired
    ConfigSystemService configSystemService;

    public void handleFileUploadToPath(CommonsMultipartFile fileUpload, String numberTicket) throws Exception {

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);

        String keyFind = "PATH";
        ConfigSystem configSystem = configSystemService.findByKey(keyFind);
        String saveDirectory = configSystem.getConfigText() + yearInString;

//        File file = new File(saveDirectory + File.separator + convertNameAndTypeFile(fileUpload.getOriginalFilename(), numberTicket));
//        System.out.println("file: "+file);
//        file.delete();
        
        
        if (fileUpload != null && fileUpload.getSize() > 0) {
            File files = new File(saveDirectory + File.separator + convertNameAndTypeFile(fileUpload.getOriginalFilename(), numberTicket));
            if (!files.exists()) {
                files.mkdirs();
            }
            if (!numberTicket.equals("")) {
                fileUpload.transferTo(files);
            }

        }
       

        // returns to the view "Result"
    }

    private String convertNameAndTypeFile(String originName, String numberTicket) {

        String newName = numberTicket.replace("/", "_");
        String originFiletype = originName.substring(originName.lastIndexOf('.'), originName.length());

        return newName + originFiletype;
    }

}
