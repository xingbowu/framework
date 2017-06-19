package com.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

/**
 * Created by xingbowu on 17/6/16.
 */
public class HdfsFileSystemOperation {
    public HdfsFileSystemOperation(){

    }

    public void addFile(String src, String dest, Configuration conf) throws IOException{

        FileSystem fileSystem = FileSystem.get(conf);
        String fileName =  src.substring(src.lastIndexOf('/')+1, src.length());
        if(dest.charAt(dest.length()-1)!='/'){
            dest = dest + '/' + fileName;
        }else {
            dest = dest + fileName;
        }

        Path path = new Path(dest);
        FSDataOutputStream stream;
        if(fileSystem.exists(path)){
            fileSystem.delete(path,true);
            System.out.println("File " + dest + " already existed!");

        }
        stream = fileSystem.create(path);
        InputStream in = new BufferedInputStream(new FileInputStream(new File(src)));
        int numBytes = 0;
        byte[] b = new byte[1024];
        while((numBytes =  in.read(b))>0){
            stream.write(b, 0 ,numBytes);
        }

        in.close();
        stream.close();
        fileSystem.close();

    }


    public void readFile(String fileName, Configuration configuration) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        Path path = new Path(fileName);
        if (!fileSystem.exists(path)) {
            System.out.println("file " + fileName + " doesn't exist");
        }

        FSDataInputStream inputStream = fileSystem.open(path);
        String file = fileName.substring(fileName.lastIndexOf('/')+1,
                fileName.length());
        OutputStream out = new BufferedOutputStream( new FileOutputStream(
                new File(file)
        ));
        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes=inputStream.read(b))>0) {
            out.write(b, 0, numBytes);
        }
        inputStream.close();
        out.close();
        fileSystem.close();
    }

    public void deleteFile(String fileName, Configuration configuration) throws IOException {
            FileSystem fileSystem = FileSystem.get(configuration);

            Path path = new Path(fileName);
            if(!fileSystem.exists(path)){
                System.out.println("File " + fileName + " doesn't exist");
                return;
            }

            fileSystem.delete(path, true);
            fileSystem.close();
    }

    public void createDir(String dir, Configuration configuration) throws IOException {
        FileSystem fileSystem = FileSystem.get(configuration);
        Path path = new Path(dir);
        if(fileSystem.exists(path)){
            System.out.println("Dir " + dir + " already exist");
            return;
        }
        fileSystem.mkdirs(path);
        fileSystem.close();
    }

    public static void main(String[] args) {
        String dest = "/user/tmp/";
        String src = "/Users/xingbowu/tmp/tmp";
        Configuration conf = new Configuration ();
        conf.set("fs.defaultFS","hdfs://10.218.133.230:9000");
        HdfsFileSystemOperation client = new HdfsFileSystemOperation();
        try {
            client.addFile(src,dest,conf);
            client.readFile(dest+"tmp", conf);
            client.deleteFile(dest, conf);
            client.createDir(dest, conf);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
