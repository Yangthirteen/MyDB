package top.mydb.transport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class Transporter {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Transporter(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    // 发送包
    public void send(byte[] data) throws Exception {
        String raw = hexEncode(data);
        writer.write(raw);
        writer.flush();
    }

    // 接收包
    public byte[] receive() throws Exception {
        String line = reader.readLine();
        if(line == null) {
            close();
        }
        return hexDecode(line);
    }

    // 关闭传输
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

    // 避免特殊字符造成问题，这里会将数据转成十六进制字符串（Hex String），并为信息末尾加上换行符
    // 可以很简单地使用 BufferedReader 和 Writer 来直接按行读写了
    private String hexEncode(byte[] buf) {
        return Hex.encodeHexString(buf, true)+"\n";
    }

    private byte[] hexDecode(String buf) throws DecoderException {
        return Hex.decodeHex(buf);
    }
}
