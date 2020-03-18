package com.heart.postman.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

/**
 * @ClassName: Base64Utils
 * @Description: Base64编码图片与MultipartFile文件的转换工具类
 * @Author: lwf14
 * @Date: 2020/3/18 15:54
 * @Version: v1.0
 */
public class Base64Utils {

    /**
     * MultipartFile文件转Base64编码字符串
     *
     * @param multipartFile
     * @return
     */
    public static String multipartFileToBase64(MultipartFile multipartFile) {
        byte[] bytes = null;
        try {
            bytes = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Base64.Encoder encoder = Base64.getEncoder();
        String encode = encoder.encodeToString(bytes);
        String originalFilename = multipartFile.getOriginalFilename();
        String prefix = "data:image/" + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String type = ";base64,";
        return prefix + type + encode;
    }

    /**
     * Base64编码字符串转MultipartFile文件
     *
     * @param base64str
     * @return
     */
    public static MultipartFile base64ToMultipartFile(String base64str) {

        String[] split = base64str.split(",");
        String header = split[0];
        String image = split[1];
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(image);
        for (int i = 0; i < decode.length; i++) {
            if (decode[i] < 0) {
                decode[i] += 256;
            }
        }
        return Base64Decoder.multipartFile(decode, header);
    }

    private static class Base64Decoder implements MultipartFile {

        private final byte[] image;

        private final String header;

        private final String imageName = UUID.randomUUID().toString().replace("-", "").toLowerCase();

        private Base64Decoder(byte[] image, String header) {
            this.image = image;
            this.header = header.split(";")[0];
        }

        public static MultipartFile multipartFile(byte[] image, String header) {
            return new Base64Decoder(image, header);
        }

        @Override
        public String getName() {
            return imageName + "." + header.split("/")[1];
        }

        @Override
        public String getOriginalFilename() {
            //base64解码转化成multipartFile后，无法获取原文件名
            return getName();
        }

        @Override
        public String getContentType() {
            return header.split(":")[1];
        }

        @Override
        public boolean isEmpty() {
            return image == null || image.length == 0;
        }

        @Override
        public long getSize() {
            return image.length;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return image;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(image);
        }

        @Override
        public void transferTo(File file) throws IOException, IllegalStateException {
            new FileOutputStream(file).write(image);
        }
    }
}
