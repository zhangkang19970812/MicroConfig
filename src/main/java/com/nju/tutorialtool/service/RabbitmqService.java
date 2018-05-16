package com.nju.tutorialtool.service;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

@Service
public class RabbitmqService {

    public void addQueue(String path, String direct) {
        File config = new File(path + "/config");
        config.mkdir();
        File queue = new File(path + "/config/RabbitConfig.java");
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            queue.createNewFile();
            fw = new FileWriter(queue);
            writer = new BufferedWriter(fw);
            String content = "package " + direct + ".config;\n" +
                    "\n" +
                    "import org.springframework.amqp.core.Queue;\n" +
                    "import org.springframework.context.annotation.Bean;\n" +
                    "import org.springframework.context.annotation.Configuration;\n" +
                    "\n" +
                    "@Configuration\n" +
                    "public class RabbitConfig {\n" +
                    "    @Bean\n" +
                    "    public Queue helloQueue() {\n" +
                    "        return new Queue(\"hello\");\n" +
                    "    }\n" +
                    "}";
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addSender(String path, String direct) {
        File config = new File(path + "/sender");
        config.mkdir();
        File sender = new File(path + "/sender/Sender.java");
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            sender.createNewFile();
            fw = new FileWriter(sender);
            writer = new BufferedWriter(fw);
            String content = "package " + direct + ".config;\n" +
                    "\n" +
                    "import org.springframework.amqp.core.AmqpTemplate;\n" +
                    "import org.springframework.beans.factory.annotation.Autowired;\n" +
                    "import org.springframework.stereotype.Component;\n" +
                    "\n" +
                    "import java.util.Date;\n" +
                    "\n" +
                    "@Component\n" +
                    "public class Sender {\n" +
                    "    @Autowired\n" +
                    "    private AmqpTemplate rabbitTemplate;\n" +
                    "\n" +
                    "    public void send() {\n" +
                    "        String msg = \"hello receiver:\"+new Date();\n" +
                    "        System.out.println(\"Sender:\"+msg);\n" +
                    "        this.rabbitTemplate.convertAndSend(\"hello\", msg);\n" +
                    "    }\n" +
                    "}";
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRecevier(String path, String direct) {
        File config = new File(path + "/receiver");
        config.mkdir();
        File receiver = new File(path + "/receiver/Receiver.java");
        FileWriter fw = null;
        BufferedWriter writer = null;
        try {
            receiver.createNewFile();
            fw = new FileWriter(receiver);
            writer = new BufferedWriter(fw);
            String content = "package " + direct + ".config;\n" +
                    "\n" +
                    "import org.springframework.amqp.rabbit.annotation.RabbitHandler;\n" +
                    "import org.springframework.amqp.rabbit.annotation.RabbitListener;\n" +
                    "import org.springframework.stereotype.Component;\n" +
                    "\n" +
                    "@Component\n" +
                    "@RabbitListener(queues = \"hello\")\n" +
                    "\n" +
                    "public class Receiver {\n" +
                    "    @RabbitHandler\n" +
                    "    public void process(String msg) {\n" +
                    "        System.out.println(\"Receiver:\"+msg);\n" +
                    "    }\n" +
                    "}";
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String find(String location) {

        String temp = location + "/src/main/java";
        File file = new File(temp);

        boolean isEnd = true;

        while (isEnd) {

            File[] files1 = file.listFiles();
            for (File f : files1) {
                if (f.isFile() && Pattern.matches(".*java", f.getName())) {
                    return file.getAbsolutePath();
                }
            }
            file = files1[0];
        }

        return temp;

    }

}
