package com.example.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * mappedByteBuffer 可以直接在内存（堆外内存）修改，操作系统不需要拷贝一次
 * @author zyc
 */
public class MappedByteBufferDemo {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("./mappedByteBuffer.txt", "rw");

        FileChannel channel = randomAccessFile.getChannel();
        /**
         * 参数1：读写模式
         * 参数2：可以直接修改的起使位置
         * 参数3：buffer map映射的大小
         */
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        // 可能不会直接在文件体现，缓存
        map.put(0, (byte) 'H');
        map.put(3, (byte) 'a');
        map.put(5, (byte) '5'); // 越界

        randomAccessFile.close();
    }
}
