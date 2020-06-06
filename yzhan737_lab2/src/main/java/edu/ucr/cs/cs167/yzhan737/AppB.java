package edu.ucr.cs.cs167.yzhan737;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.util.Random;

public class AppB {
    public static void main(String[] args) throws IOException {
        Path input = new Path(args[0]);
        FileSystem inputfile = input.getFileSystem(new Configuration());
        if (!inputfile.exists(input)) {
            System.err.println("FileNotFoundException: " + args[0] + " (No such file or directory)");
            System.exit(-1);
        }
        long start = System.nanoTime();
        FSDataInputStream in = inputfile.open(input);
        byte[] buffer = new byte[8192];
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            in.seek(random.nextInt() & Integer.MAX_VALUE);
            in.read(buffer);
        }
        in.close();
        long end = System.nanoTime();
        System.out.printf("%.6f s", (double) (end - start) / 1000000000);
    }
}
