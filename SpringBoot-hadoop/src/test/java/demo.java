
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class demo {


    /**
     * 遍历HDFS中所有的文件
     * @throws IOException
     */
    @Test
    public void listFiles() throws URISyntaxException, IOException {
        //获取Filesystem
        FileSystem fileSystem = FileSystem.get(new URI ("hdfs://node1:8020"), new Configuration());
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(new Path("/"), true);//递归
        while (locatedFileStatusRemoteIterator.hasNext()){
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            Path path = next.getPath();
            System.out.println(path.toString());
        }

    }




    @Test
    public void fileSystem() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFs", "hdfs://node1:8020");
        FileSystem fileSystem = FileSystem.get(configuration);
        System.out.println(fileSystem.toString());
    }


    @Test
    public void fileSystem2() throws IOException, URISyntaxException {
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"), new Configuration());
        System.out.println(fileSystem + "");
    }

    @Test
    public void fileSystem3() throws IOException {
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", "hdfs://node");
        FileSystem fileSystem = FileSystem.newInstance(configuration);
        System.out.println(fileSystem);
    }

    @Test
    public void downloadFile() throws URISyntaxException, IOException {
        //获取Filesystem
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"), new Configuration());
        //获取hdfs输入流
        FSDataInputStream inputSteam = fileSystem.open(new Path("/a.txt"));
        //获取本地路径的输出流
        FileOutputStream fileOutputStream = new FileOutputStream("D://a.txt");
        //文件的拷贝
        IOUtils.copy(inputSteam, fileOutputStream);
        //关闭流
        IOUtils.closeQuietly(inputSteam);
        IOUtils.closeQuietly(fileOutputStream);
        fileOutputStream.close();
    }


    @Test
    public void downloadFile2() throws URISyntaxException, IOException, InterruptedException {
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"), new Configuration(), "root");
        fileSystem.copyToLocalFile(new Path("/a.txt"), new Path("D://a.txt"));
        fileSystem.close();
    }

    @Test
    public void uploadFile() throws URISyntaxException, IOException {
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"), new Configuration());
        fileSystem.copyFromLocalFile(new Path("/wordcount/wordcount.txt"), new Path("D://a.txt"));
        fileSystem.close();
    }

    /*
      hdfs 访问权限控制
     */


    /**
     * 小文件的合并
     * @throws URISyntaxException
     * @throws IOException
     */

    @Test
    public void littleFileToBig() throws URISyntaxException, IOException {
        //获取文件系统
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://node1:8020"), new Configuration());
        //获取hdfs大文件输出流
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/big.txt"));
        //获取本地文件夹下所有文件的详情
        LocalFileSystem local = FileSystem.getLocal(new Configuration());
        //遍历每个文件，获取每个文件的的输入流
        FileStatus[] fileStatuses = local.listStatus(new Path("D:\\ipnut"));
        //将小文件复制到大文件
        for(FileStatus fileStatus:fileStatuses){
            FSDataInputStream open = local.open(fileStatus.getPath());
            IOUtils.copy(open,fsDataOutputStream);
            IOUtils.closeQuietly(open);
        }
        //关闭流
    }




}
