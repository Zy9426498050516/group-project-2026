package org.xiaolanjing.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * 文件上传工具类
 */
public class FileUtil {

    /**
     * 保存上传的图片，返回可在浏览器访问的地址（例如 /upload/abc.png）
     *
     * @param file      上传的文件
     * @param uploadDir 保存目录
     * @return 图片访问地址；如果文件为空返回 null
     */
    public static String saveImage(MultipartFile file, String uploadDir) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String original = file.getOriginalFilename();
        String ext = "";
        if (original != null && original.contains(".")) {
            ext = original.substring(original.lastIndexOf("."));
        }
        // 用 UUID 生成唯一文件名，避免重名覆盖
        String filename = UUID.randomUUID().toString().replace("-", "") + ext;
        // 转成绝对路径：否则 transferTo 底层会把相对路径解析到临时目录，导致文件存错位置
        File dir = new File(uploadDir).getAbsoluteFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        file.transferTo(new File(dir, filename).getAbsoluteFile());
        return "/upload/" + filename;
    }
}
