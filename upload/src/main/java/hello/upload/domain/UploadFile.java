package hello.upload.domain;

import lombok.Builder;
import lombok.Data;

@Data
public class UploadFile {

	private String uploadFileName;

	private String storeFileName;

	@Builder
	public UploadFile(String uploadFileName, String storeFileName) {
		this.uploadFileName = uploadFileName;
		this.storeFileName = storeFileName;
	}
}
