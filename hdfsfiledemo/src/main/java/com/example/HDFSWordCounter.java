package com.example;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

/**
 * Created by xingbowu on 17/6/19.
 */
public class HDFSWordCounter {
    //change this to string arg in main
    public static final String inputfile = "hdfsinput.txt";
    public static final String inputmsg = "Count the amount of words in this sentence!\n";
    /**
     * @param args
     */
    public static void main(String [] args) throws IOException {

        // Create a default hadoop configuration
        Configuration config = new Configuration();
        config.set("fs.defaultFS","hdfs://10.218.133.230:9000");

        // Parse created config to the HDFS
        FileSystem fs = FileSystem.get(config);


        // Specifies a new file in HDFS.
        Path filenamePath = new Path(inputfile);

        try
        {
            // if the file already exists delete it.
            if (fs.exists(filenamePath))
            {
                //remove the file
                fs.delete(filenamePath, true);
            }

            //FSOutputStream to write the inputmsg into the HDFS file
            FSDataOutputStream fin = fs.create(filenamePath);
            fin.writeUTF(inputmsg);
            fin.close();

            //FSInputStream to read out of the filenamePath file
            FSDataInputStream fout = fs.open(filenamePath);
            String msgIn = fout.readUTF();
            //Print to screen
            System.out.println(msgIn);
            fout.close();
        }
        catch (IOException ioe)
        {
            System.err.println("IOException during operation " + ioe.toString());
            System.exit(1);
        }
    }

}
