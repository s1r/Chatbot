import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/*
 * Created on  2007/03/10
 * Modified on 2014/08/10
 */
public class Main {
	public static void main(String[] args) throws IOException{
		DatabaseConnection dbconn = new DatabaseConnection();

		System.out.println(dbconn.getState());

		Chatbot chatbot = new Chatbot("人工無脳2号");

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	    String input;
	    String response ;
	    Random rand;
        rand = new Random(System.currentTimeMillis());

	    while( true ){
		    // 入力メッセージを取得
	    	System.out.print("あなた: ");
	    	input = br.readLine();

	    	if ( input.length()==0 ) break;

	    	// 入力文字コード変換
	    	String tempStr = new String(input.getBytes("UTF-8"), "UTF-8");
	    	input = tempStr;

	    	// 人工無脳の反応メッセージを取得
	    	Integer rn = rand.nextInt(3);
	    	if ( rn == 0 ){
	    		response = chatbot.getResponse(input);
	    	}else if( rn == 1){
	    		response = chatbot.getResponse("名無し", input);
	    	}else{
	    		response = input;
	    	}

	    	// 出力
	    	System.out.println(chatbot.name + ": " + response);
	    }
	}
}
/*
public class Main extends JFrame {
    public Main() {
        setTitle("人工無脳2号");

        MainPanel panel = new MainPanel();
        Container contentPane = getContentPane();
        contentPane.add(panel);

        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Main();
    }
}
*/
