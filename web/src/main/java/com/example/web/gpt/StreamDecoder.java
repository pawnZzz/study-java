package com.example.web.gpt;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * @author zyc
 */
@Slf4j
public class StreamDecoder {
    private CharsetDecoder decoder;
    private ByteBuffer byteBuffer;

    public StreamDecoder(int bufferSize) {
        this.decoder = Charset.forName("UTF-8").newDecoder();
        this.byteBuffer = ByteBuffer.allocate(bufferSize);
    }

    public String decode(byte[] bytes, int bytesRead) {
        log.info("byteBuffer size: {}", byteBuffer.position());
        log.info("bytesRead size: {}", bytesRead);
        byteBuffer.put(bytes, 0, bytesRead);
        byteBuffer.flip();

        StringBuilder sb = new StringBuilder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        while (byteBuffer.hasRemaining()) {
            try {
                CoderResult coderResult = decoder.decode(byteBuffer, charBuffer, false);
                if (coderResult.isOverflow()) {
                    charBuffer.flip();
                    sb.append(charBuffer);
                    charBuffer.clear();
                } else if (coderResult.isUnderflow()) {
                    break;
                } else if (coderResult.isError()) {
                    // Handle decoding error
                    break;
                }
            } catch (Exception e) {
                // Handle decoding exception
                break;
            }
        }

        byteBuffer.compact();

        return sb.toString();
    }

    public String flush() {
        byteBuffer.flip();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        StringBuilder sb = new StringBuilder();
        try {
            decoder.decode(byteBuffer, charBuffer, true);
            decoder.flush(charBuffer);
            charBuffer.flip();
            sb.append(charBuffer);
        } catch (Exception e) {
            // Handle decoding exception
        }

        byteBuffer.clear();

        return sb.toString();
    }
}
