package org.apache.rocketmq.example.batch;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SplitBatchProducer {


    public static void main(String[] args) throws Exception {


        int cycleIndex = 50000;  // 循环次数 这个参数是循环设置多少个参数的


        DefaultMQProducer producer = new DefaultMQProducer("BatchProducerGroupName");
        producer.setNamesrvAddr("zjj101:9876;zjj102:9876;zjj103:9876");
        producer.start();

        //large batch
        String topic = "BatchTest";
        List<Message> messages = new ArrayList<>(100 * 1000);
        // 一次10万条消息,一次发送出去肯定是超过限制了.


        for (int i = 0; i < cycleIndex; i++) {
            messages.add(new Message(topic, "Tag", "OrderID" + i, ("Hello world " + i).getBytes()));
        }
        // 直接发送出去会报错
//        producer.send(messages);


        // 如果超过大小限制了,就用下面的代码把一个大的消息进行拆分多个小的消息,然后多次发送出去
        ListSplitter splitter = new ListSplitter(messages);
        while (splitter.hasNext()) {
            List<Message> listItem = splitter.next();
            producer.send(listItem);
        }
        producer.shutdown();
    }

}

class ListSplitter implements Iterator<List<Message>> {
    // 消息
    private final List<Message> messages;
    //大小限制
    private final int sizeLimit = 1000 * 1000;
    //当前索引
    private int currIndex;

    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * 判断是否还有数据,
     * 判断逻辑: 当前索引是否小于 消息的长度
     *
     * @return
     */
    @Override
    public boolean hasNext() {
        return currIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        int nextIndex = currIndex;//当前记录的已经用过的索引
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);

            // 如何计算一个消息的大小: 就是 topic的长度加上消息body的长度,加一个自定义属性的长度 , 再加上20
            int tmpSize = getMessageSize(message);


            // 如果当前取出来的消息长度大于预先设置的sizeLimit(消息最大长度,)直接就跳出循环,然后记录下nextIndex索引位置
            if (tmpSize > sizeLimit) { // 如果消息长度超过了  sizeLimit(1百万)
                // 如果下一个索引减去当前索引为0,那么就给下一个索引进行加1,这样目的是下次循环的时候,就可以通过nextIndex属性拿取下一个索引的值
                if (nextIndex - currIndex == 0) {
                    nextIndex++;
                }
                break;
            }

            // 什么时候 多个消息累加的长度+当前取出来的消息的长度 >  sizeLimit(预先设置的消息最大长度),就执行break跳出当前循环
            //否则就接着 累加消息.
            if (tmpSize + totalSize > sizeLimit) {
                break;
            } else {

                totalSize += tmpSize;
            }

        }
        /*subList方法,通过起始索引和结束索引获取List的一部分
            参数1: 截取元素的起始位置，包含该索引位置元素
            参数2: 截取元素的结束位置，不包含该索引位置元素

         */
        List<Message> subList = messages.subList(currIndex, nextIndex);

        System.out.println("当前的currIndex是: " + currIndex + " 当前的nextIndex是" + nextIndex);

        currIndex = nextIndex;


        return subList;
    }

    /**
     * 计算消息大小
     * 如何计算一个消息的大小: 就是 topic的长度加上消息body的长度,加一个自定义属性的长度 , 再加上20
     *
     * @param message 消息
     * @return 消息长度
     */
    private int getMessageSize(Message message) {

        int tmpSize = message.getTopic().length() + message.getBody().length;
        //消息属性的长度
        Map<String, String> properties = message.getProperties();
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            tmpSize += entry.getKey().length() + entry.getValue().length();
        }

        tmpSize = tmpSize + 20; //对日志的开销
        return tmpSize;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not allowed to remove");
    }
}
