package hansanhha.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/files")
public class FileUploadController {
    
    private final Path uploadDir = Paths.get("uploads");

    @PostMapping("/multi-upload")
    public ResponseEntity<?> handleMultiFileUpload(@RequestParam("files") List<MultipartFile> files) {
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "empty file"));
        }

        var result = new ArrayList<>();

        files.forEach(file -> {
            if (!file.isEmpty()) {
                var filename = file.getOriginalFilename();
                var dest = uploadDir.resolve(filename).normalize();
                // file.transfer(dest) // 파일 저장
                result.add("successfully uploaded: " + filename);
            } else {
                result.add("empty file");
            }
        });
        
        return ResponseEntity.ok(result);
    }
    
}
