package pl.dominik.football.services;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.dominik.football.domain.entity.Image;
import pl.dominik.football.domain.repository.ImageRepository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ResourceLoader resourceLoader;

    private static String UPLOAD_ROOT = "upload-dir";

    public Page<Image> findPage(Pageable pageable) {
        return imageRepository.findAll(pageable);
    }

    public Resource findOneImage(String filename) {
        return resourceLoader.getResource("file:" + UPLOAD_ROOT + "/" + filename);
    }

    public void createImage(MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            Files.copy(file.getInputStream(), Paths.get(UPLOAD_ROOT, file.getOriginalFilename()));
            imageRepository.save(new Image(file.getOriginalFilename()));

//                imageRepository.save(new Image("thumbnail." + file.getOriginalFilename()));
                Thumbnails.of(UPLOAD_ROOT + "/" + file.getOriginalFilename())
                        .size(585, 440)
                        .toFiles(Rename.PREFIX_DOT_THUMBNAIL);
        }
    }

    public void deleteImage(String filename) throws IOException {

        final Image byName = imageRepository.findByName(filename);
        imageRepository.delete(byName);
        Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
        Files.deleteIfExists(Paths.get(UPLOAD_ROOT, "thumbnail." + filename));
    }

    @Bean
    CommandLineRunner setUp(ImageRepository imageRepository) throws IOException {

        return (args) -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

            Files.createDirectory(Paths.get(UPLOAD_ROOT));

            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/test.jpg"));
            imageRepository.save(new Image("test.jpg"));


            FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/test2.jpg"));
            imageRepository.save(new Image("test2.jpg"));


            FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/test3.jpg"));
            imageRepository.save(new Image("test3.jpg"));
        };
    }

}