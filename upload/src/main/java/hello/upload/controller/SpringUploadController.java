package hello.upload.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/spring")
public class SpringUploadController {

	@Value("${file.dir}")
	private String fileDir;

	@GetMapping("/upload")
	public String newFile() {
		log.info("request /servlet/v1/upload");
		return "upload-form";
	}

	@PostMapping("/upload")
	public String saveFile(@RequestParam String itemName,
		@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {

		log.info("request={}", request);
		log.info("itemName={}", itemName);
		log.info("multipartFile={}", file);

		if (!file.isEmpty()) {
			String path = fileDir + file.getOriginalFilename();
			log.info("file path={}", path);
			file.transferTo(new File(path));
		}

		return "upload-form";
	}
}
