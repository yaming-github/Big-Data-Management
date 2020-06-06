package edu.ucr.cs.cs167.yzhan737;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.err.println("IllegalArgumentException");
            System.exit(-1);
        } else {
            Path input = new Path(args[0]);
            Path output = new Path(args[1]);
            FileSystem inputfile = input.getFileSystem(new Configuration());
            FileSystem outputfile = output.getFileSystem(new Configuration());
            if (!inputfile.exists(input)) {
                System.err.println("FileNotFoundException: " + args[0] + " (No such file or directory)");
                System.exit(-1);
            }
            if (outputfile.exists(output)) {
                System.err.println("FileAlreadyExistsException: Output directory " + args[1] + " already exists");
                System.exit(-1);
            }
            long start = System.nanoTime();
            FSDataInputStream in = inputfile.open(input);
            FSDataOutputStream out = outputfile.create(output);
            byte[] buffer = new byte[1024];
            int read;
            long length = 0;
            while ((read = in.read(buffer)) > 0) {
                out.write(buffer);
                length += read;
            }
            in.close();
            out.close();
            long end = System.nanoTime();
            System.out.printf("Copied %d bytes from '%s' to '%s' in %.6f seconds", length, args[0], args[1],
                    (double) (end - start) / 1000000000);
        }
    }
}
