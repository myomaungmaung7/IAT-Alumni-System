package iat.alumni.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import org.springframework.web.multipart.MultipartFile;

public class DummyMultipartFile {
	public static MultipartFile create(String filename, String contentType, String content) throws IOException {
        byte[] contentBytes = content.getBytes();
        return new MultipartFile() {
            @Override
            public String getName() {
                return filename;
            }

            @Override
            public String getOriginalFilename() {
                return filename;
            }

            @Override
            public String getContentType() {
                return contentType;
            }

            @Override
            public boolean isEmpty() {
                return contentBytes.length == 0;
            }

            @Override
            public long getSize() {
                return contentBytes.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return contentBytes;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(contentBytes);
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
            	Files.write(dest.toPath(), contentBytes);
            }
        };
    }
}
