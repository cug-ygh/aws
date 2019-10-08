package work.mywork;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
/**
 * Hello world!
 *
 */
public class send_message 
{
	static String url;
	static AmazonSQS sqs;
	static List<Message>messages;
	static Date date;
	static String str;
	static int num=0;
	static JTextArea textArea1=new JTextArea();
	static JTextArea textArea2=new JTextArea();
    //ArrayList
	public static void init() {
		//创建对象
		sqs = AmazonSQSClientBuilder.standard().withRegion(Regions.US_EAST_1).build();
		//代码创建队列
//		CreateQueueRequest create_request = new CreateQueueRequest("queue");
//		sqs.createQueue(create_request);
//		url=sqs.getQueueUrl("queue").getQueueUrl();
		//手动创建
		url="https://sqs.us-east-1.amazonaws.com/545058511834/MyQueue";
		date=new Date();
		//System.out.println(date.toString()+":连接亚马逊云服务\n");
		textArea1.append(date.toString()+":连接亚马逊云服务\n");
		sendwindow();
	}
	public static void sendmessage() throws IOException 
	{
		 str=textArea2.getText();
		 System.out.println(str);
		//控制台版本
		/*Scanner sc = new Scanner(System.in);
		while(!sc.hasNext("eof")){// 通过判断in.hasNext()的参数
			 str = sc.nextLine();
			 if(str.equals("quit")) {
					
					sc.close();
					System.out.println("---输入完毕---\n");
					break;
			}
		*/
			 date=new Date();
			 //System.out.println(str);
				SendMessageRequest send_msg_request = new SendMessageRequest()
				        .withQueueUrl(url)
				        .withMessageBody(date.toString()+"  "+str)
				        .withDelaySeconds(5);
				sqs.sendMessage(send_msg_request);
				//num++;
				//System.out.println("消息"+num+"发送完成");
		
				textArea2.setText("");
				textArea1.append(date.toString()+"\n");
				textArea1.append(str+"\n");
				//保存到txt文件中
				File fp=new File("D:\\Users\\Administrator\\eclipse-workspace\\mywork\\src\\main\\java\\work\\mywork\\message.txt");
				FileOutputStream fos = null;
				PrintStream ps = null;
				try {			
					fos = new FileOutputStream(fp,true);// 文件输出流		
					ps = new PrintStream(fos);		
					} 
				catch (FileNotFoundException e) {
					e.printStackTrace();	
					}
				System.out.println(date.toString()+"\r\n"+str+"\r\n");
				ps.print(date.toString()+"\r\n"+str+"\r\n");
				ps.close();
				System.out.println("1");
		//num=0;
		
	}
	@SuppressWarnings("deprecation")
	public static void sendwindow() {
		JFrame jframe=new JFrame("发送窗口");
		Container contenpane=jframe.getContentPane();
		JPanel panel=new JPanel();
		panel.setLayout(null);
	    
		//设置一个发送消息的窗口
		//显示框
		textArea1.setEditable(false);
		textArea1.setFont(new Font("宋体",Font.PLAIN,16));
		textArea1.setLineWrap(true);
		JScrollPane up=new JScrollPane(textArea1,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		up.setBounds(0, 20, 460, 280);
		panel.add(up);
	
		//输入框
		
		textArea2.setFont(new Font("宋体",Font.PLAIN,16));
		textArea2.setLineWrap(true);
		JScrollPane down=new JScrollPane(textArea2,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		down.setBounds(0, 320, 460, 100);
		panel.add(down);
		
		//发送按钮
		JButton botton1=new JButton();
		botton1.setBounds(400, 430,80,30);
		botton1.setLabel("发送");
		panel.add(botton1);
		
		botton1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					sendmessage();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		contenpane.add(panel);
		jframe.setSize(646,516);
		jframe.setVisible(true);
		
	}
    public static void main( String[] args ) throws IOException
    {
    	
        init();
   
        
    }
}
