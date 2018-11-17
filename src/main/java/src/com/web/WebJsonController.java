package src.com.web;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class WebJsonController {

	private static final Logger logger = LoggerFactory.getLogger(WebJsonController.class);

	@GetMapping("/cluster")
	public ResponseEntity<byte[]> downloadFile(HttpServletRequest request, @RequestParam(value="longitude", required = false, defaultValue = "8.476682") String longitude,
			@RequestParam(value="latitude", required = false, defaultValue = "49.483752") String latitude,
			@RequestParam(value="algorithm", required = false, defaultValue = "simple") String algorithm) {
		
		System.out.println("Request received for area (" + latitude + ", " + longitude+ ").");
    	
		
		FrontProcessor processor = new FrontProcessor();
    	processor.process(latitude, longitude, algorithm);
    	File tmpFile = GeoJsonFormatter.format(processor.getInstances(), processor.getClusters());
    	
    	
		System.out.println("-> Returning results of size "+ ((int) (tmpFile.length()  / 1024)) +"KB ...");
        //java.nio.file.Path path = Paths.get(tmpFile.getAbsolutePath());
        
		String filename = tmpFile.getName();
        byte[] tmpBytes = "{\"type\": \"FeatureCollection\",\n \"features\": []\n}".getBytes();
		try {
			tmpBytes = this.bytesFromStream(new FileInputStream(tmpFile));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tmpFile.delete();
		}
		// Try to determine file's content type
		String  contentType = "application/octet-stream";

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(tmpBytes);
	}

	public byte[] bytesFromStream(InputStream in) {

		try {
			return IOUtils.toByteArray(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
