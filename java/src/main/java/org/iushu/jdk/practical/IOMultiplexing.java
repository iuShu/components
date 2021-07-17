package org.iushu.jdk.practical;

import org.iushu.jdk.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.lang.Thread.State.TERMINATED;

public class IOMultiplexing {

    /**
     * block until it found an event is ready
     * but it doesn't know which socket are ready
     * traversal to all socket to find out which is ready
     * <p>
     * be subjected to fd size (default 1024)
     * kernel requires copy data from system space to user space
     */
    static void select() {

    }

    /**
     * not much different from select()
     * no limited to fd size due to its link list structure
     * kernel requires copy data from system space to user space
     * level trigger: report again if a previous event not being handled
     */
    static void poll() {

    }

    /**
     * block until it found an event is ready
     * it can recognize which socket are ready
     * no need the traversal compared to select()
     * no limited to fd size
     * allocates a shared space for both kernel and user
     * <p>
     * supports level and edge trigger
     * rim trigger: report which socket are ready and only report once
     */
    static void epoll() {

    }

    /**
     * Blocking-IO in jdk (half async)
     * blocking at ServerSocket#accept()
     * delegate socket handling at other thread after accepted a client connected (or thread pool)
     * <p>
     * exchange data by In/OutputStream
     *
     * @see ServerSocket#accept()
     * @see Socket#connect(SocketAddress)
     */
    static void bio() {
        Runnable server = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(5889);
                Socket client = serverSocket.accept();
                System.out.println("s accept client");
                if (client.isConnected()) {
                    OutputStream out = client.getOutputStream();
                    out.write("message from server".getBytes());
                    out.flush();
                    System.out.println("s write over");
                    Utils.sleep(500);

                    InputStream in = client.getInputStream();
                    byte[] buffer = new byte[in.available()];
                    int size = in.read(buffer);
                    System.out.println("s rec: " + size + " " + new String(buffer));
                }
                Utils.sleep(2000);
                System.out.println("s conn: " + client.isConnected());
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Runnable client = () -> {
            try {
                Utils.sleep(3000);
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(5889));
                if (socket.isConnected()) {
                    OutputStream out = socket.getOutputStream();
                    out.write("ping".getBytes());
                    out.flush();
                    System.out.println("c write over");
                    Utils.sleep(500);

                    InputStream in = socket.getInputStream();
                    byte[] buffer = new byte[in.available()];
                    int size = in.read(buffer);
                    System.out.println("c rec: " + size + " " + new String(buffer));
                }
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread s = new Thread(server, "server");
        Thread c = new Thread(client, "client");
        Runnable inspector = () -> {
            while (true) {
                System.out.println(s.getName() + " " + s.getState());
                System.out.println(c.getName() + " " + c.getState());
                System.out.println("-----------------------------------------");
                Utils.sleep(500);
                if (s.getState() == TERMINATED && c.getState() == TERMINATED)
                    break;
            }
        };
        s.start();
        c.start();
        new Thread(inspector, "inspector").start();
    }

    /**
     * Non-Blocking-IO in jdk
     *
     * @see #select()
     * @see java.nio.channels.Selector
     * @see java.nio.channels.ServerSocketChannel
     * @see java.nio.ByteBuffer
     * @since 1.4
     */
    static void nio() {
        int maxConn = 1024;
        Runnable server = () -> {
            try {
                Selector selector = Selector.open();
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(new InetSocketAddress(5889), maxConn);
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

                while (true) {
                    selector.select(500);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (!key.isValid())
                            continue;

                        if (key.isAcceptable()) {
                            ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                            SocketChannel socketChannel = channel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            socketChannel.write(ByteBuffer.wrap("message".getBytes()));
                            System.out.println("s accept and write");
                        }
                        if (key.isReadable()) {
                            System.out.println("s read event");
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            if (channel.read(buffer) > 0) {
                                buffer.flip();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);
                                System.out.println("s rec: " + new String(bytes));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Runnable client = () -> {
            try {
                Selector selector = Selector.open();
                SocketChannel channel = SocketChannel.open();
                channel.configureBlocking(false);
                Utils.sleep(2000);

                if (channel.connect(new InetSocketAddress(5889))) {
                    channel.register(selector, SelectionKey.OP_READ);
                    channel.write(ByteBuffer.wrap("ping".getBytes()));
                    System.out.println("c conn and write");
                }
                else
                    channel.register(selector, SelectionKey.OP_CONNECT);

                // exchange
                while (true) {
                    selector.select(500);
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (!key.isValid())
                            continue;

                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        if (key.isConnectable()) {
                            if (socketChannel.finishConnect()) {
                                socketChannel.register(selector, SelectionKey.OP_READ);
                                socketChannel.write(ByteBuffer.wrap("ping2".getBytes()));
                                System.out.println("c conn and write");
                            }
                            else
                                System.out.println("c can not connect");
                        }
                        if (key.isReadable()) {
                            System.out.println("c read event");
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            if (socketChannel.read(buffer) > 0) {
                                buffer.flip();
                                byte[] bytes = new byte[buffer.remaining()];
                                buffer.get(bytes);
                                System.out.println("c rec: " + new String(bytes));
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        Thread st = new Thread(server, "st");
        Thread ct = new Thread(client, "ct");
        Runnable inspector = () -> {
            while (true) {
                System.out.println(st.getName() + " " + st.getState());
                System.out.println(ct.getName() + " " + ct.getState());
                System.out.println("-----------------------------------------");
                Utils.sleep(500);
                if (st.getState() == TERMINATED && ct.getState() == TERMINATED)
                    break;
            }
        };
        new Thread(inspector, "inspector").start();
        st.start();
        ct.start();
    }

    /**
     * Asynchronous-IO in jdk (NIO 2.0)
     *
     * @see java.util.concurrent.Future
     * @see java.nio.channels.CompletionHandler
     * @since 1.7
     */
    static void aio() {
        SocketAddress address = new InetSocketAddress("127.0.0.1", 5889);
        Runnable server = () -> {
            try {
                AsynchronousServerSocketChannel serverChannel = AsynchronousServerSocketChannel.open().bind(address);
                serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Object>() {
                    @Override
                    public void completed(AsynchronousSocketChannel channel, Object attachment) {
                        try {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            Future<Integer> future = channel.read(buffer);
                            System.out.println("s accept: " + channel.getRemoteAddress());
                            if (future.get() < 0)   // wait read over
                                return;

                            buffer.flip();
                            byte[] bytes = new byte[buffer.remaining()];
                            buffer.get(bytes);
                            System.out.println("s rec: " + new String(bytes));
                            channel.write(ByteBuffer.wrap("ack".getBytes()));
                        } catch (InterruptedException | ExecutionException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        System.out.println("s err: " + exc.getClass().getName());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Runnable client = () -> {
            try {
                AsynchronousSocketChannel socketChannel = AsynchronousSocketChannel.open();
                Future<Void> f = socketChannel.connect(address);
                System.out.println("c conn from " + socketChannel.getLocalAddress() + " to " + address);
                f.get();
                socketChannel.write(ByteBuffer.wrap("ping".getBytes()));
                Utils.sleep(500);
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                Future<Integer> future = socketChannel.read(buffer);
                if (future.get() < 0)   // wait read over
                    return;

                buffer.flip();
                byte[] bytes = new byte[buffer.remaining()];
                buffer.get(bytes);
                System.out.println("c rec: " + new String(bytes));
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };
        new Thread(server, "server").start();
        new Thread(client, "client").start();
    }

    public static void main(String[] args) {
//        bio();
//        nio();
        aio();
    }

}
