package work.mywork;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

public class recieve_message {
	static String url;
	static AmazonSQS sqs;
	static List<Message>messages;
	static Date date;
	static String str;
	static int num=0;
	static JTextArea textArea1=new JTextArea();
    //ArrayList
	public static void init() {
		//创建对象
		sqs = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		url="https://sqs.us-east-1.amazonaws.com/545058511834/MyQueue";
		date=new Date();
		textArea1.append(date.toString()+":连接亚马逊云服务\n");
		//System.out.println(date.toString()+":连接亚马逊云服务\n");
		
		recievewindow();
		
	}

public static void recievemessage() {
	//接收消息的对象
	
	ReceiveMessageRequest rec = new ReceiveMessageRequest(url);
	
	rec.setMaxNumberOfMessages(10);//最大接受量
	rec.withWaitTimeSeconds(20);
	messages=sqs.receiveMessage(rec).getMessages();
		//接收后删除消息
	for (Message m : messages) {
		    sqs.deleteMessage(url, m.getReceiptHandle());
		   
		}
	    for(Message m:messages) {
	    	 num++;
	    	 textArea1.append("消息"+num+":"+m.getBody()+"\n");
	    	 //System.out.println("消息"+num+":");
	    	 //System.out.print(m.getBody()+"\n");
	    }
	   
	
   //循环输出全部消息
    while(messages.size()!=0) {
		recievemessage();
		}
    

}
@SuppressWarnings("deprecation")
public static void recievewindow() {
	JFrame jframe=new JFrame("接收窗口");
	Container contenpane=jframe.getContentPane();
	JPanel panel=new JPanel();
	panel.setLayout(null);
    
	//设置一个接受消息显示的窗口
	//显示框
	textArea1.setEditable(false);
	textArea1.setFont(new Font("宋体",Font.PLAIN,16));
	textArea1.setLineWrap(true);
	JScrollPane up=new JScrollPane(textArea1,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
			ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	up.setBounds(0, 0, 280, 450);
	panel.add(up);

	//接收按钮
	JButton botton1=new JButton();
	botton1.setBounds(180, 450,100,30);
	botton1.setLabel("接收消息");
	panel.add(botton1);
	
	botton1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			textArea1.append("---开始从亚马逊云接收消息---\n");
			recievemessage();
			textArea1.append("---接收完成---共接受"+num+"条消息");
		}
	});
	
	contenpane.add(panel);
	jframe.setSize(300,540);
	jframe.setVisible(true);
	
}
public static void main( String[] args ) throws IOException
{
	
   init();
   //System.out.println("---开始从亚马逊云接收消息---\n");
  // recievemessage();
   //		System.out.println("---接收完成---共接受"+num+"条消息");
}
}
